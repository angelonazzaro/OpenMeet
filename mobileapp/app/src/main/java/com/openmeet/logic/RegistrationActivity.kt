package com.openmeet.logic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.openmeet.R
import com.openmeet.data.meeter.MeeterProxyDAO
import com.openmeet.shared.data.meeter.Meeter
import java.security.InvalidParameterException
import java.text.SimpleDateFormat
import java.util.*
import com.google.android.material.datepicker.MaterialDatePicker as DatepickerMaterialDatePicker

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_registration)

        val birthdayFld = findViewById<TextInputLayout>(R.id.birthdayField)
        val nameFld = findViewById<TextInputLayout>(R.id.nameField)
        val surnameFld = findViewById<TextInputLayout>(R.id.surnameField)
        val emailFld = findViewById<TextInputLayout>(R.id.emailField)
        val passwordFld = findViewById<TextInputLayout>(R.id.passwordField)
        val confirmPasswordFld = findViewById<TextInputLayout>(R.id.confirmPasswordField)


        val confirmButton = findViewById<Button>(R.id.registrationBtn)
        val snackbarView = findViewById<View>(R.id.auth_reg_container)
        val progressionIndicator = findViewById<View>(R.id.linearProgressIndicator)

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

        val str = intent.getStringExtra("email").toString()
        emailFld.editText?.setText(str)

        confirmButton.setOnClickListener {
            progressionIndicator.visibility = View.VISIBLE

            val result = true
               // checkForm(nameFld, surnameFld, datePicker.selection, birthdayFld, emailFld, passwordFld, confirmPasswordFld)


            if (result){
                val meeter = Meeter()
                meeter.meeterName = nameFld.editText?.text.toString()
                meeter.meeterSurname = surnameFld.editText?.text.toString()
                meeter.email = emailFld.editText?.text.toString()
                meeter.pwd = passwordFld.editText?.text.toString()
                meeter.birthDate = java.sql.Date(datePicker.selection!!)

                startActivity(
                    Intent(this, Registration2Activity::class.java).putExtra("email", meeter.email)
                )
                overridePendingTransition(0, 0)
                // Uncomment below and remove above
                /*Thread {
                    val retrieveMail = MeeterProxyDAO(this).doRetrieveByCondition("${Meeter.MEETER_EMAIL} = '${meeter.email}'")
                    if(retrieveMail == null)
                        Snackbar.make(snackbarView, R.string.connection_error, Snackbar.LENGTH_SHORT).show()
                    else
                        if(retrieveMail.size == 0){
                            if(!MeeterProxyDAO(this).doSave(meeter))
                                Snackbar.make(snackbarView, R.string.connection_error, Snackbar.LENGTH_SHORT).show()
                            else{
                                startActivity(
                                    Intent(this, RegistrationActivity::class.java).putExtra("email", meeter.email)
                                )
                                overridePendingTransition(0, 0)
                            }

                        }
                        else
                            Snackbar.make(snackbarView, R.string.duplicate_mail, Snackbar.LENGTH_SHORT).show()

                }.start()*/

            }
            progressionIndicator.visibility = View.GONE
        }

        birthdayFld.editText?.setOnClickListener {
            datePicker.show(supportFragmentManager, "birth_picker")
        }

        datePicker.addOnPositiveButtonClickListener {
            birthdayFld.editText?.setText(getDate(datePicker.selection, "dd/MM/yyyy"))
        }

    }



    override fun onBackPressed() {
        super.getOnBackPressedDispatcher().onBackPressed()
        overridePendingTransition(0, 0)

    }

    fun getDate(millis: Long?, format: String): String? {
        val formatter = SimpleDateFormat(format)
        return formatter.format(millis)
    }

    //this function if all values are correct
    fun checkForm(
        name: TextInputLayout,
        surname: TextInputLayout,
        birthdayMillis: Long?,
        birthday: TextInputLayout,
        email: TextInputLayout,
        password: TextInputLayout,
        confirmPassword: TextInputLayout
    ): Boolean {

        name.error = ""
        surname.error = ""
        birthday.error = ""
        email.error = ""
        password.error = ""
        confirmPassword.error = ""

        val nameText = name.editText?.text.toString()
        val surnameText = surname.editText?.text.toString()
        val emailText = email.editText?.text.toString()
        val passText = password.editText?.text.toString()
        val confPassText = confirmPassword.editText?.text.toString()
        var flag = true


        //check if name is blanck or empty
        if (nameText.isBlank() || nameText.isEmpty()) {
            name.error = getString(R.string.name_null_error)
            flag = false
        }
        //check if name is blanck or empty
        if (surnameText.isBlank() || surnameText.isEmpty()) {
            surname.error = getString(R.string.surname_null_error)
            flag = false
        }
        //check if birthday date inserted has more 18 years old
        if (birthdayMillis != null) {
            val now = Calendar.getInstance().timeInMillis
            val diff = Calendar.getInstance()
            diff.timeInMillis = now - birthdayMillis
            if (diff.get(Calendar.YEAR) - 1970 < 18){
                birthday.error = getString(R.string.underage_error)
                flag = false
            }

        }
        //check mail
        if (!(emailText.isEmpty() || emailText.isBlank())) {

            if (!emailText.matches(Regex("^[a-z0-9!#$%&'*+=?^_`{|}~/-]+([.][a-z0-9!#$%&'*+=?^_`{|}~/-]+)*@([a-z0-9-]+[.])+[a-z]+$"))) {
                email.error = getString(R.string.email_format_error)
                flag = false
            } else {
                val splittedEmail = splitEmail(emailText)
                if (splittedEmail.first.length > 64) {
                    email.error = getString(R.string.email_local_toolong_error)
                    flag = false
                } else if (splittedEmail.second.length > 255) {
                    email.error = getString(R.string.email_domain_toolong_error)
                    flag = false
                }
            }
        } else {
            email.error = getString(R.string.email_null_error)
            flag = false
        }
        if (passText.isEmpty() || passText.isBlank()) {
            password.error = getString(R.string.password_null_error)
            flag = false
        }
        if (confPassText.isEmpty() || confPassText.isBlank()) {
            confirmPassword.error = getString(R.string.password_conf_null_error)
            flag = false
        }
        if (confPassText != passText) {
            confirmPassword.error = getString(R.string.password_unmatch_error)
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
