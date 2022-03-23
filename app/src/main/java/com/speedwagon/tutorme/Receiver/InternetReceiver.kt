package com.speedwagon.tutorme.Receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.speedwagon.tutorme.application

class InternetReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
            val isConnected = checkConnection(context)

            if (connectionReceiverListener != null)
                connectionReceiverListener!!.onNetworkConnectionChange(isConnected)
        }

        interface ConnectionReceiverListener{
            fun onNetworkConnectionChange(isConnected : Any)
        }

        companion object{
            var connectionReceiverListener: ConnectionReceiverListener? = null
            val isConnected: Boolean
                get() {
                    val cn = application.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val activeNetwork = cn.activeNetworkInfo
                    return (activeNetwork != null && activeNetwork.isConnectedOrConnecting)
                }
        }

        private fun checkConnection(context: Context?): Boolean{
            val cm = context?.getSystemService((Context.CONNECTIVITY_SERVICE)) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return (activeNetwork != null && activeNetwork.isConnectedOrConnecting)
        }
}

