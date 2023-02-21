package com.openmeet.logic

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.openmeet.R
import com.openmeet.data.message.MessageProxyDAO
import com.openmeet.data.message.MessageRoomDB.*
import com.openmeet.data.rating.RatingProxyDAO
import com.openmeet.shared.data.message.Message

class SingleChatActivity : AppCompatActivity() {

    // it will created if is necessary
    private val appDB by lazy {
        AppDatabase.getDatabase(this).msgDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_single_chat_screen)

        val senderMeeter = null
        val receiverMeeter = null
        var temp = null

    }

}