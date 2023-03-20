package com.demo.intentservice

import android.app.IntentService
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.demo.intentservice.MainActivity.Companion.Tag
import java.util.*

class MyIntentServiceBinder : IntentService("MyService"){
    private var randomNumber:Int? = null
    private val mBinder = MyBinder()
    override fun onBind(intent: Intent?): IBinder? {
        Log.e(Tag, "onBind() called")
        return mBinder
    }

    inner class MyBinder : Binder() {
        fun getMyService(): MyIntentServiceBinder {
            return this@MyIntentServiceBinder
        }
    }

    fun getRandomNumber(): Int? {
        return randomNumber
    }
    override fun onHandleIntent(intent: Intent?) {
        Log.e(Tag,"onHandleIntent() called")
        for (i in 1..10){
            Thread.sleep(1000)
            randomNumber = Random().nextInt(100)
            Log.e(Tag, "RandomNumber: $randomNumber")
        }
    }
}