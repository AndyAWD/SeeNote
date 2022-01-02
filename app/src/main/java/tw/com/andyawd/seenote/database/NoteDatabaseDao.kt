package tw.com.andyawd.seenote.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDatabaseDao {

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * from note_table WHERE id = :key")
    suspend fun get(key: Long): Note?

    @Query("DELETE FROM note_table")
    suspend fun clear()

    @Query("SELECT * FROM note_table ORDER BY id DESC")
    fun getAll(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table ORDER BY id DESC LIMIT 1")
    suspend fun getFirst(): Note?

    @Query("UPDATE note_table SET title =:text WHERE id = :id")
    suspend fun updateTitle(id: Long, text: String): Int

    @Query("UPDATE note_table SET content =:text WHERE id = :id")
    suspend fun updateContent(id: Long, text: String): Int
}