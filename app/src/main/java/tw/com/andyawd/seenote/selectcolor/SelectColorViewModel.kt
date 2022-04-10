package tw.com.andyawd.seenote.selectcolor

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

class SelectColorViewModel(
    private val settingDataSource: SettingDatabaseDao,
    private val noteDataSource: NoteDatabaseDao,
    private val isFromWriteNote: Boolean,
    private val isFromTagPage: Boolean,
    private val type: String,
    private val noteId: Long
) : ViewModel() {

    private var _setting = MutableLiveData<Setting?>()
    val setting: LiveData<Setting?>
        get() = _setting

    private var _note = MutableLiveData<Note?>()
    val note: LiveData<Note?>
        get() = _note

    private var _isUpdateFinish = MutableLiveData<Boolean>()
    val isUpdateFinish: LiveData<Boolean>
        get() = _isUpdateFinish

    init {
        viewModelScope.launch {
            _setting.value = settingDataSource.getFirst()

            if (isFromWriteNote) {
                _note.value = noteDataSource.get(noteId)
            }
        }

        _isUpdateFinish.value = false
    }

    fun changeSettingSize(size: Int) {
        _setting.value?.let {
            val newTextSize = it.textSize?.copy(selectColor = size)
            val newSetting = it.copy(textSize = newTextSize)
            _setting.value = newSetting
        }
    }

    fun updateTextSize() {
        viewModelScope.launch {
            _setting.value?.let {
                settingDataSource.update(it)
            }
        }
    }

    fun updateSelectColor(color: String) {
        AWDLog.d("updateSelectColor: $color / isFromWriteNote: $isFromWriteNote / isFromTagPage: $isFromTagPage")
        viewModelScope.launch {
            if (isFromWriteNote) {
                _note.value?.let {
                    val newNote: Note
                    when (type) {
                        BaseConstants.TAG_TEXT_COLOR -> {
                            val newColor = it.tagColor?.copy(textColor = color)
                            newNote = it.copy(tagColor = newColor)
                            _note.value = newNote
                        }
                        BaseConstants.TAG_BACKGROUND_COLOR -> {
                            val newColor = it.tagColor?.copy(backgroundColor = color)
                            newNote = it.copy(tagColor = newColor)
                            _note.value = newNote
                        }
                        BaseConstants.TITLE_TEXT_COLOR -> {
                            val newColor = it.titleColor?.copy(textColor = color)
                            newNote = it.copy(titleColor = newColor)
                            _note.value = newNote
                        }
                        BaseConstants.TITLE_BACKGROUND_COLOR -> {
                            val newColor = it.titleColor?.copy(backgroundColor = color)
                            newNote = it.copy(titleColor = newColor)
                            _note.value = newNote
                        }
                        BaseConstants.CONTENT_TEXT_COLOR -> {
                            val newColor = it.contentColor?.copy(textColor = color)
                            newNote = it.copy(contentColor = newColor)
                            _note.value = newNote
                        }
                        BaseConstants.CONTENT_BACKGROUND_COLOR -> {
                            val newColor = it.contentColor?.copy(backgroundColor = color)
                            newNote = it.copy(contentColor = newColor)
                            _note.value = newNote
                        }
                        BaseConstants.DATE_TEXT_COLOR -> {
                            val newColor = it.dateColor?.copy(textColor = color)
                            newNote = it.copy(dateColor = newColor)
                            _note.value = newNote
                        }
                        BaseConstants.DATE_BACKGROUND_COLOR -> {
                            val newColor = it.dateColor?.copy(backgroundColor = color)
                            newNote = it.copy(dateColor = newColor)
                            _note.value = newNote
                        }
                        else -> {
                            newNote = it.copy()
                            _note.value = newNote
                        }
                    }
                }

                updateNoteColor()
                return@launch
            }

            if (isFromTagPage) {
                _setting.value?.let {
                    val newSetting: Setting
                    when (type) {
                        BaseConstants.TAG_TEXT_COLOR -> {
                            val newColor = it.tag?.copy(textColor = color)
                            newSetting = it.copy(tag = newColor)
                            _setting.value = newSetting
                        }
                        BaseConstants.TAG_BACKGROUND_COLOR -> {
                            val newColor = it.tag?.copy(backgroundColor = color)
                            newSetting = it.copy(tag = newColor)
                            _setting.value = newSetting
                        }
                        BaseConstants.TITLE_TEXT_COLOR -> {
                            val newColor = it.title?.copy(textColor = color)
                            newSetting = it.copy(title = newColor)
                            _setting.value = newSetting
                        }
                        BaseConstants.TITLE_BACKGROUND_COLOR -> {
                            val newColor = it.title?.copy(backgroundColor = color)
                            newSetting = it.copy(title = newColor)
                            _setting.value = newSetting
                        }
                        BaseConstants.CONTENT_TEXT_COLOR -> {
                            val newColor = it.content?.copy(textColor = color)
                            newSetting = it.copy(content = newColor)
                            _setting.value = newSetting
                        }
                        BaseConstants.CONTENT_BACKGROUND_COLOR -> {
                            val newColor = it.content?.copy(backgroundColor = color)
                            newSetting = it.copy(content = newColor)
                            _setting.value = newSetting
                        }
                        BaseConstants.DATE_TEXT_COLOR -> {
                            val newColor = it.date?.copy(textColor = color)
                            newSetting = it.copy(date = newColor)
                            _setting.value = newSetting
                        }
                        BaseConstants.DATE_BACKGROUND_COLOR -> {
                            val newColor = it.date?.copy(backgroundColor = color)
                            newSetting = it.copy(date = newColor)
                            _setting.value = newSetting
                        }
                        else -> {
                            newSetting = it.copy()
                            _setting.value = newSetting
                        }
                    }
                }

                updateSettingColor()
                return@launch
            }
        }
    }

    private fun updateSettingColor() {
        viewModelScope.launch {
            _setting.value?.let {
                settingDataSource.update(it)
                onUpdateFinish()
            }
        }
    }

    private fun updateNoteColor() {
        viewModelScope.launch {
            _note.value?.let {
                noteDataSource.update(it)
                onUpdateFinish()
            }
        }
    }

    private fun onUpdateFinish() {
        _isUpdateFinish.value = true
    }
}