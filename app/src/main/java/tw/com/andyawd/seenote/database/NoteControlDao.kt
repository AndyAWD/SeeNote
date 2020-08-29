package tw.com.andyawd.seenote.database

import androidx.room.*
import io.reactivex.Flowable
import tw.com.andyawd.seenote.base.BaseConstants
import tw.com.andyawd.seenote.bean.NoteBean

@Dao
interface NoteControlDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNoteObject(note: NoteBean): Long

    @Delete
    fun deleteNoteObject(note: NoteBean): Int

    @Query("DELETE FROM ${BaseConstants.ROOM_NOTE} WHERE ${BaseConstants.ID} == :id")
    fun deleteNoteById(id: Int): Int

    @Query("SELECT * FROM ${BaseConstants.ROOM_NOTE} ORDER BY ${BaseConstants.ID} ASC")
    fun getNoteAllInventory(): Flowable<List<NoteBean>>

    @Query("SELECT * FROM ${BaseConstants.ROOM_NOTE} WHERE ${BaseConstants.ID} LIKE :id LIMIT 1")
    fun getNoteObjectById(id: Int): NoteBean

    @Update
    fun modifyNoteObject(note: NoteBean): Int

    @Query("UPDATE ${BaseConstants.ROOM_NOTE} SET ${BaseConstants.TITLE} =:title WHERE ${BaseConstants.ID} == :id")
    fun modifyNoteTitleById(id: Int, title: String): Int

    @Query("UPDATE ${BaseConstants.ROOM_NOTE} SET ${BaseConstants.CONTENT} =:content WHERE ${BaseConstants.ID} == :id")
    fun modifyNoteContentById(id: Int, content: String): Int
}