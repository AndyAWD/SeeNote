package tw.com.andyawd.seenote.writenote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.com.andyawd.andyawdlibrary.AWDLog
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.bean.Color
import tw.com.andyawd.seenote.bean.Date
import tw.com.andyawd.seenote.bean.Note
import tw.com.andyawd.seenote.bean.Setting
import tw.com.andyawd.seenote.database.NoteDatabaseDao
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class WriteNoteViewModel(
    private val noteDatabase: NoteDatabaseDao,
    private val settingDataSource: SettingDatabaseDao,
    application: Application,
    private var noteId: Long
) : AndroidViewModel(application) {

    private var _note = MutableLiveData<Note?>()
    val note: LiveData<Note?>
        get() = _note

    private var _setting = MutableLiveData<Setting?>()
    val setting: LiveData<Setting?>
        get() = _setting

    private var _isDatabaseDeleted = MutableLiveData<Boolean?>()
    val isDatabaseDeleted: LiveData<Boolean?>
        get() = _isDatabaseDeleted

    private var _speakStatus = MutableLiveData<String?>()
    val speakStatus: LiveData<String?>
        get() = _speakStatus

    init {
        initNote()
        initSetting()
        _speakStatus.value = SPOKEN
    }

    private fun initSetting() {
        viewModelScope.launch {
            _setting.value = settingDataSource.getFirst()
        }
    }

    private fun initNote() {
        viewModelScope.launch {
            AWDLog.d("noteId: $noteId")
            if (BaseConstants.CREATE_NOTE == noteId) {
                val timeMillis = System.currentTimeMillis()
                val color = Color()
                noteId = noteDatabase.insert(
                    Note(
                        date = Date(create = timeMillis, edit = timeMillis),
                        titleColor = color,
                        contentColor = color,
                        dateColor = color,
                        labelColor = color
                    )
                )
                _note.value = getNoteFromDatabase(noteId)
            } else {
                _note.value = getNoteFromDatabase(noteId)
            }
            AWDLog.d("noteId: $noteId / _note.value: ${_note.value}")
        }
    }

    private suspend fun getNoteFromDatabase(primaryKey: Long): Note {
        return noteDatabase.get(primaryKey)
    }

    private fun updateNote(note: Note) {
        viewModelScope.launch {
            noteDatabase.update(note)
        }
    }

    fun updateNoteTitle(title: String) {
        _note.value?.let {
            val newDate = it.date?.copy(edit = System.currentTimeMillis())
            val newNote = it.copy(title = title, date = newDate)
            it.title = title

            updateNote(newNote)
        }
    }

    fun updateNoteContent(content: String) {
        _note.value?.let {
            val newDate = it.date?.copy(edit = System.currentTimeMillis())
            val newNote = it.copy(content = content, date = newDate)
            it.content = content

            updateNote(newNote)
        }
    }

    fun deleteNote() {
        viewModelScope.launch {
            _note.value?.let {
                noteDatabase.delete(it)
                _isDatabaseDeleted.value = true
            }
        }
    }

    fun changeSettingSize(size: Int) {
        _setting.value?.let {
            val newTextSize = it.textSize?.copy(writerNote = size)
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

    fun speaking() {
        viewModelScope.launch {
            _speakStatus.value = SPEAKING
        }
    }

    fun spoken() {
        viewModelScope.launch {
            _speakStatus.value = SPOKEN
        }
    }

//    fun readyPlay() {
//        viewModelScope.launch {
//            _speakStatus.value = READY_PLAY
//        }
//    }

    fun readyPlayTitle() {
        viewModelScope.launch {
            _speakStatus.value = READY_PLAY_TITLE
        }
    }

    fun readyPlayContent() {
        viewModelScope.launch {
            _speakStatus.value = READY_PLAY_CONTENT
        }
    }

    fun playError() {
        viewModelScope.launch {
            _speakStatus.value = PLAY_ERROR
        }
    }

    fun enabled(isEnable: Boolean) {
        viewModelScope.launch {
            if (isEnable) {
                _speakStatus.value = ENABLE_TRUE
            } else {
                _speakStatus.value = ENABLE_FALSE
            }
        }
    }

    fun shutdown() {
        viewModelScope.launch {
            _speakStatus.value = SHUTDOWN
        }
    }

    fun invertColor() {
        _note.value?.let { note ->
            _setting.value?.let { setting ->
                var noteTitleTextColor = note.titleColor?.textColor
                var noteTitleBackgroundColor = note.titleColor?.backgroundColor
                var noteContentTextColor = note.contentColor?.textColor
                var noteContentBackgroundColor = note.contentColor?.backgroundColor
                var noteDateTextColor = note.dateColor?.textColor
                var noteDateBackgroundColor = note.dateColor?.backgroundColor

                val settingTitleTextColor = setting.title?.textColor ?: BaseConstants.EMPTY_STRING
                val settingTitleBackgroundColor =
                    setting.title?.backgroundColor ?: BaseConstants.EMPTY_STRING
                val settingContentTextColor =
                    setting.content?.textColor ?: BaseConstants.EMPTY_STRING
                val settingContentBackgroundColor =
                    setting.content?.backgroundColor ?: BaseConstants.EMPTY_STRING
                val settingDateTextColor = setting.date?.textColor ?: BaseConstants.EMPTY_STRING
                val settingDateBackgroundColor =
                    setting.date?.backgroundColor ?: BaseConstants.EMPTY_STRING

                if (noteTitleTextColor.isNullOrEmpty()) {
                    noteTitleTextColor = settingTitleTextColor
                }

                if (noteTitleBackgroundColor.isNullOrEmpty()) {
                    noteTitleBackgroundColor = settingTitleBackgroundColor
                }

                val newTitleColor = note.titleColor?.copy(
                    textColor = noteTitleBackgroundColor,
                    backgroundColor = noteTitleTextColor
                )

                if (noteContentTextColor.isNullOrEmpty()) {
                    noteContentTextColor = settingContentTextColor
                }

                if (noteContentBackgroundColor.isNullOrEmpty()) {
                    noteContentBackgroundColor = settingContentBackgroundColor
                }

                val newBackgroundColor = note.contentColor?.copy(
                    textColor = noteContentBackgroundColor,
                    backgroundColor = noteContentTextColor
                )

                if (noteDateTextColor.isNullOrEmpty()) {
                    noteDateTextColor = settingDateTextColor
                }

                if (noteDateBackgroundColor.isNullOrEmpty()) {
                    noteDateBackgroundColor = settingDateBackgroundColor
                }


                val newDateColor = note.dateColor?.copy(
                    textColor = noteDateBackgroundColor,
                    backgroundColor = noteDateTextColor
                )

                val newNote = note.copy(
                    titleColor = newTitleColor,
                    contentColor = newBackgroundColor,
                    dateColor = newDateColor
                )

                updateNote(newNote)
                _note.value = newNote
            }
        }
    }

    companion object {
        const val SPEAKING = "speaking"
        const val SPOKEN = "spoken"

        //        const val READY_PLAY = "ready_play"
        const val READY_PLAY_TITLE = "ready_play_title"
        const val READY_PLAY_CONTENT = "ready_play_content"
        const val PLAY_ERROR = "play_error"
        const val ENABLE_TRUE = "enable_true"
        const val ENABLE_FALSE = "enable_false"
        const val SHUTDOWN = "shutdown"
    }
}