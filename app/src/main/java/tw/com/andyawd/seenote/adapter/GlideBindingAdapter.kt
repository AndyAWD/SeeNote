package tw.com.andyawd.seenote.adapter

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import tw.com.andyawd.seenote.base.BaseConstants

object GlideBindingAdapter {

    @JvmStatic
    @BindingAdapter(BaseConstants.GLIDE_URI)
    fun setGlideUri(imageView: AppCompatImageView, uri: Drawable) {
        Glide.with(imageView.context).load(uri).into(imageView)
    }

    @JvmStatic
    @BindingAdapter(BaseConstants.GLIDE_URL)
    fun setGlideUrl(imageView: AppCompatImageView, url: String) {
        Glide.with(imageView.context).load(url).into(imageView)
    }
}