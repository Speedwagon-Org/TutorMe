package com.speedwagon.tutorme.Profile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.speedwagon.tutorme.Discussion.DiscussionContent
import com.speedwagon.tutorme.Explore.ExploreAdapter
import com.speedwagon.tutorme.Explore.ExploreItem
import com.speedwagon.tutorme.LogoutDialog
import com.speedwagon.tutorme.Notification.ItemNotification
import com.speedwagon.tutorme.R
import com.speedwagon.tutorme.RemindMe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.lang.Exception
import kotlin.random.Random

private const val REQUEST_CODE_IMAGE_PICK = 0
private const val EXTRA_STATUS= "STATUS_STATE"
private var someStateValue = "kosong"
private var clicked = true

class profile_nav : Fragment(), ProfileAdapater.OnprofileClickListener{


    private  lateinit var auth: FirebaseAuth
    private lateinit var Database: FirebaseDatabase
    private lateinit var databaseReference : DatabaseReference
    private lateinit var userReference : DatabaseReference
    private lateinit var itemlist: ArrayList<profileitem>
    private lateinit var itemRecyclerView: RecyclerView
    private var imgUri : Uri? = null

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
        //Floating action button
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        val remind = view.findViewById<FloatingActionButton>(R.id.fab_remind)
        val logout = view.findViewById<FloatingActionButton>(R.id.LogOut)
        val edit = view.findViewById<FloatingActionButton>(R.id.fab_edit)
        fab.setOnClickListener {
            if(clicked){
                edit.visibility = View.VISIBLE
                remind.visibility = View.VISIBLE
                logout.visibility = View.VISIBLE
                clicked = false
            }
            else{
                remind.visibility = View.INVISIBLE
                logout.visibility = View.INVISIBLE
                edit.visibility = View.INVISIBLE
                clicked = true
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
        edit.setOnClickListener {
            val currentUsername: TextView = view.findViewById(R.id.usernameid)
            val input = currentUsername.text.toString()
            val bundle = Bundle()
            bundle.putString("data", input)
            val fragment = UpdateProfile()
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView, fragment)
                ?.commit()
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
                            Toast.makeText(context, "foto profile belum di set", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Snackbar.make(View(context),"something went wrong \n${error.message}", Snackbar.LENGTH_LONG).show()
            }

        })
        //buka galeri
        view.findViewById<Button>(R.id.upload).setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also{
                it.type = "image/*"
                startActivityForResult(it, REQUEST_CODE_IMAGE_PICK)
            }
        }

        itemRecyclerView = view.findViewById<RecyclerView>(R.id.sertifikatview)
        itemlist = arrayListOf()
        itemRecyclerView?.layoutManager = LinearLayoutManager(context)
        fetchData()


        return view
    }

    private fun fetchData(){
        auth = FirebaseAuth.getInstance()
        Database = FirebaseDatabase.getInstance("https://tutorme-78b90-default-rtdb.asia-southeast1.firebasedatabase.app/")
        databaseReference = Database.getReference("sertifikat/${auth.uid.toString()}/")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    itemlist.clear()
                    snapshot.children.forEach {
                        val ContentObj = it.value as HashMap<*, *>
                        val categories = profileitem()
                        with(categories){
                            try {
                                idimage = ContentObj["idimage"] as String
                            } catch (e : Exception){
                                Toast.makeText(context, "$e", Toast.LENGTH_SHORT).show()
                            }
                        }
                        itemlist.add(categories)
                    }
                    itemRecyclerView.adapter = ProfileAdapater(itemlist,this@profile_nav)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getRandomString(length: Int) : String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }

    //ambil gambar yang dipilih
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE_PICK){
            data?.data?.let {
                imgUri = it
            }
        }
        val id = getRandomString(8)
        auth = FirebaseAuth.getInstance()
        uploadImgToStorage("${auth.uid.toString()}+$id")
    }

    private fun uploadImgToStorage(filename : String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            imgUri?.let{
                Database = FirebaseDatabase.getInstance("https://tutorme-78b90-default-rtdb.asia-southeast1.firebasedatabase.app/")
                val imageRef = FirebaseStorage.getInstance().reference
                imageRef.child("sertifikat/$filename").putFile(imgUri!!)
                withContext(Dispatchers.Main){
                    Toast.makeText(context, "Certificate Upload Successfully", Toast.LENGTH_SHORT).show()
                    val userData = profileitem(
                        idimage = filename
                    )
                    Database.getReference("sertifikat/${auth.uid}").child("$filename/").setValue(userData)
                }
            }
            view?.findViewById<Button>(R.id.savepfp)?.visibility = View.INVISIBLE
        } catch (e : Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
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
                        try {
                            if(auth.currentUser!!.uid==it.key){
                                view?.findViewById<TextView>(R.id.usernameid)?.text = userObj["username"] as String
                            }
                        }catch (e: java.lang.Exception){
                            Toast.makeText(context, "loading . . .", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onprofileClicked(position: Int, item: profileitem) {
        val intent = Intent(context, sertifikat_preview::class.java).apply {
            putExtra("idimage", item.idimage)
        }
        startActivity(intent)
    }
}