package tw.com.andyawd.seenote.bean.hackmd

import tw.com.andyawd.seenote.BaseConstants

data class CreateNote(
    val title: String = BaseConstants.EMPTY_STRING,
    val content: String = BaseConstants.EMPTY_STRING,
    val readPermission: String = BaseConstants.OWNER,
    val commentPermission: String = BaseConstants.OWNER,
    val writePermission: String = BaseConstants.OWNER
)