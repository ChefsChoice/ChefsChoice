package com.example.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import kotlin.math.roundToInt

class MainActivityMLK : AppCompatActivity() {
    // Request code for camera intent
    // Secret handshake that authenticates for correct result
    val REQUEST_IMAGE_CAPTURE = 1

    // Create variables for RecyclerView
    val labelList = mutableListOf<String>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: LabelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.tvLabel)
        manager = LinearLayoutManager(this)

        // Click listener set on Button to launch camera activity
        findViewById<FloatingActionButton>(R.id.button).setOnClickListener {
            dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun dispatchTakePictureIntent(requestCode: Int) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, requestCode)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check if requestCode matches REQUEST_IMAGE_CAPTURE value
        // Check if result was OK
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Unpack Intent data to retrieve image as Bitmap
            val imageBitmap = data?.extras?.get("data") as Bitmap

            // Create InputImage object
            val image = InputImage.fromBitmap(imageBitmap, 0)
            // Create instance of ImageLabeler
            val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
            // Process InputImage using labeler
            labeler.process(image)
                .addOnSuccessListener { labels ->
                    // Task completed successfully
                    for (label in labels) {
                        val text = label.text
                        val confidence = label.confidence * 100
                        val index = label.index
                        labelList.add("$text : ${"%.1f".format(confidence)}%")
                        Log.d("ImageRecognition", "$text : $confidence")
                    }
                    recyclerView.apply {
                        myAdapter = LabelAdapter(labelList)
                        layoutManager = manager
                        adapter = myAdapter
                    }
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    // ...
                }

           // val options = BarcodeScannerOptions.Builder()
            //    .setBarcodeFormats(
            //        Barcode.FORMAT_QR_CODE,
             //       Barcode.FORMAT_AZTEC)
            //   .build()

            // Bind Bitmap image to ImageView. Unsure of if this is still necessary
           // findViewById<ImageView>(R.id.capturedImage).setImageBitmap(imageBitmap)
        }
    }
}

