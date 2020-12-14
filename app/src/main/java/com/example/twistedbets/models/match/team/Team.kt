package com.example.twistedbets.models.match.team

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class Team (
    @SerializedName("teamId") var teamId: Int,
    @SerializedName("win") var win: String,
    @SerializedName("firstBlood") var firstBlood: Boolean,
    @SerializedName("firstTower") var firstTower: Boolean,
    @SerializedName("firstInhibitor") var firstInhibitor: Boolean,
    @SerializedName("firstBaron") var firstBaron: Boolean,
    @SerializedName("firstDragon") var firstDragon: Boolean,
    @SerializedName("firstRiftHerald") var firstRiftHerald: Boolean,
    @SerializedName("towerKills") var towerKills: Int,
    @SerializedName("inhibitorKills") var inhibitorKills: Int,
    @SerializedName("baronKills") var baronKills: Int,
    @SerializedName("dragonKills") var dragonKills: Int,
    @SerializedName("stats") var stats: List<Participants>
){
}
