package com.example.twistedbets.models.bet

import com.example.twistedbets.models.Summoner
import java.io.Serializable

data class BetList (
    var summoner : Summoner,
    var selectedBets : MutableList<BetPresets>
) : Serializable{


}