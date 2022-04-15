package tw.com.andyawd.seenote.writenote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import tw.com.andyawd.andyawdlibrary.AWDLog
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.bean.Color
import tw.com.andyawd.seenote.bean.Date
import tw.com.andyawd.seenote.bean.Note
import tw.com.andyawd.seenote.bean.Setting
import tw.com.andyawd.seenote.bean.hackmd.HackmdNote
import tw.com.andyawd.seenote.database.HackmdDatabaseDao
import tw.com.andyawd.seenote.database.NoteDatabaseDao
import tw.com.andyawd.seenote.database.SettingDatabaseDao
import tw.com.andyawd.seenote.http.HttpManager
import tw.com.andyawd.seenote.http.HttpResponseListener
import tw.com.andyawd.seenote.http.factory.BearerTokenAuthorizationFactory
import tw.com.andyawd.seenote.http.factory.CreateNoteBodyFactory
import tw.com.andyawd.seenote.http.factory.HackmdCreateNoteUrlFactory
import tw.com.andyawd.seenote.http.factory.HackmdUpdateNoteUrlFactory

class WriteNoteViewModel(
    application: Application,
    private val noteDatabase: NoteDatabaseDao,
    private val settingDataSource: SettingDatabaseDao,
    private val hackmdDatabaseDao: HackmdDatabaseDao,
    private var noteId: Long,
) : AndroidViewModel(application) {

    private var _note = MutableLiveData<Note?>()
    val note: LiveData<Note?>
        get() = _note

    private var _setting = MutableLiveData<Setting?>()
    val setting: LiveData<Setting?>
        get() = _setting

    private var _isDatabaseDeleted = MutableLiveData<Boolean?>()
    val isDatabaseDeleted: LiveData<Boolean?>
        get() = _isDatabaseDeleted

    private var _speakStatus = MutableLiveData<String?>()
    val speakStatus: LiveData<String?>
        get() = _speakStatus

    private var _httpStatus = MutableLiveData<String?>()
    val httpStatus: LiveData<String?>
        get() = _httpStatus

    private var _tag = MutableLiveData<String?>()
    val tag: LiveData<String?>
        get() = _tag

    private val _tagPageDetail = MutableLiveData<String?>()
    val tagPageDetail
        get() = _tagPageDetail

    private val _settingPageDetail = MutableLiveData<String?>()
    val settingPageDetail
        get() = _settingPageDetail

    private val _notePageDetail = MutableLiveData<String?>()
    val notePageDetail
        get() = _notePageDetail

    init {
        initNote()
        initSetting()
        _speakStatus.value = SPOKEN
    }

    private fun initSetting() {
        viewModelScope.launch {
            _setting.value = settingDataSource.getFirst()
        }
    }

    private fun initNote() {
        viewModelScope.launch {
            AWDLog.d("noteId: $noteId")
            if (BaseConstants.CREATE_NOTE == noteId) {
                val timeMillis = System.currentTimeMillis()
                val color = Color()
                noteId = noteDatabase.insert(
                    Note(
                        date = Date(create = timeMillis, edit = timeMillis),
                        titleColor = color,
                        contentColor = color,
                        dateColor = color,
                        tagColor = color,
                        tag = mutableListOf(getApplication<Application>().getString(R.string.app_name))
                    )
                )
                _note.value = getNoteFromDatabase(noteId)
            } else {
                _note.value = getNoteFromDatabase(noteId)
            }
        }
    }

    private suspend fun getNoteFromDatabase(primaryKey: Long): Note {
        return noteDatabase.get(primaryKey)
    }

    private fun updateNote(note: Note) {
        viewModelScope.launch {
            noteDatabase.update(note)
        }
    }

    fun deleteTag(tag: String) {
        _note.value?.let { note ->
            val newTagList: MutableList<String>? = note.tag?.toMutableList()
            newTagList?.remove(tag)

            val newNote = note.copy(tag = newTagList)
            _note.value = newNote
        }
    }

    private fun updateNoteHackmdId(hackmdId: String) {
        _note.value?.let {
            val newDate = it.date?.copy(edit = System.currentTimeMillis())
            val newNote = it.copy(hackmdId = hackmdId, date = newDate)
            it.hackmdId = hackmdId

            updateNote(newNote)
        }
    }

    fun addTag(tag: String) {
        if (tag.isEmpty()) {
            return
        }

        _tag.value = tag

        _note.value?.let { note ->
            val newTagList: MutableList<String>? = note.tag?.toMutableList()
            newTagList?.add(tag)

            val distinctTagList = newTagList?.distinct()
            val newNote = note.copy(tag = distinctTagList)
            _note.value = newNote
            _tag.value = null
        }
    }

    fun onSnackBarChecked() {
        _tag.value = null
    }

    fun saveNote() {
        _note.value?.let {
            updateNote(it)
        }
    }

    fun editNoteTitle(title: String) {
        _note.value?.let {
            val newDate = it.date?.copy(edit = System.currentTimeMillis())
            val newNote = it.copy(title = title, date = newDate)
            _note.value = newNote
        }
    }

    fun editNoteContent(content: String) {
        _note.value?.let {
            val newDate = it.date?.copy(edit = System.currentTimeMillis())
            val newNote = it.copy(content = content, date = newDate)
            _note.value = newNote
        }
    }

    fun deleteNote() {
        viewModelScope.launch {
            _note.value?.let {
                noteDatabase.delete(it)
                _isDatabaseDeleted.value = true
            }
        }
    }

    fun changeSettingSize(size: Int) {
        _setting.value?.let {
            val newTextSize = it.textSize?.copy(writerNote = size)
            val newSetting = it.copy(textSize = newTextSize)
            _setting.value = newSetting
        }
    }

    fun updateSetting() {
        viewModelScope.launch {
            _setting.value?.let {
                settingDataSource.update(it)
            }
        }
    }

    fun speaking() {
        viewModelScope.launch {
            _speakStatus.value = SPEAKING
        }
    }

    fun spoken() {
        viewModelScope.launch {
            _speakStatus.value = SPOKEN
        }
    }

    fun readyPlayTitle() {
        viewModelScope.launch {
            _speakStatus.value = READY_PLAY_TITLE
        }
    }

    fun readyPlayContent() {
        viewModelScope.launch {
            _speakStatus.value = READY_PLAY_CONTENT
        }
    }

    fun playError() {
        viewModelScope.launch {
            _speakStatus.value = PLAY_ERROR
        }
    }

    fun enabled(isEnable: Boolean) {
        viewModelScope.launch {
            if (isEnable) {
                _speakStatus.value = ENABLE_TRUE
            } else {
                _speakStatus.value = ENABLE_FALSE
            }
        }
    }

    fun shutdown() {
        viewModelScope.launch {
            _speakStatus.value = SHUTDOWN
        }
    }

    fun invertColor() {
        _note.value?.let { note ->
            _setting.value?.let { setting ->
                var noteTagTextColor = note.tagColor?.textColor
                var noteTagBackgroundColor = note.tagColor?.backgroundColor
                var noteTitleTextColor = note.titleColor?.textColor
                var noteTitleBackgroundColor = note.titleColor?.backgroundColor
                var noteContentTextColor = note.contentColor?.textColor
                var noteContentBackgroundColor = note.contentColor?.backgroundColor
                var noteDateTextColor = note.dateColor?.textColor
                var noteDateBackgroundColor = note.dateColor?.backgroundColor

                val settingTagTextColor = setting.tag?.textColor ?: BaseConstants.EMPTY_STRING
                val settingTagBackgroundColor =
                    setting.tag?.backgroundColor ?: BaseConstants.EMPTY_STRING
                val settingTitleTextColor = setting.title?.textColor ?: BaseConstants.EMPTY_STRING
                val settingTitleBackgroundColor =
                    setting.title?.backgroundColor ?: BaseConstants.EMPTY_STRING
                val settingContentTextColor =
                    setting.content?.textColor ?: BaseConstants.EMPTY_STRING
                val settingContentBackgroundColor =
                    setting.content?.backgroundColor ?: BaseConstants.EMPTY_STRING
                val settingDateTextColor = setting.date?.textColor ?: BaseConstants.EMPTY_STRING
                val settingDateBackgroundColor =
                    setting.date?.backgroundColor ?: BaseConstants.EMPTY_STRING

                if (noteTagTextColor.isNullOrEmpty()) {
                    noteTagTextColor = settingTagTextColor
                }

                if (noteTagBackgroundColor.isNullOrEmpty()) {
                    noteTagBackgroundColor = settingTagBackgroundColor
                }

                val newTagColor = note.tagColor?.copy(
                    textColor = noteTagBackgroundColor,
                    backgroundColor = noteTagTextColor
                )

                if (noteTitleTextColor.isNullOrEmpty()) {
                    noteTitleTextColor = settingTitleTextColor
                }

                if (noteTitleBackgroundColor.isNullOrEmpty()) {
                    noteTitleBackgroundColor = settingTitleBackgroundColor
                }

                val newTitleColor = note.titleColor?.copy(
                    textColor = noteTitleBackgroundColor,
                    backgroundColor = noteTitleTextColor
                )

                if (noteContentTextColor.isNullOrEmpty()) {
                    noteContentTextColor = settingContentTextColor
                }

                if (noteContentBackgroundColor.isNullOrEmpty()) {
                    noteContentBackgroundColor = settingContentBackgroundColor
                }

                val newBackgroundColor = note.contentColor?.copy(
                    textColor = noteContentBackgroundColor,
                    backgroundColor = noteContentTextColor
                )

                if (noteDateTextColor.isNullOrEmpty()) {
                    noteDateTextColor = settingDateTextColor
                }

                if (noteDateBackgroundColor.isNullOrEmpty()) {
                    noteDateBackgroundColor = settingDateBackgroundColor
                }

                val newDateColor = note.dateColor?.copy(
                    textColor = noteDateBackgroundColor,
                    backgroundColor = noteDateTextColor
                )

                val newNote = note.copy(
                    tagColor = newTagColor,
                    titleColor = newTitleColor,
                    contentColor = newBackgroundColor,
                    dateColor = newDateColor
                )

                updateNote(newNote)
                _note.value = newNote
            }
        }
    }

    fun uploadHackmd() {
        viewModelScope.launch {
            if (_setting.value?.user?.hackmdToken.isNullOrEmpty()) {
                _httpStatus.value = BaseConstants.NOT_HACKMD_TOKEN
                return@launch
            }

            _note.value?.let { note ->
                _setting.value?.let { setting ->
                    val body = CreateNoteBodyFactory(
                        note.title,
                        note.tag,
                        note.content
                    ).createBody.getBody()
                    val token = BearerTokenAuthorizationFactory(
                        setting.user?.hackmdToken ?: BaseConstants.EMPTY_STRING
                    ).createAuthorization.getAuthorization()

                    AWDLog.d("note.hackmdId.isEmpty(): ${note.hackmdId.isEmpty()} / note.hackmdId: ${note.hackmdId}")
                    if (note.hackmdId.isEmpty()) {
                        AWDLog.d("if")
                        val postUrl = HackmdCreateNoteUrlFactory().createUrl.getUrl()
                        HttpManager.INSTANCE.post(
                            body,
                            token,
                            postUrl,
                            getApplication(),
                            object : HttpResponseListener {
                                override fun onFailure(status: String, responseBody: String) {
                                    _httpStatus.postValue(status)
                                }

                                override fun onSuccess(responseBody: String) {
                                    _httpStatus.postValue(BaseConstants.SUCCESS)
                                    val a = insertUserNoteList(responseBody)
                                    AWDLog.d("insertUserNoteList: $a")
                                }
                            })
                    } else {
                        AWDLog.d("else")
                        val patchUrl = HackmdUpdateNoteUrlFactory(note.hackmdId).createUrl.getUrl()
                        HttpManager.INSTANCE.patch(
                            body,
                            token,
                            patchUrl,
                            getApplication(),
                            object : HttpResponseListener {
                                override fun onFailure(status: String, responseBody: String) {
                                    _httpStatus.postValue(status)
                                }

                                override fun onSuccess(responseBody: String) {
                                    AWDLog.d("onSuccess: $responseBody")
                                    _httpStatus.postValue(BaseConstants.SUCCESS)
                                }
                            })
                    }
                }
            }
        }
    }

    fun insertUserNoteList(responseBody: String) {
        viewModelScope.launch {
            val hackmdNote =
                Json.decodeFromString(HackmdNote.serializer(), responseBody)
            hackmdDatabaseDao.insertNote(hackmdNote)
            updateNoteHackmdId(hackmdNote.id ?: BaseConstants.EMPTY_STRING)
        }
    }

    fun onTagPageItemClicked(tag: String) {
        _tagPageDetail.value = tag
    }

    fun onTagPageNavigated() {
        _tagPageDetail.value = null
    }

    fun onSettingPageClicked(setting: String) {
        _settingPageDetail.value = setting
    }

    fun onSettingPageNavigated() {
        _settingPageDetail.value = null
    }

    fun onNotePageClicked(note: String) {
        _notePageDetail.value = note
    }

    fun onNotePageNavigated() {
        _notePageDetail.value = null
    }

    companion object {
        const val SPEAKING = "speaking"
        const val SPOKEN = "spoken"
        const val READY_PLAY_TITLE = "ready_play_title"
        const val READY_PLAY_CONTENT = "ready_play_content"
        const val PLAY_ERROR = "play_error"
        const val ENABLE_TRUE = "enable_true"
        const val ENABLE_FALSE = "enable_false"
        const val SHUTDOWN = "shutdown"
    }
}