package com.example.twistedbets.repository

import android.content.Context
import com.example.twistedbets.dao.BetListDao
import com.example.twistedbets.database.BetListRoomDatabase
import com.example.twistedbets.models.bet.BetList

 class BetListRepository(context: Context) {

    private var betListDao: BetListDao

    init {
        val betListRoomDatabase = BetListRoomDatabase.getDatabase(context)
        betListDao = betListRoomDatabase!!.betListDao()
    }

    fun getAllBetLists(): List<BetList> {
        return betListDao.getAllBetLists()
    }

    fun insertBetList(betList: BetList) {
        betListDao.insertBetList(betList)
    }

    fun deleteBetList(betList: BetList) {
        betListDao.deleteBetList(betList)
    }


    fun updateBetList(betList: BetList) {
        betListDao.updateBetList(betList)
    }
}
