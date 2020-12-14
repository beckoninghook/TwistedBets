package com.example.twistedbets.models.match

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class MatchListItem(

    @SerializedName("platformId") var platformId: String,
    @SerializedName("gameId") var gameId: BigInteger,
    @SerializedName("champion") var champion: Int,
    @SerializedName("queue") var queue: Int,
    @SerializedName("season") var season: Int,
    @SerializedName("timestamp") var timestamp: BigInteger,
    @SerializedName("role") val role : String,
    @SerializedName("lane") val lane : String
) {
}