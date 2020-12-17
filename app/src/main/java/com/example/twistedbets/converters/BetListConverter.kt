package com.example.twistedbets.converters

import androidx.room.TypeConverter
import com.example.twistedbets.models.Summoner
import com.google.gson.Gson

class BetListConverter {

    /**
     * Convert a Summoner to a Json
     */
    @TypeConverter
    fun fromSummonerToJson(summoner : Summoner): String {
        return Gson().toJson(summoner)
    }

    /**
     * Convert a json to a Summoner
     */
    @TypeConverter
    fun toSummoner(jsonSummoner: String): Summoner{
        return Gson().fromJson(jsonSummoner , Summoner::class.java)
    }


}