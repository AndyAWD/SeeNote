package tw.com.andyawd.seenote.tagpage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tw.com.andyawd.seenote.database.NoteDatabaseDao
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class TagPageViewModelFactory(
    private val application: Application,
    private val noteDataSource: NoteDatabaseDao,
    private val settingDataSource: SettingDatabaseDao
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TagPageViewModel::class.java)) {
            return TagPageViewModel(application, noteDataSource, settingDataSource) as T
        }

        throw IllegalArgumentException("找不到 ViewModel Class")
    }
}