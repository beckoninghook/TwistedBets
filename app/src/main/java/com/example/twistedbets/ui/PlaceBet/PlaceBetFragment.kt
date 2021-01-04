package com.example.twistedbets.ui.PlaceBet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.twistedbets.R
import com.example.twistedbets.models.bet.BetPresets
import com.example.twistedbets.models.wallet.Wallet
import com.example.twistedbets.repository.WalletRepository
import com.example.twistedbets.vm.SummonerViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_place_bets.*
const val REQ_SUMMONER_KEY = "req_summoner"
const val BUNDLE_SUMMONER_KEY = "bundle_summoner"

class PlaceBetFragment : Fragment() {

    private val summonerViewModel: SummonerViewModel by viewModels()
    private lateinit var walletRepository: WalletRepository



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place_bets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner: Spinner = view.findViewById(R.id.regionSpinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.regions,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        walletRepository = WalletRepository(requireContext())
        checkForWallet()
        view.findViewById<Button>(R.id.btnFind).setOnClickListener {
            //set all bet amounts on 0 from previous sessions
            for (bet in BetPresets.BETS){
                bet.amount = 0
            }
            summonerViewModel.getSummonerByName(inputSummonersName.text.toString())
            summonerViewModel.summoner.observe(viewLifecycleOwner, Observer {

                Log.i(it.name , inputSummonersName.text.toString() )
                if (it.name == inputSummonersName.text.toString()){
                    setFragmentResult(REQ_SUMMONER_KEY, bundleOf(Pair(BUNDLE_SUMMONER_KEY, it)))
                    findNavController().navigate(R.id.action_navigation_dashboard_to_select_bet)
                }else {
                    val snackbar = Snackbar.make(view,
                        "Cannot find user, Check if the region or name is correct",
                        Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }
            })



        }

    }

    private fun checkForWallet(){
        if (walletRepository.getAllWallets().isEmpty()){
            val newWallet = Wallet(200)
            walletRepository.insertWallet(newWallet)
        }
    }

}