package tw.com.andyawd.seenote.tagpage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.com.andyawd.andyawdlibrary.AWDLog
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.bean.*
import tw.com.andyawd.seenote.database.NoteDatabaseDao
import tw.com.andyawd.seenote.database.SettingDatabaseDao

class TagPageViewModel(
    application: Application,
    private val noteDataSource: NoteDatabaseDao,
    private val settingDataSource: SettingDatabaseDao
) : AndroidViewModel(application) {

    private var _note = MutableLiveData<List<Note>>()
    val note: LiveData<List<Note>>
        get() = _note

    private var _setting = MutableLiveData<Setting?>()
    val setting: LiveData<Setting?>
        get() = _setting

    private val _searchText = MutableLiveData<String?>()
    val searchText: LiveData<String?>
        get() = _searchText

    private var _tag = MutableLiveData<List<Tag>?>()
    val tag: LiveData<List<Tag>?>
        get() = _tag

    private val _tagPageDetail = MutableLiveData<Long?>()
    val tagPageDetail
        get() = _tagPageDetail

    init {
        viewModelScope.launch {
            val settingList = settingDataSource.getAll()

            if (settingList.isEmpty()) {
                val color = Color()
                settingDataSource.insert(
                    Setting(
                        textSize = TextSize(),
                        title = color,
                        content = color,
                        date = color,
                        label = color,
                        user = User()
                    )
                )
                _setting.value = settingDataSource.getFirst()
            } else {
                _setting.value = settingDataSource.getFirst()
            }

//            _note.value = noteDataSource.getAll()
            AWDLog.d("noteDataSource.getAllTag(): ${noteDataSource.getAllTag()}")


            _tag.value = getDistinctTagList(noteDataSource.getAllTag())
        }
    }

    fun queryTag() {
        _searchText.value?.let { text ->
            viewModelScope.launch {

                val tagList = getDistinctTagList(noteDataSource.getAllTag())
                val filterSearchTagList = arrayListOf<Tag>()

                tagList.forEach {
                    if (BaseConstants.INDEX_NOT_SEARCH != it.text.indexOf(text)) {
                        filterSearchTagList.add(Tag(text = it.text))
                    }
                }

                _tag.value = filterSearchTagList
            }
        }
    }

    fun updateSetting() {
        _setting.value?.let {
            viewModelScope.launch {
                settingDataSource.update(it)
            }
        }
    }

    fun inputSearchText(text: String) {
        _searchText.value = text
    }

    fun changeTagPageSize(size: Int) {
        _setting.value?.let {
            val newTextSize = it.textSize?.copy(notePage = size)
            val newSetting = it.copy(textSize = newTextSize)
            _setting.value = newSetting
        }
    }

    fun onTagPageNavigated() {
        _tagPageDetail.value = null
    }

    private fun getDistinctTagList(tagList: List<Tag>): List<Tag> {
        val list = mutableListOf<Tag>()

        tagList.forEach { tag ->
            tag.text.split(",").map { it }.forEach {
                list.add(Tag(text = it))
            }
        }

        return list.distinct()
    }
}