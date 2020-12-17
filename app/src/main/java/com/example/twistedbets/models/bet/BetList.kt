package com.example.twistedbets.models.bet

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.twistedbets.converters.BetListConverter
import com.example.twistedbets.models.Summoner
import com.example.twistedbets.models.match.MatchListItem
import java.io.Serializable
@Entity(tableName = "betListTable")
data class BetList (

    @ColumnInfo(name = "summoner")
    @TypeConverters(BetListConverter::class)
    var summoner : Summoner,

    @ColumnInfo(name = "selectedBets")
    var selectedBets : MutableList<BetPresets>,

    @ColumnInfo(name = "lastMatch")
    var lastMatch : MatchListItem?,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
) : Serializable{


}