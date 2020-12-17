package com.example.twistedbets.ui.selectBet

import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.twistedbets.R
import com.example.twistedbets.adapter.SelectBetScreenAdapter
import com.example.twistedbets.models.Summoner
import com.example.twistedbets.models.bet.BetList
import com.example.twistedbets.models.bet.BetPresets
import com.example.twistedbets.models.match.Match
import com.example.twistedbets.models.match.MatchListItem
import com.example.twistedbets.ui.PlaceBet.BUNDLE_SUMMONER_KEY
import com.example.twistedbets.ui.PlaceBet.REQ_SUMMONER_KEY
import com.example.twistedbets.vm.MatchViewModel
import kotlinx.android.synthetic.main.fragment_select_bets.*
import java.io.Serializable

const val REQ_BETS_KEY = "req_bets"
const val BUNDLE_BETS_KEY = "bundle_bets"


class SelectBetFragment : Fragment() {

    private val betPresets = BetPresets.BETS
    private val betScreenAdapter = SelectBetScreenAdapter(betPresets , ::onPlusOrMinusClick )
    private var summoner: Summoner = Summoner()

    private var lastMatch: MatchListItem? = null
    private val matchViewModel: MatchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_bets, container, false)

    }

    private fun onPlusOrMinusClick(betPresets: BetPresets, char : Char ) {
        println(betPresets)
        println(char)
        if (char == '+'){
            betPresets.amount += 1;
        }

        if (char == '-'){
            if (betPresets.amount > 0){
                betPresets.amount -= 1
            }
        }

        betScreenAdapter.notifyDataSetChanged();
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        view.findViewById<Button>(R.id.btnContinueSelBet).setOnClickListener {
            val betList = BetList(summoner , betPresets , lastMatch)
            println(betList)
            setFragmentResult(REQ_BETS_KEY, bundleOf(Pair(BUNDLE_BETS_KEY, betList )))
            findNavController().navigate(R.id.action_select_bets_to_confirm_bets)
        }

        getSummoner()

    }
    private fun initViews(){
        rvBetSelector.layoutManager = GridLayoutManager(activity , 1)
        rvBetSelector.adapter = betScreenAdapter
        rvBetSelector.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

    }


    private fun getSummoner() {
        setFragmentResultListener(REQ_SUMMONER_KEY) { key, bundle ->
            bundle.getSerializable(BUNDLE_SUMMONER_KEY)?.let {
                println(it)
                summoner = it as Summoner
                tvSelectBets.text = getString(R.string.selecting_bets_title, summoner.name)
                ObserveMatchList()
            } ?: Log.e("SelectBetFragment", "Request triggered, but empty reminder text!")
        }
        println(summoner)
    }

    fun ObserveMatchList(){
        matchViewModel.getMatchListFromEncryptedAccountId(summoner.accountId)
        matchViewModel.matches.observe(viewLifecycleOwner, Observer {
            Log.i("last match " , it[0].champion.toString())
            lastMatch = it[0]
        })
    }

}