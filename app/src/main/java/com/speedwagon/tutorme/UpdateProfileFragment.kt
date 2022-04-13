package com.speedwagon.tutorme

import android.app.Activity
import android.app.AlertDialog
import android.content.ContextWrapper
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.speedwagon.tutorme.Discussion.DiscussionContent


class UpdateProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_profile, container, false)
        val textView: TextView = view.findViewById(R.id.currentname)

        val args = this.arguments
        val inputdata = args?.get("data")
        textView.text = inputdata.toString()

        view.findViewById<Button>(R.id.Back).setOnClickListener {
            this.findActivity()?.onBackPressed()
        }
    }
        //Tombol Kembali
        private fun findActivity(): Activity{
            var context = this
            while (context is ContextWrapper){
                if (context is Activity)
                    return context
                context = context.baseContext as DiscussionContent
            }
            return null
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView,profile_nav())?.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        val btn : Button = view.findViewById(R.id.ConfirmUpdate)
        btn.setOnClickListener{
            val editText : EditText = view.findViewById(R.id.UsernameUpdateEditText)
            val input = editText.text.toString()
            val bundle = Bundle()
            bundle.putString("data",input)
            val fragment = profile_nav()
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView,fragment)?.commit()
        }
        return view
    }

    private fun findActivity(): Activity? {

    }
}
