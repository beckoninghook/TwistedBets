package com.example.twistedbets.dao

import androidx.room.*
import com.example.twistedbets.models.bet.BetList

@Dao
interface BetListDao {

    @Query("SELECT * FROM betListTable")
    fun getAllBetLists(): List<BetList>

    @Insert
    fun insertBetList(betList: BetList)

    @Delete
    fun deleteBetList(betList: BetList)

    @Update
    fun updateBetList(betList: BetList)

}
