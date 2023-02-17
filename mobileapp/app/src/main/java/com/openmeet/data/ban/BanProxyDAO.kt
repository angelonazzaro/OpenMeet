package com.openmeet.data.ban

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.openmeet.shared.data.ban.Ban
import com.openmeet.shared.data.storage.DAO
import com.openmeet.utils.ContextDAO
import com.openmeet.utils.VolleyRequestSender
import com.openmeet.utils.VolleyResponseCallback
import org.json.JSONObject
import java.util.HashMap
import java.util.concurrent.CountDownLatch
import java.util.logging.Level

/**
 * This class is used to make request to the BanService and manage the response.
 *
 * @see DAO
 *
 * @author Yuri Brandi
 */
class BanProxyDAO(context: Context) : ContextDAO(context), DAO<Ban> {

    override fun doRetrieveByCondition(condition: String): MutableList<Ban>? {

        DAO.logger.log(Level.INFO, "doRetrieveByCondition: $condition")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "BanService",
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

        val bans = jsonResp.getString("data")
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        DAO.logger.log(Level.INFO, "doRetrieveByCondition: $bans")

        return gson.fromJson(bans, Array<Ban>::class.java).toMutableList()
    }

    override fun doRetrieveByCondition(condition: String, rows_count: Int): MutableList<Ban>? {

        DAO.logger.log(Level.INFO, "doRetrieveByCondition: $condition")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "BanService",
                hashMapOf("operation" to DAO.DO_RETRIEVE_BY_CONDITION_LIMIT, "condition" to condition, "rows_count" to rows_count.toString()),
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

        val bans = jsonResp.getString("data")
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        DAO.logger.log(Level.INFO, "doRetrieveByCondition: $bans")

        return gson.fromJson(bans, Array<Ban>::class.java).toMutableList()
    }

    override fun doRetrieveByCondition(condition: String, offset: Int, rows_count: Int): MutableList<Ban>? {

        DAO.logger.log(Level.INFO, "doRetrieveByCondition: $condition")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "BanService",
                hashMapOf("operation" to DAO.DO_RETRIEVE_BY_CONDITION_LIMIT_OFFSET, "condition" to condition, "offset" to offset.toString(), "rows_count" to rows_count.toString()),
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

        val bans = jsonResp.getString("data")
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        DAO.logger.log(Level.INFO, "doRetrieveByCondition: $bans")

        return gson.fromJson(bans, Array<Ban>::class.java).toMutableList()
    }

    override fun doRetrieveByKey(key: String): Ban? {

        DAO.logger.log(Level.INFO, "doRetrieveByKey: $key")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "BanService",
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

        val bans = jsonResp.getString("data")
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        DAO.logger.log(Level.INFO, "doRetrieveByKey: $bans")

        return gson.fromJson(bans, Ban::class.java)


    }

    override fun doRetrieveAll(): MutableList<Ban>? {

        DAO.logger.log(Level.INFO, "doRetrieveAll")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "BanService",
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

        val bans = jsonResp.getString("data")
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        DAO.logger.log(Level.INFO, "doRetrieveAll: $bans")

        return gson.fromJson(bans, Array<Ban>::class.java).toMutableList()

    }

    override fun doRetrieveAll(row_count: Int): MutableList<Ban>? {

        DAO.logger.log(Level.INFO, "doRetrieveAll: $row_count")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "BanService",
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

        val bans = jsonResp.getString("data")
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        DAO.logger.log(Level.INFO, "doRetrieveAll: $bans")

        return gson.fromJson(bans, Array<Ban>::class.java).toMutableList()

    }

    override fun doRetrieveAll(offset: Int, row_count: Int): MutableList<Ban>? {

        DAO.logger.log(Level.INFO, "doRetrieveAll: $offset, $row_count")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "BanService",
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

        val bans = jsonResp.getString("data")
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        DAO.logger.log(Level.INFO, "doRetrieveAll: $bans")

        return gson.fromJson(bans, Array<Ban>::class.java).toMutableList()

    }

    override fun doSave(obj: Ban?): Boolean {

        DAO.logger.log(Level.INFO, "doSave: $obj")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "BanService",
                hashMapOf(
                    "operation" to DAO.DO_SAVE,
                    "Ban" to GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(obj)
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

        DAO.logger.log(Level.INFO, "doSave: ${jsonResp.getString("data")}")

        return jsonResp.getString("data").toBoolean()

    }

    override fun doSave(values: HashMap<String, *>?): Boolean {

        DAO.logger.log(Level.INFO, "doSave: $values")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "BanService",
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

        DAO.logger.log(Level.INFO, "doSave: ${jsonResp.getString("data")}")

        return jsonResp.getString("data").toBoolean()

    }

    override fun doUpdate(values: HashMap<String, *>?, condition: String): Boolean {

        DAO.logger.log(Level.INFO, "doUpdate: $values, $condition")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "BanService",
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

        DAO.logger.log(Level.INFO, "doUpdate: ${jsonResp.getString("data")}")

        return jsonResp.getString("data").toBoolean()

    }

    override fun doSaveOrUpdate(obj: Ban?): Boolean {

        DAO.logger.log(Level.INFO, "doSaveOrUpdate: $obj")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "BanService",
                hashMapOf(
                    "operation" to DAO.DO_SAVE_OR_UPDATE,
                    "Ban" to GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(obj)
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

        DAO.logger.log(Level.INFO, "doSaveOrUpdate: ${jsonResp.getString("data")}")

        return jsonResp.getString("data").toBoolean()

    }

    override fun doDelete(condition: String): Boolean {

        DAO.logger.log(Level.INFO, "doDelete: $condition")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "BanService",
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

        DAO.logger.log(Level.INFO, "doDelete: ${jsonResp.getString("data")}")

        return jsonResp.getString("data").toBoolean()
    }
}