package com.example.twistedbets.ui.betBacklog


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twistedbets.R
import com.example.twistedbets.adapter.BetlistScreenAdapter
import com.example.twistedbets.models.bet.BetList
import com.example.twistedbets.models.match.MatchListItem
import com.example.twistedbets.repository.BetListRepository
import com.example.twistedbets.vm.MatchViewModel
import com.example.twistedbets.vm.SummonerViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_bets.*
import kotlinx.android.synthetic.main.fragment_place_bets.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.lang.IndexOutOfBoundsException


class BetBacklogFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var betListRepository: BetListRepository
    private lateinit var betLists : List<BetList>
    private val mainScope =  CoroutineScope(Dispatchers.Main)
   private lateinit  var betlistScreenAdapter : BetlistScreenAdapter
    private val summonerViewModel: SummonerViewModel by viewModels()
    private val matchViewModel: MatchViewModel by viewModels()
    private lateinit  var matchListItems : List<MatchListItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_bets, container, false)

        return root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        betListRepository = BetListRepository(requireContext())
        betLists = betListRepository.getAllBetLists()
        betlistScreenAdapter = BetlistScreenAdapter(betLists )
        initViews()

    }


    private fun onBetClick(betList: BetList) {
        getMatches(betList.summoner.accountId)
        ObserveMatchList(betList)

    }



    private fun initViews(){
        val mLayoutManager =  LinearLayoutManager(activity )
        mLayoutManager.reverseLayout = true
        mLayoutManager.setStackFromEnd(true);
        rvBets.layoutManager = mLayoutManager
        rvBets.layoutManager
        rvBets.adapter = betlistScreenAdapter
        rvBets.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    fun getMatches(id : String){
        matchViewModel.getMatchListFromEncryptedAccountId(id)
    }

    private fun ObserveMatchList(betList: BetList) {
        matchViewModel.matches.observe(viewLifecycleOwner, Observer {
            checkForNewGame(it[0].gameId , betList , it)
        })
    }

    private fun checkForNewGame(gameId: Long, betList: BetList , matchListItemList: List<MatchListItem>) {
        Thread.sleep(1_000)
        println("check for new game: " + gameId.toString() + " vs " + betList.lastMatch?.gameId)
        val lastMatchId =  betList.lastMatch?.gameId
        if(gameId != betList.lastMatch?.gameId){
            Snackbar.make(rvBets, "${betList.summoner.name} has played a new game", Snackbar.LENGTH_SHORT).show()
            val matchedGame = matchListItemList.find { idOfGame -> idOfGame.gameId == lastMatchId }
            try {
                matchViewModel.getMatchFromMatchId(matchListItemList[(matchListItemList.indexOf(matchedGame)- 1 )].gameId)
                ObserveNewlyPlayedMatch(matchListItemList[(matchListItemList.indexOf(matchedGame)- 1 )].champion  , betList)
            }catch (e : IndexOutOfBoundsException){
                Log.e("Something went wrong" , e.toString())
            }
        }else {
            Snackbar.make(rvBets, "${betList.summoner.name} needs to play another game", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun ObserveNewlyPlayedMatch(championId : Int , betList: BetList){
        var playerKD = 0.0
        matchViewModel.match.observe(viewLifecycleOwner, Observer {
            val selectedSummoner = it.participants.find { participant -> participant.championId == championId}
            if (selectedSummoner != null) {
                playerKD = selectedSummoner.stats.kills.toDouble() / selectedSummoner.stats.deaths.toDouble()
                println(selectedSummoner.stats.kills.toDouble())
                println( selectedSummoner.stats.deaths.toDouble())
                Snackbar.make(rvBets ,  "player KD is " + String.format("%.2f", playerKD) , Snackbar.LENGTH_SHORT ).show()
            }
        })
    }
}