package tw.com.andyawd.seenote.settingnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.com.andyawd.seenote.database.Setting
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class SettingNoteViewModel(
    private val dataSource: SettingDatabaseDao
) : ViewModel() {

    private var _setting = MutableLiveData<Setting?>()
    val setting: LiveData<Setting?>
        get() = _setting

    private var _size = MutableLiveData<Float>()
    val size: LiveData<Float>
        get() = _size

    init {
        viewModelScope.launch {
            _setting.value = dataSource.getFirst()
            _size.value = _setting.value?.settingSize
        }
    }

    fun changeSettingSize(size: Float) {
        _size.value = size
    }

    fun updateSettingSize() {
        _setting.value?.let {
            val newSetting = it.copy(settingSize = _size.value!!)
            updateSetting(newSetting)
        }
    }

    private fun updateSetting(setting: Setting) {
        viewModelScope.launch {
            dataSource.update(setting)
        }
    }
}