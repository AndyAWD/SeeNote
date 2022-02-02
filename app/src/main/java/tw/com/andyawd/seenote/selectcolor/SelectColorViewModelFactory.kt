package tw.com.andyawd.seenote.selectcolor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class SelectColorViewModelFactory(
    private val dataSource: SettingDatabaseDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectColorViewModel::class.java)) {
            return SelectColorViewModel(dataSource) as T
        }

        throw IllegalArgumentException("找不到 ViewModel class")
    }
}