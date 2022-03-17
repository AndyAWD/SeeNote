package tw.com.andyawd.seenote.writenote

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tw.com.andyawd.seenote.database.HackmdDatabaseDao
import tw.com.andyawd.seenote.database.NoteDatabaseDao
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class WriteNoteViewModelFactory(
    private val application: Application,
    private val noteDataSource: NoteDatabaseDao,
    private val settingDataSource: SettingDatabaseDao,
    private val hackmdDatabaseDao: HackmdDatabaseDao,
    private val dataPrimaryKey: Long,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WriteNoteViewModel::class.java)) {
            return WriteNoteViewModel(
                application,
                noteDataSource,
                settingDataSource,
                hackmdDatabaseDao,
                dataPrimaryKey
            ) as T
        }

        throw IllegalArgumentException("找不到 ViewModel class")
    }
}