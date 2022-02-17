package com.example.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.observables.GroupedObservable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import java.util.concurrent.TimeUnit

class ChangeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change)

        val list = ArrayList<String>()

        //buffer(整合成一個包裹再輸出，count決定一包幾個)
        Observable.just("A", "B", "C", "D", "E", "F")
            .buffer(4)
            .subscribe(object : Observer<List<String>> {
                override fun onSubscribe(d: Disposable) {
                    println("onSubscribe")
                }

                override fun onNext(t: List<String>) {
                    for (i in t) {
                        println("String: $i")
                    }
                    println("buffer: $t")
                    //印出[A, B, C, D]
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                    println("onComplete")
                }
            })

        //cast
        val numbers = Observable.just<Number>(1, 4.0, 3f, 7, 12, 4.6, 5)
        numbers.filter { x: Number? ->
            Integer::class.java.isInstance(x)
        }
            .cast(Integer::class.java)
            .subscribe { x: Integer? -> println("cast : $x") }//印出 1,7,12,5

        //concatMap
        Observable.range(0, 5)
            .concatMap { i: Int? ->
                val delay = Math.round(Math.random() * 2)
                Observable.timer(delay, TimeUnit.SECONDS)
                    .map { n: Long? -> i }
            }
            .blockingSubscribe { obj: Int? ->
                println("concatMap : $obj")
            }


        //flatMap
        val str = listOf("abc", "de")
        str.flatMap { s -> s.toSet() }.run { println("flatMap : $this") }//[a, b, c, d, e]

        //flatMap
        arrayListOf(
            Book("Kotlin", arrayListOf(Page(arrayListOf("Page1", "Page2")))),
            Book("Java", arrayListOf(Page(arrayListOf("Page3", "Page4")))),
            Book("Android", arrayListOf(Page(arrayListOf("Page5", "Page6"))))
        ).toObservable().flatMap {
            Observable.fromIterable(it.pageList)
        }.flatMap { Observable.fromIterable(it.wordList) }
            .subscribeBy(onNext = {
                list.add(it)
                println("flatMap : $it")
                println("list : $list")
            }, onError = {
                println("Error : $it")
            })

        //flatMap
        Observable.just("A", "B", "C")
            .flatMap { a: String ->
                Observable.intervalRange(1, 3, 0, 1, TimeUnit.SECONDS)
                    .map { b: Long -> "($a, $b)" }
            }.subscribe { println("flatMap : $it") }


        //GroupBy(分組)
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

        //map
        Observable.just(1, 2, 3)
            .map { t ->
                t * 2
            }.subscribe {
                println("Map : $it")
            }

        //Scan(每加總一次，就印出一次)
        Observable.just("J", "A", "V", "A")
            .scan { t1, t2 -> t1 + t2 }
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {
                    println("onSubscribe")
                }

                override fun onNext(t: String) {
                    println("Scan : $t")
                    //印出 J JA JAV JAVA
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                    println("onComplete")
                }
            })

        //Scan
        Observable.just(1, 2, 3, 4, 5)
            .scan(object : Function2<Int, Int, Int> {
                override fun invoke(p1: Int, p2: Int): Int {
                    return p1 + p2
                }
            }).subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: Int) {
                    println("Scan : $t")
                    //印出 1 3 6 10 15
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                    println("onComplete")
                }
            })

        //window
        io.reactivex.rxjava3.core.Observable
            .interval(1, TimeUnit.SECONDS)
            .take(5)
            .window(5, TimeUnit.SECONDS)//間隔時間
            .subscribe(object :
                io.reactivex.rxjava3.core.Observer<io.reactivex.rxjava3.core.Observable<Long>> {
                override fun onSubscribe(d: io.reactivex.rxjava3.disposables.Disposable) {
                }

                override fun onNext(t: io.reactivex.rxjava3.core.Observable<Long>) {
                    println("onNext : $t")
                    t.subscribe(object : Consumer<Long> {
                        override fun accept(t: Long) {
                            println("window : $t")
                        }
                    })
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })

        Observable.merge(Observable.range(0,5).window(3,1)).subscribe { println("window : $it") }

    }
}


