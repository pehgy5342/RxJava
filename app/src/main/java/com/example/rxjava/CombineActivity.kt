package com.example.rxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

/*
 結合類
*/
class CombineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combine)


        //and/then/when

        //CombineLatest
        val observable1 = Observable.interval(400, TimeUnit.MILLISECONDS)
        val observable2 = Observable.interval(250, TimeUnit.MILLISECONDS)

        Observable.combineLatest(observable1, observable2, object : BiFunction<Long, Long, String> {
            override fun apply(t1: Long, t2: Long): String {
                return "observable1 = $t1 observable2 = $t2"
            }
        }).take(2).subscribe { println("CombineLatest : $it") }

        //join

        //merge
        val odd = Observable.just(1, 3, 5)
        val even = Observable.just(2, 4, 6)
        Observable.merge(odd, even)
            .subscribe {
                println("merge : $it")
            }

        //startWith(指定在被觀察者的前面插入資料)
        val names = Observable.just("Spock", "McCoy");
        val otherNames = Observable.just("Git", "Code","8");
        names.startWith(otherNames).subscribe { println("startWith : $it") }


        //switch
        val firstObservable = Observable.interval(600, TimeUnit.MILLISECONDS)
        val secondObservable = Observable.interval(100, TimeUnit.MILLISECONDS)

        //zip
        val data1 = Observable.intervalRange(0, 1, 1, 1, TimeUnit.SECONDS).map { id -> "A" + id }
        val data2 = Observable.intervalRange(0, 2, 2, 1, TimeUnit.SECONDS).map { id -> "B" + id }
        Observable.zip(data1, data2, BiFunction<String, String, String> { t1, t2 -> "$t1 $t2" })
            .subscribe {
                println("zip : $it")
            }

    }
}