package com.example.twistedbets.models.match

import com.example.twistedbets.models.match.team.Team
import com.google.gson.annotations.SerializedName
import java.math.BigInteger

class Match(    @SerializedName("gameId") var gameId: BigInteger,
                @SerializedName("platformId") var platformId: String,
                @SerializedName("gameDuration") var gameDuration: Int,
                @SerializedName("gameMode") var gameMode: String,
                @SerializedName("gameType") var gameType: String,
                @SerializedName("teams") var teams: List<Team>
) {
}