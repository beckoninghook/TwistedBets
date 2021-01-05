package com.example.twistedbets.ui.confirmBet

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.twistedbets.R
import com.example.twistedbets.adapter.ConfirmBetScreenAdapter

import com.example.twistedbets.models.bet.BetList
import com.example.twistedbets.models.bet.BetPresets
import com.example.twistedbets.models.wallet.Wallet
import com.example.twistedbets.repository.BetListRepository
import com.example.twistedbets.repository.WalletRepository
import com.example.twistedbets.ui.selectBet.BUNDLE_BETS_KEY
import com.example.twistedbets.ui.selectBet.REQ_BETS_KEY
import kotlinx.android.synthetic.main.fragment_confirm_bet.*


class ConfirmBetFragment : Fragment() {
    //Als de user in dit fragment op confirm klikt gaat het saldo pas omlaag.
    //Kan het ook live doen not sure yet

    //ALS ER OP CONFIRM WORD GEKLIKT GEBRUIK DE BETLIST REPO OM OP TE SLAAN NAAR LOCAL STORAGE

    private lateinit var betListRepository: BetListRepository
    private var betPresets = BetPresets.BETS.filter { it.amount != 0 }
    private val confirmBetScreenAdapter = ConfirmBetScreenAdapter(betPresets , ::onPlusOrMinusClick )
    private var betList: BetList? = null
    private lateinit var walletRepository: WalletRepository
    private  var creditAmount : Float = 0.0f
    private var minusTotal = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_bet, container, false)

    }

    private fun onPlusOrMinusClick(betPresets: BetPresets, char : Char ) {
        if (char == '+'){
            betPresets.amount += 1;
            minusTotal += 1

            tvNewBalance.text = getString(R.string.new_balance_text , creditAmount  - minusTotal )
        }

        if (char == '-'){
            if (betPresets.amount > 0){
                betPresets.amount -= 1
                minusTotal -= 1

                tvNewBalance.text = getString(R.string.new_balance_text , creditAmount  - minusTotal )
            }
        }

        confirmBetScreenAdapter.notifyDataSetChanged()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        betListRepository = BetListRepository(requireContext())
        walletRepository = WalletRepository(requireContext())
        creditAmount = this.walletRepository.getAllWallets()[0].credits.toFloat()
        observeAddBetlistResult()
        initViews(view)
    }




    private fun observeAddBetlistResult() {
        setFragmentResultListener(REQ_BETS_KEY) { key, bundle ->
            bundle.getSerializable(BUNDLE_BETS_KEY)?.let {
                betList = it as BetList
                betPresets = betList!!.selectedBets

                Log.i("player champion" , betList!!.lastMatch?.champion.toString())
                tvSelectedBetsTitle.text = getString(R.string.selecting_bets_title, betList!!.summoner.name)
                confirmBetScreenAdapter.notifyDataSetChanged();
            } ?: Log.e("ConfirmBetFragment", "Request triggered, but empty reminder text!")

        }
    }


    private fun initViews(view: View){
        rvSelectedBets.layoutManager = GridLayoutManager(activity , 1)
        rvSelectedBets.adapter = confirmBetScreenAdapter
        rvSelectedBets.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        for (bets in betPresets){
            minusTotal += bets.amount
        }

        tvNewBalance.text = getString(R.string.new_balance_text , creditAmount  - minusTotal )

        view.findViewById<Button>(R.id.btnConfirm).setOnClickListener {
            val newCreditVal = creditAmount  - minusTotal
            walletRepository.updateWallet(Wallet(newCreditVal.toInt() , 1))
            println(walletRepository.getAllWallets()[0].credits)
            betListRepository.insertBetList(betList!!)
            findNavController().navigate(R.id.action_confirm_bets_to_bet_backlog)
        }

    }




}


