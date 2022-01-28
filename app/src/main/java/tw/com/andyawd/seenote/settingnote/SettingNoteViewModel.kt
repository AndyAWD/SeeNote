package tw.com.andyawd.seenote.settingnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tw.com.andyawd.seenote.database.Setting
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class SettingNoteViewModel(
    private val dataSource: SettingDatabaseDao
) : ViewModel() {

    private var _buttonSize = MutableLiveData<Float>()
    val buttonSize: LiveData<Float>
        get() = _buttonSize

    private var _setting = MutableLiveData<Setting?>()
    val setting: LiveData<Setting?>
        get() = _setting

    init {
        _buttonSize.value = 80.0f
    }

    fun changeTextSize(size: Float) {
        _buttonSize.value = size
    }
}