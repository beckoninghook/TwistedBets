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
import com.example.twistedbets.models.wallet.Wallet
import com.example.twistedbets.repository.BetListRepository
import com.example.twistedbets.repository.WalletRepository
import com.example.twistedbets.vm.MatchViewModel
import com.example.twistedbets.vm.SummonerViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_bets.*
import kotlinx.android.synthetic.main.fragment_place_bets.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.lang.IndexOutOfBoundsException


class BetBacklogFragment : Fragment() {

    private lateinit var betListRepository: BetListRepository
    private lateinit var walletRepository: WalletRepository
    private  var betLists = ArrayList<BetList>()
    private lateinit var wallet : Wallet
   private   var betlistScreenAdapter = BetlistScreenAdapter(betLists , ::onBetClick)
    private val matchViewModel: MatchViewModel by viewModels()


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

        walletRepository = WalletRepository(requireContext())
        betListRepository = BetListRepository(requireContext())

        initViews()
        getBetlistFromDatabase()
    }


    private fun onBetClick(betList: BetList) {

        Snackbar.make(rvBets, "Checking if ${betList.summoner.name} has played a new game", Snackbar.LENGTH_INDEFINITE).show()
        getMatches(betList.summoner.accountId)
        ObserveMatchList(betList)
    }

    private fun getBetlistFromDatabase(){
        val betLists = betListRepository.getAllBetLists() as ArrayList<BetList>
        this@BetBacklogFragment.betLists.clear()
        this@BetBacklogFragment.betLists.addAll(betLists.filter { !it.isBetResolved })
        betlistScreenAdapter.notifyDataSetChanged()

    }


    private fun initViews(){
        val mLayoutManager =  LinearLayoutManager(activity )
        mLayoutManager.reverseLayout = true
        mLayoutManager.setStackFromEnd(true);

        rvBets.layoutManager = mLayoutManager
        rvBets.layoutManager
        rvBets.adapter = betlistScreenAdapter
        rvBets.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        tvCredits.text = walletRepository.getAllWallets()[0].credits.toString()
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
        val lastMatchId =  betList.lastMatch?.gameId
        if(gameId != betList.lastMatch?.gameId){
            Snackbar.make(rvBets, "Checking game stats of: ${betList.summoner.name}", Snackbar.LENGTH_SHORT).show()
            val matchedGame = matchListItemList.find { idOfGame -> idOfGame.gameId == lastMatchId }
            try {
                matchViewModel.getMatchFromMatchId(matchListItemList[(matchListItemList.indexOf(matchedGame)- 1 )].gameId)
                ObserveNewlyPlayedMatch(matchListItemList[(matchListItemList.indexOf(matchedGame)- 1 )].champion  , betList)
            }catch (e : IndexOutOfBoundsException){
                Log.e("Something went wrong" , e.toString())
            }
        }else {
            Snackbar.make(rvBets, "${betList.summoner.name} did not play another game", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun ObserveNewlyPlayedMatch(championId : Int , betList: BetList){
        matchViewModel.match.observe(viewLifecycleOwner, Observer { itMatch ->
            val selectedSummoner = itMatch.participants.find { participant -> participant.championId == championId}
            if (selectedSummoner != null) {
                checkBetWinOrLoss(betList.selectedBets.filter { it.amount != 0 } , itMatch , selectedSummoner , betList)
            }
        })
    }

    private fun checkBetWinOrLoss(betPresets: List<BetPresets> , match : Match , selectedSummoner : Participants , betList: BetList){
        var creditsWon = 0;
        val team : Team? = match.teams.find { id -> id.teamId == selectedSummoner.teamId }
        for (bets in betPresets ){

            when(bets.id){
                1 -> if(selectedSummoner.stats.kills.toDouble() / selectedSummoner.stats.deaths.toDouble() >= 1.0){
                        bets.betStatus = BetStatus.WON
                        creditsWon += ( bets.amount * bets.multiplier )
                    }else {
                        bets.betStatus = BetStatus.LOST
                    }
                2 -> if(selectedSummoner.stats.kills.toDouble() / selectedSummoner.stats.deaths.toDouble() < 1.0){
                        bets.betStatus = BetStatus.WON
                    creditsWon += ( bets.amount  * bets.multiplier )
                }else {
                        bets.betStatus = BetStatus.LOST
                    }
                3 -> if (team != null) {
                        if (team.baronKills > 0){
                            bets.betStatus = BetStatus.WON
                            creditsWon += ( bets.amount  * bets.multiplier )
                        }else {
                            bets.betStatus = BetStatus.LOST
                        }
                }
                4 -> if (team != null) {
                    if (team.dragonKills == 5){
                        bets.betStatus = BetStatus.WON
                        creditsWon += ( bets.amount  * bets.multiplier )
                    }else {
                        bets.betStatus = BetStatus.LOST
                    }
                }
                5 -> if (team != null){
                        if (team.firstTower){
                            bets.betStatus = BetStatus.WON
                            creditsWon += ( bets.amount  * bets.multiplier )
                        }else {
                            bets.betStatus = BetStatus.LOST
                        }
                    }
                6 -> if(selectedSummoner.stats.win){
                        bets.betStatus = BetStatus.WON
                        creditsWon += ( bets.amount  * bets.multiplier )
                    }else {
                        bets.betStatus = BetStatus.LOST
                    }
                7 -> if(!selectedSummoner.stats.win){
                        bets.betStatus = BetStatus.WON
                        creditsWon += ( bets.amount  * bets.multiplier )
                    }else {
                        bets.betStatus = BetStatus.LOST
                    }
                8 -> if(selectedSummoner.stats.totalMinionsKilled >= 150){
                        bets.betStatus = BetStatus.WON
                        creditsWon += ( bets.amount  * bets.multiplier )
                    }else {
                        bets.betStatus = BetStatus.LOST
                    }
                9 -> if(selectedSummoner.stats.wardsPlaced >= 5){
                        bets.betStatus = BetStatus.WON
                        creditsWon += ( bets.amount  * bets.multiplier )
                    }else {
                        bets.betStatus = BetStatus.LOST
                    }
                10 ->  if(selectedSummoner.stats.doubleKills >= 1){
                            bets.betStatus = BetStatus.WON
                            creditsWon += ( bets.amount  * bets.multiplier )
                        }else {
                            bets.betStatus = BetStatus.LOST
                        }
                11 ->  if(selectedSummoner.stats.tripleKills >= 1){
                            bets.betStatus = BetStatus.WON
                            creditsWon += ( bets.amount  * bets.multiplier )
                        }else {
                            bets.betStatus = BetStatus.LOST
                        }
                12 ->  if(selectedSummoner.stats.quadraKills >= 1){
                    bets.betStatus = BetStatus.WON
                    creditsWon += ( bets.amount  * bets.multiplier )
                }else {
                    bets.betStatus = BetStatus.LOST
                }
                13 ->  if(selectedSummoner.stats.pentaKills >= 1){
                    bets.betStatus = BetStatus.WON
                    creditsWon += ( bets.amount  * bets.multiplier )
                }else {
                    bets.betStatus = BetStatus.LOST
                }
            }
        }

        if ( !betList.isBetResolved){
            betList.wonCredits = creditsWon

            if (creditsWon == 0 ){
                Snackbar.make(rvBets, "You did not win any credits.", Snackbar.LENGTH_LONG).show()
            }else {
                Snackbar.make(rvBets, "Added $creditsWon credits to your wallet.", Snackbar.LENGTH_LONG).show()
            }

            walletRepository.updateWallet(Wallet(walletRepository.getAllWallets()[0].credits + creditsWon, 1))

            tvCredits.text = walletRepository.getAllWallets()[0].credits.toString()
             betList.isBetResolved = true

           // betListRepository.deleteBetList(betList)
            getBetlistFromDatabase()
        }else {
            println("bet is already resolved")
        }
    }
}