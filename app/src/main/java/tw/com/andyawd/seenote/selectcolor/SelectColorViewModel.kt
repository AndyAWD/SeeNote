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

    private var _size = MutableLiveData<Float>()
    val size: LiveData<Float>
        get() = _size

    private var _color = MutableLiveData<String?>()
    val color: LiveData<String?>
        get() = _color

    private var _isUpdateFinish = MutableLiveData<Boolean>()
    val isUpdateFinish: LiveData<Boolean>
        get() = _isUpdateFinish

    init {
        viewModelScope.launch {
            _setting.value = dataSource.getFirst()
            _size.value = _setting.value?.selectSize
        }

        _isUpdateFinish.value = false
    }

    fun changeSelectSize(size: Float) {
        _size.value = size
    }

    fun updateSelectSize() {
        _setting.value?.let {
            val newSetting = it.copy(selectSize = _size.value!!)
            updateSelect(newSetting)
        }
    }

    fun selectColor(color: String) {
        _color.value = color
    }

    fun updateSelectColor() {
        viewModelScope.launch {
            _setting.value?.let {
                val newSetting: Setting
                when (page) {
                    BaseConstants.TITLE_TEXT_COLOR -> {
                        newSetting =
                            it.copy(titleTextColor = _color.value ?: BaseConstants.EMPTY_STRING)
                    }
                    BaseConstants.TITLE_BACKGROUND_COLOR -> {
                        newSetting = it.copy(
                            titleBackgroundColor = _color.value ?: BaseConstants.EMPTY_STRING
                        )
                    }
                    BaseConstants.CONTENT_TEXT_COLOR -> {
                        newSetting =
                            it.copy(contentTextColor = _color.value ?: BaseConstants.EMPTY_STRING)
                    }
                    BaseConstants.CONTENT_BACKGROUND_COLOR -> {
                        newSetting = it.copy(
                            contentBackgroundColor = _color.value ?: BaseConstants.EMPTY_STRING
                        )
                    }
                    BaseConstants.DATE_TEXT_COLOR -> {
                        newSetting =
                            it.copy(dateTextColor = _color.value ?: BaseConstants.EMPTY_STRING)
                    }
                    BaseConstants.DATE_BACKGROUND_COLOR -> {
                        newSetting = it.copy(
                            dateBackgroundColor = _color.value ?: BaseConstants.EMPTY_STRING
                        )
                    }
                    else -> {
                        newSetting = it.copy()
                    }
                }

                updateSelect(newSetting)
            }
        }
    }

    private fun updateSelect(setting: Setting) {
        viewModelScope.launch {
            dataSource.update(setting)
            onUpdateFinish()
        }
    }

    private fun onUpdateFinish() {
        _isUpdateFinish.value = true
    }
}