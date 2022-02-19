package tw.com.andyawd.seenote.settingdate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class SettingDateViewModelFactory(
    private val dataSource: SettingDatabaseDao
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingDateViewModel::class.java)) {
            return SettingDateViewModel(dataSource) as T
        }

        throw IllegalArgumentException("找不到 ViewModel class")
    }
}