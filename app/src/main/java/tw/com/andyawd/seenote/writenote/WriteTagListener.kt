package tw.com.andyawd.seenote.writenote

class WriteTagListener(val itemClickListener: (tag: String) -> Unit) {
    fun onItemClick(tag: String) = itemClickListener(tag)
}