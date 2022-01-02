package tw.com.andyawd.seenote.writenote

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tw.com.andyawd.seenote.database.NoteDatabaseDao

class WriteNoteViewModelFactory(
    private val dataSource: NoteDatabaseDao,
    private val application: Application,
    private val dataPrimaryKey: Long,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WriteNoteViewModel::class.java)) {
            return WriteNoteViewModel(dataSource, application, dataPrimaryKey) as T
        }

        throw IllegalArgumentException("找不到 ViewModel class")
    }
}