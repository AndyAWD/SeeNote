package tw.com.andyawd.seenote.database

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscription
import tw.com.andyawd.seenote.`interface`.OnNoteControlListener
import tw.com.andyawd.seenote.bean.NoteBean

class NoteControlManager {

    var onNoteControlListener: OnNoteControlListener? = null
    private var getNoteAllInventorySubscription: Subscription? = null


    companion object {
        val instance = NoteControlManagerHolder.noteControlManagerHolder
    }

    private object NoteControlManagerHolder {
        val noteControlManagerHolder = NoteControlManager()
    }

    @SuppressLint("CheckResult")
    fun insertNoteObject(note: NoteBean) {
        Observable
            .create<Long> {
                it.onNext(SeeNoteDatabase.getDatabase().noteControlDao().insertNoteObject(note))
                it.onComplete()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                var disposable: Disposable? = null

                override fun onComplete() {
                    disposable?.dispose()
                }

                override fun onNext(t: Long) {
                    onNoteControlListener?.insertNoteObjectSuccess(t)
                }

                override fun onError(t: Throwable) {
                    onNoteControlListener?.error(t)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }
            })
    }

    @SuppressLint("CheckResult")
    fun deleteNoteObject(note: NoteBean) {
        Observable
            .create<Int> {
                it.onNext(SeeNoteDatabase.getDatabase().noteControlDao().deleteNoteObject(note))
                it.onComplete()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Int> {
                var disposable: Disposable? = null

                override fun onComplete() {
                    disposable?.dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(t: Int) {
                    onNoteControlListener?.deleteNoteObjectSuccess(t)
                }

                override fun onError(t: Throwable) {
                    onNoteControlListener?.error(t)
                }

            })
    }

    @SuppressLint("CheckResult")
    fun deleteNoteById(id: Int) {
        Observable
            .create<Int> {
                it.onNext(SeeNoteDatabase.getDatabase().noteControlDao().deleteNoteById(id))
                it.onComplete()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Int> {
                var disposable: Disposable? = null

                override fun onComplete() {
                    disposable?.dispose()
                }

                override fun onNext(t: Int) {
                    onNoteControlListener?.deleteNoteByIdSuccess(t)
                }

                override fun onError(t: Throwable) {
                    onNoteControlListener?.error(t)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }
            })
    }

    @SuppressLint("CheckResult")
    fun getNoteAllInventory() {
        SeeNoteDatabase
            .getDatabase()
            .noteControlDao()
            .getNoteAllInventory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    onNoteControlListener?.getNoteAllInventorySuccess(it)
                }, {
                    onNoteControlListener?.error(it)
                }, {
                    getNoteAllInventorySubscription?.cancel()
                }, {
                    it.request(1)
                    getNoteAllInventorySubscription = it
                })
    }

    @SuppressLint("CheckResult")
    fun getNoteObjectById(id: Int) {
        Observable
            .create<NoteBean> {
                it.onNext(SeeNoteDatabase.getDatabase().noteControlDao().getNoteObjectById(id))
                it.onComplete()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<NoteBean> {
                var disposable: Disposable? = null

                override fun onComplete() {
                    disposable?.dispose()
                }

                override fun onNext(t: NoteBean) {
                    onNoteControlListener?.getNoteObjectByIdSuccess(t)
                }

                override fun onError(t: Throwable) {
                    onNoteControlListener?.error(t)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }
            })
    }

    @SuppressLint("CheckResult")
    fun modifyNoteObject(note: NoteBean) {
        Observable
            .create<Int> {
                it.onNext(SeeNoteDatabase.getDatabase().noteControlDao().modifyNoteObject(note))
                it.onComplete()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Int> {
                var disposable: Disposable? = null

                override fun onComplete() {
                    disposable?.dispose()
                }

                override fun onNext(t: Int) {
                    onNoteControlListener?.modifyNoteObjectSuccess(t)
                }

                override fun onError(t: Throwable) {
                    onNoteControlListener?.error(t)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }
            })
    }

    @SuppressLint("CheckResult")
    fun modifyNoteTitleById(id: Int, title: String) {
        Observable
            .create<Int> {
                it.onNext(SeeNoteDatabase.getDatabase().noteControlDao().modifyNoteTitleById(id, title))
                it.onComplete()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Int> {
                var disposable: Disposable? = null

                override fun onComplete() {
                    disposable?.dispose()
                }

                override fun onNext(t: Int) {
                    onNoteControlListener?.modifyNoteTitleByIdSuccess(t)
                }

                override fun onError(t: Throwable) {
                    onNoteControlListener?.error(t)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }
            })
    }

    @SuppressLint("CheckResult")
    fun modifyNoteContentById(id: Int, title: String) {
        Observable
            .create<Int> {
                it.onNext(SeeNoteDatabase.getDatabase().noteControlDao().modifyNoteContentById(id, title))
                it.onComplete()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Int> {
                var disposable: Disposable? = null

                override fun onComplete() {
                    disposable?.dispose()
                }

                override fun onNext(t: Int) {
                    onNoteControlListener?.modifyNoteContentByIdSuccess(t)
                }

                override fun onError(t: Throwable) {
                    onNoteControlListener?.error(t)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }
            })
    }
}