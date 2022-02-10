package tw.com.andyawd.seenote.settingtitle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class SettingTitleViewModelFactory(
    private val dataSource: SettingDatabaseDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingTitleViewModel::class.java)) {
            return SettingTitleViewModel(dataSource) as T
        }
        throw IllegalArgumentException("找不到 ViewModel 類")
    }
}