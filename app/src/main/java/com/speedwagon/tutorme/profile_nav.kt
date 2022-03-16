package com.speedwagon.tutorme

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.speedwagon.tutorme.databinding.FragmentProfileNavBinding
import kotlinx.android.synthetic.main.fragment_profile_nav.*

private const val EXTRA_STATUS= "STATUS_STATE"
private  var someStateValue = "kosong"

class profile_nav : Fragment() {
    private lateinit var Username : TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile_nav, container, false)
        val textView: TextView = view.findViewById(R.id.usernameid)

        textView.text = savedInstanceState?.getString(EXTRA_STATUS)

        val args = this.arguments
        val inputdata = args?.get("data")
        textView.text = inputdata.toString()

        val btn = view.findViewById<Button>(R.id.UpdateProfileBtn)

        btn.setOnClickListener {
            val currentUsername: TextView = view.findViewById(R.id.usernameid)
            val input = currentUsername.text.toString()
            val bundle = Bundle()
            bundle.putString("data", input)
            val fragment = UpdateProfileFragment()
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView, fragment)
                ?.commit()
        }
        return view
    }

    // onSaveInstanceState

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_STATUS,usernameid.text.toString())
    }
}