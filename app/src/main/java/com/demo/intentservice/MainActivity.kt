package com.demo.intentservice

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.demo.intentservice.MyIntentService.Companion.ACTION_MESSAGE
import com.demo.intentservice.MyIntentService.Companion.RESULT
import com.demo.intentservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val Tag ="MyIntentService"
    }
    private var mainActivityBinding: ActivityMainBinding? = null
    private val broadcastReceiver = MyBroadcastReceiver()
    private var mService: MyIntentServiceBinder? = null
    private var serviceConnection : ServiceConnection? = null
    private var mIsBounded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding?.root)
        initClickListeners()
        // Using binder
        bindBoundService()
        // Using broadcast
        startMyService()
        registerIntentService()
    }

    private fun registerIntentService() {
        val filter = IntentFilter()
        filter.addAction("com.example.myapp.ACTION_MY_MESSAGE")
        registerReceiver(broadcastReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    private fun startMyService() {
        startService(Intent(this,MyIntentService::class.java))
    }

    private fun initClickListeners() {
        mainActivityBinding?.button?.setOnClickListener {
            if (mIsBounded) {
                mService?.getRandomNumber()?.let {
                    mainActivityBinding?.tvRandomNumber?.text = it.toString()
                }
            }
        }
    }

    class MyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent?.action?.equals(ACTION_MESSAGE) == true){
                Log.e(Tag, "onReceive called: ${intent.getIntExtra(RESULT,-100)}")
            }
        }
    }

    private fun bindBoundService(){
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, bidner: IBinder?) {
                Log.e(Tag,"onServiceConnected called")
                mService = (bidner as MyIntentServiceBinder.MyBinder).getMyService()
                mIsBounded = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.e(Tag,"onServiceDisconnected called")
                mIsBounded = false
            }
        }

        Intent(this, MyIntentServiceBinder::class.java).also {
            startService(it)
            bindService(it, serviceConnection as ServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }
}