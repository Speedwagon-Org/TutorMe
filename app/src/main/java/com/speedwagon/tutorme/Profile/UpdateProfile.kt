package com.speedwagon.tutorme.Profile

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.speedwagon.tutorme.Discussion.ItemDiscussion
import com.speedwagon.tutorme.Explore.ExploreAdapter
import com.speedwagon.tutorme.Explore.ExploreItem
import com.speedwagon.tutorme.LoginRegister.DataUser
import com.speedwagon.tutorme.LoginRegister.Login
import com.speedwagon.tutorme.R
import com.speedwagon.tutorme.RemindMe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Comment
import java.io.File

private const val REQUEST_CODE_IMAGE_PICK = 0

class UpdateProfile : Fragment() {

    private  lateinit var auth: FirebaseAuth
    private lateinit var userReference : DatabaseReference
    private var imgUri : Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_profile, container, false)
        val textView : TextView = view.findViewById(R.id.currentname)

        val savepfp = view?.findViewById<Button>(R.id.savepfp)
        savepfp?.setOnClickListener{
            auth = FirebaseAuth.getInstance()
            uploadImgToStorage("user_${auth.uid}_profile_pict")
        }

        val args = this.arguments
        val inputdata = args?.get("data")
        textView.text = inputdata.toString()

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView,profile_nav())?.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        //update nama
        val btn : Button = view.findViewById(R.id.ConfirmUpdate)
        btn.setOnClickListener{
            FetchDataUser()
            view.findViewById<TextView>(R.id.currentname).text = view.findViewById<EditText>(R.id.UsernameUpdateEditText).text
            Toast.makeText(
                context,
                "Your username has been change",
                Toast.LENGTH_SHORT).show()
        }
        //Pick picture
        val editProfilePicture = view.findViewById<ImageView>(R.id.editpfp)
        editProfilePicture.setOnClickListener{
            Intent(Intent.ACTION_GET_CONTENT).also{
                it.type = "image/*"
                startActivityForResult(it, REQUEST_CODE_IMAGE_PICK)
            }
        }

        val pfp = view?.findViewById<ImageView>(R.id.pfp)
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
    private fun FetchDataUser(){
        val editText = view?.findViewById<EditText>(R.id.UsernameUpdateEditText)?.text.toString()
        auth = FirebaseAuth.getInstance()
        userReference = FirebaseDatabase.getInstance("https://tutorme-78b90-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")
        userReference.child("${auth.uid}").child("username").setValue(editText)


    }

    // Open File Explorer handler
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE_PICK){
            data?.data?.let {
                imgUri = it
                view?.findViewById<ImageView>(R.id.pfp)?.setImageURI(it)
            }
            view?.findViewById<Button>(R.id.savepfp)?.visibility = View.VISIBLE
        }
    }

    // Replace Profile Picture Handler
    private fun uploadImgToStorage(filename : String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            imgUri?.let{
                val imageRef = FirebaseStorage.getInstance().reference
                // Delete old profile pict
                imageRef.child("pfp_user/$filename").delete()

                // Upload new Profile Pict
                imageRef.child("pfp_user/$filename").putFile(imgUri!!)
                withContext(Dispatchers.Main){
                    Toast.makeText(context, "Image Updated Successfully", Toast.LENGTH_SHORT).show()
                }
            }
            view?.findViewById<Button>(R.id.savepfp)?.visibility = View.INVISIBLE
        } catch (e : Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }


}