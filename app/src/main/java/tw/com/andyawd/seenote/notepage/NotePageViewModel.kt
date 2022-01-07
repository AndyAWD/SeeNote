package tw.com.andyawd.seenote.notepage

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tw.com.andyawd.seenote.database.NoteDatabaseDao

class NotePageViewModel(
    private val dataSource: NoteDatabaseDao,
    private val application: Application
) : ViewModel() {

    val note = dataSource.getAll()

    private val _notePageDetail = MutableLiveData<Long?>()

    val notePageDetail
        get() = _notePageDetail

    fun onItemClicked(id: Long) {
        _notePageDetail.value = id
    }

    fun onNotePageNavigated() {
        _notePageDetail.value = null
    }

}