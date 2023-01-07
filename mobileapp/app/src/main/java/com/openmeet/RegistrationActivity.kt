package com.openmeet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_registration)

        val birthday = findViewById<TextInputLayout>(R.id.birthdayField)

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Seleziona la data di nascita")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
            .setCalendarConstraints(
                CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointBackward.now())
                    .build()
            )
            .build()


        birthday.editText?.setOnClickListener{
            datePicker.show(supportFragmentManager,"birth_picker")
        }

        datePicker.addOnPositiveButtonClickListener {
            birthday.editText?.setText(getDate(datePicker.selection, "dd/MM/yyyy"))
        }

        datePicker.addOnNegativeButtonClickListener{
            Toast.makeText(this,"Ricorda di inserire sempre la data di nascita", Toast.LENGTH_LONG).show()
        }

        //TODO: add all the logic
        //TODO: set onClickListener on birthdayField, so in this way datepicker will be shown

    }

    fun getDate(millis: Long?,format: String): String? {
        val formatter = SimpleDateFormat(format)
        return formatter.format(millis)
    }


}
