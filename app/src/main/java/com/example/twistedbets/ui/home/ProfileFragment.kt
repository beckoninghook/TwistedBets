package com.example.twistedbets.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twistedbets.R
import com.example.twistedbets.adapter.BetlistScreenAdapter
import com.example.twistedbets.adapter.history.BetHistoryAdapter
import com.example.twistedbets.models.bet.BetList
import com.example.twistedbets.repository.BetListRepository
import com.example.twistedbets.repository.WalletRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_bets.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.tvCredits

class ProfileFragment : Fragment() {

    private lateinit var walletRepository: WalletRepository

    private lateinit var betListRepository: BetListRepository
    private  var betLists = ArrayList<BetList>()
    private   var betlistHistoryAdapter = BetHistoryAdapter(betLists , ::onBetClick)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        walletRepository = WalletRepository(requireContext())
        betListRepository = BetListRepository(requireContext())
        initViews()
        getBetlistFromDatabase()
    }
    private fun getBetlistFromDatabase(){
        val betLists = betListRepository.getAllBetLists() as ArrayList<BetList>
        this@ProfileFragment.betLists.clear()
        this@ProfileFragment.betLists.addAll(betLists.filter { it.isBetResolved })
        betlistHistoryAdapter.notifyDataSetChanged()
    }

    private fun onBetClick(betList: BetList) {

        Snackbar.make(rvBets, "Checking if ${betList.summoner.name} has played a new game", Snackbar.LENGTH_INDEFINITE).show()

    }


    private fun initViews(){
        tvCredits.text = walletRepository.getAllWallets()[0].credits.toString()

        val mLayoutManager =  LinearLayoutManager(activity )

        rvBetHistory.layoutManager = mLayoutManager
        rvBetHistory.layoutManager
        rvBetHistory.adapter = betlistHistoryAdapter
        rvBetHistory.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))


    }
}