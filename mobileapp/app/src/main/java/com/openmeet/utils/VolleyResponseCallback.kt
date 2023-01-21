package com.openmeet.utils

interface VolleyResponseCallback {
    fun onError(error: String)
    fun onSuccess(response: String)
}