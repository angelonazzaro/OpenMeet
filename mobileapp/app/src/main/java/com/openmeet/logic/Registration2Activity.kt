package com.openmeet.logic

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.openmeet.R
import com.openmeet.data.interest.InterestProxyDAO

class Registration2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_2)

        val instrTxt = findViewById<TextView>(R.id.instructionTxt)
        val continueBtn = findViewById<Button>(R.id.continueBtn)
        val email = intent.getStringExtra("email").toString()



        instrTxt.text = "Aggiungi dai 3 ai 6 interessi."

        //doImageUploadPhase()
        doInterestPhase()

        continueBtn.setOnClickListener {
            Toast.makeText(this, getSelectedCheckboxes().toString(), Toast.LENGTH_SHORT).show()
        }

    }

    fun doInterestPhase(){

        val snackbarView = findViewById<View>(R.id.auth_reg2_container)

        val interestLayout = findViewById<LinearLayout>(R.id.interestLayout)


        Thread {

            val ret = InterestProxyDAO(this).doRetrieveAll()

            if(ret == null)
                Snackbar.make(snackbarView, R.string.connection_error, Snackbar.LENGTH_SHORT).show()
            else{
                runOnUiThread {
                    for (interest in ret){
                        val check = CheckBox(this)
                        check.text = interest.description
                        interestLayout.addView(check)
                    }
                }

            }

        }.start()


        val filterField = findViewById<TextInputLayout>(R.id.filterField)
        filterField.editText?.doOnTextChanged { text, start, before, count ->
            if(text != null){
                for (checkbox in interestLayout.children)
                    if(checkbox is CheckBox) {
                        val textWords = checkbox.text.split(" ")
                        for(word in textWords){
                            if(word.length > text.length) {
                                if (word.substring(0, text.length)
                                        .equals(text.toString(), ignoreCase = true)
                                )
                                    checkbox.visibility = View.VISIBLE
                                else
                                    checkbox.visibility = View.GONE
                            }
                        }
                    }
            }

        }
    }

    fun getSelectedCheckboxes(): MutableList<String>? {
        val interestLayout = findViewById<LinearLayout>(R.id.interestLayout)

        val checkList = mutableListOf<String>()


        for (checkbox in interestLayout.children)
            if(checkbox is CheckBox && checkbox.isChecked)
                checkList.add(checkbox.text.toString())

        return checkList
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