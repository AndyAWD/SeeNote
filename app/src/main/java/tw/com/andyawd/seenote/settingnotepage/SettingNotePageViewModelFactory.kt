package tw.com.andyawd.seenote.settingnotepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SettingNotePageViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingNotePageViewModel::class.java)) {
            return SettingNotePageViewModel() as T
        }
        throw IllegalArgumentException("找不到 ViewModel 類")
    }
}