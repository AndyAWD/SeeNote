package tw.com.andyawd.seenote.writenote

class NoteTagListener(val itemClickListener: (id: Long) -> Unit) {
    fun onItemClick(tag: String) = itemClickListener(0)
}