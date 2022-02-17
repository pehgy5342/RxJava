package com.example.rxjava

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import java.lang.IllegalArgumentException
import java.lang.NullPointerException
import java.lang.NumberFormatException

/*
 錯誤類
*/
class ErrorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)

        //onErrorReturn
        Single.just("2A")
            .map { v -> v.toInt(10) }
            .onErrorReturn { error: Throwable? ->
                if (error is NumberFormatException)
                    return@onErrorReturn 0 else throw IllegalArgumentException()
            }
            .subscribe({ x -> println("onErrorReturn : $x") }
            ) { error: Throwable? ->
                println("onError should not be printed!")
            }


        //onErrorReturnItem
        Single.just("2A")
            .map { v -> v.toInt(10) }
            .onErrorReturnItem(0)
            .subscribe(
                { x: Int? -> println("onErrorReturnItem : $x") }
            ) { error: Throwable? ->
                println("onError should not be printed!")
            }


        //onExceptionResumeNext(異常繼續下一步)
        Observable.create(ObservableOnSubscribe<Int> { e ->
            e.onNext(1)
            e.onNext(2)
            e.onNext(3)
            e.onError(NullPointerException())
            e.onNext(4)
        })
            .onErrorResumeNext { throwable ->
                Log.d(TAG, "onErrorResumeNext ")
                Observable.just(4)
            }
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable?) {
                    Log.d(TAG, "onSubscribe ")
                }

                override fun onNext(integer: Int) {
                    Log.d(TAG, "onNext111 $integer")
                }

                override fun onError(e: Throwable?) {
                    Log.d(TAG, "onError ")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete ")
                }
            })


        //retry(發生錯誤時，重複發射item，直到沒有異常)
        var first = true

        Observable.create(ObservableOnSubscribe<Int> { e ->
            e.onNext(1)
            e.onNext(2)
            if (first) {
                first = false
                e.onError(NullPointerException())
            }
        } as ObservableOnSubscribe<Int>?)
            .retry(9)
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe ");
                }

                override fun onNext(t: Int) {
                    Log.d(TAG, "onNext $t");
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError ");
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete ");
                }

            })

        //retryUntil
        Observable.create(ObservableOnSubscribe { e: ObservableEmitter<Int> ->
            e.onNext(4)
            e.onNext(5)
            e.onError(NullPointerException())
            e.onNext(6)
            e.onComplete()
        })
            .retryUntil { true } //返回值為true 不再執行則調用onError，false則是重複執行(4,5)
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe ")
                }

                override fun onNext(integer: Int) {
                    Log.d(TAG, "onNext $integer")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError ")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete ")
                }
            })
    }
}
