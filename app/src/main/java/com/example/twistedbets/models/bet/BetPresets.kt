package com.example.twistedbets.models.bet

data class BetPresets(
    val id : Int,
    val title : String,
    val description : String,
    var amount : Int = 0


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
                "This player's team will have defeated the most dragons"
            ),
            BetPresets(
                5,
                "Vision",
                "This player will have placed the most wards of everyone in the game."
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
                "empty",
                "lorem ipsumlorem ipsumlorem ipsumlorem ipsum lorem ipsum"
            ),
            BetPresets(8,
                "empty",
                "lorem ipsumlorem ipsumlorem ipsumlorem ipsum lorem ipsum"
            ),
            BetPresets(8,
                "empty",
                "lorem ipsumlorem ipsumlorem ipsumlorem ipsum lorem ipsum"
            ),
            BetPresets(8,
                "empty",
                "lorem ipsumlorem ipsumlorem ipsumlorem ipsum lorem ipsum"
            ),
            BetPresets(8,
                "empty",
                "lorem ipsumlorem ipsumlorem ipsumlorem ipsum lorem ipsum"
            ),
            BetPresets(8,
                "empty",
                "lorem ipsumlorem ipsumlorem ipsumlorem ipsum lorem ipsum"
            )

        )
    }
}