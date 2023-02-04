package com.openmeet.data.report

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.openmeet.shared.data.report.Report
import com.openmeet.shared.data.storage.DAO
import com.openmeet.utils.ContextDAO
import com.openmeet.utils.VolleyRequestSender
import com.openmeet.utils.VolleyResponseCallback
import org.json.JSONObject
import java.util.HashMap
import java.util.concurrent.CountDownLatch
import java.util.logging.Level

class ReportProxyDAO(context: Context) : ContextDAO(context), DAO<Report> {

    override fun doRetrieveByCondition(condition: String): MutableList<Report>? {

        DAO.logger.log(Level.INFO, "doRetrieveByCondition: $condition")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "ReportService",
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

        val reports = jsonResp.getString("data")
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        DAO.logger.log(Level.INFO, "doRetrieveByCondition: $reports")

        return gson.fromJson(reports, Array<Report>::class.java).toMutableList()
    }

    override fun doRetrieveByCondition(condition: String, offset: Int, row_count: Int): MutableList<Report>? {

        DAO.logger.log(Level.INFO, "doRetrieveByCondition: $condition")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "ReportService",
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

        val reports = jsonResp.getString("data")
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        DAO.logger.log(Level.INFO, "doRetrieveByCondition: $reports")

        return gson.fromJson(reports, Array<Report>::class.java).toMutableList()
    }

    override fun doRetrieveByKey(key: String): Report? {

        DAO.logger.log(Level.INFO, "doRetrieveByKey: $key")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "ReportService",
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

        val report = jsonResp.getString("data")
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        DAO.logger.log(Level.INFO, "doRetrieveByKey: $report")

        return gson.fromJson(report, Report::class.java)


    }

    override fun doRetrieveAll(): MutableList<Report>? {

        DAO.logger.log(Level.INFO, "doRetrieveAll")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "ReportService",
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

        val reports = jsonResp.getString("data")
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        DAO.logger.log(Level.INFO, "doRetrieveAll: $reports")

        return gson.fromJson(reports, Array<Report>::class.java).toMutableList()

    }

    override fun doRetrieveAll(row_count: Int): MutableList<Report>? {

        DAO.logger.log(Level.INFO, "doRetrieveAll: $row_count")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "ReportService",
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

        val reports = jsonResp.getString("data")
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        DAO.logger.log(Level.INFO, "doRetrieveAll: $reports")

        return gson.fromJson(reports, Array<Report>::class.java).toMutableList()

    }

    override fun doRetrieveAll(offset: Int, row_count: Int): MutableList<Report>? {

        DAO.logger.log(Level.INFO, "doRetrieveAll: $offset, $row_count")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "ReportService",
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

        val reports = jsonResp.getString("data")
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

        DAO.logger.log(Level.INFO, "doRetrieveAll: $reports")

        return gson.fromJson(reports, Array<Report>::class.java).toMutableList()

    }

    override fun doSave(obj: Report?): Boolean {

        DAO.logger.log(Level.INFO, "doSave: $obj")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "ReportService",
                hashMapOf(
                    "operation" to DAO.DO_SAVE,
                    "report" to GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(obj)
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
            .doHttpPostRequest(getUrl() + "ReportService",
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
            .doHttpPostRequest(getUrl() + "ReportService",
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

    override fun doSaveOrUpdate(obj: Report?): Boolean {

        DAO.logger.log(Level.INFO, "doSaveOrUpdate: $obj")

        var resp = ""
        val latch = CountDownLatch(1)

        VolleyRequestSender.getInstance(this.context)
            .doHttpPostRequest(getUrl() + "ReportService",
                hashMapOf(
                    "operation" to DAO.DO_SAVE_OR_UPDATE,
                    "report" to GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(obj)
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
            .doHttpPostRequest(getUrl() + "ReportService",
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