package tw.com.andyawd.seenote.notepage

class TagPageListener(val itemClickListener: (tag: String) -> Unit) {
    fun onItemClick(tag: String) = itemClickListener(tag)
}