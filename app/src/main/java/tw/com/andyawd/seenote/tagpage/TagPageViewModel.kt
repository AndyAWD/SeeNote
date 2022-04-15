package tw.com.andyawd.seenote.tagpage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.bean.*
import tw.com.andyawd.seenote.database.NoteDatabaseDao
import tw.com.andyawd.seenote.database.SettingDatabaseDao
import tw.com.andyawd.seenote.database.StringTypeConverter

class TagPageViewModel(
    application: Application,
    private val noteDataSource: NoteDatabaseDao,
    private val settingDataSource: SettingDatabaseDao
) : AndroidViewModel(application) {

    private var _note = MutableLiveData<List<Note>>()
    val note: LiveData<List<Note>>
        get() = _note

    private var _setting = MutableLiveData<Setting?>()
    val setting: LiveData<Setting?>
        get() = _setting

    private val _searchText = MutableLiveData<String?>()
    val searchText: LiveData<String?>
        get() = _searchText

    private var _tag = MutableLiveData<List<String>?>()
    val tag: LiveData<List<String>?>
        get() = _tag

    private val _tagPageDetail = MutableLiveData<String?>()
    val tagPageDetail
        get() = _tagPageDetail

    private val _settingPageDetail = MutableLiveData<String?>()
    val settingPageDetail
        get() = _settingPageDetail

    private val _writeNoteDetail = MutableLiveData<String?>()
    val writeNoteDetail
        get() = _writeNoteDetail

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
                        tag = color,
                        user = User()
                    )
                )
                _setting.value = settingDataSource.getFirst()
            } else {
                _setting.value = settingDataSource.getFirst()
            }

            val allTagString = StringTypeConverter().toString(noteDataSource.getAllTag())
            if (allTagString?.isNotEmpty() == true) {
                _tag.value = StringTypeConverter().fromString(allTagString)?.distinct()

            } else {
                _tag.value = null
            }
        }
    }

    fun queryTag() {
        _searchText.value?.let { text ->
            viewModelScope.launch {
                val searchTagString =
                    StringTypeConverter().toString(noteDataSource.getSearchTag(text))
                val searchTagList = StringTypeConverter().fromString(searchTagString)

                val filterSearchTagList = arrayListOf<String>()

                searchTagList?.distinct()?.forEach {
                    if (BaseConstants.INDEX_NOT_SEARCH != it.indexOf(text)) {
                        filterSearchTagList.add(it)
                    }
                }

                _tag.value = filterSearchTagList
            }
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

    fun changeTagPageSize(size: Int) {
        _setting.value?.let {
            val newTextSize = it.textSize?.copy(notePage = size)
            val newSetting = it.copy(textSize = newTextSize)
            _setting.value = newSetting
        }
    }

    fun onTagPageItemClicked(tag: String) {
        _tagPageDetail.value = tag
    }

    fun onTagPageNavigated() {
        _tagPageDetail.value = null
    }

    fun onSettingPageClicked(setting: String) {
        _settingPageDetail.value = setting
    }

    fun onSettingPageNavigated() {
        _settingPageDetail.value = null
    }

    fun onWriteNoteClicked(write: String) {
        _writeNoteDetail.value = write
    }

    fun onWriteNoteNavigated() {
        _writeNoteDetail.value = null
    }
}