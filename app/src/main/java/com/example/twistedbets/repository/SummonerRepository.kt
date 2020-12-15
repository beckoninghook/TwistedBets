package com.example.twistedbets.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.twistedbets.BuildConfig
import com.example.twistedbets.api.SummonerApi
import com.example.twistedbets.api.SummonerApiService
import com.example.twistedbets.models.Summoner
import kotlinx.coroutines.withTimeout

class SummonerRepository {


    private val summonerApiService: SummonerApiService = SummonerApi.createApi()

    private val _summoner: MutableLiveData<Summoner> = MutableLiveData()

    /**
     * Expose non MutableLiveData via getter
     * Encapsulation :)
     */
    val summoner: LiveData<Summoner>
        get() = _summoner


    class SummonerRefreshError(message: String, cause: Throwable) : Throwable(message, cause)

    suspend fun getSummonerFromName(summonerName  : String){
        try {
            //timeout the request after 5 seconds
            val result = withTimeout(10_000) {
                val filter = HashMap<String , String>()
                filter["api_key"] = BuildConfig.ApiKey

                summonerApiService.getSummoner(summonerName , filter)
            }

            _summoner.value = result;
        } catch (error: Throwable) {
            throw SummonerRefreshError("Unable to refresh Movies", error)
        }
    }

}