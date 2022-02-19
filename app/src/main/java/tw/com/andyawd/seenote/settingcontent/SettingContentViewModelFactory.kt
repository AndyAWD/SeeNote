package tw.com.andyawd.seenote.settingcontent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class SettingContentViewModelFactory(
    private val dataSource: SettingDatabaseDao
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingContentViewModel::class.java)) {
            return SettingContentViewModel(dataSource) as T
        }
        throw IllegalArgumentException("找不到 ViewModel 類")
    }
}