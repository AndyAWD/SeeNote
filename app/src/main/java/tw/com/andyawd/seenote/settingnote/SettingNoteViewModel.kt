package tw.com.andyawd.seenote.settingnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingNoteViewModel() : ViewModel() {

    private var _buttonSize = MutableLiveData<Float>()
    val buttonSize: LiveData<Float>
        get() = _buttonSize

    init {
        _buttonSize.value = 80.0f
    }

    fun changeTextSize(size: Float) {
        _buttonSize.value = size
    }
}