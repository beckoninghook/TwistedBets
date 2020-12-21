package com.example.twistedbets.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twistedbets.R
import com.example.twistedbets.models.bet.BetList
import com.example.twistedbets.models.bet.BetPresets
import kotlinx.android.synthetic.main.fragment_bets.*
import kotlinx.android.synthetic.main.item_bet_select.view.*
import kotlinx.android.synthetic.main.item_betlist_parent.view.*

class BetlistScreenAdapter (private val betLists : List<BetList>   , private val onClick: (BetList) -> Unit)  :
    RecyclerView.Adapter<BetlistScreenAdapter.ViewHolder>()  {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var betScreenAdapter : BetScreenAdapter
        init {
            itemView.setOnClickListener { onClick(betLists[adapterPosition]) }
        }
        fun bind(betLists: BetList) {
            itemView.tvBetListTitle.text = betLists.summoner.name
            val context = itemView.context
            var otherRecyclerview = itemView.findViewById<RecyclerView>(R.id.rvItemBets)
            otherRecyclerview.layoutManager =  GridLayoutManager(context , 1)
            betScreenAdapter = BetScreenAdapter(betLists.selectedBets.filter { it.amount != 0 })
            otherRecyclerview.adapter = betScreenAdapter
        }
    }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_betlist_parent, parent, false)
        )
    }

    override fun getItemCount(): Int = betLists.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(betLists[position])

}