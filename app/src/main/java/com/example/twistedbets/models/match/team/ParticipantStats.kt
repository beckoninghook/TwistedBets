package com.example.twistedbets.models.match.team

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

class ParticipantStats (
    @SerializedName("kills") var kills: Int,
    @SerializedName("teamId") var teamId: Int,
    @SerializedName("assists") var assists: Int,
    @SerializedName("pentaKills") var pentaKills: Int,
    @SerializedName("quadraKills") var quadraKills: Int
){

}