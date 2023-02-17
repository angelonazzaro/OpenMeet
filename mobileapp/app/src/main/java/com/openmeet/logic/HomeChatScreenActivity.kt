package com.openmeet.logic

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.divider.MaterialDivider
import com.google.android.material.snackbar.Snackbar
import com.openmeet.R
import com.openmeet.data.meeter.MeeterProxyDAO
import com.openmeet.data.message.MessageProxyDAO
import com.openmeet.data.rating.RatingProxyDAO
import com.openmeet.shared.data.meeter.Meeter
import com.openmeet.shared.data.message.Message
import java.util.*
import kotlin.system.exitProcess


/**
 * This class is used to display the chats preview.
 *
 * @author Yuri Brandi
 */
class HomeChatScreenActivity : AppCompatActivity() {
    private var backBtnLastPress = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_chat_screen)


        val snackbarView = findViewById<View>(R.id.home_generalContainer)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)



        bottomNav.selectedItemId = R.id.chat_Tab



        bottomNav.setOnItemSelectedListener{ item ->
            when(item.itemId){
                R.id.discover_Tab -> {
                    startActivity(
                        Intent(this, HomeScreenActivity::class.java).putExtra("ID", intent.getStringExtra("ID").toString())
                    )
                    overridePendingTransition(0, 0)
                }

                R.id.user_Tab -> {
                    startActivity(
                        Intent(this, HomeUserScreenActivity::class.java).putExtra(
                            "ID",
                            intent.getStringExtra("ID").toString()
                        )
                    )
                    overridePendingTransition(0, 0)
                }
            }
            true
        }


        retrieveChats(intent.getStringExtra("ID").toString())
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

    /**
     * This method is used to retrieve the chats preview.
     *
     * @param meeterID the meeter ID
     *
     * @author Yuri Brandi
     */
    fun retrieveChats(meeterID: String){

        val progressionIndicator = findViewById<View>(R.id.linearProgressIndicator)
        val snackbarView = findViewById<View>(R.id.home_generalContainer)
        val emptyChatTxt = findViewById<TextView>(R.id.emptyChatView)

        emptyChatTxt.visibility = View.GONE

        Thread {

            runOnUiThread { progressionIndicator.visibility = View.VISIBLE }

            val MatchedList = RatingProxyDAO(this).doRetrieveMatches(meeterID)
            println(MatchedList)
            if (MatchedList != null)
                if(MatchedList.size > 0){
                    val ChatList = MessageProxyDAO(this).doRetrieveByCondition("${Message.MESSAGE_MEETER_SENDER} = $meeterID OR ${Message.MESSAGE_MEETER_RECEIVER} = $meeterID")
                    if (ChatList != null) {
                        displayChatsPreview(MatchedList, ChatList)
                    }
                    else
                        Snackbar.make(snackbarView, getString(R.string.connection_error) + "1", Snackbar.LENGTH_SHORT).show()
                }
                else
                    runOnUiThread { emptyChatTxt.visibility = View.VISIBLE }
            else
                Snackbar.make(snackbarView, getString(R.string.connection_error)+ "2", Snackbar.LENGTH_SHORT).show()

            runOnUiThread { progressionIndicator.visibility = View.GONE }
        }.start()
    }


    /**
     * This method is used to display the chats preview.
     *
     * @param matchedList the matched list
     * @param chatList the chat list
     *
     * @author Yuri
     */
    fun displayChatsPreview(matchedList: MutableList<Meeter>, chatList: MutableList<Message>){

        val chatsView = findViewById<LinearLayout>(R.id.chatsLayout)
        val snackbarView = findViewById<View>(R.id.home_generalContainer)

        for(matchedMeeter in matchedList){
            val messageView = LinearLayout(this)
            messageView.orientation = LinearLayout.HORIZONTAL

            val meeter = MeeterProxyDAO(this).doRetrieveByKey(matchedMeeter.id.toString())
            if(meeter != null){

                val view: LinearLayout =
                    LayoutInflater.from(this).inflate(R.layout.chat_view, null) as LinearLayout

                view.setOnClickListener {
                    val intent = Intent(this, SingleChatActivity::class.java)
                    intent.putExtra("ID", intent.getStringExtra("ID").toString())
                    intent.putExtra("RECEIVER_ID", matchedMeeter.id.toString())

                    startActivity(intent)
                    overridePendingTransition(0, 0)
                }
                val divider = MaterialDivider(this)
                divider.dividerInsetStart = 24
                divider.dividerInsetEnd = 24

                runOnUiThread {


                    for(v in view.children){
                        if (v is TextView && v.id == R.id.textValues)
                            v.text = "${meeter.meeterName} ${meeter.meeterSurname} "
                    }
                    chatsView.addView(view)
                    chatsView.addView(divider)
                }
            }
            else
                Snackbar.make(snackbarView, getString(R.string.connection_error), Snackbar.LENGTH_SHORT).show()

        }

    }

}