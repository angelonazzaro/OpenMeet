package com.openmeet.logic

import android.content.Context
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

/**
 * This activity is used to register a new meeter. It is the first step of the registration process.
 * It asks for the meeter's name, surname, email, password and birthdate.
 *
 * @author Yuri Brandi
 */
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

            val result = checkForm(nameFld, surnameFld, datePicker.selection, birthdayFld, emailFld, passwordFld, confirmPasswordFld, true)


            if (result){
                val meeter = Meeter()
                meeter.meeterName = nameFld.editText?.text.toString()
                meeter.meeterSurname = surnameFld.editText?.text.toString()
                meeter.email = emailFld.editText?.text.toString()
                meeter.pwd = passwordFld.editText?.text.toString()
                meeter.birthdate = java.sql.Date(datePicker.selection!!)


               /* startActivity(
                    Intent(this, Registration2Activity::class.java).putExtra("email", meeter.email)
                )
                overridePendingTransition(0, 0)*/
                // Uncomment below and remove above
                Thread {
                    runOnUiThread {
                        progressionIndicator.visibility = View.VISIBLE
                    }

                    if(!verifyUniregisteredMeeter(meeter.email, this)){
                        if(!doRegisterMeeter(meeter, this))
                            Snackbar.make(snackbarView, R.string.connection_error, Snackbar.LENGTH_SHORT).show()
                        else{
                            startActivity(
                                Intent(this, Registration2Activity::class.java).putExtra("email", meeter.email)
                            )
                            overridePendingTransition(0, 0)
                        }
                    }
                    else
                        Snackbar.make(snackbarView, R.string.duplicate_mail, Snackbar.LENGTH_SHORT).show()


                    runOnUiThread {
                        progressionIndicator.visibility = View.GONE
                    }

                }.start()

            }
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


    /**
     * This function checks if the meeter inputs are valid (e.g. email is valid, password is long enough, etc.).
     * If they are not, it displays the error message.
     *
     * @param name the name of the meeter
     * @param surname the surname of the meeter
     * @param birthdayMillis the birthdate of the meeter in milliseconds
     * @param birthday the birthdate of the meeter
     * @param email the email of the meeter
     * @param password the password of the meeter
     * @param confirmPassword the confirmation of the password
     * @param displayErrors if true, it displays the error messages. Used for test purposes.
     * @param email the email of the meeter
     *
     * @return true if the meeter is already registered, false otherwise
     *
     * @author Yuri Brandi
     */
    //this function checks if all values are correct (TESTED)
    fun checkForm(
        name: TextInputLayout,
        surname: TextInputLayout,
        birthdayMillis: Long?,
        birthday: TextInputLayout,
        email: TextInputLayout,
        password: TextInputLayout,
        confirmPassword: TextInputLayout,
        displayErrors: Boolean
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
            if(displayErrors)
                name.error = getString(R.string.name_null_error)
            flag = false
        }
        //check if name is blanck or empty
        if (surnameText.isBlank() || surnameText.isEmpty()) {
            if(displayErrors)
                surname.error = getString(R.string.surname_null_error)
            flag = false
        }
        //check if birthday date inserted has more 18 years old
        if (birthdayMillis != null) {
            if (getAge(birthdayMillis) < 18){
                if(displayErrors)
                    birthday.error = getString(R.string.underage_error)
                flag = false
            }

        }
        //check mail
        if (!(emailText.isEmpty() || emailText.isBlank())) {

            if (!emailText.matches(Regex("^[a-z0-9!#$%&'*+=?^_`{|}~/-]+([.][a-z0-9!#$%&'*+=?^_`{|}~/-]+)*@([a-z0-9-]+[.])+[a-z]+$"))) {
                if(displayErrors)
                    email.error = getString(R.string.email_format_error)
                flag = false
            } else {
                val splittedEmail = splitEmail(emailText)
                if (splittedEmail.first.length > 64) {
                    if(displayErrors)
                        email.error = getString(R.string.email_local_toolong_error)
                    flag = false
                } else if (splittedEmail.second.length > 255) {
                    if(displayErrors)
                        email.error = getString(R.string.email_domain_toolong_error)
                    flag = false
                }
            }
        } else {
            if(displayErrors)
                email.error = getString(R.string.email_null_error)
            flag = false
        }
        if (passText.isEmpty() || passText.isBlank()) {
            if(displayErrors)
                password.error = getString(R.string.password_null_error)
            flag = false
        }else{
            if (passText.length < 8 || passText.length > 16) {
                if(displayErrors)
                    password.error = getString(R.string.password_length_error)
                flag = false
            }
            else
            if (!Regex("[!?@#\$£¢~€()+={|}^&*;,._-]+").containsMatchIn(passText)) {
                if(displayErrors)
                    password.error = getString(R.string.password_special_char_error)
                flag = false
            }
            else
            if (!Regex("[0-9]+").containsMatchIn(passText)) {
                if(displayErrors)
                    password.error = getString(R.string.password_number_error)
                flag = false
            }else
            if (!Regex("[A-Z]+").containsMatchIn(passText)) {
                if(displayErrors)
                    password.error = getString(R.string.password_uppercase_error)
                flag = false
            }
            else
            if (!Regex("[a-z]+").containsMatchIn(passText)) {
                if(displayErrors)
                    password.error = getString(R.string.password_lowercase_error)
                flag = false
            }


        }

        if (confPassText.isEmpty() || confPassText.isBlank()) {
            if(displayErrors)
                confirmPassword.error = getString(R.string.password_conf_null_error)
            flag = false
        }else
            if (confPassText != passText) {
                if(displayErrors)
                    confirmPassword.error = getString(R.string.password_unmatch_error)
                flag = false
            }

        return flag

    }

    /**
     * This function splits the email in two parts: the local part and the domain part.
     * @param email the email to split
     *
     * @return a pair of strings containing the local and the domain part of the email
     *
     * @author Yuri Brandi
     */
    fun splitEmail(email: String): Pair<String, String> {
        val str = email.split('@').toTypedArray()
        if (str.size != 2)
            throw InvalidParameterException("Probably invalid mail")
        return Pair(str[0], str[1])
    }

    /**
     * This function calculates the age of a person given his birthday.
     *
     * @param birthdayMillis the birthday of the person in milliseconds
     *
     * @return the age of the person
     *
     * @author Yuri Brandi
     */
    fun getAge(birthdayMillis: Long): Int{
        val now = Calendar.getInstance().timeInMillis
        val diff = Calendar.getInstance()
        diff.timeInMillis = now - birthdayMillis
        return (diff.get(Calendar.YEAR) - 1970)
    }

    /**
     * This function checks if the email is already registered in the database.
     *
     * @param email the email to check
     * @param cntx the context of the activity
     *
     * @return true if the email is already registered, false otherwise
     *
     * @author Yuri Brandi
     */
    fun verifyUniregisteredMeeter(email: String, cntx: Context): Boolean{

        val retrieveMail = MeeterProxyDAO(cntx).doRetrieveByCondition("${Meeter.MEETER_EMAIL} = '$email'")
        if(retrieveMail == null)
            return false
        else
            if(retrieveMail.size > 0)
                return true

        return false
    }

    fun doRegisterMeeter(meeter: Meeter, cntx: Context): Boolean{
        return MeeterProxyDAO(cntx).doSave(meeter)
    }


}

//TODO: change Toast in checkForm to error messagges
