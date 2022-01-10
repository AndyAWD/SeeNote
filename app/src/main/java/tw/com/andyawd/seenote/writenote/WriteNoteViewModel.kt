package tw.com.andyawd.seenote.writenote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.com.andyawd.seenote.database.Note
import tw.com.andyawd.seenote.database.NoteDatabaseDao

class WriteNoteViewModel(
    private val database: NoteDatabaseDao,
    application: Application,
    private val dataPrimaryKey: Long
) : AndroidViewModel(application) {

    private var note = MutableLiveData<Note?>()

    init {
        initNote()
    }

    private fun initNote() {
        viewModelScope.launch {

//            if (BaseConstants.CREATE_NOTE == dataPrimaryKey) {
//                val createNote = Note()
//                insert(createNote)
//                note.value = createNote
//            } else {
//                note.value = getNoteFromDatabase(dataPrimaryKey)
//            }
        }
    }

    private suspend fun getNoteFromDatabase(primaryKey: Long): Note? {
        return database.get(primaryKey)
    }

    private fun insertNote(note: Note) {
        viewModelScope.launch {
            database.insert(note)
        }
    }

    private fun getNoteEntry(title: String, content: String): Note {
        return Note(
            title = title,
            content = content,
        )
    }

    fun addNote(title: String, content: String) {
        val newNote = getNoteEntry(title, content)
        insertNote(newNote)
    }

    fun isEntryValid(title: String, content: String): Boolean {
        if (title.isNotEmpty() && content.isNotEmpty()) {
            return true
        }
        return false
    }

    fun editNoteTitle(text: String) {
        viewModelScope.launch {
            database.updateTitle(1, text)
        }
    }

    fun editNoteContent(text: String) {
        viewModelScope.launch {
            database.updateContent(1, text)
        }
    }
}