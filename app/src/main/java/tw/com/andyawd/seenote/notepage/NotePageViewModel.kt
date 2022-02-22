package tw.com.andyawd.seenote.notepage

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.database.NoteDatabaseDao
import tw.com.andyawd.seenote.database.Setting
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class NotePageViewModel(
    private val noteDataSource: NoteDatabaseDao,
    private val settingDataSource: SettingDatabaseDao,
    private val application: Application
) : ViewModel() {

    val note = noteDataSource.getAll()

    private var _setting = MutableLiveData<Setting?>()
    val setting: LiveData<Setting?>
        get() = _setting

    private val _notePageDetail = MutableLiveData<Long?>()
    val notePageDetail
        get() = _notePageDetail

    init {
        viewModelScope.launch {
            val settingList = settingDataSource.getAll()

            if (settingList.isEmpty()) {
                settingDataSource.insert(Setting())
            } else {
                _setting.value = settingDataSource.getFirst()
            }
        }
    }

    fun onItemClicked(id: Long) {
        _notePageDetail.value = id
    }

    fun onNotePageNavigated() {
        _notePageDetail.value = null
    }

    fun changeNotePageSize(size: Float) {
        _setting.value?.let {
            val newSetting = it.copy(pageSize = size)
            _setting.value = newSetting
        }
    }


    fun updateSettingSize() {
        _setting.value?.let {
            val newSetting = it.copy(pageSize = _setting.value?.pageSize ?: BaseConstants.TEXT_SIZE)
            updateSetting(newSetting)
        }
    }

    private fun updateSetting(setting: Setting) {
        viewModelScope.launch {
            settingDataSource.update(setting)
        }
    }
}