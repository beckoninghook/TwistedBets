package com.example.twistedbets.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.twistedbets.repository.SummonerRepository
import kotlinx.coroutines.launch

class SummonerViewModel (application: Application) : AndroidViewModel(application) {

    private val summonerRepository = SummonerRepository()
    val summoner = summonerRepository.summoner
    private val _errorText: MutableLiveData<String> = MutableLiveData()

    /**
     * Expose non MutableLiveData via getter
     * errorText can be observed from view for error showing
     * Encapsulation :)
     */
    val errorText: LiveData<String>
        get() = _errorText
    /**
     * The viewModelScope is bound to Dispatchers.Main and will automatically be cancelled when the ViewModel is cleared.
     * Extension method of lifecycle-viewmodel-ktx library
     */
    fun getSummonerByName(name : String) {

        viewModelScope.launch {
            try {
                //the triviaRepository sets it's own livedata property
                //our own trivia LiveData property points to te one in that repository
                //timeout the request after 5 seconds
                summonerRepository.getSummonerFromName(name)
                Log.i("Summoner Available" , name )
            } catch (error: SummonerRepository.SummonerRefreshError) {
                _errorText.value = error.message
                Log.e("Summoner error", error.cause.toString())
            }
        }
    }

}