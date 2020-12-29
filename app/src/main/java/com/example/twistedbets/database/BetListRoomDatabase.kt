package com.example.twistedbets.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.twistedbets.converters.BetListConverter
import com.example.twistedbets.dao.BetListDao
import com.example.twistedbets.models.bet.BetList


@Database(entities = [BetList::class], version = 1, exportSchema = false)
@TypeConverters(BetListConverter::class)
abstract class BetListRoomDatabase : RoomDatabase() {

    abstract fun betListDao(): BetListDao

    companion object {
        private const val DATABASE_NAME = "BETLIST_DB"

        @Volatile
        private var betListRoomDatabaseInstance: BetListRoomDatabase? = null

        fun getDatabase(context: Context): BetListRoomDatabase? {
            if (betListRoomDatabaseInstance == null) {
                synchronized(BetListRoomDatabase::class.java) {
                    if (betListRoomDatabaseInstance == null) {
                        betListRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            BetListRoomDatabase::class.java, DATABASE_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return betListRoomDatabaseInstance
        }
    }

}
