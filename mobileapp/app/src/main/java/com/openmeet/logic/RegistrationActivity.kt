package com.openmeet.logic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.textfield.TextInputLayout
import com.openmeet.R
import java.security.InvalidParameterException
import java.text.SimpleDateFormat
import java.util.*
import com.google.android.material.datepicker.MaterialDatePicker as DatepickerMaterialDatePicker

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_registration)

        val birthday = findViewById<TextInputLayout>(R.id.birthdayField)
        val name = findViewById<TextInputLayout>(R.id.nameField)
        val surname = findViewById<TextInputLayout>(R.id.surnameField)
        val email = findViewById<TextInputLayout>(R.id.emailField)
        val password = findViewById<TextInputLayout>(R.id.passwordField)
        val confirmPassword = findViewById<TextInputLayout>(R.id.confirmPasswordField)
        val confirmButton = findViewById<Button>(R.id.registrationBtn)

        val datePicker = DatepickerMaterialDatePicker.Builder.datePicker()
            .setTitleText("Seleziona la data di nascita")
            .setSelection(DatepickerMaterialDatePicker.todayInUtcMilliseconds())
            .setInputMode(DatepickerMaterialDatePicker.INPUT_MODE_TEXT)
            .setCalendarConstraints(
                CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointBackward.now())
                    .build()
            )
            .build()

        confirmButton.setOnClickListener {
            val result =
                checkForm(name, surname, datePicker.selection, email, password, confirmPassword)
            if (result)
                Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "NOT SUCCESS", Toast.LENGTH_SHORT).show()
        }

        birthday.editText?.setOnClickListener {
            datePicker.show(supportFragmentManager, "birth_picker")
        }

        datePicker.addOnPositiveButtonClickListener {
            birthday.editText?.setText(getDate(datePicker.selection, "dd/MM/yyyy"))
        }

        datePicker.addOnNegativeButtonClickListener {
            Toast.makeText(this, "Ricorda di inserire sempre la data di nascita", Toast.LENGTH_LONG)
                .show()
        }

    }

    fun getDate(millis: Long?, format: String): String? {
        val formatter = SimpleDateFormat(format)
        return formatter.format(millis)
    }

    //this function if all values are correct
    fun checkForm(
        name: TextInputLayout,
        surname: TextInputLayout,
        birthday: Long?,
        email: TextInputLayout,
        password: TextInputLayout,
        confirmPassword: TextInputLayout
    ): Boolean {

        val nameText = name.editText?.text.toString()
        val surnameText = surname.editText?.text.toString()
        val emailText = email.editText?.text.toString()
        val passText = password.editText?.text.toString()
        val confPassText = confirmPassword.editText?.text.toString()
        var flag = true


        //check if name is blanck or empty
        if (nameText.isBlank() || nameText.isEmpty()) {
            Toast.makeText(this, "Non hai inserito il nome", Toast.LENGTH_LONG).show()
            flag = false
        }
        //check if name is blanck or empty
        if (surnameText.isBlank() || surnameText.isEmpty()) {
            Toast.makeText(this, "Non hai inserito il cognome", Toast.LENGTH_LONG).show()
            flag = false
        }
        //check if birthday date inserted has more 18 years old
        if (birthday != null) {
            val now = Calendar.getInstance().timeInMillis
            val diff = Calendar.getInstance()
            diff.timeInMillis = now - birthday
            if (diff.get(Calendar.YEAR) - 1970 < 18)
                Toast.makeText(this, "Hai meno di 18 anni", Toast.LENGTH_LONG).show()
            flag = false

        }
        //check mail
        if (!(emailText.isEmpty() || emailText.isBlank())) {

            if (!emailText.matches(Regex("^[a-z0-9!#$%&'*+=?^_`{|}~/-]+([.][a-z0-9!#$%&'*+=?^_`{|}~/-]+)*@([a-z0-9-]+[.])+[a-z]+$"))) {
                Toast.makeText(this, "Formato mail non valido", Toast.LENGTH_LONG).show()
                flag = false
            } else {
                val splittedEmail = splitEmail(emailText)
                if (splittedEmail.first.length > 64) {
                    Toast.makeText(this, "Parte locale TROPPO lunga", Toast.LENGTH_LONG).show()
                    flag = false
                } else if (splittedEmail.second.length > 255) {
                    Toast.makeText(this, "Parte dominio TROPPO lunga", Toast.LENGTH_LONG).show()
                    flag = false
                }
            }
        } else {
            Toast.makeText(this, "Non hai inserito la mail", Toast.LENGTH_LONG).show()
            flag = false
        }
        if (passText.isEmpty() || passText.isBlank()) {
            Toast.makeText(this, "Non hai inserito la password", Toast.LENGTH_LONG).show()
            flag = false
        }
        if (confPassText.isEmpty() || confPassText.isBlank()) {
            Toast.makeText(this, "Non hai inserito la password", Toast.LENGTH_LONG).show()
            flag = false
        }
        if (confPassText != passText) {
            Toast.makeText(this, "Le password inserite sono diverse", Toast.LENGTH_LONG).show()
            flag = false
        }

        return flag

    }

    fun splitEmail(email: String): Pair<String, String> {
        val str = email.split('@').toTypedArray()

        if (str.size != 2)
            throw InvalidParameterException("Probably invalid mail")
        return Pair(str[0], str[1])
    }


}

//TODO: change Toast in checkForm to error messagges
