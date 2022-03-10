package tw.com.andyawd.seenote.settingpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tw.com.andyawd.seenote.database.HackmdDatabaseDao
import tw.com.andyawd.seenote.database.NoteDatabaseDao
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class SettingPageViewModelFactory(
    private val settingDataSource: SettingDatabaseDao,
    private val noteDataSource: NoteDatabaseDao,
    private val hackmdDatabaseDao: HackmdDatabaseDao,
    private val page: String,
    private val noteId: Long
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingPageViewModel::class.java)) {
            return SettingPageViewModel(
                settingDataSource,
                noteDataSource,
                hackmdDatabaseDao,
                page,
                noteId
            ) as T
        }

        throw IllegalArgumentException("找不到 ViewModel class")
    }
}