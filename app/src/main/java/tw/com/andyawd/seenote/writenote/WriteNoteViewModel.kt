package tw.com.andyawd.seenote.writenote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.database.*

class WriteNoteViewModel(
    private val noteDatabase: NoteDatabaseDao,
    private val settingDataSource: SettingDatabaseDao,
    application: Application,
    private val dataPrimaryKey: Long
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

    init {
        initNote()
        initSetting()
    }

    private fun initSetting() {
        viewModelScope.launch {
            _setting.value = settingDataSource.getFirst()
        }
    }

    private fun initNote() {
        viewModelScope.launch {
            if (BaseConstants.CREATE_NOTE == dataPrimaryKey) {
                val timeMillis = System.currentTimeMillis()
                val color = Color()
                val noteId = noteDatabase.insert(
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
                _note.value = getNoteFromDatabase(dataPrimaryKey)
            }
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
}