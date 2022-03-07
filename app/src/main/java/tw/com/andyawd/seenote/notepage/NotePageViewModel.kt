package tw.com.andyawd.seenote.notepage

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.com.andyawd.seenote.bean.*
import tw.com.andyawd.seenote.database.NoteDatabaseDao
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class NotePageViewModel(
    private val noteDataSource: NoteDatabaseDao,
    private val settingDataSource: SettingDatabaseDao,
    private val application: Application
) : ViewModel() {

    private var _note = MutableLiveData<List<Note>>()
    val note: LiveData<List<Note>>
        get() = _note

    private var _setting = MutableLiveData<Setting?>()
    val setting: LiveData<Setting?>
        get() = _setting

    private val _notePageDetail = MutableLiveData<Long?>()
    val notePageDetail
        get() = _notePageDetail

    private val _searchText = MutableLiveData<String?>()
    val searchText: LiveData<String?>
        get() = _searchText

    init {
        viewModelScope.launch {
            val settingList = settingDataSource.getAll()

            if (settingList.isEmpty()) {
                val color = Color()
                settingDataSource.insert(
                    Setting(
                        textSize = TextSize(),
                        title = color,
                        content = color,
                        date = color,
                        label = color,
                        user = User()
                    )
                )
                _setting.value = settingDataSource.getFirst()
            } else {
                _setting.value = settingDataSource.getFirst()
            }

            _note.value = noteDataSource.getAll()
        }
    }

    fun onItemClicked(id: Long) {
        _notePageDetail.value = id
    }

    fun onNotePageNavigated() {
        _notePageDetail.value = null
    }

    fun changeNotePageSize(size: Int) {
        _setting.value?.let {
            val newTextSize = it.textSize?.copy(notePage = size)
            val newSetting = it.copy(textSize = newTextSize)
            _setting.value = newSetting
        }
    }

    fun updateSetting() {
        _setting.value?.let {
            viewModelScope.launch {
                settingDataSource.update(it)
            }
        }
    }

    fun inputSearchText(text: String) {
        _searchText.value = text
    }

    fun queryNote() {
        _searchText.value?.let { text ->
            viewModelScope.launch {
                _note.value = noteDataSource.getSearchText(text)
            }
        }
    }
}