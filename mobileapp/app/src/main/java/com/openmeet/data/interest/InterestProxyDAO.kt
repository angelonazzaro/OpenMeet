package com.openmeet.data.interest

import com.openmeet.shared.data.interest.Interest
import com.openmeet.shared.data.storage.DAO
import java.util.HashMap

class InterestProxyDAO : DAO<Interest> {

    override fun doRetrieveByCondition(p0: String?): MutableList<Interest> {
        TODO("Not yet implemented")
    }

    override fun doRetrieveByKey(p0: String?): Interest {
        TODO("Not yet implemented")
    }

    override fun doRetrieveAll(): MutableList<Interest> {
        TODO("Not yet implemented")
    }

    override fun doRetrieveAll(p0: Int): MutableList<Interest> {
        TODO("Not yet implemented")
    }

    override fun doRetrieveAll(p0: Int, p1: Int): MutableList<Interest> {
        TODO("Not yet implemented")
    }

    override fun doSave(p0: Interest?): Boolean {
        TODO("Not yet implemented")
    }

    override fun doSave(p0: HashMap<String, *>?): Boolean {
        TODO("Not yet implemented")
    }

    override fun doUpdate(p0: HashMap<String, *>?, p1: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun doSaveOrUpdate(p0: Interest?): Boolean {
        TODO("Not yet implemented")
    }

    override fun doDelete(p0: String?): Boolean {
        TODO("Not yet implemented")
    }
}