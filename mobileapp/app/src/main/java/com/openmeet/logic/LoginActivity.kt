package com.openmeet.logic

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.openmeet.R
import com.openmeet.data.meeter.MeeterProxyDAO
import com.openmeet.shared.data.meeter.Meeter
import com.openmeet.utils.InvalidVolleyRequestException
import com.openmeet.utils.VolleyRequestSender
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_login)


        val emailFld = findViewById<TextInputLayout>(R.id.emailFixedField)
        val pswFld = findViewById<TextInputLayout>(R.id.pswField)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val registrationTxt = findViewById<TextView>(R.id.registrationTxt)

        val snackbarView = findViewById<View>(R.id.auth_login_container)

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

            try {
                MeeterProxyDAO(this).doRetrieveByCondition("TRUE"/*Meeter.MEETER_EMAIL + " = '$email'"*/)
            } catch (e: InvalidVolleyRequestException) {
                Snackbar.make(snackbarView, R.string.connection_error, Snackbar.LENGTH_LONG).show()
            }


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
}