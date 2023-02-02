package com.openmeet.logic

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.openmeet.R
import com.openmeet.data.interest.InterestProxyDAO
import com.openmeet.data.meeter.MeeterProxyDAO
import com.openmeet.data.meeter_interest.Meeter_InterestProxyDAO
import com.openmeet.shared.data.meeter.Meeter
import com.openmeet.shared.data.meeter_interest.Meeter_Interest
import java.util.*
import kotlin.system.exitProcess


class HomeScreenActivity : AppCompatActivity() {

    private var backBtnLastPress = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)


        val snackbarView = findViewById<View>(R.id.home_generalContainer)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)


        val meeterInfoLayout = findViewById<ConstraintLayout>(R.id.meeterInfoLayout)
        val meeterBiography = findViewById<TextView>(R.id.biographyView)
        val interestLayout = findViewById<LinearLayout>(R.id.interestLayout)
        val expandBtn = findViewById<ImageButton>(R.id.expandBtn)
        val compressBtn = findViewById<ImageButton>(R.id.compressBtn)

        bottomNav.selectedItemId = R.id.discover_Tab

        expandBtn.setOnClickListener {
            meeterBiography.visibility = View.VISIBLE
            interestLayout.visibility = View.VISIBLE
            compressBtn.visibility = View.VISIBLE
            expandBtn.visibility = View.GONE
            meeterInfoLayout.layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT

        }

        compressBtn.setOnClickListener {
            meeterBiography.visibility = View.GONE
            interestLayout.visibility = View.GONE
            compressBtn.visibility = View.GONE
            expandBtn.visibility = View.VISIBLE
            meeterInfoLayout.layoutParams.height = ConstraintLayout.LayoutParams.MATCH_PARENT
        }

        //findViewById<MaterialCardView>(R.id.materialCardView).setOnTouchListener()
        doDiscoverFlow()

    }

    //Override del comportamento del back Button che passa alla schermata precedente/esce
    override fun onBackPressed() {

        if (backBtnLastPress + 2000 > System.currentTimeMillis()){
            this.finish()
            exitProcess(0)
        }
        else {
            Toast.makeText(this, getString(R.string.double_back_prompt), Toast.LENGTH_SHORT)
                .show()
            backBtnLastPress = System.currentTimeMillis()
        }
    }

    fun doDiscoverFlow() {
        val snackbarView = findViewById<View>(R.id.home_generalContainer)
        val progressionIndicator = findViewById<View>(R.id.linearProgressIndicator)
        val meeterName = findViewById<TextView>(R.id.meeterName)
        val meeterCity = findViewById<TextView>(R.id.meeterLocation)
        val meeterBiography = findViewById<TextView>(R.id.biographyView)
        val interestLayout = findViewById<LinearLayout>(R.id.interestLayout)

        Thread {
            runOnUiThread {
                progressionIndicator.visibility = View.VISIBLE
            }

            val meeterList = MeeterProxyDAO(this).doRetrieveByCondition("${Meeter.MEETER_ID} = 6")

            if (meeterList != null) {
                Snackbar.make(snackbarView, meeterList.size.toString(), Snackbar.LENGTH_SHORT).show()
            }
            runOnUiThread {
                progressionIndicator.visibility = View.GONE
            }

            if (meeterList != null) {
                val meeter = meeterList[0]
                meeterName.text = "${meeter.meeterName} ${meeter.meeterSurname}, ${getAge(meeter.birthDate)}"
                meeterCity.text = "${meeter.publicKey}"
                meeterBiography.text = meeter.biography

                val meeter_interestList = Meeter_InterestProxyDAO(this).doRetrieveByCondition("${Meeter_Interest.MEETER_INTEREST_MEETER_ID} = ${meeter.id}")

                val interestDAO = InterestProxyDAO(this)
                if (meeter_interestList != null) {
                    for(meeter_interest in meeter_interestList){
                        val interest = interestDAO.doRetrieveByKey(meeter_interest.id.toString())
                        if (interest != null) {
                            val intTxt = MaterialButton(this)
                            intTxt.text = interest.description
                            intTxt.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
                            intTxt.setTextColor(Color.BLACK)

                            val space = Space(this)


                            runOnUiThread {
                                interestLayout.addView(intTxt)
                                interestLayout.addView(space)
                                space.layoutParams.width = 16
                            }

                        }
                    }
                }

            }
            else
                Snackbar.make(snackbarView, getString(R.string.connection_error), Snackbar.LENGTH_SHORT).show()

        }.start()
    }

    fun getAge(birthday: Date): Int{
        val now = Calendar.getInstance().timeInMillis
        val diff = Calendar.getInstance()
        diff.timeInMillis = now - birthday.time
        return (diff.get(Calendar.YEAR) - 1970)
    }

}