package com.openmeet.logic

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.openmeet.R
import com.openmeet.data.interest.InterestProxyDAO
import com.openmeet.data.meeter.MeeterProxyDAO
import com.openmeet.data.meeter_interest.Meeter_InterestProxyDAO
import com.openmeet.shared.data.interest.Interest
import com.openmeet.shared.data.meeter.Meeter
import com.openmeet.shared.data.meeter_interest.Meeter_Interest
import com.openmeet.utils.UserEncryptedData
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

        val progressionIndicator = findViewById<View>(R.id.linearProgressIndicator)
        val snackbarView = findViewById<View>(R.id.home_generalContainer)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.user_Tab

        val biography = findViewById<TextInputLayout>(R.id.biographyField)
        val genderInput = findViewById<TextInputLayout>(R.id.genderIdentityInput)
        val genderAutoComplete = findViewById<AutoCompleteTextView>(R.id.genderAutoComplete)
        val searchingGenderInput = findViewById<TextInputLayout>(R.id.genderOrientationInput)
        val searGenderAutoComplete = findViewById<AutoCompleteTextView>(R.id.genderOrientationAutoComplete)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val logoutButton = findViewById<Button>(R.id.logoutBtn)

        val id = intent.getStringExtra("ID").toString()

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

        retrieveMeeter(id)

        saveButton.setOnClickListener {
            meeter.biography = biography.editText?.text.toString()
            updateMeeterInfo(meeter)
        }

        logoutButton.setOnClickListener {

            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.logout_dialog)
                .setNegativeButton(R.string.negative_dialog){ dialog, which -> }
                .setPositiveButton(R.string.positive_dialog2){ dialog, which ->

                    UserEncryptedData(this).deleteAllValues()
                    startActivity(
                        Intent(this, AuthActivity::class.java)
                    )
                    overridePendingTransition(0, 0)
                }
                .show()
        }

        genderAutoComplete.setOnItemClickListener { adapterView, view, i, l ->
            val genderLabels: MutableList<String> = resources.getStringArray(R.array.gender_identity_labels).toMutableList()
            meeter.gender = genderLabels[i]
            updateMeeterInfo(meeter)
        }

        searGenderAutoComplete.setOnItemClickListener { adapterView, view, i, l ->
            val genderOrientLabels: MutableList<String> = resources.getStringArray(R.array.gender_orientation_labels).toMutableList()
            meeter.searchingGender = genderOrientLabels[i]
            updateMeeterInfo(meeter)
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
        val snackbarView = findViewById<View>(R.id.home_generalContainer)

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
                meeter.setPwd(temp.pwd, false)
                meeter.publicKey = temp.publicKey
            }
            else
                Snackbar.make(snackbarView, R.string.connection_error, Snackbar.LENGTH_LONG).show()

            runOnUiThread {
                progressionIndicator.visibility = View.GONE
            }

            updateUI()
        }.start()

    }

    fun updateUI() {

        val infoField = findViewById<TextView>(R.id.personalInfoField)
        val biography = findViewById<TextInputLayout>(R.id.biographyField)
        val genderAutoComplete = findViewById<AutoCompleteTextView>(R.id.genderAutoComplete)
        val searGenderAutoComplete = findViewById<AutoCompleteTextView>(R.id.genderOrientationAutoComplete)

        val genderList: MutableList<String> = resources.getStringArray(R.array.gender_identity_items).toMutableList()
        val genderOrList: MutableList<String> = resources.getStringArray(R.array.gender_orientation_items).toMutableList()

        runOnUiThread {
            infoField.text = "${meeter.meeterName} ${meeter.meeterSurname}, ${getAge(meeter.birthdate)}"
            biography.editText?.setText(meeter.biography)

            when(meeter.gender) {
                "M" -> genderAutoComplete.setText(genderList[0], false)
                "F" -> genderAutoComplete.setText(genderList[1], false)
                "N" -> genderAutoComplete.setText(genderList[2], false)
            }
            when(meeter.searchingGender) {
                "M" -> searGenderAutoComplete.setText(genderOrList[0], false)
                "F" -> searGenderAutoComplete.setText(genderOrList[1], false)
                "N" -> searGenderAutoComplete.setText(genderOrList[2], false)
                "B" -> searGenderAutoComplete.setText(genderOrList[3], false)
                "A" -> searGenderAutoComplete.setText(genderOrList[4], false)
            }

        }

        makeInterestLayout()

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

    fun makeInterestLayout(){
        val meeter_interestList =
            Meeter_InterestProxyDAO(this).doRetrieveByCondition("${Meeter_Interest.MEETER_INTEREST_MEETER_ID} = ${meeter.id}")

        val interestDAO = InterestProxyDAO(this)
        if (meeter_interestList != null) {
            for (meeter_interest in meeter_interestList) {
                val interest = interestDAO.doRetrieveByKey(meeter_interest.interestId.toString())
                if (interest != null) {
                    addToInterestLayout(interest)
                }
            }
        }

        val addInt = Interest()
        addInt.description = "+"
        addToInterestLayout(addInt)
    }
    fun addToInterestLayout(interest: Interest) {
        val interestLayout = findViewById<LinearLayout>(R.id.interestLayout)

        val intTxt = MaterialButton(this)
        intTxt.text = interest.description
        intTxt.backgroundTintList = this.getColorStateList(R.color.mid_purple)
        intTxt.setTextColor(Color.WHITE)

        val space = Space(this)

        runOnUiThread {
            interestLayout.addView(intTxt)
            interestLayout.addView(space)
            space.layoutParams.width = 16

            intTxt.setOnClickListener {
                if(intTxt.text == "+"){
                    MaterialAlertDialogBuilder(this)
                        .setTitle(R.string.add_interest)
                        .setPositiveButton(R.string.positive_dialog){ dialog, which -> }
                        .show()
                }
                else
                    MaterialAlertDialogBuilder(this)
                        .setTitle("${getString(R.string.remove_interest_dialog)} \"${interest.description}?\"")
                        .setPositiveButton(R.string.positive_dialog2){ dialog, which ->
                            resetInterestLayout()
                            print(interest)
                            Thread{
                                Meeter_InterestProxyDAO(this).doDelete("${Meeter_Interest.MEETER_INTEREST_INTEREST_ID} = ${interest.id}" +
                                        " AND ${Meeter_Interest.MEETER_INTEREST_MEETER_ID} = ${meeter.id}")
                                makeInterestLayout()
                            }.start()




                        }
                        .show()
            }
        }

    }

    fun resetInterestLayout() {
        val interestLayout = findViewById<LinearLayout>(R.id.interestLayout)
        interestLayout.removeAllViewsInLayout()

    }

    fun updateMeeterInfo(m: Meeter){
        val snackbarView = findViewById<View>(R.id.home_generalContainer)

        Thread {
            if(!MeeterProxyDAO(this).doSaveOrUpdate(m))
                Snackbar.make(snackbarView, R.string.connection_error, Snackbar.LENGTH_LONG).show()
        }.start()
    }

}