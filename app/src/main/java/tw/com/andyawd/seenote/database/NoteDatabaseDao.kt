package tw.com.andyawd.seenote.database

import androidx.room.*

@Dao
interface NoteDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note): Long

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * from note_table WHERE id = :key")
    suspend fun get(key: Long): Note

    @Query("DELETE FROM note_table")
    suspend fun clear()

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    suspend fun getAll(): List<Note>

    @Query("SELECT * FROM note_table WHERE title LIKE '%' || :text || '%' OR content LIKE '%' || :text || '%'  ORDER BY id ASC")
    suspend fun getSearchText(text: String): List<Note>

    @Query("SELECT * FROM note_table ORDER BY id DESC LIMIT 1")
    suspend fun getFirst(): Note?

    @Query("UPDATE note_table SET title =:text WHERE id = :id")
    suspend fun updateTitle(id: Long, text: String): Int

    @Query("UPDATE note_table SET content =:text WHERE id = :id")
    suspend fun updateContent(id: Long, text: String): Int
}