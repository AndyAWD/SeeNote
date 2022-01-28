package tw.com.andyawd.seenote

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import tw.com.andyawd.seenote.database.NoteDatabaseDao
import tw.com.andyawd.seenote.database.SeeNoteDatabase
import tw.com.andyawd.seenote.database.SettingDatabaseDao
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NoteDatabaseTest {

    private lateinit var noteDao: NoteDatabaseDao
    private lateinit var settingDao: SettingDatabaseDao
    private lateinit var db: SeeNoteDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, SeeNoteDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteDao = db.noteDatabaseDao
        settingDao = db.settingDatabaseDao

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun noteInsertAndGetFirst() {
//        val note = Note()
//        noteDao.insert(note)
//        val newNote = noteDao.getFirst()
//        assertEquals(newNote?.title, "")
    }

    @Test
    @Throws(Exception::class)
    fun settingInsertAndGetFirst() {
//        val setting = Setting()
//        settingDao.insert(setting)
//        val newSetting = settingDao.getFirst()
//        assertEquals(newSetting?.pageContentBackgroundColor, "")
    }
}