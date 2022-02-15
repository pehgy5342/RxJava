package com.example.rxjava

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.CompletableObserver
import io.reactivex.MaybeObserver
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.*
import java.util.concurrent.TimeUnit

class FilterActivity : AppCompatActivity() {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)


        //Map & Filter
        Observable
            .just(1, 2, 3)
            .filter { it % 2 == 1 }
            .map { it * 2 }
            .subscribe {
                println("Filter : $it")
            }

        //distinct(過濾掉重複)
//        Observable.just("6", 1, true, 0.5)
        Observable.just(1, 1, 2, 2, 1, 3)
            .distinct()
            .subscribe(object : Observer<Any> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: Any) {
                    println("Distinct : $t")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }
            })

        //ElementAt
        Observable.just(1, 2, 3, 4)
            .elementAt(1)
            .subscribe {
                println("ElementAt : $it")
            }

        //Filter
        Observable.just(1, 2, 3, 4, 5, 6)
            .filter { t -> t > 3 }
            .subscribe {
                println("$it")
            }


        //FirstElement
        Observable.just(5,6,7)
            .firstElement()
            .subscribe {
                println("FirstElement $it")
            }


        //IgnoreElements
        Observable.just(1,2,3,4,5,6,7,8)
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


        //lastElement
        Observable.just(1,2,3)
            .lastElement()
            .subscribe {
                println("lastElement : $it")
            }

        //sample
        val time = Observable.interval(0,0,TimeUnit.SECONDS)
        time
            .sample(3,TimeUnit.SECONDS)
            .subscribe {
                println("Sample :$it")
            }

        //skip(忽略)
        Observable.just(1,2,3,4)
            .skip(2)
            .subscribe {
                println("skip : $it")
            }

        //skipLast(忽略後面)
        Observable.just(1,2,3,4)
            .skipLast(2)
            .subscribe {
                println("skipLast : $it")
            }

        //take(拿取)
        Observable.just(1,2,3,4)
            .take(2)
            .subscribe {
                println("take : $it")
            }

        //takeLast(拿取後面)
        Observable.just(1,2,3,4)
            .takeLast(2)
            .subscribe {
                println("takeLast : $it")
            }
    }

}