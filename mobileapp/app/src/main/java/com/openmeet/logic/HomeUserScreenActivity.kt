package com.openmeet.logic

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.openmeet.R
import com.openmeet.data.meeter.MeeterProxyDAO
import com.openmeet.shared.data.meeter.Meeter
import java.util.*
import kotlin.system.exitProcess

/**
 * This class is used to update the Meeter's settings.
 *
 * @author Yuri Brandi
 */
class HomeUserScreenActivity: AppCompatActivity() {

    private var backBtnLastPress = 0L
    private var meeter = Meeter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_user_screen)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.user_Tab

        val id = intent.getStringExtra("ID").toString()
        retrieveMeeter(id)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.chat_Tab -> {
                    startActivity(
                        Intent(this, HomeChatScreenActivity::class.java).putExtra(
                            "ID",
                            intent.getStringExtra("ID").toString()
                        )
                    )
                    overridePendingTransition(0, 0)
                }

                R.id.discover_Tab -> {
                    startActivity(
                        Intent(this, HomeScreenActivity::class.java).putExtra(
                            "ID",
                            intent.getStringExtra("ID").toString()
                        )
                    )
                    overridePendingTransition(0, 0)
                }
            }
            true
        }


    }

    //Override del comportamento del back Button che passa alla schermata precedente/esce
    override fun onBackPressed() {

        if (backBtnLastPress + 2000 > System.currentTimeMillis()) {
            this.finish()
            exitProcess(0)
        } else {
            Toast.makeText(this, getString(R.string.double_back_prompt), Toast.LENGTH_SHORT)
                .show()
            backBtnLastPress = System.currentTimeMillis()
        }
    }


    /**
     * Retrieve the Meeter's data from the database
     *
     * @param id the Meeter's id
     *
     * @author Yuri Brandi
     */
    fun retrieveMeeter(id: String) {

        val progressionIndicator = findViewById<View>(R.id.linearProgressIndicator)
        Log.d("Meeter id", id)


        Thread {
            runOnUiThread {
                progressionIndicator.visibility = View.VISIBLE
            }

            val temp = MeeterProxyDAO(this).doRetrieveByKey(id)
            Log.d("Meeter retrieved", temp.toString())
            if (temp != null) {
                meeter.id = temp.id
                meeter.meeterName = temp.meeterName
                meeter.meeterSurname = temp.meeterSurname
                meeter.biography = temp.biography
                meeter.gender = temp.gender
                meeter.searchingGender = temp.searchingGender
                meeter.birthdate = temp.birthdate
                meeter.email = temp.email
            }

            runOnUiThread {
                progressionIndicator.visibility = View.GONE
            }

            updateUI()

        }.start()

    }

    fun updateUI() {

        val infoField = findViewById<TextView>(R.id.personalInfoField)
        val biography = findViewById<TextInputLayout>(R.id.biographyField)
        val gender = findViewById<TextInputLayout>(R.id.genderIdentityInput)
        val searchingGender = findViewById<TextInputLayout>(R.id.genderOrientationInput)

        infoField.text = "${meeter.meeterName} ${meeter.meeterSurname}, ${getAge(meeter.birthdate)}"
        if (meeter.biography == null)
            biography.editText?.setText("test")
        else
            biography.editText?.setText(meeter.biography)
//        when(meeter.gender) {
//            'M' -> gender.editText?.setText("Male")
//            'F' -> gender.editText?.setText("Female")
//            'N' -> gender.editText?.setText("Non-Binary")
//        }
//        when(meeter.searchingGender) {
//            'M' -> searchingGender.editText?.setText("Male")
//            'F' -> searchingGender.editText?.setText("Female")
//            'N' -> searchingGender.editText?.setText("Non-Binary")
//            'B' -> searchingGender.editText?.setText("Both (Males & Females)")
//            'A' -> searchingGender.editText?.setText("Everybody")
//        }


    }

    /**
     * Get the Meeter's age from the birthdate
     *
     * @param birthdate the Meeter's birthdate
     *
     * @author Yuri Brandi
     */
    fun getAge(birthday: Date): Int {
        val now = Calendar.getInstance().timeInMillis
        val diff = Calendar.getInstance()
        diff.timeInMillis = now - birthday.time
        return (diff.get(Calendar.YEAR) - 1970)
    }

}