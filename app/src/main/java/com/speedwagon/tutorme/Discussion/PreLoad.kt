package com.speedwagon.tutorme.Discussion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.speedwagon.tutorme.Home.home
import com.speedwagon.tutorme.R
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PreLoad : Fragment() {
    private var mhs = listOf(
        ItemDiscussion("Asep","Halo"),ItemDiscussion("Yanto","Halo juga"),ItemDiscussion("Yono","Hmmm")
        ,ItemDiscussion("SiPalingTahu","Ni diskusi apa sih ?"),ItemDiscussion("Kipli","Bang hatiku kok terasa sepi sekali")
        ,ItemDiscussion("Kiplili","Ga usah ngeBadut"),ItemDiscussion("Zhen","Valo yok ges")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pre_load, container, false)
        val btn_yes = view?.findViewById<Button>(R.id.btn_yes)
        val btn_no = view?.findViewById<Button>(R.id.btn_no)

        btn_yes?.setOnClickListener {
            executeLoadData()
        }
        btn_no?.setOnClickListener {
            val home = home()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentContainerView,home)?.commit()

        }
        return view
    }


    private fun executeLoadData() {
        val btn_yes = view?.findViewById<Button>(R.id.btn_yes)
        val btn_no = view?.findViewById<Button>(R.id.btn_no)
        val myProgress = view?.findViewById<ProgressBar>(R.id.myProgress)

        btn_no?.isEnabled = false
        btn_yes?.isEnabled = false
        myProgress?.progress=0
        var database = CommentTransaction(this.requireContext())
        doAsync {
            for (userData in mhs) {
                database.addUser(userData)
                uiThread {
                    myProgress!!.progress += myProgress!!.max/mhs.size
                    Log.w("Progress","${myProgress?.progress}")
                }
            }
            uiThread {
                finishThisActivity()
            }
        }
    }

    fun finishThisActivity(){
        var myFirstRunSharePref = FirstRunSharePref(this.requireContext())
        myFirstRunSharePref.firstRun = false
        val intent = Intent(context, DiscussionContent::class.java)
        startActivity(intent)
    }

}