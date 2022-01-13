package tw.com.andyawd.seenote.notepage

import android.widget.TextView
import androidx.databinding.BindingAdapter
import tw.com.andyawd.seenote.database.Note
import java.text.DateFormat

@BindingAdapter("changeCreateTime")
fun TextView.setChangeCreateTime(item: Note) {
    item.let {
        text = DateFormat.getInstance().format(item.createTime)
    }
}

@BindingAdapter("changeTitle")
fun TextView.setChangeTitle(item: Note) {
    text = "${item.title}"
}

@BindingAdapter("changeContent")
fun TextView.setChangeContent(item: Note) {
    text = "${item.content}"
}