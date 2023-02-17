package com.openmeet.logic

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.openmeet.R
import com.openmeet.data.ban.BanProxyDAO
import com.openmeet.data.meeter.MeeterProxyDAO
import com.openmeet.shared.data.ban.Ban
import com.openmeet.shared.data.meeter.Meeter
import com.openmeet.shared.data.meeter.MeeterDAO
import com.openmeet.shared.utils.PasswordEncrypter
import com.openmeet.utils.UserEncryptedData
import org.json.JSONObject
import java.security.InvalidParameterException
import java.sql.Timestamp


class AuthActivity : AppCompatActivity() {

    private var backBtnLastPress = 0L


    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        callbackManager = CallbackManager.Factory.create()

        val fbLoginBtn = findViewById<Button>(R.id.fb_login_button) as LoginButton
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


        fbLoginBtn.setOnClickListener {
            val loginManager = LoginManager.getInstance()
            loginManager.setLoginBehavior(LoginBehavior.KATANA_ONLY)
            loginManager.logInWithReadPermissions(this, listOf("email", "public_profile"
                , "user_gender", "user_birthday"));
        }

        fbLoginBtn.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                val graphRequest = GraphRequest.newMeRequest(result?.accessToken){ _, response ->
                    getFacebookData(response?.getJSONObject())
                }

                val parameters = Bundle()
                parameters.putString("fields", "id, email, birthday, gender, name")
                graphRequest.parameters = parameters
                graphRequest.executeAsync()
            }

            override fun onCancel() {
                TODO("Not yet implemented")
            }

            override fun onError(exception: FacebookException) {
                TODO("Not yet implemented")
            }
        })

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

        val sharedPrefs =
            this.getSharedPreferences(getString(R.string.STD_PREFS), Context.MODE_PRIVATE)

        val snackbarView = findViewById<View>(R.id.auth_container)
        val sharedStoredValues = UserEncryptedData(this).getAllAsHashMap()

        if (sharedStoredValues["email"] != null && sharedStoredValues["pwd"] != null) {
            println("TEST" + sharedStoredValues["email"] + " " + sharedStoredValues["pwd"])
            Thread {
                val ret = MeeterProxyDAO(this).doRetrieveByCondition(
                    "${Meeter.MEETER_EMAIL} = '${sharedStoredValues["email"]}' AND ${Meeter.MEETER_PWD} = '${
                        PasswordEncrypter.sha1(sharedStoredValues["pwd"])
                    }'"
                )
                if (ret == null)
                    Snackbar.make(snackbarView, R.string.connection_error, Snackbar.LENGTH_SHORT)
                        .show()
                else
                    if (ret.size == 0)
                        Snackbar.make(snackbarView, R.string.login_failed, Snackbar.LENGTH_SHORT)
                            .show()
                    else {
                        //Go To HomePage
                        val meeterID = ret[0].id.toString()

                        val ban = checkBan(meeterID)
                        if(ban == null){
                            //Snackbar.make(snackbarView, sharedPrefs.getInt("registration_stage", 0), Snackbar.LENGTH_SHORT).show()
                            if(sharedPrefs.getInt("registration_stage", -1) == -1){
                                startActivity(
                                    Intent(this, HomeScreenActivity::class.java).putExtra("ID", meeterID)
                                )
                                overridePendingTransition(0, 0)
                            }
                            else{
                                startActivity(
                                    Intent(this, Registration2Activity::class.java).putExtra("ID", meeterID)
                                )
                                overridePendingTransition(0, 0)
                            }
                        }
                        else{
                            runOnUiThread {
                                MaterialAlertDialogBuilder(this)
                                    .setTitle(R.string.banned_dialog_title)
                                    .setMessage(
                                        "${getString(R.string.banned_dialog_message)}\n\n" +
                                        "${getString(R.string.banned_dialog_description)} ${ban.description}\n" +
                                        "${getString(R.string.banned_dialog_expiry)} ${ban.endTime}"
                                    )
                                    .setPositiveButton(R.string.positive_dialog){ dialog, which -> }
                                    .show()
                            }
                        }
                    }
            }.start()
        }
    }

    private fun getFacebookData(obj: JSONObject?) {
        val name = obj?.getString("name")
        val birthday = obj?.getString("birthday")
        val gender = obj?.getString("gender")
        val email = obj?.getString("email")

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    fun checkBan(meeterID: String): Ban?{
        val ret = BanProxyDAO(this).doRetrieveByCondition(
            "${Ban.BAN_MEETER_ID} = $meeterID AND ${Ban.BAN_END_TIME} > CURDATE()")

        Log.d("Existing bans", ret.toString())
        if(ret != null && ret.size > 0)
            return ret[0]
        return null
    }
}
