package com.example.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function3
import io.reactivex.observables.GroupedObservable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/*
 Change
*/
class ChangeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change)

        //buffer
        Observable.just("A", "B", "C", "D", "E", "F")
            .buffer(3)
            .subscribe(object : Observer<List<String>> {
                override fun onSubscribe(d: Disposable) {
                    println("onSubscribe")
                }

                override fun onNext(t: List<String>) {
                    for (i in t) {
                        println("String: $i")
                    }
                    println("String: $t")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                    println("onComplete")
                }
            })

        //flatMap
        val str = listOf("abc", "de")
        str.flatMap { s -> s.toSet() }.run { println("flatMap : $this") }


        arrayListOf(
            Book("Kotlin", arrayListOf(Page(arrayListOf("Page1", "Page2")))),
            Book("Java", arrayListOf(Page(arrayListOf("Page3", "Page4")))),
            Book("Android", arrayListOf(Page(arrayListOf("Page5", "Page6"))))
        ).toObservable().flatMap {
            Observable.fromIterable(it.pageList)
        }.flatMap { Observable.fromIterable(it.wordList) }
            .subscribeBy(onNext = { println("flatMap : $it") })


        //GroupBy
        val EVEN_NUMBER_KEY = "even number"
        val ODD_NUMBER_KEY = "odd number"
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
            .groupBy { t ->
                if (t % 2 == 0) {
                    EVEN_NUMBER_KEY
                } else {
                    ODD_NUMBER_KEY
                }
            }.subscribe(object : Observer<GroupedObservable<String, Int>> {
                override fun onSubscribe(d: Disposable) {
                    println("onSubscribe")
                }

                override fun onNext(t: GroupedObservable<String, Int>) {
                    if (t.key == EVEN_NUMBER_KEY) {
                        t.subscribe(object : Observer<Int> {
                            override fun onSubscribe(d: Disposable) {
                            }

                            override fun onNext(t: Int) {
                                println("偶數 : $t")
                            }

                            override fun onError(e: Throwable) {
                            }

                            override fun onComplete() {
                                println("Group onComplete")
                            }
                        })
                    }
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                    println("onComplete")
                }
            })


        //Scan
        Observable.just("J", "A", "V", "A")
            .scan { t1, t2 -> t1 + t2 }
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {
                    println("onSubscribe")
                }

                override fun onNext(t: String) {
                    println(t)
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                    println("onComplete")
                }
            })

        Observable.just(1, 2, 3, 4, 5)
            .scan(object : Function2<Int, Int, Int> {
                override fun invoke(p1: Int, p2: Int): Int {
                    return p1 + p2
                }
            }).subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: Int) {
                    println("Next : $t")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                    println("onComplete")
                }
            })

    }
}


data class Book(val name: String, val pageList: List<Page>)

data class Page(val wordList: List<String>)