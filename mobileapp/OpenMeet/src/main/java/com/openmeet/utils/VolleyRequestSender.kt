package com.openmeet.utils

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

/**
 * This class is used to send HTTP requests to the server
 *
 * @author Yuri Brandi
 */
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

        const val ERROR_STR = "VOLLEY_ERROR"

    }


    val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }


    fun doHttpPostRequest(
        url: String,
        params: HashMap<String, String>,
        /*callbackForSuccess: (String) -> Unit,
        callbackForError: (String) -> Unit*/
        callback: VolleyResponseCallback
    ) {

        //create a string request and use a success callback in case of success
        //but in case of failure, use a failure callback

        val stringRequest = object : StringRequest(
            Method.POST, url,
            { response ->
                callback.onSuccess(response)
                println("Sto per chimare il callback di successo")
                //callbackForSuccess(response)
            },
            { error ->
                callback.onError("$ERROR_STR $error")

                println("Sto per chimare il callback di errore")
                //callbackForError(error.toString())

            }) {
            override fun getParams() = params

        }
        this.addToRequestQueue(stringRequest)
    }
}