package com.example.twistedbets.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.twistedbets.dao.WalletDao
import com.example.twistedbets.models.wallet.Wallet

@Database(entities = [Wallet::class], version = 1 , exportSchema = false)
abstract class WalletRoomDatabase : RoomDatabase() {
    abstract fun walletDao() : WalletDao


    companion object{
        private const val DATABASE_NAME = "WALLET_DB"

        @Volatile
        private var walletRoomDatabaseInstance: WalletRoomDatabase? = null

        fun getDatabase(context: Context): WalletRoomDatabase? {
            if (walletRoomDatabaseInstance == null) {
                synchronized(BetListRoomDatabase::class.java) {
                    if (walletRoomDatabaseInstance == null) {
                        walletRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            WalletRoomDatabase::class.java, DATABASE_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return walletRoomDatabaseInstance
        }
    }
}