package com.example.twistedbets.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.math.BigInteger

data class Summoner     (
    @SerializedName("id") var id: String ="",
    @SerializedName("accountId") var accountId: String ="",
    @SerializedName("puuid") var puuid: String ="",
    @SerializedName("name") var name: String ="",
    @SerializedName("profileIconId") var profileIconId: Int = 0,
    @SerializedName("revisionDate") var revisionDate: BigInteger = BigInteger.valueOf(0),
    @SerializedName("summonerLevel") val summonerLevel : Int = 0


) : Serializable {
}