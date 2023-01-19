package com.openmeet.utils

import android.content.Context
import com.openmeet.R


abstract class ContextDAO(context: Context) {

    protected val context = context

    fun getUrl() : String {
        return "http://" + context.getString(R.string.request_server_address)
    }
}