package tw.com.andyawd.seenote.settingtitle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.com.andyawd.seenote.database.Setting
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class SettingTitleViewModel(
    private val dataSource: SettingDatabaseDao
) : ViewModel() {

    private var _setting = MutableLiveData<Setting?>()
    val setting: LiveData<Setting?>
        get() = _setting

    private var _size = MutableLiveData<Float>()
    val size: LiveData<Float>
        get() = _size

    private var _titleTextColor = MutableLiveData<String>()
    val titleTextColor: LiveData<String?>
        get() = _titleTextColor

    private var _titleBackgroundColor = MutableLiveData<String>()
    val titleBackgroundColor: LiveData<String?>
        get() = _titleBackgroundColor

    init {
        viewModelScope.launch {
            _setting.value = dataSource.getFirst()
            _size.value = _setting.value?.settingSize
            _titleTextColor.value = _setting.value?.titleTextColor
            _titleBackgroundColor.value = _setting.value?.titleBackgroundColor
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

    fun updateTitleTextColor() {
        _setting.value?.let {
            val newSetting = it.copy(titleTextColor = _titleTextColor.value ?: "")
            updateSetting(newSetting)
        }
    }

    fun updateTitleBackgroundColor() {
        _setting.value?.let {
            val newSetting = it.copy(titleTextColor = _titleBackgroundColor.value ?: "")
            updateSetting(newSetting)
        }
    }

    private fun updateSetting(setting: Setting) {
        viewModelScope.launch {
            dataSource.update(setting)
        }
    }
}