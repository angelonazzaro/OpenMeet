package com.openmeet.data.meeter

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.openmeet.shared.data.meeter.Meeter
import com.openmeet.shared.data.storage.DAO
import com.openmeet.utils.ContextDAO
import com.openmeet.utils.VolleyRequestSender
import com.openmeet.utils.VolleyResponseCallback
import org.json.JSONObject
import java.util.concurrent.CountDownLatch


class MeeterProxyDAO(context: Context) : ContextDAO(context), DAO<Meeter> {


    override fun doRetrieveByCondition(condition: String): MutableList<Meeter>? {

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "MeeterService",
                hashMapOf("operation" to DAO.DO_RETRIEVE_BY_CONDITION, "condition" to condition),
                object : VolleyResponseCallback {
                    override fun onError(error: String) {
                        resp = error
                        latch.countDown()
                    }

                    override fun onSuccess(response: String) {
                        resp = response
                        latch.countDown()
                    }

                }
            )

        latch.await()

        if (resp.contains(VolleyRequestSender.ERROR_STR))
            return null

        val jsonResp = JSONObject(resp)

        if (jsonResp.getString("status") == "error")
            return null

        val meetersData = jsonResp.getString("data")

        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()
        return gson.fromJson(meetersData, Array<Meeter>::class.java).toMutableList()

    }

    override fun doRetrieveByKey(key: String?): Meeter {
        TODO("Not yet implemented")
    }

    override fun doRetrieveAll(): MutableList<Meeter> {
        TODO("Not yet implemented")
    }

    override fun doRetrieveAll(row_count: Int): MutableList<Meeter> {
        TODO("Not yet implemented")
    }

    override fun doRetrieveAll(offset: Int, row_count: Int): MutableList<Meeter> {
        TODO("Not yet implemented")
    }

    override fun doSave(obj: Meeter?): Boolean {

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "MeeterService",
                hashMapOf("operation" to DAO.DO_SAVE, "meeter" to obj.toString()),
                object : VolleyResponseCallback {
                    override fun onError(error: String) {
                        resp = error
                        latch.countDown()
                    }

                    override fun onSuccess(response: String) {
                        resp = response
                        latch.countDown()
                    }

                }
            )

        latch.await()

        if (resp.contains(VolleyRequestSender.ERROR_STR))
            return false

        val jsonResp = JSONObject(resp)

        if (jsonResp.getString("status") == "error")
            return false

        val isSuccesful = jsonResp.getString("data")

        return isSuccesful.toBoolean()
    }

    override fun doSave(values: HashMap<String, *>?): Boolean {
        TODO("Not yet implemented")
    }

    override fun doUpdate(obj: HashMap<String, *>?, p1: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun doSaveOrUpdate(obj: Meeter?): Boolean {
        TODO("Not yet implemented")
    }

    override fun doDelete(condition: String?): Boolean {
        TODO("Not yet implemented")
    }

}