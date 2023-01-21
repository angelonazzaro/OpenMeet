package com.openmeet.logic

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.openmeet.R
import com.openmeet.data.meeter.MeeterProxyDAO
import com.openmeet.utils.VolleyResponseListener

class LoginActivity : AppCompatActivity(), VolleyResponseListener {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_login)


        val emailFld = findViewById<TextInputLayout>(R.id.emailFixedField)
        val pswFld = findViewById<TextInputLayout>(R.id.pswField)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val registrationTxt = findViewById<TextView>(R.id.registrationTxt)



        val str = intent.getStringExtra("email").toString()
        emailFld.editText?.setText(str)
        Toast.makeText(this, str, Toast.LENGTH_LONG).show()


        pswFld.editText?.setText("test")

        /*
            roberto.st@gmail.com; test
         */
        val email = emailFld.editText?.text.toString()

        loginBtn.setOnClickListener {

            val pwd = pswFld.editText?.text.toString()

            MeeterProxyDAO(this).doRetrieveByCondition("")


        }

        registrationTxt.setOnClickListener {
            startActivity(
                Intent(this, RegistrationActivity::class.java).putExtra("email", email)
            )
            overridePendingTransition(0, 0)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)

    }

    override fun requestFinished(isSuccessful: Boolean, response: String) {
        println("Sono in login activity $isSuccessful, $response")

        val snackbarView = findViewById<View>(R.id.auth_login_container)
        Snackbar.make(snackbarView, R.string.connection_error, Snackbar.LENGTH_SHORT).show()
    }
}