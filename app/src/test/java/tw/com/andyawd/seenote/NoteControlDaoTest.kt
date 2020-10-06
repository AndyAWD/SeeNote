package tw.com.andyawd.seenote

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import tw.com.andyawd.seenote.bean.NoteBean
import tw.com.andyawd.seenote.database.NoteControlDao
import tw.com.andyawd.seenote.database.SeeNoteDatabase


@ExperimentalCoroutinesApi
class NoteControlDaoTest {

    private lateinit var noteControlDao: NoteControlDao
    private lateinit var seeNoteDatabase: SeeNoteDatabase

    @Before
    fun createDb() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        seeNoteDatabase = Room.inMemoryDatabaseBuilder(context, SeeNoteDatabase::class.java).build()
        noteControlDao = seeNoteDatabase.noteControlDao()
    }

    @After
    fun closeDb() {
        seeNoteDatabase.close()
    }

    @Test
    fun writeUserAndReadInList() = runBlockingTest {

        val noteBean = NoteBean().apply {
            id = 0
            title = "title_0"
            content = "content_0"
            time = 0
        }

        noteControlDao.insertNoteObject(noteBean)

        val byTitle = noteControlDao.getNoteObjectById(0)

        assertThat(byTitle, equalTo(noteBean))
    }
}