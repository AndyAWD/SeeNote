package tw.com.andyawd.seenote.tagpage

class TagPageListener(val itemClickListener: (tag: String) -> Unit) {
    fun onItemClick(tag: String) = itemClickListener(tag)
}