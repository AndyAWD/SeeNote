package tw.com.andyawd.seenote.writenote

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import tw.com.andyawd.seenote.bean.Setting

class WriteTagAdapter : ListAdapter<String, WriteTagViewHolder>(WriteTagDiffCallBack()) {

    private var writeTagListener: WriteTagListener? = null
    private var setting: Setting? = null

    fun setOnItemClickListener(listener: WriteTagListener) {
        this.writeTagListener = listener
    }

    fun changeSetting(setting: Setting) {
        this.setting = setting
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WriteTagViewHolder {
        return WriteTagViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WriteTagViewHolder, position: Int) {
        val item = getItem(position)

        if (writeTagListener == null) {
            holder.bind(item, setting)
        } else {
            holder.bind(item, setting, writeTagListener!!)
        }
    }
}