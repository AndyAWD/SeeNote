package tw.com.andyawd.seenote.tagpage


sealed class TagDataItem {
    abstract val id: Long

    data class Body(val tag: String) : TagDataItem() {
        override val id: Long
            get() = 1L
    }

    object Header : TagDataItem() {
        override val id: Long
            get() = Long.MIN_VALUE
    }
}
