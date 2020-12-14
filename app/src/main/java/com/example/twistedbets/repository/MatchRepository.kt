package com.example.twistedbets.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.twistedbets.api.MatchApi
import com.example.twistedbets.api.MatchApiService
import com.example.twistedbets.api.SummonerApi
import com.example.twistedbets.api.SummonerApiService
import com.example.twistedbets.models.Summoner
import com.example.twistedbets.models.match.Match
import com.example.twistedbets.models.match.MatchListItem
import kotlinx.coroutines.withTimeout
import java.math.BigInteger

class MatchRepository {
    private val matchApiService : MatchApiService = MatchApi.createApi()

    //MATCHES LIST
    private val _matches : MutableLiveData<List<MatchListItem>> = MutableLiveData()

    val matches: LiveData<List<MatchListItem>>
        get() = _matches

    //SINGLE MATCH
    private val _match : MutableLiveData<Match> = MutableLiveData()

    val match: LiveData<Match>
        get() = _match


    class MatchRefreshError(message: String, cause: Throwable) : Throwable(message, cause)

    suspend fun getMatchListFromEncryptedAccountId(encryptedAccountId  : String){
        try {
            //timeout the request after 5 seconds
            val result = withTimeout(10_000) {
                val filter = HashMap<String , String>()
                filter["api_key"] = "RGAPI-4066c02e-a549-4f11-bd94-d23975e82d94"

                matchApiService.getMatches(encryptedAccountId , filter)
            }

            _matches.value = result.matches;
        } catch (error: Throwable) {
            throw MatchRefreshError("Unable to refresh Matches", error)
        }
    }

    suspend fun getMatchFromMatchId(matchId  : BigInteger){
        try {
            //timeout the request after 5 seconds
            val result = withTimeout(10_000) {
                val filter = HashMap<String , String>()
                filter["api_key"] = "RGAPI-4066c02e-a549-4f11-bd94-d23975e82d94"

                matchApiService.getMatch(matchId , filter)
            }

            _match.value = result;
        } catch (error: Throwable) {
            throw MatchRefreshError("Unable to refresh Matches", error)
        }
    }

}