package com.example.rxjava

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import java.lang.Exception
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

/**
 * Operator
 **/
class CreateActivity : AppCompatActivity() {
    @SuppressLint("CheckResult")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        //Empty(不發射數據，只會調用onComplete)
        Observable.empty<String>().subscribeBy(
            onNext = { println(" next ") },
            onComplete = { println(" complete ") },
            onError = { println(" error ") }
        )

        //Never(甚麼也不會做)
        Observable.never<String>().subscribeBy(
            onNext = { println(" next ") },
            onComplete = { println(" complete ") },
            onError = { println(" error ") }
        )

        //Error(不發射數據，以錯誤終止，只會調用onError)
        Observable.error<Exception>(Exception()).subscribeBy(
            onNext = { println(" next ") },
            onComplete = { println(" complete ") },
            onError = { println(" error ") }
        )


        //Interval(幾秒後開始顯示,隔幾秒再顯示)
        Observable.interval(5000, 3000, TimeUnit.MILLISECONDS)
            .subscribe {
                Log.e("Interval", "Hello~")
            }


        //Range(指定範圍顯示)
        Observable.range(7, 5)
            .subscribe {
                println("Range :$it")
            }

        //Repeat(設定範圍讓他重複出現)
        Observable.range(5, 5).repeat().subscribe { println("repeat : $it") }


        //Timer(幾秒後開始顯示)
        Observable.timer(3000, TimeUnit.MILLISECONDS)
            .subscribe {
                Log.e("Timer", "Timer")
            }
    }
}