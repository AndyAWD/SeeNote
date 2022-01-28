package tw.com.andyawd.seenote.notepage

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.com.andyawd.seenote.database.NoteDatabaseDao
import tw.com.andyawd.seenote.database.Setting
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class NotePageViewModel(
    private val noteDataSource: NoteDatabaseDao,
    private val settingDataSource: SettingDatabaseDao,
    private val application: Application
) : ViewModel() {

    val note = noteDataSource.getAll()

    private val _notePageDetail = MutableLiveData<Long?>()

    val notePageDetail
        get() = _notePageDetail

    fun onItemClicked(id: Long) {
        _notePageDetail.value = id
    }

    fun onNotePageNavigated() {
        _notePageDetail.value = null
    }

    fun loadSetting() {
        viewModelScope.launch {
            val settingList = settingDataSource.getAll()

            if (settingList.isEmpty()) {
                settingDataSource.insert(Setting())
            } else {
                settingDataSource.getFirst()
            }
        }
    }
}