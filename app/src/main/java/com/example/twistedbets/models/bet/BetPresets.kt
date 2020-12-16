package com.example.twistedbets.models.bet

data class BetPresets(
    val title : String,
    val description : String,
    var amount : Int = 0


) {
    companion object {
        val BETS = mutableListOf(
            BetPresets(
                "KD",
                "Player will have a positive Kill death Ratio"
            ),
            BetPresets(
                "KD",
                "Player will have a Negative Kill death Ratio"
            ),
            BetPresets(
                "Objectives",
                "This player's team will have defeated the baron nashor."
            ),
            BetPresets(
                "Objectives",
                "This player's team will have defeated the most dragons"
            ),
            BetPresets(
                "Vision",
                "This player will have placed the most wards of everyone in the game."
            ),
            BetPresets(
                "Game",
                "Player will win this game"
            ),
            BetPresets(
                "Game",
                "Player will lose this game"
            ),

            BetPresets(
                "empty",
                "lorem ipsumlorem ipsumlorem ipsumlorem ipsum lorem ipsum"
            ),
            BetPresets(
                "empty",
                "lorem ipsumlorem ipsumlorem ipsumlorem ipsum lorem ipsum"
            ),
            BetPresets(
                "empty",
                "lorem ipsumlorem ipsumlorem ipsumlorem ipsum lorem ipsum"
            ),
            BetPresets(
                "empty",
                "lorem ipsumlorem ipsumlorem ipsumlorem ipsum lorem ipsum"
            ),
            BetPresets(
                "empty",
                "lorem ipsumlorem ipsumlorem ipsumlorem ipsum lorem ipsum"
            ),
            BetPresets(
                "empty",
                "lorem ipsumlorem ipsumlorem ipsumlorem ipsum lorem ipsum"
            )

        )
    }
}