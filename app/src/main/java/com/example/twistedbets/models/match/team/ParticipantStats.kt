package com.example.twistedbets.models.match.team

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

class ParticipantStats (
    @SerializedName("kills") var kills: Int,
    @SerializedName("deaths") var deaths: Int,
    @SerializedName("teamId") var teamId: Int,
    @SerializedName("assists") var assists: Int,
    @SerializedName("pentaKills") var pentaKills: Int,
    @SerializedName("quadraKills") var quadraKills: Int,
    @SerializedName("doubleKills") var doubleKills: Int,
    @SerializedName("tripleKills") var tripleKills: Int,
    @SerializedName("win") var win: Boolean,
    @SerializedName("visionScore") var visionScore : Int,
    @SerializedName("totalMinionsKilled") var totalMinionsKilled : Int,
    @SerializedName("wardsPlaced") var wardsPlaced : Int

){

}