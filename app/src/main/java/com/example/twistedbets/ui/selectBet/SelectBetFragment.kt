package com.example.twistedbets.ui.selectBet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.twistedbets.R
import com.example.twistedbets.adapter.SelectBetScreenAdapter
import com.example.twistedbets.models.BetPresets
import kotlinx.android.synthetic.main.fragment_select_bets.*
import kotlinx.android.synthetic.main.item_bet_select.*

class SelectBetFragment : Fragment() {

    private val betPresets = BetPresets.BETS
    private val betScreenAdapter = SelectBetScreenAdapter(betPresets , ::onPlusOrMinusClick )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_bets, container, false)

    }

    private fun onPlusOrMinusClick(betPresets: BetPresets , char : Char ) {
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
    }
    private fun initViews(){
        rvBetSelector.layoutManager = GridLayoutManager(activity , 1)
        rvBetSelector.adapter = betScreenAdapter
        rvBetSelector.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }


}