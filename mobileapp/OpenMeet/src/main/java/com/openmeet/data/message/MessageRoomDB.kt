package com.openmeet.data.message

import android.content.Context
import androidx.room.*
import com.openmeet.shared.data.message.Message
import com.openmeet.shared.data.storage.DAO
import kotlinx.coroutines.flow.Flow
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

    // Annotates class to be a Room Database with a table (entity) of the LocalMessage class
    @Database(entities = [LocalMessage::class], version = 1, exportSchema = false)
    abstract class AppDatabase : RoomDatabase() {

        abstract fun msgDao(): LocalMessageDao

        companion object {

            // Singleton prevents multiple instances of database opening at the
            // same time.

            @Volatile private var INSTANCE: AppDatabase? = null

            fun getDatabase(context: Context): AppDatabase {

                // if the INSTANCE is not null, then return it,
                // if it is, then create the database

                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_db"
                    ).build()
                    INSTANCE = instance

                    // return instance
                    instance
                }

            }

        }
    }

}