package com.example.twistedbets.models

data class BetPresets(
    val title : String,
    val description : String
) {
    companion object {
        val BETS = listOf(
            BetPresets("KD" , "Player will have a positive Kill death Ratio"),
            BetPresets("KD" , "Player will have a Negative Kill death Ratio"),
            BetPresets("Objectives" , "This player's team will have defeated the baron nashor."),
            BetPresets("Objectives" , "This player's team will have defeated the most dragons"),
            BetPresets("Vision" , "This player will have placed the most wards of everyone in the game."),
            BetPresets("Game" , "Player will win this game"),
            BetPresets("Game" , "Player will lose this game")
        )
    }
}