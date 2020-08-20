package tw.com.andyawd.seenote.database

import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import tw.com.andyawd.seenote.base.BaseApplication
import tw.com.andyawd.seenote.base.BaseConstants
import tw.com.andyawd.seenote.bean.NoteBean

@Database(entities = [NoteBean::class], version = 1)
abstract class SeeNoteDatabase : RoomDatabase() {

    abstract fun noteControlDao(): NoteControlDao

    companion object {

        @Volatile
        private var instance: SeeNoteDatabase? = null

        fun getDatabase(): SeeNoteDatabase {
            val templateInstance =
                instance

            if (templateInstance != null) {
                return templateInstance
            }

            synchronized(this) {
                val instance = databaseBuilder(
                    BaseApplication.context(),
                    SeeNoteDatabase::class.java,
                    BaseConstants.ROOM_NOTE
                ).build()

                Companion.instance = instance

                return instance
            }
        }
    }
}