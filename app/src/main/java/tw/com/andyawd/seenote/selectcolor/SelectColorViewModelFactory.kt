package tw.com.andyawd.seenote.selectcolor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SelectColorViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectColorViewModel::class.java)) {
            return SelectColorViewModel() as T
        }

        throw IllegalArgumentException("找不到 ViewModel class")
    }
}