package com.openmeet.logic

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputLayout
import com.openmeet.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_login)

        val emailFld = findViewById<TextInputLayout>(R.id.emailFixedField)
        val pswFld = findViewById<TextInputLayout>(R.id.pswField)

        val str = intent.getStringExtra("email").toString()
        emailFld.editText?.setText(str)
        Toast.makeText(this, str, Toast.LENGTH_LONG).show()


        findViewById<Button>(R.id.loginBtn).setOnClickListener {

            val email = emailFld.editText?.text
            val psw = pswFld.editText?.text

            val queue = Volley.newRequestQueue(this)
            val url = "https://www.yuribrandi.com/"
            //val url = "http://192.168.1.33:8080/Gradle___com_openmeet___openmeet_webapp_1_0_SNAPSHOT_war/androidReq?email=" + str + "&password=" + psw

// Request a string response from the provided URL.
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    Toast.makeText(this,  "Response is: ${response.substring(0, 500)}", Toast.LENGTH_LONG).show()
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this,  "Errore nella volley request" + error, Toast.LENGTH_LONG).show()
                    System.err.println(error);
                })

// Add the request to the RequestQueue.
            queue.add(stringRequest)

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)

    }
}