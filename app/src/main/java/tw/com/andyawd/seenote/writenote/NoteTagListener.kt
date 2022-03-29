package tw.com.andyawd.seenote.writenote

class NoteTagListener(val itemClickListener: (tag: String) -> Unit) {
    fun onItemClick(tag: String) = itemClickListener(tag)
}