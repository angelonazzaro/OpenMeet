package com.openmeet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import kotlin.math.log

class PersonaDataRegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persona_data_registration)

        val birthday = findViewById<TextInputLayout>(R.id.birthdayField)

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Seleziona la data di nascita")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
            .build()

        datePicker.show(supportFragmentManager,"")

        datePicker.addOnPositiveButtonClickListener {
            Toast.makeText(this, "" + datePicker.selection + "",  Toast.LENGTH_LONG).show()
        }

        birthday.setOnClickListener{

        }



        //TODO: add all the logic
        //TODO: find a way to get the value and then paste it birthdayField
        //TODO: set onClickListener on birthdayField, so in this way datepicker will be shown

    }
}