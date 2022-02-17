package com.example.rxjava

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers

/*
 Scheduler(調度器)
*/
class SchedulersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedulers)

        val getObservable = Observable.just(1, 2, 3, 4, 5, 6)

        val getObserver = object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                println("onSubscribe")
            }

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


        //Scheduler
        Observable.just("hello", "word")
            .subscribeOn(Schedulers.newThread())//指定subscribe() 發生在新的線程
            .observeOn(AndroidSchedulers.mainThread())//指定 Subscriber 的回調發生在主線程
            .subscribe(object : Consumer<String> {
                override fun accept(t: String) {
                    println("Scheduler : $t")
                }
            })

        //多線程
        Observable.just("Hey", "Apple")
            .subscribeOn(Schedulers.newThread()) //發生在新的線程
            .observeOn(Schedulers.io()) //指定：在io線程中處理
            .map(object : Function<String, String> {
                override fun apply(t: String): String {
                    return t //處理數據
                }
            }).observeOn(AndroidSchedulers.mainThread())//指定：在主線程中處理
            .subscribe(object : Consumer<String> {
                override fun accept(t: String) {
                    println("Schedulers : $t") //消費事件
                }
            })
    }
}



