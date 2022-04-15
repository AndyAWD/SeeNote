package tw.com.andyawd.seenote.notepage

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.com.andyawd.andyawdlibrary.AWDLog
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.bean.Note
import tw.com.andyawd.seenote.bean.Setting
import tw.com.andyawd.seenote.database.NoteDatabaseDao
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class NotePageViewModel(
    private val application: Application,
    private val noteDataSource: NoteDatabaseDao,
    private val settingDataSource: SettingDatabaseDao,
    private val tag: String,
) : ViewModel() {

    private var _note = MutableLiveData<List<Note>>()
    val note: LiveData<List<Note>>
        get() = _note

    private var _setting = MutableLiveData<Setting?>()
    val setting: LiveData<Setting?>
        get() = _setting

    private val _searchText = MutableLiveData<String?>()
    val searchText: LiveData<String?>
        get() = _searchText

    private val _notePageDetail = MutableLiveData<Long?>()
    val notePageDetail
        get() = _notePageDetail

    private val _tagPageDetail = MutableLiveData<String?>()
    val tagPageDetail
        get() = _tagPageDetail

    init {
        viewModelScope.launch {
            _setting.value = settingDataSource.getFirst()

            AWDLog.d("tag: $tag")

            if (BaseConstants.ALL_TAG == tag) {
                _note.value = noteDataSource.getNoteFromTag(BaseConstants.EMPTY_STRING)
            } else {
                _note.value = noteDataSource.getNoteFromTag(tag)
            }
        }
    }

    fun onItemClicked(id: Long) {
        _notePageDetail.value = id
    }

    fun onNotePageNavigated() {
        _notePageDetail.value = null
    }

    fun onTagPageClicked(tag: String) {
        _tagPageDetail.value = tag
    }

    fun onTagPageNavigated() {
        _tagPageDetail.value = null
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