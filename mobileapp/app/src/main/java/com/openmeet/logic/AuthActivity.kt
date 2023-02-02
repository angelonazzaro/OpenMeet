package com.openmeet.logic

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.openmeet.R
import com.openmeet.data.meeter.MeeterProxyDAO
import com.openmeet.shared.data.meeter.Meeter
import com.openmeet.shared.data.meeter.MeeterDAO
import com.openmeet.shared.utils.PasswordEncrypter
import com.openmeet.utils.UserEncryptedData
import java.security.InvalidParameterException


class AuthActivity : AppCompatActivity() {
    private var backBtnLastPress = 0L
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val loginBtn = findViewById<Button>(R.id.nextStepBtn)
        val emailFld = findViewById<TextInputLayout>(R.id.emailField)
        emailFld.editText?.setText("roberto.st@gmail.com")

        tryStoredLogin()

        /*
            mike.st@gmail.com; test
            roberto.st@gmail.com; test
         */
        loginBtn.setOnClickListener {

            val email = emailFld.editText?.text.toString().lowercase()

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

                    //val meeterDao = MeeterDAO(DS)
                    startActivity(
                        Intent(this, LoginActivity::class.java).putExtra("email", email)
                    )
                    overridePendingTransition(0, 0)

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

        if (backBtnLastPress + 2000 > System.currentTimeMillis())
            super.getOnBackPressedDispatcher().onBackPressed()
        else {
            Toast.makeText(this, getString(R.string.double_back_prompt), Toast.LENGTH_SHORT)
                .show()
            backBtnLastPress = System.currentTimeMillis()
        }
    }

    fun tryStoredLogin() {

        val snackbarView = findViewById<View>(R.id.auth_container)
        val sharedStoredValues = UserEncryptedData(this).getAllAsHashMap()

        if(sharedStoredValues["email"] != null && sharedStoredValues["pwd"] != null){
            println("TEST" + sharedStoredValues["email"] +  " " + sharedStoredValues["pwd"])
            Thread {
                val ret = MeeterProxyDAO(this).doRetrieveByCondition("${Meeter.MEETER_EMAIL} = '${sharedStoredValues["email"]}' AND ${Meeter.MEETER_PWD} = '${PasswordEncrypter.sha1(sharedStoredValues["pwd"])}'")
                if(ret == null)
                    Snackbar.make(snackbarView, R.string.connection_error, Snackbar.LENGTH_SHORT).show()
                else
                    if(ret.size == 0)
                        Snackbar.make(snackbarView, R.string.login_failed, Snackbar.LENGTH_SHORT).show()
                    else{
                        //Go To HomePage
                        startActivity(
                            Intent(this, HomeScreenActivity::class.java).putExtra("email", ret[0].email)
                        )
                        overridePendingTransition(0, 0)
                    }
            }.start()
        }


    }
}
