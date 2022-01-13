package tw.com.andyawd.seenote.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * from note_table WHERE id = :key")
    fun get(key: Long): Flow<Note>

    @Query("DELETE FROM note_table")
    suspend fun clear()

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getAll(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table ORDER BY id DESC LIMIT 1")
    suspend fun getFirst(): Note?

    @Query("UPDATE note_table SET title =:text WHERE id = :id")
    suspend fun updateTitle(id: Long, text: String): Int

    @Query("UPDATE note_table SET content =:text WHERE id = :id")
    suspend fun updateContent(id: Long, text: String): Int
}