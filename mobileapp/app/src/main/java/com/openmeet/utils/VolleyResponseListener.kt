package com.openmeet.utils

interface VolleyResponseListener {

    fun requestFinished(isSuccessful: Boolean, response: String)
}