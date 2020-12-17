package com.example.twistedbets.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twistedbets.R
import com.example.twistedbets.models.bet.BetPresets
import kotlinx.android.synthetic.main.item_bet_select.view.*
import kotlin.reflect.KFunction2

class ConfirmBetScreenAdapter(private val betPresets: List<BetPresets>, private val onClick: KFunction2<@ParameterName(
    name = "betPresets"
) BetPresets, @ParameterName(name = "char") Char, Unit>
) :
    RecyclerView.Adapter<ConfirmBetScreenAdapter.ViewHolder>()  {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.btnPlus.setOnClickListener { onClick(betPresets[adapterPosition]  , '+') }
            itemView.btnMinus.setOnClickListener { onClick(betPresets[adapterPosition] , '-') }
        }


        fun bind(betPresets: BetPresets) {

            if(betPresets.amount != 0){
                itemView.tvBetTitle.text = betPresets.title
                itemView.tvBetDesc.text = betPresets.description
                itemView.tvAmount.text = betPresets.amount.toString()
            }else {
                itemView.visibility = View.GONE
            }

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