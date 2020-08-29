package tw.com.andyawd.seenote.`interface`

import tw.com.andyawd.seenote.bean.NoteBean

abstract class OnNoteControlListener {
    open fun insertNoteObjectSuccess(long: Long) {}
    open fun deleteNoteObjectSuccess(int: Int) {}
    open fun deleteNoteByIdSuccess(int: Int) {}
    open fun getNoteAllInventorySuccess(inventory: List<NoteBean>) {}
    open fun getNoteObjectByIdSuccess(noteBean: NoteBean) {}
    open fun modifyNoteObjectSuccess(int: Int) {}
    open fun modifyNoteTitleByIdSuccess(int: Int) {}
    open fun modifyNoteContentByIdSuccess(int: Int) {}
    open fun error(t: Throwable) {}
}