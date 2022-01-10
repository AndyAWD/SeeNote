package tw.com.andyawd.seenote.notepage

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tw.com.andyawd.seenote.database.Note

class NotePageAdapter() :
    ListAdapter<NotePageItem, RecyclerView.ViewHolder>(NotePageDiffCallback()) {

    private var notePageListener: NotePageListener? = null

    fun setOnItemClickListener(notePageListener: NotePageListener) {
        this.notePageListener = notePageListener
    }

    fun addHeaderAndSubmitList(list: List<Note>?) {
        val items = when (list) {
            null -> listOf(NotePageItem.Header)
            else -> listOf(NotePageItem.Header) + list.map { NotePageItem.NotePageBody(it) }
        }
        submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> NotePageHeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> NotePageBodyViewHolder.from(parent)
            else -> throw ClassCastException("未知的 viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NotePageBodyViewHolder -> {
                val item = getItem(position) as NotePageItem.NotePageBody

                if (notePageListener == null) {
                    holder.bind(item.note)
                } else {
                    holder.bind(item.note, notePageListener!!)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is NotePageItem.Header -> ITEM_VIEW_TYPE_HEADER
            is NotePageItem.NotePageBody -> ITEM_VIEW_TYPE_ITEM
        }
    }

    companion object {
        private const val ITEM_VIEW_TYPE_HEADER = 0
        private const val ITEM_VIEW_TYPE_ITEM = 1
    }
}