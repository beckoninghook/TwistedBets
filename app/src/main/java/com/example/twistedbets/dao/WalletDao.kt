package com.example.twistedbets.dao

import androidx.room.*
import com.example.twistedbets.models.bet.BetList
import com.example.twistedbets.models.wallet.Wallet

@Dao
interface WalletDao {
    @Query("SELECT * FROM wallettable")
    fun getAllWallets(): List<Wallet>

    @Insert
    fun insertWallet(wallet: Wallet)

    @Delete
    fun deleteWallet(wallet: Wallet)

    @Update
    fun updateWallet(wallet: Wallet)


}