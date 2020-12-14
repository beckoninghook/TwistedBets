package com.example.twistedbets.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MatchApi {
    companion object {
        // The base url off the api.
        private const val baseUrl = "https://euw1.api.riotgames.com/"
        /**
         * @return [MatchApiService] The service class off the retrofit client.
         */
        fun createApi(): MatchApiService{
            // Create an OkHttpClient to be able to make a log of the network traffic
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            // Create the Retrofit instance
            val matchApi = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // Return the Retrofit TriviaApiService
            return matchApi.create(MatchApiService::class.java)
        }
    }
}