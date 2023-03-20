package com.demo.intentservice

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.demo.intentservice.MainActivity.Companion.Tag
import java.util.*

class MyIntentService : IntentService("MyService"){
    private var randomNumber:Int? = null
    override fun onHandleIntent(intent: Intent?) {
        Log.e(Tag,"onHandleIntent() called")
        for (i in 1..20){
            Thread.sleep(1000)
            randomNumber = Random().nextInt(100)
            Log.e(Tag, "RandomNumber_NEW: $randomNumber")
        }
        sendBroadCast()
    }

    private fun sendBroadCast() {
        val broadcastIntent = Intent()
        broadcastIntent.action = ACTION_MESSAGE
        broadcastIntent.putExtra(RESULT, randomNumber)
        sendBroadcast(broadcastIntent)
    }

    companion object{
        const val ACTION_MESSAGE = "com.example.myapp.ACTION_MY_MESSAGE"
        const val RESULT = "result"
    }
}