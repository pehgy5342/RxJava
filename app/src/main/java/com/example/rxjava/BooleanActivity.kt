package com.example.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class BooleanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boolean)

        //all
        Observable.just(1, 2, 3, 4)
            .all { t -> t > 0 }
            .subscribe(object : SingleObserver<Boolean> {
                override fun onSubscribe(d: Disposable) {
                    println("onSubscribe")
                }

                override fun onSuccess(t: Boolean) {
                    println("all: $t")
                }

                override fun onError(e: Throwable) {
                }
            })

        //contains
        Observable.just(2, 55, 39, 46)
            .contains(55)
            .subscribe(object : SingleObserver<Boolean> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(t: Boolean) {
                    println("contains: $t")
                }

                override fun onError(e: Throwable) {
                }
            })

        //SequenceEqual(判斷是否相同)
        val odd = Observable.just(1, 3, 5)
        val even = Observable.just(1, 3, 5)
        Observable.sequenceEqual(odd, even)
            .subscribe(object : SingleObserver<Boolean> {
                override fun onSubscribe(d: Disposable) {
                }
                override fun onSuccess(t: Boolean) {
                    println("SequenceEqual : $t")
                }
                override fun onError(e: Throwable) {
                }
            })


    }
}