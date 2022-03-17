package tw.com.andyawd.seenote.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import tw.com.andyawd.seenote.bean.hackmd.UserNoteListItem

@Dao
interface HackmdDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userNoteList: List<UserNoteListItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userNote: UserNoteListItem): Long
}