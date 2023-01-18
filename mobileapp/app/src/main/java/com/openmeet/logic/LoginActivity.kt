package com.openmeet.logic

import android.os.Bundle
<<<<<<< HEAD
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
=======

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.google.android.material.snackbar.Snackbar

>>>>>>> 5b9adc580af95b975488f514c453b485ae9b805f
import com.google.android.material.textfield.TextInputLayout
import com.openmeet.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_login)

        val emailFld = findViewById<TextInputLayout>(R.id.emailFixedField)

        val str = intent.getStringExtra("email").toString()
        emailFld.editText?.setText(str)
        Toast.makeText(this, str, Toast.LENGTH_LONG).show()
<<<<<<< HEAD
=======


        /*
            roberto.st@gmail.com; test
         */
        findViewById<Button>(R.id.loginBtn).setOnClickListener {

            val email = emailFld.editText?.text.toString()
            val pwd = pswFld.editText?.text.toString()

            doHttpLogin(email, pwd)

        }

>>>>>>> 5b9adc580af95b975488f514c453b485ae9b805f
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)

    }
}