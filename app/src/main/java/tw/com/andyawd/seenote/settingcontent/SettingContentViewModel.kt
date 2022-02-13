package tw.com.andyawd.seenote.settingcontent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.com.andyawd.seenote.database.Setting
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class SettingContentViewModel(
    private val dataSource: SettingDatabaseDao
) : ViewModel() {

    private var _setting = MutableLiveData<Setting?>()
    val setting: LiveData<Setting?>
        get() = _setting

    private var _size = MutableLiveData<Float>()
    val size: LiveData<Float>
        get() = _size

    private var _contentTextColor = MutableLiveData<String>()
    val contentTextColor: LiveData<String?>
        get() = _contentTextColor

    private var _contentBackgroundColor = MutableLiveData<String>()
    val contentBackgroundColor: LiveData<String?>
        get() = _contentBackgroundColor

    init {
        viewModelScope.launch {
            _setting.value = dataSource.getFirst()
            _size.value = _setting.value?.settingSize
            _contentTextColor.value = _setting.value?.contentTextColor
            _contentBackgroundColor.value = _setting.value?.contentBackgroundColor
        }
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
            dataSource.update(setting)
        }
    }
}