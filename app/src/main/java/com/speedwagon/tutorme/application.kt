package com.speedwagon.tutorme

import android.app.Application
import com.speedwagon.tutorme.Receiver.InternetReceiver

class application : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun setConnectionListener(listener: InternetReceiver.ConnectionReceiverListener){
        InternetReceiver.connectionReceiverListener = listener
    }

    companion object{
        @get:Synchronized
        lateinit var instance: application
    }
}