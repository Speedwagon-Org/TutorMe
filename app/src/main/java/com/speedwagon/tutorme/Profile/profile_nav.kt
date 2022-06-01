package com.speedwagon.tutorme.Profile

import android.annotation.SuppressLint
import android.database.Cursor
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.speedwagon.tutorme.LogoutDialog
import com.speedwagon.tutorme.R
import com.speedwagon.tutorme.RemindMe
import java.io.BufferedReader
import java.io.File
import java.lang.Exception


private const val EXTRA_STATUS= "STATUS_STATE"
private var someStateValue = "kosong"
private var clicked = false

class profile_nav : Fragment() {


    private  lateinit var auth: FirebaseAuth
    private lateinit var Database: FirebaseDatabase
    private lateinit var databaseReference : DatabaseReference
    private lateinit var userReference : DatabaseReference

    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //username dari firebase
        FetchDataUser()

        //onSaveinstance
        val view = inflater.inflate(R.layout.fragment_profile_nav, container, false)
        val textView: TextView = view.findViewById(R.id.usernameid)
        textView.text = savedInstanceState?.getString(EXTRA_STATUS)

        //sqlite
        try {
            val deskripsi : TextView? = view?.findViewById(R.id.deskripsi)
            val db = DBhelper(this.requireContext(), null)
            val cursor = db.fetch()

            if(cursor!!.moveToFirst())
            {
                deskripsi?.text = (cursor.getString(cursor.getColumnIndex(DBhelper.DES)))
            }
            else
            {
                deskripsi?.text = "Halo !!! Apa motivasi mu!!"
            }
        }catch (e: java.lang.Exception){
            Toast.makeText(
                context,
                "Error load description",
                Toast.LENGTH_SHORT).show()
        }

        //to update profile
        val btn = view?.findViewById<Button>(R.id.EditProfile)
        btn?.setOnClickListener {
            val currentUsername: TextView = view.findViewById(R.id.usernameid)
            val input = currentUsername.text.toString()
            val bundle = Bundle()
            bundle.putString("data", input)
            val fragment = UpdateProfile()
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView, fragment)
                ?.commit()
        }

        //Floating action button
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        val remind = view.findViewById<FloatingActionButton>(R.id.fab_remind)
        val logout = view.findViewById<FloatingActionButton>(R.id.LogOut)
        fab.setOnClickListener {
            clicked = !clicked
            if(clicked){
                remind.visibility = View.VISIBLE
                logout.visibility = View.VISIBLE
            }
            else{
                remind.visibility = View.INVISIBLE
                logout.visibility = View.INVISIBLE
            }
        }
        remind.setOnClickListener{
            val remindMe = RemindMe()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentContainerView,remindMe)?.commit()
        }
        logout.setOnClickListener{
            val dialog = LogoutDialog()
            dialog.show(parentFragmentManager,"dialogLogut")
        }

        //update pfp
        val pfp = view?.findViewById<ImageView>(R.id.pfp_profile_nav)
        auth = FirebaseAuth.getInstance()
        userReference = FirebaseDatabase.getInstance("https://tutorme-78b90-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")
        userReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    if (snapshot.exists() && it.key == auth.currentUser!!.uid){
                        val userData = it.value as HashMap <*,*>

                        // User Profile Handler
                        val imageRef = FirebaseStorage.getInstance().reference.child("pfp_user/user_${auth.uid}_profile_pict")
                        val localFile = File.createTempFile("tempProfileImage", "png")
                        imageRef.getFile(localFile).addOnSuccessListener {
                            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                            pfp?.setImageBitmap(bitmap)
                        }.addOnFailureListener{ e->
                            Snackbar.make(View(context), "${e.message}", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Snackbar.make(View(context),"something went wrong \n${error.message}", Snackbar.LENGTH_LONG).show()
            }

        })

        return view
    }

    // onSaveInstanceState

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val usernameid= view?.findViewById<TextView>(R.id.usernameid)
        outState.putString(EXTRA_STATUS,usernameid?.text.toString())
    }


    private fun FetchDataUser(){
        auth = FirebaseAuth.getInstance()
        Database = FirebaseDatabase.getInstance("https://tutorme-78b90-default-rtdb.asia-southeast1.firebasedatabase.app/")
        databaseReference = Database.getReference("User/")
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    if(snapshot.exists()){
                        val userObj = it.value as HashMap <*,*>
                        if(auth.currentUser!!.uid==it.key){
                            view?.findViewById<TextView>(R.id.usernameid)?.text = userObj["username"] as String
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}