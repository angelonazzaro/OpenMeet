package com.openmeet.data.meeter_interest

import com.openmeet.shared.data.storage.IEntity

data class Meeter_Interest(var id: Int = 0, var meeterId: Int = 0) : IEntity {

    companion object {
        const val MEETER_INTEREST = "Meeter_Interest"
        const val MEETER_INTEREST_ID = "$MEETER_INTEREST.id"
        const val MEETER_INTEREST_MEETER_ID = "$MEETER_INTEREST.meeterId"
    }

    override fun toHashMap(): HashMap<String, *> {

        val map = HashMap<String, Any>()
        map["id"] = id
        map["meeterId"] = meeterId

        return map
    }

    override fun toHashMap(vararg fields: String?): java.util.HashMap<String, *> {

        val map = HashMap<String, Any>()
        for (field in fields) {
            when (field) {
                MEETER_INTEREST_ID -> map["id"] = id
                MEETER_INTEREST_MEETER_ID -> map["meeterId"] = meeterId
            }
        }
        return map
    }
}