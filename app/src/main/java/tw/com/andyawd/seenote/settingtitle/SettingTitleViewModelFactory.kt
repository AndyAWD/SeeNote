package tw.com.andyawd.seenote.settingtitle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tw.com.andyawd.seenote.database.NoteDatabaseDao
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class SettingTitleViewModelFactory(
    private val settingDataSource: SettingDatabaseDao,
    private val noteDataSource: NoteDatabaseDao,
    private val page: String,
    private val noteId: Long
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingTitleViewModel::class.java)) {
            return SettingTitleViewModel(settingDataSource, noteDataSource, page, noteId) as T
        }

        throw IllegalArgumentException("找不到 ViewModel 類")
    }
}