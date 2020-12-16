package com.example.twistedbets.ui.confirmBet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.twistedbets.R
import com.example.twistedbets.adapter.ConfirmBetScreenAdapter

import com.example.twistedbets.models.bet.BetList
import com.example.twistedbets.models.bet.BetPresets
import com.example.twistedbets.ui.selectBet.BUNDLE_BETS_KEY
import com.example.twistedbets.ui.selectBet.REQ_BETS_KEY
import kotlinx.android.synthetic.main.fragment_confirm_bet.*


class ConfirmBetFragment : Fragment() {
    //Als de user in dit fragment op confirm klikt gaat het saldo pas omlaag.
    //Kan het ook live doen not sure yet

    private var betPresets = BetPresets.BETS
    private val confirmBetScreenAdapter = ConfirmBetScreenAdapter(betPresets , ::onPlusOrMinusClick )
    private var betList: BetList? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_bet, container, false)

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

        confirmBetScreenAdapter.notifyDataSetChanged();
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAddBetlistResult()
        deleteNotUsedBets()
        initViews()
    }

    private fun deleteNotUsedBets(){
        println(betList)
    }


    private fun observeAddBetlistResult() {
        setFragmentResultListener(REQ_BETS_KEY) { key, bundle ->
            bundle.getSerializable(BUNDLE_BETS_KEY)?.let {
                betList = it as BetList
                betPresets = betList!!.selectedBets

                confirmBetScreenAdapter.notifyDataSetChanged();
            } ?: Log.e("ConfirmBetFragment", "Request triggered, but empty reminder text!")

        }
    }


    private fun initViews(){
        rvSelectedBets.layoutManager = GridLayoutManager(activity , 1)
        rvSelectedBets.adapter = confirmBetScreenAdapter
        rvSelectedBets.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

    }




}

