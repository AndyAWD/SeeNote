package tw.com.andyawd.seenote

import android.app.Application
import tw.com.andyawd.andyawdlibrary.AWDConstants
import tw.com.andyawd.andyawdlibrary.AWDLog
import tw.com.andyawd.seenote.database.NoteDatabase

class BaseApplication : Application() {
    init {
        context = this
    }

    companion object {
        private var context: BaseApplication? = null

        fun context(): BaseApplication {
            return context as BaseApplication
        }
    }

    override fun onCreate() {
        super.onCreate()

        AWDLog.setLogLevel(AWDConstants.LOG_VERBOSE)
        NoteDatabase.getInstance(this).noteDatabaseDao
    }
}