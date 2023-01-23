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
import com.openmeet.shared.data.meeter.Meeter
import com.openmeet.shared.utils.PasswordEncrypter

import java.nio.charset.Charset
import java.security.MessageDigest
import java.sql.Date

import com.openmeet.utils.UserEncryptedData



class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_login)


        val emailFld = findViewById<TextInputLayout>(R.id.emailFixedField)
        val pswFld = findViewById<TextInputLayout>(R.id.pswField)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val registrationTxt = findViewById<TextView>(R.id.registrationTxt)

        val snackbarView = findViewById<View>(R.id.auth_login_container)
        val progressionIndicator = findViewById<View>(R.id.linearProgressIndicator)

        val str = intent.getStringExtra("email").toString()
        emailFld.editText?.setText(str)
        Toast.makeText(this, str, Toast.LENGTH_LONG).show()


        pswFld.editText?.setText("test")

        /*
            roberto.st@gmail.com; test
         */
        val email = emailFld.editText?.text.toString()

        loginBtn.setOnClickListener {

            progressionIndicator.visibility = View.VISIBLE
            val pwd = pswFld.editText?.text.toString()

            //For testing
//            Thread {
//
////                //Test 1
//                val ret = MeeterProxyDAO(cntx).doRetrieveByCondition("${Meeter.MEETER_EMAIL} = '$email' AND ${Meeter.MEETER_PWD} = '${PasswordEncrypter.sha1(pwd)}'")
////                //Test 2
////                val ret = MeeterProxyDAO(cntx).doRetrieveByKey("1")
////                //Test 3
////                val ret = MeeterProxyDAO(cntx).doRetrieveAll()
////                //Test 4
////                val ret = MeeterProxyDAO(cntx).doRetrieveAll(2)
////                //Test 5
////                val ret = MeeterProxyDAO(cntx).doRetrieveAll(2, 4)
////                //Test 6
////                val meeter = Meeter()
////                meeter.email = "provaDoSave@gmail.com"
////                meeter.pwd = "provaDoSave"
////                meeter.meeterName = "provaDoSave"
////                meeter.meeterSurname = "provaDoSave"
////                meeter.birthDate = Date.valueOf("1999-01-01")
////                val ret = MeeterProxyDAO(cntx).doSave(meeter)
////                //Test 7
////                val meeter = Meeter()
////                meeter.email = "provaDoSavePartial@gmail.com"
////                meeter.pwd = "provaDoSavePartial"
////                meeter.meeterName = "provaDoSavePartial"
////                meeter.meeterSurname = "provaDoSavePartial"
////                meeter.birthDate = Date.valueOf("1999-01-01")
////                val hmap = meeter.toHashMap(Meeter.MEETER_EMAIL, Meeter.MEETER_PWD, Meeter.MEETER_MEETER_NAME, Meeter.MEETER_MEETER_SURNAME, Meeter.MEETER_BIRTH_DATE)
////                val ret = MeeterProxyDAO(cntx).doSave(meeter)
//                //Test 8
////                val ret = MeeterProxyDAO(cntx).doUpdate(
////                    hashMapOf(
////                        "email" to "roberto.st@gmail.com",
////                        "pwd" to "test"
////                    ), "${Meeter.MEETER_EMAIL} = 'provaDoSavePartial@gmail.com'"
////                )
////                //Test 9
////                val meeter = Meeter()
////                meeter.email = "provaDoSaveOrUpdate"
////                meeter.pwd = "provaDoSaveOrUpdate"
////                meeter.meeterName = "provaDoSaveOrUpdate"
////                meeter.meeterSurname = "provaDoSaveOrUpdate"
////                meeter.birthDate = Date.valueOf("1999-01-01")
////                val ret = MeeterProxyDAO(cntx).doSaveOrUpdate(meeter)
////                //Test 10
////                val ret =
////                    MeeterProxyDAO(cntx).doDelete("${Meeter.MEETER_EMAIL} = 'provaDoSaveOrUpdate'")
//
//                print("RET: $ret")
//
//                if (ret == null)
//                    Snackbar.make(snackbarView, R.string.connection_error, Snackbar.LENGTH_SHORT)
//                        .show()
//                else {
//
//                }

           Thread {

                val ret = MeeterProxyDAO(this).doRetrieveByCondition("${Meeter.MEETER_EMAIL} = '$email' AND ${Meeter.MEETER_PWD} = '${PasswordEncrypter.sha1(pwd)}'")

                if(ret == null)
                    Snackbar.make(snackbarView, R.string.connection_error, Snackbar.LENGTH_SHORT).show()
                else
                    if(ret.size == 0)
                        Snackbar.make(snackbarView, R.string.login_failed, Snackbar.LENGTH_SHORT).show()
                    else{
                        UserEncryptedData(this).storeCredentials(email, pwd)
                    }


            }.start()
            progressionIndicator.visibility = View.GONE
        }

        registrationTxt.setOnClickListener {
            startActivity(
                Intent(this, RegistrationActivity::class.java).putExtra("email", email)
            )
            overridePendingTransition(0, 0)
        }
    }

    override fun onBackPressed() {
        super.getOnBackPressedDispatcher().onBackPressed()
        overridePendingTransition(0, 0)

    }
}
