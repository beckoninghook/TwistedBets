package com.example.twistedbets.ui.betBacklog

import com.example.twistedbets.ui.home.HomeViewModel


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.twistedbets.R
import com.example.twistedbets.adapter.BetlistScreenAdapter
import com.example.twistedbets.adapter.ConfirmBetScreenAdapter
import com.example.twistedbets.models.bet.BetList
import com.example.twistedbets.models.bet.BetPresets
import com.example.twistedbets.repository.BetListRepository
import kotlinx.android.synthetic.main.fragment_bets.*
import kotlinx.android.synthetic.main.fragment_select_bets.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class BetBacklogFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var betListRepository: BetListRepository
    private lateinit var betLists : List<BetList>
    private val mainScope =  CoroutineScope(Dispatchers.Main)
   private lateinit  var betlistScreenAdapter : BetlistScreenAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_bets, container, false)

        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }


    private fun OnBetClick(betPresets: BetPresets ) {
        println(betPresets)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        betListRepository = BetListRepository(requireContext())
        betLists = betListRepository.getAllBetLists()
        betlistScreenAdapter = BetlistScreenAdapter(betLists)
        initViews()
        println("late Init")
    }
    private fun initViews(){


        rvBets.layoutManager = GridLayoutManager(activity , 1)
        rvBets.adapter = betlistScreenAdapter
        rvBets.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

    }


}