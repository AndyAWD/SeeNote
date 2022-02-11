package tw.com.andyawd.seenote.writenote

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tw.com.andyawd.seenote.database.NoteDatabaseDao
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class WriteNoteViewModelFactory(
    private val noteDataSource: NoteDatabaseDao,
    private val settingDataSource: SettingDatabaseDao,
    private val application: Application,
    private val dataPrimaryKey: Long,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WriteNoteViewModel::class.java)) {
            return WriteNoteViewModel(
                noteDataSource,
                settingDataSource,
                application,
                dataPrimaryKey
            ) as T
        }

        throw IllegalArgumentException("找不到 ViewModel class")
    }
}