package com.openmeet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_registration)

        val birthday = findViewById<TextInputLayout>(R.id.birthdayField)

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Seleziona la data di nascita")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
            .build()

        datePicker.show(supportFragmentManager,"birth_picker")

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