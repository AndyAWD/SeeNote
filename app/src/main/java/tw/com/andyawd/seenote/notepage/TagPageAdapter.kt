package tw.com.andyawd.seenote.notepage

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import tw.com.andyawd.seenote.bean.Setting
import tw.com.andyawd.seenote.bean.Tag

class TagPageAdapter : ListAdapter<Tag, TagPageBodyViewHolder>(TagPageDiffCallback()) {
    private var tagPageListener: TagPageListener? = null
    private var setting: Setting? = null

    fun setOnItemClickListener(tagPageListener: TagPageListener) {
        this.tagPageListener = tagPageListener
    }

    fun changeSetting(setting: Setting) {
        this.setting = setting
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagPageBodyViewHolder {
        return TagPageBodyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TagPageBodyViewHolder, position: Int) {
        val item = getItem(position)

        if (tagPageListener == null) {
            holder.bind(item, setting)
        } else {
            holder.bind(item, setting, tagPageListener!!)
        }
    }
}