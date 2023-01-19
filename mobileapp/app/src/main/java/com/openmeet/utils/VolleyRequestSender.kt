package com.openmeet.utils

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleyRequestSender private constructor(context: Context){
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
}