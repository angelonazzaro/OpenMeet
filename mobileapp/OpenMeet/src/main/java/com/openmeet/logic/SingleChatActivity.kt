package com.openmeet.logic

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.openmeet.R
import com.openmeet.data.meeter.MeeterProxyDAO
import com.openmeet.data.message.MessageProxyDAO
import com.openmeet.data.message.MessageRoomDB.*
import com.openmeet.shared.data.meeter.Meeter
import com.openmeet.shared.data.message.Message
import java.util.*

class SingleChatActivity : AppCompatActivity() {

    // it will be created if necessary
    private val appDB by lazy {
        AppDatabase.getDatabase(this).msgDao()
    }

    private var senderMeeter = Meeter()
    private var receiverMeeter = Meeter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_single_chat_screen)

        val progressionIndicator = findViewById<View>(R.id.linearProgressIndicator)
        val snackbarView = findViewById<View>(R.id.home_generalContainer)
        val chatTitle = findViewById<TextView>(R.id.chatTitle)

        val backBtn = findViewById<Button>(R.id.backButton)
        val sendBtn = findViewById<Button>(R.id.sendButton)
        val messageField = findViewById<TextInputLayout>(R.id.messageField)

        Thread {
            runOnUiThread { progressionIndicator.visibility = View.VISIBLE }

            var temp = MeeterProxyDAO(this).doRetrieveByKey(intent.getStringExtra("ID").toString())
            if(temp == null)
                Snackbar.make(snackbarView, getString(R.string.connection_error), Snackbar.LENGTH_SHORT).show()
            else
                senderMeeter = temp

            temp = MeeterProxyDAO(this).doRetrieveByKey(intent.getStringExtra("RECEIVER_ID").toString())
            if(temp == null)
                Snackbar.make(snackbarView, getString(R.string.connection_error), Snackbar.LENGTH_SHORT).show()
            else {
                receiverMeeter = temp
                chatTitle.setText("${receiverMeeter.meeterName} ${receiverMeeter.meeterSurname}")
            }

            runOnUiThread { progressionIndicator.visibility = View.GONE }

        }.start()

        backBtn.setOnClickListener {
            super.getOnBackPressedDispatcher().onBackPressed()
            overridePendingTransition(0, 0)
        }

        sendBtn.setOnClickListener {
            val message = messageField.editText?.text.toString()
            sendMessage(senderMeeter.id, receiverMeeter.id, message)
            messageField.editText?.text?.clear()
        }
    }

    fun retrieveChats(senderId: Int, receiverId: Int){

    }

    fun sendMessage(senderId: Int, receiverId: Int, body: String){
        val snackbarView = findViewById<View>(R.id.home_generalContainer)
        Thread {
            val m = Message()
            m.meeterSender = senderId
            m.meeterReceiver = receiverId
            m.text = body
            m.sentTime = java.sql.Timestamp(System.currentTimeMillis())

            if(!MessageProxyDAO(this).doSave(m))
                Snackbar.make(snackbarView, getString(R.string.connection_error), Snackbar.LENGTH_SHORT).show()
        }.start()

    }

}