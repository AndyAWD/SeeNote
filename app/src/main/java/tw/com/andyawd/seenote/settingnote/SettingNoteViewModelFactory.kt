package tw.com.andyawd.seenote.settingnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SettingNoteViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingNoteViewModel::class.java)) {
            return SettingNoteViewModel() as T
        }

        throw IllegalArgumentException("找不到 ViewModel class")
    }
}