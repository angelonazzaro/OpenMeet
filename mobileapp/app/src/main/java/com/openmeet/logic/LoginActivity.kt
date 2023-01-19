package com.openmeet.logic

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.openmeet.R
import com.openmeet.utils.VolleyRequestSender
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_login)


        val emailFld = findViewById<TextInputLayout>(R.id.emailFixedField)
        val pswFld = findViewById<TextInputLayout>(R.id.pswField)

        val str = intent.getStringExtra("email").toString()
        emailFld.editText?.setText(str)
        Toast.makeText(this, str, Toast.LENGTH_LONG).show()


        /*
            roberto.st@gmail.com; test
         */
        findViewById<Button>(R.id.loginBtn).setOnClickListener {

            val email = emailFld.editText?.text.toString()
            val pwd = pswFld.editText?.text.toString()

            doHttpLogin(email, pwd)

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)

    }

    fun doHttpLogin(email : String, pwd : String){

        val url = "http://" + getString(R.string.request_server_address) + "LoginServlet"
        val snackbarView = findViewById<View>(R.id.auth_login_container)

        // Request a string response from the provided URL.
        val stringRequest = object : StringRequest(
            Method.POST, url,
            { response ->
                val snackbarView = findViewById<View>(R.id.auth_login_container)
                val jsonResp = JSONObject(response)
                if(jsonResp.getString("status") == "success"){
                    Toast.makeText(this, "Success: $response", Toast.LENGTH_LONG).show()
                }
                else{
                    if(jsonResp.getString("message") == "incorrect_credentials"){
                        Snackbar.make(snackbarView, R.string.login_failed, Snackbar.LENGTH_LONG).show()
                        findViewById<TextInputLayout>(R.id.emailFixedField).error = getString(R.string.login_failed_email)
                        findViewById<TextInputLayout>(R.id.pswField).error = getString(R.string.login_failed_password)
                    }
                }
            },
            { error ->

                Snackbar.make(snackbarView, R.string.connection_error, Snackbar.LENGTH_LONG).show()

                //Toast.makeText(this, "Errore nella volley request: " + error, Toast.LENGTH_LONG).show()
                System.err.println("Volley request error: $error")

            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String,String>()
                params["email"] = email
                params["pwd"] = pwd
                return params
            }

        }

        // Add the request to the RequestQueue with Singleton Design Pattern.
        VolleyRequestSender.getInstance(this).addToRequestQueue(stringRequest)
    }
}