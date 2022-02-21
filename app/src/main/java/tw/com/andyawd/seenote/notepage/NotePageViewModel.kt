package tw.com.andyawd.seenote.notepage

import android.app.Application
import androidx.lifecycle.LiveData
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

    private var _setting = MutableLiveData<Setting?>()
    val setting: LiveData<Setting?>
        get() = _setting

    private var _size = MutableLiveData<Float>()
    val size: LiveData<Float>
        get() = _size

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

        _size.value = _setting.value?.settingSize
    }

    fun onItemClicked(id: Long) {
        _notePageDetail.value = id
    }

    fun onNotePageNavigated() {
        _notePageDetail.value = null
    }

    fun changeSettingSize(size: Float) {
        _size.value = size
    }


    fun updateSettingSize() {
        _setting.value?.let {
            val newSetting = it.copy(settingSize = _size.value ?: 80F)
            updateSetting(newSetting)
        }
    }

    private fun updateSetting(setting: Setting) {
        viewModelScope.launch {
            settingDataSource.update(setting)
        }
    }
}