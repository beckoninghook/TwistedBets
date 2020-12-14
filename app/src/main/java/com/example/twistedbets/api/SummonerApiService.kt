package com.example.twistedbets.api

import com.example.twistedbets.models.Summoner
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface SummonerApiService {

    @GET("/lol/summoner/v4/summoners/by-name/{summonerName}" )
    suspend fun getSummoner( @Path("summonerName") summonerName: String? , @QueryMap filter: HashMap<String, String>): Summoner

}