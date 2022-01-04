package tw.com.andyawd.seenote.notepage

import android.app.Application
import androidx.lifecycle.ViewModel
import tw.com.andyawd.seenote.database.NoteDatabaseDao

class NotePageViewModel(
    private val dataSource: NoteDatabaseDao,
    private val application: Application
) : ViewModel() {

    val note = dataSource.getAll()

}