package tw.com.andyawd.seenote

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.jakewharton.rxbinding3.view.clicks
import com.ncapdevi.fragnav.FragNavController
import kotlinx.android.synthetic.main.activity_main.*
import tw.com.andyawd.andyawdlibrary.AWDLog
import tw.com.andyawd.seenote.`interface`.OnNoteControlListener
import tw.com.andyawd.seenote.bean.NoteBean
import tw.com.andyawd.seenote.database.NoteControlManager

class MainActivity : AppCompatActivity() {

    private val fragNavController: FragNavController = FragNavController(supportFragmentManager, R.id.clAmGroup)


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val noteBean = NoteBean()
        noteBean.apply {
            this.title = "01_title"
            this.content = "01_content"
            this.time = System.currentTimeMillis().toInt()
        }

        NoteControlManager.instance.onNoteControlListener = onNoteControlListener

        tvAmInsertNoteObject.clicks().subscribe {
            NoteControlManager.instance.insertNoteObject(noteBean)
        }

        tvAmDeleteNoteObject.clicks().subscribe {
            NoteControlManager.instance.deleteNoteObject(noteBean)
        }

        tvAmDeleteNoteById.clicks().subscribe {
            NoteControlManager.instance.deleteNoteById(1)
        }

        tvAmGetNoteAllInventory.clicks().subscribe {
            NoteControlManager.instance.getNoteAllInventory()
        }

        tvAmGetNoteObjectById.clicks().subscribe {
            NoteControlManager.instance.getNoteObjectById(3)
        }

        tvAmModifyNoteTitleById.clicks().subscribe {
            NoteControlManager.instance.modifyNoteTitleById(2, "標題修改後")
        }

        tvAmModifyNoteContentById.clicks().subscribe {
            NoteControlManager.instance.modifyNoteContentById(2, "內文修改後")
        }
    }

    private val onNoteControlListener: OnNoteControlListener = object : OnNoteControlListener() {
        override fun insertNoteObjectSuccess(long: Long) {
            super.insertNoteObjectSuccess(long)
            AWDLog.d("新增一筆: $long")
        }

        override fun deleteNoteObjectSuccess(int: Int) {
            super.deleteNoteObjectSuccess(int)
            AWDLog.d("刪除物件: $int")
        }

        override fun deleteNoteByIdSuccess(int: Int) {
            super.deleteNoteByIdSuccess(int)
            AWDLog.d("刪除編號物件: $int")
        }

        override fun getNoteAllInventorySuccess(inventory: List<NoteBean>) {
            super.getNoteAllInventorySuccess(inventory)
            AWDLog.d("取得清單筆數: ${inventory.size} \n ${Gson().toJson(inventory)}")
        }

        override fun getNoteObjectByIdSuccess(noteBean: NoteBean) {
            super.getNoteObjectByIdSuccess(noteBean)
            AWDLog.d("取得編號物件: ${Gson().toJson(noteBean)}")

            noteBean.apply {
                this.content = "取得編號物件修改後"
            }
            NoteControlManager.instance.modifyNoteObject(noteBean)
        }

        override fun modifyNoteObjectSuccess(int: Int) {
            super.modifyNoteObjectSuccess(int)
            AWDLog.d("修改物件: $int")
        }

        override fun modifyNoteTitleByIdSuccess(int: Int) {
            super.modifyNoteTitleByIdSuccess(int)
            AWDLog.d("修改編號物件標題: $int")
        }

        override fun modifyNoteContentByIdSuccess(int: Int) {
            super.modifyNoteContentByIdSuccess(int)
            AWDLog.d("修改編號物件內容: $int")
        }

        override fun error(t: Throwable) {
            super.error(t)
            AWDLog.d("錯誤: $t")
        }
    }
}