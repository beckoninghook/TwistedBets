package com.example.twistedbets.repository

import android.content.Context
import com.example.twistedbets.dao.WalletDao
import com.example.twistedbets.database.WalletRoomDatabase
import com.example.twistedbets.models.bet.BetList
import com.example.twistedbets.models.wallet.Wallet

class WalletRepository(context: Context) {
    private var walletDao : WalletDao

    init {
        val walletRoomDatabase = WalletRoomDatabase.getDatabase(context)
        walletDao = walletRoomDatabase!!.walletDao()
    }

    fun getAllWallets(): List<Wallet> {
        return walletDao.getAllWallets()
    }

    fun insertWallet(wallet: Wallet) {
        walletDao.insertWallet(wallet)
    }

    fun deleteWallet(wallet: Wallet) {
        walletDao.deleteWallet(wallet)
    }


    fun updateWallet(wallet: Wallet) {
        walletDao.updateWallet(wallet)
    }
}