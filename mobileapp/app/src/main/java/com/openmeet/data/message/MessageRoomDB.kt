package com.openmeet.data.message

import androidx.room.*
import com.openmeet.shared.data.message.Message
import com.openmeet.shared.data.storage.DAO
import java.sql.Timestamp

class MessageRoomDB {
    @Entity
    data class LocalMessage(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "text") val text: String?,
        @ColumnInfo(name = "sent_time") val sentTime: Timestamp?,
        @ColumnInfo(name = "delivered_time") val deliveredTime: Timestamp?,
        @ColumnInfo(name = "read_time") val readTime: Timestamp?,
        @ColumnInfo(name = "meeter_sender") val meeterSender: Int,
        @ColumnInfo(name = "meeter_receiver") val meeterReceiver: Int
    )

    @Dao
    interface LocalMessageDao {
        @Query("SELECT * FROM LocalMessage")
        fun getAll(): List<LocalMessage>

        @Query("SELECT * FROM LocalMessage WHERE meeter_sender = :meeterSender")
        fun findBySender(meeterSender: Int): LocalMessage

        @Query("SELECT * FROM LocalMessage WHERE meeter_receiver = :meeterReceiver")
        fun findByReceiver(meeterReceiver: Int): LocalMessage

        @Insert
        fun insertAll(vararg users: LocalMessage)

        @Delete
        fun delete(msg: LocalMessage)
    }

    @Database(entities = [LocalMessage::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun userDao(): LocalMessageDao
    }

}