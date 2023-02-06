package com.openmeet.logic

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.openmeet.R
import com.openmeet.data.interest.InterestProxyDAO
import com.openmeet.data.meeter.MeeterProxyDAO
import com.openmeet.data.meeter_interest.Meeter_InterestProxyDAO
import com.openmeet.data.report.ReportProxyDAO
import com.openmeet.shared.data.meeter.Meeter
import com.openmeet.shared.data.meeter_interest.Meeter_Interest
import com.openmeet.shared.data.report.Report
import java.sql.Timestamp
import java.util.*
import kotlin.system.exitProcess


class HomeScreenActivity : AppCompatActivity() {

    private var backBtnLastPress = 0L
    private var meeterList = mutableListOf<Meeter>()
    private var listIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)


        val snackbarView = findViewById<View>(R.id.home_generalContainer)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)


        val meeterInfoLayout = findViewById<ConstraintLayout>(R.id.meeterInfoLayout)
        val meeterBiography = findViewById<TextView>(R.id.biographyView)
        val interestView = findViewById<HorizontalScrollView>(R.id.horizontalScrollView)
        val reportBtn = findViewById<TextView>(R.id.reportText)
        val expandBtn = findViewById<ImageButton>(R.id.expandBtn)
        val compressBtn = findViewById<ImageButton>(R.id.compressBtn)

        val likeBtn = findViewById<FloatingActionButton>(R.id.floating_like_button)
        val dislikeBtn = findViewById<FloatingActionButton>(R.id.floating_like_button)
        val undoBtn = findViewById<FloatingActionButton>(R.id.floating_undo_button)

        bottomNav.selectedItemId = R.id.discover_Tab


        expandBtn.setOnClickListener {
            meeterBiography.visibility = View.VISIBLE
            interestView.visibility = View.VISIBLE
            reportBtn.visibility = View.VISIBLE
            compressBtn.visibility = View.VISIBLE
            expandBtn.visibility = View.GONE
            meeterInfoLayout.layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT

        }

        compressBtn.setOnClickListener {
            meeterBiography.visibility = View.GONE
            interestView.visibility = View.GONE
            reportBtn.visibility = View.GONE
            compressBtn.visibility = View.GONE
            expandBtn.visibility = View.VISIBLE
            meeterInfoLayout.layoutParams.height = ConstraintLayout.LayoutParams.MATCH_PARENT
        }

        undoBtn.setOnClickListener {
            undoBtn.visibility = View.INVISIBLE
            Thread {
                updateCardView(meeterList[--listIndex])
            }.start()
        }


        likeBtn.setOnClickListener {

            if (listIndex + 1 == meeterList.size)
                getDiscoverFlow()
            else {
                Thread {
                    updateCardView(meeterList[++listIndex])
                }.start()
            }

            undoBtn.visibility = View.VISIBLE

            object : CountDownTimer(3000, 3000) {
                override fun onTick(millisUntilFinished: Long) {
                    Log.d("TickPerformed", millisUntilFinished.toString())
                }

                override fun onFinish() {
                    undoBtn.visibility = View.INVISIBLE
                }
            }.start()


        }

        reportBtn.setOnClickListener {
            val view = LayoutInflater.from(this).inflate(R.layout.home_report_dialog, null)

            MaterialAlertDialogBuilder(this)
                .setView(view)
                .setTitle(R.string.report_dialog_title)
                .setPositiveButton(resources.getString(R.string.report_positive_dialog)) { dialog, which ->
                    //Toast.makeText(this, intent.getStringExtra("ID").toString(), Toast.LENGTH_SHORT).show()
                    doReportMeeter(view.findViewById<RadioGroup>(R.id.radioReportGroup).checkedRadioButtonId, Integer.parseInt(intent.getStringExtra("ID")), meeterList[listIndex].id)
                }
                .setNegativeButton(resources.getString(R.string.cancel_dialog)) { dialog, which -> }
                .show()
        }

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

                R.id.user_Tab -> {

                }
            }
            true
        }


        //findViewById<MaterialCardView>(R.id.materialCardView).setOnTouchListener()
        getDiscoverFlow()

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

    fun getDiscoverFlow() {
        val snackbarView = findViewById<View>(R.id.home_generalContainer)
        val progressionIndicator = findViewById<View>(R.id.linearProgressIndicator)



        Thread {
            runOnUiThread {
                progressionIndicator.visibility = View.VISIBLE
            }

            //Snackbar.make(snackbarView, "Reload", Snackbar.LENGTH_SHORT).show()
            val temp = MeeterProxyDAO(this).doRetrieveAll(
                10,
                10
            ) //Aggiungere ciclo degli offset
            if (temp != null) {
                if (meeterList.size != 0) {
                    val lastMeeter = meeterList[meeterList.size - 1]
                    meeterList.add(0, lastMeeter)
                    meeterList = temp
                } else {
                    meeterList = temp
                }

                Log.d("Meeter retrieved", meeterList.size.toString())
                updateCardView(meeterList[listIndex])

            } else
                Snackbar.make(
                    snackbarView,
                    getString(R.string.connection_error),
                    Snackbar.LENGTH_SHORT
                ).show()

            //Snackbar.make(snackbarView, meeterList.size.toString(), Snackbar.LENGTH_SHORT).show()

            runOnUiThread {
                progressionIndicator.visibility = View.GONE
            }


        }.start()
    }

    fun updateCardView(meeter: Meeter) {
        Log.d("Updating card", meeter.toString())

        val meeterName = findViewById<TextView>(R.id.meeterName)
        val meeterCity = findViewById<TextView>(R.id.meeterLocation)
        val meeterBiography = findViewById<TextView>(R.id.biographyView)

        runOnUiThread {
            meeterName.text =
                "${meeter.meeterName} ${meeter.meeterSurname}, ${getAge(meeter.birthdate)}"
            meeterCity.text = meeter.city
            meeterBiography.text = meeter.biography
        }

        resetInterestLayout()
        val meeter_interestList =
            Meeter_InterestProxyDAO(this).doRetrieveByCondition("${Meeter_Interest.MEETER_INTEREST_MEETER_ID} = ${meeter.id}")

        val interestDAO = InterestProxyDAO(this)
        if (meeter_interestList != null) {
            for (meeter_interest in meeter_interestList) {
                val interest = interestDAO.doRetrieveByKey(meeter_interest.id.toString())
                if (interest != null) {
                    addToInterestLayout(interest.description)
                }
            }
        }
    }

    fun addToInterestLayout(interest: String) {
        val interestLayout = findViewById<LinearLayout>(R.id.interestLayout)

        val intTxt = MaterialButton(this)
        intTxt.text = interest
        intTxt.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
        intTxt.setTextColor(Color.BLACK)

        val space = Space(this)

        runOnUiThread {
            interestLayout.addView(intTxt)
            interestLayout.addView(space)
            space.layoutParams.width = 16
        }
    }

    fun resetInterestLayout() {
        val interestLayout = findViewById<LinearLayout>(R.id.interestLayout)
        runOnUiThread {
            interestLayout.removeAllViewsInLayout()
        }
    }

    fun doRegisterRating(rating: Boolean, meetereRaterID: String, meeterRatedID: String) {

    }

    fun doReportMeeter(selectedRadio: Int, reporterMeeter: Int, reportedMeeter: Int){

        val snackbarView = findViewById<View>(R.id.home_generalContainer)

        val rep = Report()
        rep.meeterReporter = reporterMeeter
        rep.meeterReported = reportedMeeter
        rep.creationDate = Timestamp(System.currentTimeMillis())
        rep.reason = when(selectedRadio){
            R.id.radio_report_spam -> "Spam"

            R.id.radio_report_inappropriate -> "Inappropriate content"

            R.id.radio_report_nudity -> "Nudity"

            R.id.radio_report_impersonation -> "Impersonation"

            else -> "general_report" //Should never happen
        }

        Thread {

            if(ReportProxyDAO(this).doSave(rep)){
                runOnUiThread {
                    MaterialAlertDialogBuilder(this)
                        .setTitle(R.string.report_done_title)
                        .setMessage(R.string.report_done_message)
                        .setPositiveButton(R.string.positive_dialog){ dialog, which -> }
                        .show()
                }
            }
            else
                Snackbar.make(snackbarView, getString(R.string.connection_error), Snackbar.LENGTH_SHORT).show()

        }.start()

    }

    fun getAge(birthday: Date): Int {
        val now = Calendar.getInstance().timeInMillis
        val diff = Calendar.getInstance()
        diff.timeInMillis = now - birthday.time
        return (diff.get(Calendar.YEAR) - 1970)
    }

}