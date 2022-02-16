package com.example.rxjava

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.CompletableObserver
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class FilterActivity : AppCompatActivity() {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        //debounce(防止重複點擊)
        val source = io.reactivex.rxjava3.core.Observable.create<String> { emitter ->
            emitter.onNext("A")

            Thread.sleep(1_500) //1.5s(1秒以內無其他元素)

            emitter.onNext("B")

            Thread.sleep(500)

            emitter.onNext("C")

            Thread.sleep(250)

            emitter.onNext("D")

            Thread.sleep(2_000) //2s(1秒以內無其他元素)

            emitter.onNext("E") //E發射後無其他元素
            emitter.onComplete()
        }

        source.subscribeOn(Schedulers.io())
            .debounce(1, TimeUnit.SECONDS) //在1秒以內，發送的任何參數皆不顯示
            .blockingSubscribe { item ->
                println("debounce : $item ")
            }


        //distinct(過濾掉重複)
        Observable.just(1, 1, 2, 2, 1, 3)
            .distinct()
            .subscribe { println("distinct : $it") }


        //distinctUntilChanged(去掉左右相鄰的重複數據)
        Observable.just(1, 1, 2, 2, 1, 3)
            .distinctUntilChanged()
            .subscribe { println("distinctUntilChanged : $it") }

        //ofType(過濾類型)
        Observable.just("6", 1, true, 0.5, 8)
            .ofType(Integer::class.java)
            .subscribe { println("ofType : $it") }


        //ElementAt()
        Observable.just(1, 2, 3, 4)
            .elementAt(1)
            .subscribe {
                println("ElementAt : $it")
            }

        //Filter(過濾)
        Observable.just(1, 2, 3, 4, 5, 6)
            .filter { t -> t > 3 }
            .subscribe {
                println("Filter : $it")
            }

        //Filter
        Observable
            .just(1, 2, 3)
            .filter { it % 2 == 1 }
            .subscribe {
                println("Filter : $it")
            }

        //First
        val firstObservable = Observable.just("A", "B", "C");
        val firstOrDefault = firstObservable.first("D");
        firstOrDefault.subscribe { it -> println("first : $it") }


        //FirstElement
        Observable.just(5, 6, 7)
            .firstElement()
            .subscribe {
                println("FirstElement $it")
            }

        //IgnoreElements
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8)
            .ignoreElements()
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    println("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    println("onSubscribed")
                }

                override fun onError(e: Throwable) {
                }
            })

        //ignoreElement()
        val a: Single<Long> = Single.timer(1, TimeUnit.SECONDS)
        val completable: Completable = a.ignoreElement()
        completable.doOnComplete { println("1秒就出現!") }
            .blockingAwait()


        //ignoreElements()
        val data = io.reactivex.rxjava3.core.Observable.intervalRange(1, 5, 1, 1, TimeUnit.SECONDS)
        val com: Completable = data.ignoreElements()
        com.doOnComplete { println("5秒就出現!") }
            .blockingAwait()


        //Last
        val lastObservable = Observable.just("A", "B", "C");
        val lastOrDefault = lastObservable.last("D");
        lastOrDefault.subscribe { it -> println("last : $it") }


        //lastElement
        Observable.just(1, 2, 3)
            .lastElement()
            .subscribe {
                println("lastElement : $it")
            }

        //sample(定期發出數據)
        Observable.interval(0, 0, TimeUnit.SECONDS)
            .sample(3, TimeUnit.SECONDS)
            .subscribe {
                println("Sample :$it")
            }
        io.reactivex.rxjava3.core.Observable.create<String> { emitter ->
            emitter.onNext("A")

            Thread.sleep(500)

            emitter.onNext("B")

            Thread.sleep(200)

            emitter.onNext("C")

            Thread.sleep(800)

            emitter.onNext("D")

            Thread.sleep(600)

            emitter.onNext("E")
            emitter.onComplete()
        }.subscribeOn(Schedulers.io())
            .sample(1, TimeUnit.SECONDS) //在1秒以內，發送的最新參數才顯示
            .blockingSubscribe { item ->
                println("sample : $item ")
            }


        //throttleFirst
        io.reactivex.rxjava3.core.Observable.create<String> { emitter ->
            emitter.onNext("A")

            Thread.sleep(500)

            emitter.onNext("B")

            Thread.sleep(200)

            emitter.onNext("C")

            Thread.sleep(800)

            emitter.onNext("D")

            Thread.sleep(600)

            emitter.onNext("E")
            emitter.onComplete()
        }.subscribeOn(Schedulers.io())
            .throttleFirst(1, TimeUnit.SECONDS) //在1秒以內，發送的最舊參數才顯示
            .blockingSubscribe { item ->
                println("throttleFirst : $item ")
            }

        //throttleLast(同sample)
        io.reactivex.rxjava3.core.Observable.create<String> { emitter ->
            emitter.onNext("A")

            Thread.sleep(500)

            emitter.onNext("B")

            Thread.sleep(200)

            emitter.onNext("C")

            Thread.sleep(800)

            emitter.onNext("D")

            Thread.sleep(600)

            emitter.onNext("E")
            emitter.onComplete()
        }.subscribeOn(Schedulers.io())
            .throttleLast(1, TimeUnit.SECONDS) //在1秒以內，發送的最舊參數才顯示
            .blockingSubscribe { item ->
                println("throttleLast : $item ")
            }

        //throttleWithTimeout(同debounce)

        //throttleLatest
        io.reactivex.rxjava3.core.Observable.create<String> { emitter ->
            emitter.onNext("A")

            Thread.sleep(500)

            emitter.onNext("B")

            Thread.sleep(200)

            emitter.onNext("C")

            Thread.sleep(200)

            emitter.onNext("D")

            Thread.sleep(400)

            emitter.onNext("E")

            Thread.sleep(400)

            emitter.onNext("F")

            Thread.sleep(400)

            emitter.onNext("G")

            Thread.sleep(2000)

            emitter.onComplete()
        }.subscribeOn(Schedulers.io())
            .throttleLatest(1, TimeUnit.SECONDS) //先顯示第一個，在之後的1秒以內，發送的最新參數才顯示
            .blockingSubscribe { item ->
                println("throttleLatest : $item ")
            }

        //timeout
        io.reactivex.rxjava3.core.Observable.create<String> { emitter ->
            emitter.onNext("A")

            Thread.sleep(800)

            emitter.onNext("B")

            Thread.sleep(400)

            emitter.onNext("C")

            Thread.sleep(1200)

            emitter.onNext("D")

            emitter.onComplete()
        }.subscribeOn(Schedulers.io())
            .timeout(1, TimeUnit.SECONDS) //在規定時間內發出，才做顯示
            .blockingSubscribe { item ->
                println("timeout : $item ")
            }

        //skip(忽略前面)
        Observable.just(1, 2, 3, 4)
            .skip(2)
            .subscribe {
                println("skip : $it")
            }

        //skipLast(忽略後面)
        Observable.just(1, 2, 3, 4)
            .skipLast(2)
            .subscribe {
                println("skipLast : $it")
            }

        //take(拿取前面)
        Observable.just(1, 2, 3, 4)
            .take(2)
            .subscribe {
                println("take : $it")
            }

        //takeLast(拿取後面)
        Observable.just(1, 2, 3, 4)
            .takeLast(2)
            .subscribe {
                println("takeLast : $it")
            }
    }

}