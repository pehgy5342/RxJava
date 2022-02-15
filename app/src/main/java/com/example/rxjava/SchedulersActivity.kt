package com.example.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.schedulers.Schedulers


class SchedulersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedulers)


        val getObservable = Observable.just(1, 2, 3, 4, 5, 6)

        val getObserver = object : Observer<Int> {
            override fun onSubscribe(d: io.reactivex.rxjava3.disposables.Disposable) {
                println("onSubscribe")            }

            override fun onNext(t: Int) {
                println("onNext: $t")
            }

            override fun onError(e: Throwable) {
                println("onError")
            }

            override fun onComplete() {
                println("onComplete")
            }
        }

        //Subscription
        getObservable
            .filter { it > 3 }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver)

    }
}