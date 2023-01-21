package com.openmeet.data.meeter

import android.content.Context
import com.openmeet.shared.data.meeter.Meeter
import com.openmeet.shared.data.storage.DAO
import com.openmeet.utils.ContextDAO
import com.openmeet.utils.VolleyRequestSender
import com.openmeet.utils.VolleyResponseCallback
import com.openmeet.utils.VolleyResponseListener


class MeeterProxyDAO(context: Context) : ContextDAO(context), DAO<Meeter> {

    override fun doRetrieveByCondition(condition: String): MutableList<Meeter> {




        return mutableListOf()
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
        TODO("Not yet implemented")
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