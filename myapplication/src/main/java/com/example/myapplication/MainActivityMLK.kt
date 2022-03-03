package com.example.myapplication

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.google.zxing.integration.android.IntentIntegrator
import kotlin.math.roundToInt

class MainActivityMLK : AppCompatActivity() {

        private lateinit var mQrResultLauncher : ActivityResultLauncher<Intent>

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            // Alternative to "onActivityResult", because that is "deprecated"
            mQrResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if(it.resultCode == Activity.RESULT_OK) {
                    val result = IntentIntegrator.parseActivityResult(it.resultCode, it.data)

                    if(result.contents != null) {
                        // Do something with the contents (this is usually a URL)
                        println(result.contents)
                    }
                }
            }

            // Starts scanner on Create of Overlay (you can also call this function using a button click)
            startScanner()
        }


        // Start the QR Scanner
        private fun startScanner() {
            val scanner = IntentIntegrator(this)
            // QR Code Format
            scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            // Set Text Prompt at Bottom of QR code Scanner Activity
            scanner.setPrompt("QR Code Scanner Prompt Text")
            // Start Scanner (don't use initiateScan() unless if you want to use OnActivityResult)
            mQrResultLauncher.launch(scanner.createScanIntent())
        }
    }