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

    init {
        viewModelScope.launch {
            _setting.value = dataSource.getFirst()
        }
    }

    fun changeSettingSize(size: Int) {
        _setting.value?.let {
            val newTextSize = it.textSize?.copy(settingPage = size)
            val newSetting = it.copy(textSize = newTextSize)
            _setting.value = newSetting
        }
    }

    fun updateSetting() {
        viewModelScope.launch {
            _setting.value?.let {
                dataSource.update(it)
            }
        }
    }
}