package com.example.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.akarnokd.rxjava2.math.MathObservable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.subscribers.TestSubscriber
import java.util.concurrent.TimeUnit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

class MathActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_math)


        //average
        val averageObservable = Observable.fromArray(1, 2, 3, 4, 5)
        MathObservable.averageDouble(averageObservable)
            .subscribe { println("average : $it") }


        //Concat
        val alphabets1 =
            Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS).map { id -> "A" + id }
        val alphabets2 =
            Observable.intervalRange(0, 3, 2, 1, TimeUnit.SECONDS).map { id -> "B" + id }
        Observable.concat(alphabets1, alphabets2).subscribe {
            println("Concat :$it")
        }

        //Reduce
        Observable.just(0, 1, 2, 3)
            .reduce { t1, t2 -> t1 + t2 }.subscribe {
                println("Reduce : $it")
            }

        //Max
        val maxObservable = Observable.fromArray(1, 2, 3)
        MathObservable.max(maxObservable)
            .subscribe { println("Max : $it") }

        //Min
        val minObservable = Observable.fromArray(1, 2, 3)
        MathObservable.min(minObservable)
            .subscribe { println("Min : $it") }


        //Count
        val list = Observable.just(1, 2, 3)
        list.count()
            .subscribe(object : SingleObserver<Long> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(t: Long) {
                    println("Count : $t")
                }

                override fun onError(e: Throwable) {
                }
            })

        //Sum
        val sumObservable = Observable.fromArray(1, 2, 3)
        MathObservable.sumInt(sumObservable)
            .subscribe { println("Sum : $it") }

    }
}