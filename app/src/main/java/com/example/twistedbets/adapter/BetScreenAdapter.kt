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
import kotlin.reflect.KFunction2

class BetScreenAdapter (private val betPresets: List<BetPresets> , private val onClick: (BetPresets) -> Unit)  :
    RecyclerView.Adapter<BetScreenAdapter.ViewHolder>()  {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { onClick(betPresets[adapterPosition]) }
        }

        fun bind(betPresets: BetPresets) {
            itemView.tvBetTitle.text = betPresets.title
            itemView.tvBetDesc.text = betPresets.description
            itemView.tvAmount.text = betPresets.amount.toString()
        }
    }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_bet_select, parent, false)
        )
    }

    override fun getItemCount(): Int = betPresets.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(betPresets[position])

}