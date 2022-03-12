package tw.com.andyawd.seenote.settingpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.bean.Note
import tw.com.andyawd.seenote.bean.Setting
import tw.com.andyawd.seenote.bean.User
import tw.com.andyawd.seenote.bean.hackmd.UserNoteListItem
import tw.com.andyawd.seenote.database.HackmdDatabaseDao
import tw.com.andyawd.seenote.database.NoteDatabaseDao
import tw.com.andyawd.seenote.database.SettingDatabaseDao
import tw.com.andyawd.seenote.http.HttpManager
import tw.com.andyawd.seenote.http.HttpResponseListener

class SettingPageViewModel(
    private val settingDataSource: SettingDatabaseDao,
    private val noteDataSource: NoteDatabaseDao,
    private val hackmdDatabaseDao: HackmdDatabaseDao,
    private val page: String,
    private val noteId: Long
) : ViewModel() {

    private var _setting = MutableLiveData<Setting?>()
    val setting: LiveData<Setting?>
        get() = _setting

    private var _note = MutableLiveData<Note?>()
    val note: LiveData<Note?>
        get() = _note

    private var _hackmdToken = MutableLiveData<String?>()

    private var _hackmdDownloadStatus = MutableLiveData<String?>()
    val hackmdDownloadStatus: LiveData<String?>
        get() = _hackmdDownloadStatus

    init {
        viewModelScope.launch {
            _setting.value = settingDataSource.getFirst()
            _hackmdToken.value = _setting.value?.user?.hackmdToken
            _hackmdDownloadStatus.value = BaseConstants.DOWNLOAD

            if (BaseConstants.WRITER_NOTE == page) {
                _note.value = noteDataSource.get(noteId)
            }
        }
    }

    fun changeSettingSize(size: Int) {
        _setting.value?.let {
            val newTextSize = it.textSize?.copy(settingPage = size)
            val newSetting = it.copy(textSize = newTextSize)
            _setting.value = newSetting
        }
    }

    fun updateSetting() {
        viewModelScope.launch {
            _setting.value?.let {
                settingDataSource.update(it)
            }
        }
    }

    fun settingHackmdToken() {
        _setting.value?.let { setting ->
            _hackmdToken.value?.let { hackmdToken ->
                val newUser = User(hackmdToken = hackmdToken)
                val newSetting = setting.copy(user = newUser)
                _setting.value = newSetting
                updateSetting()
            }
        }
    }

    fun changeHackmdToken(token: String) {
        _hackmdToken.value = token
    }

    fun insertUserNoteList(responseBody: String) {
        viewModelScope.launch {
            val list =
                Json.decodeFromString(ListSerializer(UserNoteListItem.serializer()), responseBody)
            hackmdDatabaseDao.insert(list)
            _hackmdDownloadStatus.value = BaseConstants.DOWNLOAD_DONE
        }
    }

    fun downloadNoteList() {
        viewModelScope.launch {
            _hackmdDownloadStatus.value = BaseConstants.DOWNLOADING

            setting.value?.user?.let { user ->
                HttpManager.INSTANCE.get(
                    BaseConstants.NOTES,
                    user.hackmdToken,
                    object : HttpResponseListener {
                        override fun onFailure(status: String, responseBody: String) {
                            _hackmdDownloadStatus.postValue(status)
                        }

                        override fun onSuccess(responseBody: String) {
                            insertUserNoteList(responseBody)
                        }
                    })
            }
        }
    }
}