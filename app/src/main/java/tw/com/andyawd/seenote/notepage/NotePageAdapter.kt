package tw.com.andyawd.seenote.notepage

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tw.com.andyawd.seenote.database.Note
import tw.com.andyawd.seenote.database.Setting

class NotePageAdapter() :
    ListAdapter<NotePageItem, RecyclerView.ViewHolder>(NotePageDiffCallback()) {

    private var notePageListener: NotePageListener? = null
    private var setting: Setting? = null
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun setOnItemClickListener(notePageListener: NotePageListener) {
        this.notePageListener = notePageListener
    }

    fun addSetting(setting: Setting) {
        this.setting = setting
    }

    fun addHeaderAndSubmitList(list: List<Note>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(NotePageItem.Header)
                else -> listOf(NotePageItem.Header) + list.map { NotePageItem.Body(it) }
            }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    fun addSettingBinding() {

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
                val item = getItem(position) as NotePageItem.Body

                if (notePageListener == null) {
                    holder.bind(item.note, setting)
                } else {
                    holder.bind(item.note, setting, notePageListener!!)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is NotePageItem.Header -> ITEM_VIEW_TYPE_HEADER
            is NotePageItem.Body -> ITEM_VIEW_TYPE_ITEM
        }
    }

    companion object {
        private const val ITEM_VIEW_TYPE_HEADER = 0
        private const val ITEM_VIEW_TYPE_ITEM = 1
    }
}