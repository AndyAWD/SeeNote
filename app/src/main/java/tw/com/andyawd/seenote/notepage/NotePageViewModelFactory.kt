package tw.com.andyawd.seenote.notepage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tw.com.andyawd.seenote.database.NoteDatabaseDao
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class NotePageViewModelFactory(
    private val noteDataSource: NoteDatabaseDao,
    private val settingDataSource: SettingDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotePageViewModel::class.java)) {
            return NotePageViewModel(noteDataSource, settingDataSource, application) as T
        }

        throw IllegalArgumentException("找不到 ViewModel Class")
    }
}