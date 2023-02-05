package com.openmeet.logic


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.openmeet.R
import com.openmeet.data.meeter.MeeterProxyDAO
import com.openmeet.shared.data.meeter.Meeter
import com.openmeet.shared.data.storage.DAO
import com.openmeet.shared.utils.PasswordEncrypter
import com.openmeet.utils.ContextDAO
import com.openmeet.utils.UserEncryptedData
import com.openmeet.utils.VolleyRequestSender
import com.openmeet.utils.VolleyResponseCallback


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_login)

        val emailFld = findViewById<TextInputLayout>(R.id.emailFixedField)
        val pswFld = findViewById<TextInputLayout>(R.id.pswField)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val registrationTxt = findViewById<TextView>(R.id.registrationTxt)
        val recoverPassTxt = findViewById<TextView>(R.id.recoverPasswordTxt)

        val snackbarView = findViewById<View>(R.id.auth_login_container)
        val progressionIndicator = findViewById<View>(R.id.linearProgressIndicator)

        val str = intent.getStringExtra("email").toString()
        emailFld.editText?.setText(str)


        pswFld.editText?.setText("test")

        /*
            roberto.st@gmail.com; test
         */
        val email = emailFld.editText?.text.toString()

        loginBtn.setOnClickListener {

            val pwd = pswFld.editText?.text.toString()


           Thread {

               runOnUiThread {
                   progressionIndicator.visibility = View.VISIBLE
               }
                val ret = MeeterProxyDAO(this).doRetrieveByCondition("${Meeter.MEETER_EMAIL} = '$email' AND ${Meeter.MEETER_PWD} = '${PasswordEncrypter.sha1(pwd)}'")

                if(ret == null)
                    Snackbar.make(snackbarView, R.string.connection_error, Snackbar.LENGTH_SHORT).show()
                else
                    if(ret.size == 0)
                        Snackbar.make(snackbarView, R.string.login_failed, Snackbar.LENGTH_SHORT).show()
                    else{
                        //Saving credentials to remember the user.
                        UserEncryptedData(this).storeCredentials(email, pwd)
                        //Aggiungere verifica registrazione pt.2
                        startActivity(
                            Intent(this, HomeScreenActivity::class.java).putExtra("ID", ret[0].id.toString())
                        )
                        overridePendingTransition(0, 0)
                    }

               runOnUiThread {
                   progressionIndicator.visibility = View.GONE
               }

            }.start()
        }

        registrationTxt.setOnClickListener {
            startActivity(
                Intent(this, RegistrationActivity::class.java).putExtra("email", email)
            )
            overridePendingTransition(0, 0)
        }

        recoverPassTxt.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.recovery_dialog_title)
                .setMessage(R.string.recovery_dialog_message)
                .setNegativeButton(R.string.negative_dialog){ dialog, which -> }
                .setPositiveButton(R.string.positive_dialog){ dialog, which ->
                    sendRecoveryHTTPRequest(email)
                }
                .show()
        }

    }



    override fun onBackPressed() {
        super.getOnBackPressedDispatcher().onBackPressed()
        overridePendingTransition(0, 0)
    }

    fun sendRecoveryHTTPRequest(email: String){

        val snackbarView = findViewById<View>(R.id.auth_login_container)

        VolleyRequestSender.getInstance(this)
            .doHttpPostRequest(
                "http://" + getString(R.string.request_server_address) + "PasswordRecoveryService",
                hashMapOf(
                    "email" to email,
                ),
                object : VolleyResponseCallback {
                    override fun onError(error: String) {
                        Log.e("Password recovery Volley error", error)
                        Snackbar.make(snackbarView, getString(R.string.connection_error), Snackbar.LENGTH_SHORT)
                    }

                    override fun onSuccess(response: String) {
                        Log.d("Password recovery Volley: ", response)
                        runOnUiThread{
                            MaterialAlertDialogBuilder(this@LoginActivity)
                                .setTitle(R.string.completed_recovery_dialog_title)
                                .setPositiveButton(R.string.positive_dialog){ dialog, which -> }
                                .show()
                        }
                    }

                }
            )
    }
}
