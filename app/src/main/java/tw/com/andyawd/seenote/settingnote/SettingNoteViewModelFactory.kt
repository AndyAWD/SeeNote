package tw.com.andyawd.seenote.settingnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class SettingNoteViewModelFactory(
    private val dataSource: SettingDatabaseDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingNoteViewModel::class.java)) {
            return SettingNoteViewModel(dataSource) as T
        }

        throw IllegalArgumentException("找不到 ViewModel class")
    }
}