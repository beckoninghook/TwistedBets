package com.example.twistedbets.converters

import androidx.room.TypeConverter
import com.example.twistedbets.models.Summoner
import com.example.twistedbets.models.bet.BetPresets
import com.example.twistedbets.models.match.MatchListItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


public class BetListConverter {

    /**
     * Convert a Summoner to a Json
     */
    @TypeConverter
    fun fromSummoner(summoner : Summoner): String {
        return Gson().toJson(summoner)
    }

    /**
     * Convert a json to a Summoner
     */
    @TypeConverter
    fun stringToSummoner(jsonSummoner: String): Summoner{
        return Gson().fromJson(jsonSummoner , Summoner::class.java)
    }

    /**
     * Convert a MatchListItem to a Json
     */
    @TypeConverter
    fun fromMatchListItem(matchListItem : MatchListItem): String {
        return Gson().toJson(matchListItem)
    }

    /**
     * Convert a json to a MatchListItem
     */
    @TypeConverter
    fun stringToMatchListItem(jsonMatchListItem: String): MatchListItem{
        return Gson().fromJson(jsonMatchListItem , MatchListItem::class.java)
    }


    @TypeConverter
    fun fromBetPresetsMutableList(betPresetsList: MutableList<BetPresets>): String? {
        val type: Type = object : TypeToken<MutableList<BetPresets?>?>() {}.type
        return Gson().toJson(betPresetsList, type)
    }

    @TypeConverter
    fun toBetPresetsMutableList(mutableBetPresetsListString: String?): MutableList<BetPresets>? {
        val type: Type = object : TypeToken<MutableList<BetPresets?>?>() {}.type
        return Gson().fromJson<MutableList<BetPresets>>(mutableBetPresetsListString, type)
    }


}