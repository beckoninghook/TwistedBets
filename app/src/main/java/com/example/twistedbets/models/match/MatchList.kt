package com.example.twistedbets.models.match

import com.google.gson.annotations.SerializedName

data class MatchList(@SerializedName("matches") var matches: List<MatchListItem>) {}