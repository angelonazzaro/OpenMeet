package com.openmeet.data.message

import android.content.Context
import androidx.room.*
import com.openmeet.shared.data.message.Message
import com.openmeet.shared.data.storage.DAO
import java.sql.Timestamp

/**
 * This class is used to store the messages in the local database
 *
 * @author Yuri Brandi
 */
class MessageRoomDB {
//    @Entity
//    data class LocalMessage(
//        @PrimaryKey val id: Int,
//        @ColumnInfo(name = "text") val text: String?,
//        @ColumnInfo(name = "sent_time") val sentTime: Timestamp?,
//        @ColumnInfo(name = "delivered_time") val deliveredTime: Timestamp?,
//        @ColumnInfo(name = "read_time") val readTime: Timestamp?,
//        @ColumnInfo(name = "meeter_sender") val meeterSender: Int,
//        @ColumnInfo(name = "meeter_receiver") val meeterReceiver: Int
//    )

    @Entity
    data class LocalMessage (

        @PrimaryKey(autoGenerate = true) val id: Int,
        @Embedded(prefix = "rmt_") val message: Message
    )

    @Dao
    interface LocalMessageDao {
        @Query("SELECT * FROM LocalMessage")
        fun getAll(): List<LocalMessage>

        @Query("SELECT * FROM LocalMessage WHERE rmt_meeter_sender = :meeterSender")
        fun findBySender(meeterSender: Int): LocalMessage

        @Query("SELECT * FROM LocalMessage WHERE rmt_meeter_receiver = :meeterReceiver")
        fun findByReceiver(meeterReceiver: Int): LocalMessage

        @Insert
        fun insertAll(vararg users: LocalMessage)

        @Delete
        fun delete(msg: LocalMessage)
    }

    @Database(entities = [LocalMessage::class], version = 1, exportSchema = false)
    abstract class AppDatabase : RoomDatabase() {

        abstract fun msgDao(): LocalMessageDao

        //Singleton DI

        companion object {

            @Volatile private var INSTANCE: AppDatabase? = null

            fun getDatabase(context: Context): AppDatabase {
                val tmp = INSTANCE
                if (tmp != null)
                    return tmp
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_db"
                    ).build()
                    INSTANCE = instance
                    return instance
                }

            }

        }
    }

}