package tw.com.andyawd.seenote.notepage

import android.widget.TextView
import androidx.databinding.BindingAdapter
import tw.com.andyawd.seenote.R
import tw.com.andyawd.seenote.database.Note
import java.text.DateFormat

@BindingAdapter("changeCreateTime")
fun TextView.setChangeCreateTime(item: Note) {
    item.let {
        text = resources.getString(
            R.string.edit_date,
            DateFormat.getInstance().format(item.createDate)
        )
    }
}

@BindingAdapter("changeTitle")
fun TextView.setChangeTitle(item: Note) {
    text = item.title.ifEmpty {
        resources.getString(R.string.no_title)
    }
}

@BindingAdapter("changeContent")
fun TextView.setChangeContent(item: Note) {
    text = item.content.ifEmpty {
        resources.getString(R.string.no_content)
    }
}