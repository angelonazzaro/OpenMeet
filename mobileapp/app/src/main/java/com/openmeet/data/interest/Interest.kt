package com.openmeet.data.interest

import com.openmeet.shared.data.storage.IEntity
import java.util.HashMap

data class Interest(var id: Int = 0, var description: String? = null) : IEntity {

    companion object {
        const val INTEREST = "Interest"
        const val INTEREST_ID = "$INTEREST.id"
        const val INTEREST_DESCRIPTION = "$INTEREST.description"
    }

    override fun toHashMap(): HashMap<String, *> {

        val map = HashMap<String, Any>()
        map["id"] = id
        map["description"] = description!!

        return map
    }

    override fun toHashMap(vararg fields: String?): HashMap<String, *> {

        val map = HashMap<String, Any>()
        for (field in fields) {
            when (field) {
                INTEREST_ID -> map["id"] = id
                INTEREST_DESCRIPTION -> map["description"] = description!!
            }
        }
        return map
    }
}