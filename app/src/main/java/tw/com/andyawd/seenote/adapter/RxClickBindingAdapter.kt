package tw.com.andyawd.seenote.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.BindingAdapter
import com.jakewharton.rxbinding3.view.clicks
import tw.com.andyawd.seenote.base.BaseConstants
import java.util.concurrent.TimeUnit

object RxClickBindingAdapter {
    @SuppressLint("CheckResult")
    @JvmStatic
    @BindingAdapter(BaseConstants.RX_CLICK)
    fun setRxClick(view: View, listener: View.OnClickListener) {
        view.clicks().throttleFirst(BaseConstants.CLICK_MILLISECONDS_TIMER, TimeUnit.MILLISECONDS).subscribe {

        }
    }
}