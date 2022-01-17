package tw.com.andyawd.seenote.writenote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.database.Note
import tw.com.andyawd.seenote.database.NoteDatabaseDao

class WriteNoteViewModel(
    private val database: NoteDatabaseDao,
    application: Application,
    private val dataPrimaryKey: Long
) : AndroidViewModel(application) {

    private var _note = MutableLiveData<Note?>()
    val note: LiveData<Note?>
        get() = _note

    init {
        initNote()
    }

    private fun initNote() {
        viewModelScope.launch {
            if (BaseConstants.CREATE_NOTE == dataPrimaryKey) {
                val noteId = database.insert(Note())
                _note.value = getNoteFromDatabase(noteId)
            } else {
                _note.value = getNoteFromDatabase(dataPrimaryKey)
            }
        }
    }

    private suspend fun getNoteFromDatabase(primaryKey: Long): Note {
        return database.get(primaryKey)
    }

    fun updateNote(title: String, content: String) {
        viewModelScope.launch {
            _note.value?.let {
                val updateNote = it.copy(title = title, content = content)
                database.update(updateNote)
            }
        }
    }
}