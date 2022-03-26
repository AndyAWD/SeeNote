package tw.com.andyawd.seenote.writenote

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import tw.com.andyawd.seenote.bean.Setting

class NoteTagAdapter : ListAdapter<String, NoteTagBodyViewHolder>(NoteTagDiffCallBack()) {

    private var noteTagListener: NoteTagListener? = null
    private var setting: Setting? = null

    fun setOnItemClickListener(listener: NoteTagListener) {
        this.noteTagListener = listener
    }

    fun changeSetting(setting: Setting) {
        this.setting = setting
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteTagBodyViewHolder {
        return NoteTagBodyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NoteTagBodyViewHolder, position: Int) {
        val item = getItem(position)

        if (noteTagListener == null) {
            holder.bind(item, setting)
        } else {
            holder.bind(item, setting, noteTagListener!!)
        }
    }
}