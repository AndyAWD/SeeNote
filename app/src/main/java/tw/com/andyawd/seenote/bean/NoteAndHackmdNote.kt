package tw.com.andyawd.seenote.bean

import androidx.room.Embedded
import androidx.room.Relation
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.bean.hackmd.HackmdNote

data class NoteAndHackmdNote(
    @Embedded
    val note: Note?,

    @Relation(
        parentColumn = BaseConstants.ID,
        entityColumn = BaseConstants.PRIMARY_ID,
    )
    val hackmdNote: HackmdNote?
)
