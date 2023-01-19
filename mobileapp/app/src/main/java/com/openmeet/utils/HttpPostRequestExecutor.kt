package com.openmeet.utils

import com.android.volley.toolbox.StringRequest

class HttpPostRequestExecutor {

    fun doHttpPostRequest(url : String, params : Map<String, String>, callbackForSuccess : (String) -> Unit, callbackForError : (String) -> Unit) {

        val stringRequest = object : StringRequest(
            Method.POST, url, { response ->
                callbackForSuccess(response)
            }, { error ->
                callbackForError(error.toString())
            }) {
            override fun getParams() = params
        }
    }
}