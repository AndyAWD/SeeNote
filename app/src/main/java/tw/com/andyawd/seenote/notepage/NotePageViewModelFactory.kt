package tw.com.andyawd.seenote.notepage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tw.com.andyawd.seenote.database.NoteDatabaseDao
import tw.com.andyawd.seenote.database.SettingDatabaseDao


class NotePageViewModelFactory(
    private val application: Application,
    private val noteDataSource: NoteDatabaseDao,
    private val settingDataSource: SettingDatabaseDao,
    private val tag: String,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotePageViewModel::class.java)) {
            return NotePageViewModel(application, noteDataSource, settingDataSource, tag) as T
        }

        throw IllegalArgumentException("找不到 ViewModel Class")
    }
}