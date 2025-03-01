package com.example.twistedbets.models.match.team

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class Participants(
                        @SerializedName("participantId") var participantId: Int,
                        @SerializedName("teamId") var teamId: Int,
                        @SerializedName("championId") var championId: Int,
                        @SerializedName("stats") var stats: ParticipantStats
) {
}