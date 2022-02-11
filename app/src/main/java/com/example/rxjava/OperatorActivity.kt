package com.example.rxjava

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Operator
 **/
class OperatorActivity : AppCompatActivity() {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operator)


        //Timer
        Observable.timer(3000,TimeUnit.MILLISECONDS)
            .subscribe {
                Log.e("111","1111")
            }

        //Range
        Observable.range(7,5)
            .subscribe {
                println(it)
            }


        //Interval
        Observable.interval(3000, 3000, TimeUnit.MILLISECONDS)
            .subscribe{
//                Log.e("555","Hello~")
            }

        //Map & Filter
        Observable
            .just(1, 2, 3)
            .filter { it % 2 == 1 }
            .map { it * 2 }
            .subscribe {
                println(it)
            }

    }
}