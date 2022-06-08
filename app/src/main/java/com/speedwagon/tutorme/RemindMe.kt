package com.speedwagon.tutorme

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.speedwagon.tutorme.Receiver.EXTRA_PESAN
import com.speedwagon.tutorme.Receiver.RemindReceiver
import java.text.SimpleDateFormat
import java.util.*

class RemindMe: Fragment(){
    private var cal = Calendar.getInstance()

    @SuppressLint("SimpleDateFormat")
    //set text timer setelah dipilih waktunya(Timer belum berjalan)
    private fun remindTimePicker() :TimePickerDialog.OnTimeSetListener{
        val timeSetListener = object : TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(Tm: TimePicker?, hourOfDay: Int, minute: Int) {
                cal.set(Calendar.HOUR_OF_DAY,hourOfDay)
                cal.set(Calendar.MINUTE,minute)
                val remindTime = view?.findViewById<TextView>(R.id.remindTime)
                remindTime?.text = setTimeFormat() // lanjut ke setTimeFormat
            }

        }
        return timeSetListener
    }
    private fun setTimeFormat() : String{
        val format = "HH:mm"
        val sdf = SimpleDateFormat(format)
        return sdf.format(cal.time)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_remindme,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // memanggil Time Picker Dialog
        val stp = view.findViewById<Button>(R.id.showTimePicker)
        stp.setOnClickListener {
            TimePickerDialog(context,remindTimePicker(),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show() }

        val mAlarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var mPendingIntent: PendingIntent? = null
        var sendIntent: Intent? = null
        val setRemindMe = view.findViewById<Button>(R.id.setRemind)
        val remindTime = view.findViewById<TextView>(R.id.remindTime)
        val remindMessage = view.findViewById<EditText>(R.id.remindMessage)
        val cancelRemind = view.findViewById<Button>(R.id.cancelRemind)

        setRemindMe.setOnClickListener{
            if(mPendingIntent!=null){
                mAlarmManager.cancel(mPendingIntent)
                mPendingIntent?.cancel()
            }

            val setRemindTime = Calendar.getInstance()
            val time = remindTime.text.split(":")
            setRemindTime.set(Calendar.HOUR_OF_DAY,time[0].toInt())
            setRemindTime.set(Calendar.MINUTE,time[1].toInt())
            setRemindTime.set(Calendar.SECOND,0)

            sendIntent = Intent(context,RemindReceiver::class.java)
            sendIntent?.putExtra(EXTRA_PESAN,remindMessage.text.toString())
            mPendingIntent = PendingIntent.getBroadcast(context,101, sendIntent!!,0)
            mAlarmManager.set(AlarmManager.RTC, setRemindTime.timeInMillis,mPendingIntent)
            Toast.makeText(context,"Remind Me has been set to ${remindTime.text}:00",Toast.LENGTH_SHORT).show()
        }
        //cancel timer
        cancelRemind.setOnClickListener {
            if(mPendingIntent!=null){
                mAlarmManager.cancel(mPendingIntent)
                mPendingIntent?.cancel()
                Toast.makeText(context,"Remind Me canceled.",Toast.LENGTH_SHORT).show()
            }
        }
    }
}

