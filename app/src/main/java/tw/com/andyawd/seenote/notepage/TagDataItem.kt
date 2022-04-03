package tw.com.andyawd.seenote.notepage

import tw.com.andyawd.seenote.bean.Tag

sealed class TagDataItem {
    abstract val id: Long

    data class Body(val tag: Tag) : TagDataItem() {
        override val id: Long
            get() = tag.id
    }

    object Header : TagDataItem() {
        override val id: Long
            get() = Long.MIN_VALUE
    }
}
