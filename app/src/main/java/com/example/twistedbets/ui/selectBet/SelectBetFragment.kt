package com.example.twistedbets.ui.selectBet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.twistedbets.R
import com.example.twistedbets.adapter.SelectBetScreenAdapter
import com.example.twistedbets.models.BetPresets
import com.example.twistedbets.ui.PlaceBet.PlaceBetViewModel
import kotlinx.android.synthetic.main.fragment_place_bets.*
import kotlinx.android.synthetic.main.fragment_select_bets.*

class SelectBetFragment : Fragment() {

    private val betPresets = BetPresets.BETS
    private val betScreenAdapter = SelectBetScreenAdapter(betPresets)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_bets, container, false)

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