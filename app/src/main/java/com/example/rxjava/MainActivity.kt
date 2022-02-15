package com.example.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.lang.Exception
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //create
        Observable.create<String> { emitter ->
            emitter.onNext("hello word A")
            emitter.onNext("hello word B")
            emitter.onNext("hello word C")
            emitter.onComplete()
        }.subscribe({ s -> //onNext
            println(s)
        }, {//onError
            println("Error: " + it.message)
        }, {//onComplete
            println("Completed Observable")
        })


        Observable.create<String> {
            try {
                it.onNext("my")
                it.onNext("name")
                it.onNext("is")
                it.onComplete()
                it.onNext("Pa")
            } catch (e: Exception) {
                it.onError(e)
            }
        }.subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                println(d.isDisposed)
            }

            override fun onNext(t: String) {
                println(t)
            }

            override fun onComplete() {
                println("Completed Observable")
            }

            override fun onError(e: Throwable) {
                println("Error: " + e.message)
            }
        })


        //fromArray
        val list = arrayListOf("Pran", "Pat")
        val listObserver = Observable.fromArray(list)

        listObserver
            .subscribe(object : Observer<ArrayList<String>> {
                override fun onSubscribe(d: Disposable) {
                    println(d.isDisposed)
                }

                override fun onNext(t: ArrayList<String>) {
                    println(t)
                }

                override fun onComplete() {
                    println("Completed Observable")
                }

                override fun onError(e: Throwable) {
                    println("Error: " + e.message)
                }

            })


        //just
        Observable
            .just(1, 2, 3)
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                    println(d.isDisposed)
                }

                override fun onNext(i: Int) {
                    println(i)
                }

                override fun onComplete() {
                    println("Completed Observable")
                }

                override fun onError(e: Throwable) {
                    println("Error: " + e.message)
                }

            })

    }
}