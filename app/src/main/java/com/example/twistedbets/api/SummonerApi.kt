package com.example.twistedbets.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SummonerApi () {
    //
    companion object {
        // The base url off the api.
        private const val baseUrl = "https://euw1.api.riotgames.com/"
        /**
         * @return [MovieApiService] The service class off the retrofit client.
         */
        fun createApi(): SummonerApiService{
            // Create an OkHttpClient to be able to make a log of the network traffic
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            // Create the Retrofit instance
            val summonerApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Return the Retrofit TriviaApiService
            return summonerApi.create(SummonerApiService::class.java)
        }
    }

}