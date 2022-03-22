package tw.com.andyawd.seenote.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import tw.com.andyawd.seenote.bean.hackmd.HackmdNote
import tw.com.andyawd.seenote.bean.hackmd.HackmdNoteListItem

@Dao
interface HackmdDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteList(hackmdNoteListItem: List<HackmdNoteListItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(hackmdNote: HackmdNote): Long
}