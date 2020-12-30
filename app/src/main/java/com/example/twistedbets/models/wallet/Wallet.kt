package com.example.twistedbets.models.wallet

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "WalletTable")
data class  Wallet(

    @ColumnInfo(name = "credits")
    var credits: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

) : Serializable {
}