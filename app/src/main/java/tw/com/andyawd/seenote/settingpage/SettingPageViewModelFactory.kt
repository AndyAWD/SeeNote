package tw.com.andyawd.seenote.settingpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class SettingPageViewModelFactory(
    private val dataSource: SettingDatabaseDao
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingPageViewModel::class.java)) {
            return SettingPageViewModel(dataSource) as T
        }

        throw IllegalArgumentException("找不到 ViewModel class")
    }
}