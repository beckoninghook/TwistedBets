package com.example.twistedbets.models.bet

data class BetPresets(
    val id : Int,
    val title : String,
    val description : String,
    var amount : Int = 0,
    var betStatus: BetStatus = BetStatus.UNRESOLVED


) {
    companion object {
        val BETS = mutableListOf(
            BetPresets(
                1,
                "KD",
                "Player will have a positive Kill death Ratio"
            ),
            BetPresets(
                2,
                "KD",
                "Player will have a Negative Kill death Ratio"
            ),
            BetPresets(
                3,
                "Objectives",
                "This player's team will have defeated the baron nashor."
            ),
            BetPresets(
                4,
                "Objectives",
                "This player's team will have defeated 5 dragons"
            ),
            BetPresets(
                5,
                "Objectives",
                "This player's team will have the first tower."
            ),
            BetPresets(
                6,
                "Game",
                "Player will win this game"
            ),
            BetPresets(
                7,
                "Game",
                "Player will lose this game"
            ),

            BetPresets(
                8,
                "Farm",
                "This player will get a total minion kill count above 150"
            ),
            BetPresets(
                9,
                "Vision",
                "This player will place more than 5 wards"
            ),
            BetPresets(
                10,
                "Combos",
                "This player will get a double kill"
            ),
            BetPresets(
                11,
                "Combos",
                "This player will get a triple kill"
            ),
            BetPresets(
                12,
                "Combos",
                "This player will get a quadra kill"
            ),
            BetPresets(
                13,
                "Combos",
                "This player will get a penta kill"
            )

        )
    }
}