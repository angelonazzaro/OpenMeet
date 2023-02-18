package com.openmeet.utils

/**
 * This interface is used to manage the response of the VolleyRequestSender
 *
 * @author Yuri Brandi
 */
interface VolleyResponseCallback {
    fun onError(error: String)
    fun onSuccess(response: String)
}