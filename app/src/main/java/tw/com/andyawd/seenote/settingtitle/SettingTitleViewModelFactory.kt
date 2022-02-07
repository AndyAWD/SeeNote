package tw.com.andyawd.seenote.settingtitle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SettingTitleViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingTitleViewModel::class.java)) {
            return SettingTitleViewModel() as T
        }
        throw IllegalArgumentException("找不到 ViewModel 類")
    }
}