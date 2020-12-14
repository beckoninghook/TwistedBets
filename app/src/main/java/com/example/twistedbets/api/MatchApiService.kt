package com.example.twistedbets.api

import com.example.twistedbets.models.match.Match
import com.example.twistedbets.models.match.MatchList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import java.math.BigInteger

interface MatchApiService {

    @GET("/lol/match/v4/matchlists/by-account/{encryptedAccountId}" )
    suspend fun getMatches(@Path("encryptedAccountId") encryptedAccountId: String?, @QueryMap filter: HashMap<String, String>): MatchList

    @GET("/lol/match/v4/matches/{matchId}" )
    suspend fun getMatch(@Path("matchId") matchId: BigInteger?, @QueryMap filter: HashMap<String, String>): Match

}