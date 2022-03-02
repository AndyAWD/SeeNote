package tw.com.andyawd.seenote.selectcolor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.database.Setting
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class SelectColorViewModel(
    private val dataSource: SettingDatabaseDao,
    private val page: String
) : ViewModel() {

    private var _setting = MutableLiveData<Setting?>()
    val setting: LiveData<Setting?>
        get() = _setting

    private var _isUpdateFinish = MutableLiveData<Boolean>()
    val isUpdateFinish: LiveData<Boolean>
        get() = _isUpdateFinish

    init {
        viewModelScope.launch {
            _setting.value = dataSource.getFirst()
        }

        _isUpdateFinish.value = false
    }

    fun changeSettingSize(size: Int) {
        _setting.value?.let {
            val newTextSize = it.textSize?.copy(selectColor = size)
            val newSetting = it.copy(textSize = newTextSize)
            _setting.value = newSetting
        }
    }

    fun updateTextSize() {
        viewModelScope.launch {
            _setting.value?.let {
                dataSource.update(it)
            }
        }
    }

    fun updateSelectColor(color: String) {
        viewModelScope.launch {
            _setting.value?.let {
                val newSetting: Setting
                when (page) {
                    BaseConstants.TITLE_TEXT_COLOR -> {
                        val newColor = it.title?.copy(textColor = color)
                        newSetting = it.copy(title = newColor)
                        _setting.value = newSetting
                    }
                    BaseConstants.TITLE_BACKGROUND_COLOR -> {
                        val newColor = it.title?.copy(backgroundColor = color)
                        newSetting = it.copy(title = newColor)
                        _setting.value = newSetting
                    }
                    BaseConstants.CONTENT_TEXT_COLOR -> {
                        val newColor = it.content?.copy(textColor = color)
                        newSetting = it.copy(content = newColor)
                        _setting.value = newSetting
                    }
                    BaseConstants.CONTENT_BACKGROUND_COLOR -> {
                        val newColor = it.content?.copy(backgroundColor = color)
                        newSetting = it.copy(content = newColor)
                        _setting.value = newSetting
                    }
                    BaseConstants.DATE_TEXT_COLOR -> {
                        val newColor = it.date?.copy(textColor = color)
                        newSetting = it.copy(date = newColor)
                        _setting.value = newSetting
                    }
                    BaseConstants.DATE_BACKGROUND_COLOR -> {
                        val newColor = it.date?.copy(backgroundColor = color)
                        newSetting = it.copy(date = newColor)
                        _setting.value = newSetting
                    }
                    else -> {
                        newSetting = it.copy()
                        _setting.value = newSetting
                    }
                }

                updateColor()
            }
        }
    }

    private fun updateColor() {
        viewModelScope.launch {
            _setting.value?.let {
                dataSource.update(it)
                onUpdateFinish()
            }
        }
    }

    private fun onUpdateFinish() {
        _isUpdateFinish.value = true
    }
}