package com.example.twistedbets.ui.betBacklog


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twistedbets.R
import com.example.twistedbets.adapter.BetlistScreenAdapter
import com.example.twistedbets.models.bet.BetList
import com.example.twistedbets.models.bet.BetPresets
import com.example.twistedbets.models.bet.BetStatus
import com.example.twistedbets.models.match.Match
import com.example.twistedbets.models.match.MatchListItem
import com.example.twistedbets.models.match.team.Participants
import com.example.twistedbets.models.match.team.Team
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
        betlistScreenAdapter = BetlistScreenAdapter(betLists , ::onBetClick )
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
        matchViewModel.match.observe(viewLifecycleOwner, Observer { itMatch ->
            val selectedSummoner = itMatch.participants.find { participant -> participant.championId == championId}
            if (selectedSummoner != null) {
                checkBetWinOrLoss(betList.selectedBets.filter { it.amount != 0 } , itMatch , selectedSummoner)
                //Snackbar.make(rvBets ,  "player KD is " + String.format("%.2f", playerKD) , Snackbar.LENGTH_SHORT ).show()
            }
        })
    }

    private fun checkBetWinOrLoss(betPresets: List<BetPresets> , match : Match , selectedSummoner : Participants){
        var creditsWon = 0;
        var team : Team? = match.teams.find { id -> id.teamId == selectedSummoner.teamId }
        for (bets in betPresets ){

            when(bets.id){
                1 -> if(selectedSummoner.stats.kills.toDouble() / selectedSummoner.stats.deaths.toDouble() >= 1.0){
                        println("dis BITCH BE WINNING DAWG")
                        bets.betStatus = BetStatus.WON
                        creditsWon += ( bets.amount * 2 )
                    }else {
                        println("dis lil hombre puta BE losing lil dawg")
                        bets.betStatus = BetStatus.LOST
                    }
                2 -> if(selectedSummoner.stats.kills.toDouble() / selectedSummoner.stats.deaths.toDouble() < 1.0){
                        bets.betStatus = BetStatus.WON
                    creditsWon += ( bets.amount  * 2 )
                }else {
                        bets.betStatus = BetStatus.LOST
                    }
                3 -> if (team != null) {
                        if (team.baronKills > 0){
                            bets.betStatus = BetStatus.WON
                            creditsWon += ( bets.amount  * 2 )
                        }else {
                            bets.betStatus = BetStatus.LOST
                        }
                }
                4 -> if (team != null) {
                    if (team.dragonKills == 5){
                        bets.betStatus = BetStatus.WON
                        creditsWon += ( bets.amount  * 2 )
                    }else {
                        bets.betStatus = BetStatus.LOST
                    }
                }
                5 -> if (team != null){
                        if (team.firstTower){
                            bets.betStatus = BetStatus.WON
                            creditsWon += ( bets.amount  * 2 )
                        }else {
                            bets.betStatus = BetStatus.LOST
                        }
                    }
                6 -> if(selectedSummoner.stats.win){
                        bets.betStatus = BetStatus.WON
                        creditsWon += ( bets.amount  * 2 )
                    }else {
                        bets.betStatus = BetStatus.LOST
                    }
                7 -> if(!selectedSummoner.stats.win){
                        bets.betStatus = BetStatus.WON
                        creditsWon += ( bets.amount  * 2 )
                    }else {
                        bets.betStatus = BetStatus.LOST
                    }
                8 -> println("implement : This player will have placed the most wards of everyone in the game.")
                9 -> println("implement : This player will have placed the most wards of everyone in the game.")
                10 -> println("implement : This player will have placed the most wards of everyone in the game.")
                11 -> println("implement : This player will have placed the most wards of everyone in the game.")
                12 -> println("implement : This player will have placed the most wards of everyone in the game.")
            }
        }

        println("This player has won $creditsWon")
    }
}