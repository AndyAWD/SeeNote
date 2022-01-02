package tw.com.andyawd.seenote

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import tw.com.andyawd.seenote.database.NoteDatabase
import tw.com.andyawd.seenote.database.NoteDatabaseDao
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NoteDatabaseTest {

    private lateinit var noteDao: NoteDatabaseDao
    private lateinit var db: NoteDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteDao = db.noteDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetFirst() {
//        val note = Note()
//        noteDao.insert(note)
//        val newNote = noteDao.getFirst()
//        assertEquals(newNote?.title, "")
    }
}