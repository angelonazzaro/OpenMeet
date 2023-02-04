package com.openmeet.data.interest

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.openmeet.shared.data.interest.Interest
import com.openmeet.shared.data.storage.DAO
import com.openmeet.shared.data.storage.DAO.logger
import com.openmeet.utils.ContextDAO
import com.openmeet.utils.VolleyRequestSender
import com.openmeet.utils.VolleyResponseCallback
import org.json.JSONObject
import java.util.HashMap
import java.util.concurrent.CountDownLatch
import java.util.logging.Level

class InterestProxyDAO(context: Context) : ContextDAO(context), DAO<Interest> {

    override fun doRetrieveByCondition(condition: String): MutableList<Interest>? {

        logger.log(Level.INFO, "doRetrieveByCondition: $condition")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "InterestService",
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

        val interests = jsonResp.getString("data")
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        logger.log(Level.INFO, "doRetrieveByCondition: $interests")

        return gson.fromJson(interests, Array<Interest>::class.java).toMutableList()
    }

    override fun doRetrieveByCondition(condition: String, offset: Int, row_count: Int): MutableList<Interest>? {

        logger.log(Level.INFO, "doRetrieveByCondition: $condition")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "InterestService",
                hashMapOf("operation" to DAO.DO_RETRIEVE_BY_CONDITION_LIMIT, "condition" to condition, "offset" to offset.toString(), "rows_count" to row_count.toString()),
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

        val interests = jsonResp.getString("data")
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        logger.log(Level.INFO, "doRetrieveByCondition: $interests")

        return gson.fromJson(interests, Array<Interest>::class.java).toMutableList()
    }

    override fun doRetrieveByKey(key: String): Interest? {

        logger.log(Level.INFO, "doRetrieveByKey: $key")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "InterestService",
                hashMapOf("operation" to DAO.DO_RETRIEVE_BY_KEY, "key" to key),
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

        println(resp)

        if (resp.contains(VolleyRequestSender.ERROR_STR))
            return null

        val jsonResp = JSONObject(resp)

        if (jsonResp.getString("status") == "error")
            return null

        val interest = jsonResp.getString("data")
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        logger.log(Level.INFO, "doRetrieveByKey: $interest")

        return gson.fromJson(interest, Interest::class.java)


    }

    override fun doRetrieveAll(): MutableList<Interest>? {

        logger.log(Level.INFO, "doRetrieveAll")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "InterestService",
                hashMapOf("operation" to DAO.DO_RETRIEVE_ALL),
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

        val interests = jsonResp.getString("data")
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        logger.log(Level.INFO, "doRetrieveAll: $interests")

        return gson.fromJson(interests, Array<Interest>::class.java).toMutableList()

    }

    override fun doRetrieveAll(row_count: Int): MutableList<Interest>? {

        logger.log(Level.INFO, "doRetrieveAll: $row_count")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "InterestService",
                hashMapOf(
                    "operation" to DAO.DO_RETRIEVE_ALL_LIMIT,
                    "row_count" to row_count.toString()
                ),
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

        val interests = jsonResp.getString("data")
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        logger.log(Level.INFO, "doRetrieveAll: $interests")

        return gson.fromJson(interests, Array<Interest>::class.java).toMutableList()

    }

    override fun doRetrieveAll(offset: Int, row_count: Int): MutableList<Interest>? {

        logger.log(Level.INFO, "doRetrieveAll: $offset, $row_count")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "InterestService",
                hashMapOf(
                    "operation" to DAO.DO_RETRIEVE_ALL_LIMIT_OFFSET,
                    "offset" to offset.toString(),
                    "row_count" to row_count.toString()
                ),
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

        val interests = jsonResp.getString("data")
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        logger.log(Level.INFO, "doRetrieveAll: $interests")

        return gson.fromJson(interests, Array<Interest>::class.java).toMutableList()

    }

    override fun doSave(obj: Interest?): Boolean {

        logger.log(Level.INFO, "doSave: $obj")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "InterestService",
                hashMapOf(
                    "operation" to DAO.DO_SAVE,
                    "interest" to GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(obj)
                ),
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

        println(resp)

        if (resp.contains(VolleyRequestSender.ERROR_STR))
            return false

        val jsonResp = JSONObject(resp)

        if (jsonResp.getString("status") == "error")
            return false

        logger.log(Level.INFO, "doSave: ${jsonResp.getString("data")}")

        return jsonResp.getString("data").toBoolean()

    }

    override fun doSave(values: HashMap<String, *>?): Boolean {

        logger.log(Level.INFO, "doSave: $values")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "InterestService",
                hashMapOf(
                    "operation" to DAO.DO_SAVE_PARTIAL,
                    "values" to GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(values)
                ),
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

        logger.log(Level.INFO, "doSave: ${jsonResp.getString("data")}")

        return jsonResp.getString("data").toBoolean()

    }

    override fun doUpdate(values: HashMap<String, *>?, condition: String): Boolean {

        logger.log(Level.INFO, "doUpdate: $values, $condition")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "InterestService",
                hashMapOf(
                    "operation" to DAO.DO_UPDATE,
                    "values" to Gson().toJson(values),
                    "condition" to condition
                ),
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

        println(resp)

        if (resp.contains(VolleyRequestSender.ERROR_STR))
            return false

        val jsonResp = JSONObject(resp)

        if (jsonResp.getString("status") == "error")
            return false

        logger.log(Level.INFO, "doUpdate: ${jsonResp.getString("data")}")

        return jsonResp.getString("data").toBoolean()

    }

    override fun doSaveOrUpdate(obj: Interest?): Boolean {

        logger.log(Level.INFO, "doSaveOrUpdate: $obj")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "InterestService",
                hashMapOf(
                    "operation" to DAO.DO_SAVE_OR_UPDATE,
                    "interest" to GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(obj)
                ),
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

        println(resp)

        if (resp.contains(VolleyRequestSender.ERROR_STR))
            return false

        val jsonResp = JSONObject(resp)

        if (jsonResp.getString("status") == "error")
            return false

        logger.log(Level.INFO, "doSaveOrUpdate: ${jsonResp.getString("data")}")

        return jsonResp.getString("data").toBoolean()

    }

    override fun doDelete(condition: String): Boolean {

        logger.log(Level.INFO, "doDelete: $condition")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "InterestService",
                hashMapOf("operation" to DAO.DO_DELETE, "condition" to condition),
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

        println(resp)

        if (resp.contains(VolleyRequestSender.ERROR_STR))
            return false

        val jsonResp = JSONObject(resp)

        if (jsonResp.getString("status") == "error")
            return false

        logger.log(Level.INFO, "doDelete: ${jsonResp.getString("data")}")

        return jsonResp.getString("data").toBoolean()
    }


}
