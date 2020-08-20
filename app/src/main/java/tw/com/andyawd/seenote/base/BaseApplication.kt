package tw.com.andyawd.seenote.base

import android.app.Application

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
    }
}