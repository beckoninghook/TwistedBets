package com.example.twistedbets.ui.betBacklog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.twistedbets.R
import com.example.twistedbets.adapter.BetScreenAdapter
import com.example.twistedbets.adapter.ConfirmBetScreenAdapter
import com.example.twistedbets.models.bet.BetList
import com.example.twistedbets.models.bet.BetPresets
import com.example.twistedbets.repository.BetListRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_bets.*
import kotlinx.android.synthetic.main.fragment_confirm_bet.*

class BetBacklogFragment : Fragment() {


    private lateinit var betList: BetList
    private var selectedBets = betList.selectedBets
    private val betScreenAdapter = BetScreenAdapter(selectedBets , ::onBetClick )


    private lateinit var betListRepository: BetListRepository
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bets, container, false)
    }


    private fun onBetClick(betPresets: BetPresets) {
        Snackbar.make(rvBets, "This color is: ${betPresets.title}", Snackbar.LENGTH_LONG).show()
    }


    private fun setBetPresets (){
    //    private var betPresets = BetPresets.BETS.filter { it.amount != 0 }
        betList = betListRepository.getAllBetLists()[0]
        betScreenAdapter.notifyDataSetChanged();
    }

    private fun initViews(){
        rvSelectedBets.layoutManager = GridLayoutManager(activity , 1)
        rvSelectedBets.adapter = betScreenAdapter
        rvSelectedBets.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

    }


}