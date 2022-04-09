package tw.com.andyawd.seenote.selectcolor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tw.com.andyawd.seenote.database.NoteDatabaseDao
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class SelectColorViewModelFactory(
    private val settingDataSource: SettingDatabaseDao,
    private val noteDataSource: NoteDatabaseDao,
    private val isFromWriteNote: Boolean,
    private val isFromTagPage: Boolean,
    private val type: String,
    private val noteId: Long
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectColorViewModel::class.java)) {
            return SelectColorViewModel(
                settingDataSource,
                noteDataSource,
                isFromWriteNote,
                isFromTagPage,
                type,
                noteId
            ) as T
        }

        throw IllegalArgumentException("找不到 ViewModel class")
    }
}