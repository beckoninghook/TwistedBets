package com.example.twistedbets.models.match

import com.example.twistedbets.models.match.team.Participants
import com.example.twistedbets.models.match.team.Team
import com.google.gson.annotations.SerializedName

class Match(    @SerializedName("gameId") var gameId: Long,
                @SerializedName("platformId") var platformId: String,
                @SerializedName("gameDuration") var gameDuration: Int,
                @SerializedName("gameMode") var gameMode: String,
                @SerializedName("gameType") var gameType: String,
                @SerializedName("teams") var teams: List<Team>,
                @SerializedName("participants") var participants: List<Participants>

) {
}