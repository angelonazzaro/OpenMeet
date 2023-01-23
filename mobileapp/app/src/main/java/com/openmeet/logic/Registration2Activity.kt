package com.openmeet.logic

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.openmeet.R

class Registration2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_2)

        val instrTxt = findViewById<TextView>(R.id.instructionTxt)
        val continueBtn = findViewById<Button>(R.id.continueBtn)
        val email = intent.getStringExtra("email").toString()

        instrTxt.text = "Aggiungi foto"

        doImageUploadPhase()

    }

    fun doInterestPhase(){

    }

    fun doBiographyPhase(){

    }

    fun doImageUploadPhase(){

        val imageLayout = findViewById<ImageView>(R.id.imageView2)

        val pickMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(4)) { uris ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uris.isNotEmpty()) {
                Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
                imageLayout.setImageURI(uris[uris.size - 1])
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))


    }
}