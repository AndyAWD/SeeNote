package tw.com.andyawd.seenote.snackbar

import android.view.View
import com.google.android.material.snackbar.Snackbar
import tw.com.andyawd.seenote.BaseApplication
import tw.com.andyawd.seenote.R

class SnackBarManager {
    private lateinit var snackBar: Snackbar

    private object SnackBarManagerHolder {
        val snackBarManagerHolder = SnackBarManager()
    }

    fun alwaysShow(view: View, text: String, listener: SnackBarListener) {
        snackBar = Snackbar.make(
            view,
            text,
            Snackbar.LENGTH_INDEFINITE
        )

        snackBar.setAction(BaseApplication.context().getString(R.string.confirm)) {
            listener.onClick()
        }
        snackBar.show()
    }

    companion object {
        val INSTANCE = SnackBarManagerHolder.snackBarManagerHolder
    }
}