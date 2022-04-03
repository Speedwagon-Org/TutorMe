package com.speedwagon.tutorme.LoginRegister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.speedwagon.tutorme.R
import com.speedwagon.tutorme.databinding.FragmentRegisterBinding

class Register : Fragment() {

    private lateinit var Database: FirebaseDatabase
    private lateinit var databaseReference : DatabaseReference
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnToRegister = view.findViewById<Button>(R.id.buttonRegister)
        val btnToLog = view.findViewById<Button>(R.id.toLogin)

        //input ke database// menadaftarkan user baru
        btnToRegister.setOnClickListener {
            try {
                if (AddNewUser()) {
                    Toast.makeText(context, "Account Successfully Created!", Toast.LENGTH_SHORT).show()

                    val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.logResLayout, Login())
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()

                } else {
                    Toast.makeText(context, "Failed To Create Your Account Created!\nMake Sure You Fill It Correctly", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Failed To Create Your Account Created!", Toast.LENGTH_SHORT).show()
            }
        }

        //tombol menuju fragment login
        btnToLog.setOnClickListener{
            val loginFragment = Login()
            val transaction : FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.logResLayout,loginFragment)
            transaction.commit()
        }
    }

    //check format email dan password
    private fun emailCheck (email : String) : Boolean =  android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun passCheck (password : String) : Boolean {
        if (password.length < 8){
            Toast.makeText(context, "Minimum password length : 8", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    //input data dari register ke firebase
    private fun AddNewUser(): Boolean {
        Database = FirebaseDatabase.getInstance("https://tutorme-78b90-default-rtdb.asia-southeast1.firebasedatabase.app/")
        databaseReference = Database.getReference("User/")

        val userData = DataUser(
            username = view?.findViewById<EditText>(R.id.inputUsername)?.text.toString(),
            email = view?.findViewById<EditText>(R.id.inputEmail)?.text.toString(),
            pass = view?.findViewById<EditText>(R.id.inputPassword)?.text.toString()
        )
        userData.apply {
            if (
                username?.replace("\\s".toRegex(), "") != "" &&
                email?.replace("\\s".toRegex(), "") != "" &&
                pass?.replace("\\s".toRegex(), "") != ""
            ){
                if (emailCheck(email.toString()) && passCheck(pass.toString())){
                    databaseReference.child("user_$username/").setValue(userData)
                    return true
                } else {
                    Toast.makeText(context, "Please Check Your Email or Password", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return false
    }
}
