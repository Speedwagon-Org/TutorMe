package com.speedwagon.tutorme.Profile

import android.app.Activity
import android.content.ContextWrapper
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.speedwagon.tutorme.Discussion.DiscussionContent
import com.speedwagon.tutorme.databinding.SertifikatPreviewBinding
import java.io.File
import kotlin.math.max
import kotlin.math.min

class sertifikat_preview:AppCompatActivity() {
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f
    private lateinit var imageView: ImageView
    private lateinit var binding: SertifikatPreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SertifikatPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageView = binding.imageView
        scaleGestureDetector = ScaleGestureDetector(this,Scalelistener())

        val imageRef = FirebaseStorage.getInstance().reference.child("sertifikat/${intent.getStringExtra("idimage").toString()}")
        val localFile = File.createTempFile("tempProfileImage", "png")
        imageRef.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            imageView.setImageBitmap(bitmap)
        }.addOnFailureListener{ e->
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
        }
        binding.Back.setOnClickListener {
            this.findActivity()?.onBackPressed()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        return true
    }
    private inner class Scalelistener : ScaleGestureDetector.SimpleOnScaleGestureListener(){
        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            scaleFactor *= scaleGestureDetector.scaleFactor
            scaleFactor = max(0.1f, min(scaleFactor,10.0f))
            imageView.scaleX = scaleFactor
            imageView.scaleY = scaleFactor
            return true
        }
    }
    private fun findActivity(): Activity? {
        var context = this
        while(context is ContextWrapper){
            if(context is Activity)
                return context
            context = context.baseContext as sertifikat_preview
        }
        return null
    }
}
