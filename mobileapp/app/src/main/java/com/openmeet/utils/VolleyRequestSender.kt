package com.openmeet.utils

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.openmeet.utils.InvalidVolleyRequestException
import java.lang.System.console

class VolleyRequestSender private constructor(context: Context) {
    /*
        A key concept is that the RequestQueue must be instantiated with the Application context,
        not an Activity context. This ensures that the RequestQueue will last for the lifetime of your app,
        instead of being recreated every time the activity is recreated (for example, when the user rotates the device).
     */

    companion object {
        @Volatile
        private var INSTANCE: VolleyRequestSender? = null

        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: VolleyRequestSender(context).also { INSTANCE = it }
            }

    }


    val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }


    fun doHttpPostRequest(url: String, params: Map<String, String>): String? {

        var returnObj = ""
        var isSuccesful = false

        println("CI ARRIVO 1")

        val stringRequest = object : StringRequest(
            Method.POST, url, { response ->

                returnObj = response
                isSuccesful = true
                println("CI ARRIVO SUCCESSO $response $returnObj $isSuccesful")
            },
            { error ->

                returnObj = error.networkResponse.statusCode.toString()
                error.toString()
                isSuccesful = false
                println("CI ARRIVO ERRORE $error $returnObj $isSuccesful")
            }) {


//            override fun getParams() = params

            override fun getParams(): MutableMap<String, String> {
                val parametri = HashMap<String, String>()
                parametri["operation"] = "doRetrieveByCondition"
                parametri["condition"] = "TRUE"
                return parametri
            }

        }
        println("PARAMETRI: $params")
        this.addToRequestQueue(stringRequest)

        println("CI ARRIVO SONO USCITO")

        if (isSuccesful) {
            println("IO RETURNO $returnObj")
            return returnObj
        }
        return null
//        throw InvalidVolleyRequestException(returnObj)
    }
}