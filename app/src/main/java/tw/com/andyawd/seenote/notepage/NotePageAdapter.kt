package tw.com.andyawd.seenote.notepage

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.com.andyawd.seenote.database.Note

class NotePageAdapter(private val context: Context) : RecyclerView.Adapter<NotePageViewHolder>() {

    var data = listOf<Note>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotePageViewHolder {
        return NotePageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NotePageViewHolder, position: Int) {
        val item = data[position]
        //val resources = holder.itemView.context.resources

        holder.setData(item)

        //holder.sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)

    }

    override fun getItemCount() = data.size


}
