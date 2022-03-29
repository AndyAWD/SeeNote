package tw.com.andyawd.seenote.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tw.com.andyawd.seenote.BaseConstants
import tw.com.andyawd.seenote.bean.Note
import tw.com.andyawd.seenote.bean.Setting
import tw.com.andyawd.seenote.bean.hackmd.HackmdNote
import tw.com.andyawd.seenote.bean.hackmd.HackmdNoteListItem

@Database(
    entities = [Note::class, Setting::class, HackmdNote::class, HackmdNoteListItem::class],
    version = 6,
    exportSchema = false
)
abstract class SeeNoteDatabase : RoomDatabase() {

    abstract val noteDatabaseDao: NoteDatabaseDao
    abstract val settingDatabaseDao: SettingDatabaseDao
    abstract val hackmdDatabaseDao: HackmdDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: SeeNoteDatabase? = null

        fun getInstance(context: Context): SeeNoteDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SeeNoteDatabase::class.java,
                        BaseConstants.NOTE_DATABASE
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}