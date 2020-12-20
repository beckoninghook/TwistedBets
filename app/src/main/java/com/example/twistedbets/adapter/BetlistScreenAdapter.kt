package com.example.twistedbets.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twistedbets.R
import com.example.twistedbets.models.bet.BetList
import com.example.twistedbets.models.bet.BetPresets
import kotlinx.android.synthetic.main.item_bet_select.view.*
import kotlinx.android.synthetic.main.item_betlist_parent.view.*

class BetlistScreenAdapter (private val betLists : List<BetList>)  :
    RecyclerView.Adapter<BetlistScreenAdapter.ViewHolder>()  {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(betLists: BetList) {
            itemView.tvBetListTitle.text = betLists.summoner.name
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