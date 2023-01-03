package com.openmeet.logic

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.openmeet.R
import java.security.InvalidParameterException

var backBtnLastPress = 0L

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val loginBtn = findViewById<Button>(R.id.nextStepBtn)
        val emailFld = findViewById<TextInputLayout>(R.id.emailField)


        loginBtn.setOnClickListener {

            val email = emailFld.editText?.text.toString()
            Toast.makeText(this, email, Toast.LENGTH_LONG).show()
            if (email == null)
                Toast.makeText(this, "Qualcosa è andato storto", Toast.LENGTH_LONG).show()
            else
                if (email.isBlank() || email.isEmpty())
                //Toast.makeText(this, "email non inserita", Toast.LENGTH_LONG).show()
                    emailFld.error = getString(R.string.email_null_error)
                else if (!email.matches(Regex("^[a-z0-9!#$%&'*+=?^_`{|}~/-]+([.][a-z0-9!#$%&'*+=?^_`{|}~/-]+)*@([a-z0-9-]+[.])+[a-z]+$")))
                    emailFld.error = getString(R.string.email_format_error)
                else {
                    val splittedEmail = splitEmail(email)
                    if (splittedEmail.first.length > 64)
                        emailFld.error = getString(R.string.email_local_toolong_error)
                    else if (splittedEmail.second.length > 255)
                        emailFld.error = getString(R.string.email_domain_toolong_error)
                    else { //SUCCESS CASE
                        //to-do: controllare se l'email non esiste nel database (flow di regstrazione).
                        setContentView(R.layout.activity_auth_login)
                        val emailFld2 = findViewById<TextInputLayout>(R.id.emailFixedField)
                        emailFld2.editText?.setText(email)


                    }

                }
        }

    }

    fun splitEmail(email: String): Pair<String, String> {
        val str = email.split('@').toTypedArray()

        if (str.size != 2)
            throw InvalidParameterException("Probably invalid mail")
        return Pair(str[0], str[1])
    }

    //Override del comportamento del back Button che passa alla schermata precedente/esce
    override fun onBackPressed() {

        if (findViewById<View>(R.id.auth_container) != null) {
            if (backBtnLastPress + 2000 > System.currentTimeMillis())
                super.onBackPressed()
            else {
                Toast.makeText(this, "Torna indietro di nuovo per uscire", Toast.LENGTH_SHORT)
                    .show()
                backBtnLastPress = System.currentTimeMillis()
            }

        } else
            setContentView(R.layout.activity_auth)
    }
}
