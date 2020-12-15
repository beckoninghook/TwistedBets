package com.example.twistedbets.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.twistedbets.repository.MatchRepository
import com.example.twistedbets.repository.SummonerRepository
import kotlinx.coroutines.launch
import java.math.BigInteger

class MatchViewModel   (application: Application) : AndroidViewModel(application){

    private val matchRepository = MatchRepository()
    val match = matchRepository.match
    val matches = matchRepository.matches
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
    fun getMatchListFromEncryptedAccountId(encryptedAccountId  : String) {

        viewModelScope.launch {
            try {
                //the triviaRepository sets it's own livedata property
                //our own trivia LiveData property points to te one in that repository
                //timeout the request after 5 seconds
                matchRepository.getMatchListFromEncryptedAccountId(encryptedAccountId)
            } catch (error: MatchRepository.MatchRefreshError) {
                _errorText.value = error.message
                Log.e("Match error", error.cause.toString())
            }
        }
    }


    fun getMatchFromMatchId(matchId  : Long) {

        viewModelScope.launch {
            try {
                //the triviaRepository sets it's own livedata property
                //our own trivia LiveData property points to te one in that repository
                //timeout the request after 5 seconds
                matchRepository.getMatchFromMatchId(matchId)
            } catch (error:  MatchRepository.MatchRefreshError) {
                _errorText.value = error.message
                Log.e("Match error", error.cause.toString())
            }
        }
    }
}