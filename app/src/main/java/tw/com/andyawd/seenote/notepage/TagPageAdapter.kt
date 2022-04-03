package tw.com.andyawd.seenote.notepage

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tw.com.andyawd.seenote.bean.Setting
import tw.com.andyawd.seenote.bean.Tag

class TagPageAdapter : ListAdapter<TagDataItem, RecyclerView.ViewHolder>(TagPageDiffCallback()) {

    private var tagPageListener: TagPageListener? = null
    private var setting: Setting? = null
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun setOnItemClickListener(tagPageListener: TagPageListener) {
        this.tagPageListener = tagPageListener
    }

    fun changeSetting(setting: Setting) {
        this.setting = setting
        notifyDataSetChanged()
    }

    fun addHeaderAndSubmitList(list: List<Tag>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(TagDataItem.Header)
                else -> listOf(TagDataItem.Header) + list.map { TagDataItem.Body(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TagPageHeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_BODY -> TagPageBodyViewHolder.from(parent)
            else -> throw ClassCastException("未知的 ViewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TagPageHeaderViewHolder -> {
                if (tagPageListener == null) {
                    holder.bind(setting)
                } else {
                    holder.bind(setting, tagPageListener!!)
                }
            }
            is TagPageBodyViewHolder -> {
                val item = getItem(position) as TagDataItem.Body
                if (tagPageListener == null) {
                    holder.bind(item.tag, setting)
                } else {
                    holder.bind(item.tag, setting, tagPageListener!!)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TagDataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is TagDataItem.Body -> ITEM_VIEW_TYPE_BODY
        }
    }

    companion object {
        const val ITEM_VIEW_TYPE_HEADER = 0
        const val ITEM_VIEW_TYPE_BODY = 1
    }
}