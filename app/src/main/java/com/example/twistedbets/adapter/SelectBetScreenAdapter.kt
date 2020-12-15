package com.example.twistedbets.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.twistedbets.R
import com.example.twistedbets.models.BetPresets
import kotlinx.android.synthetic.main.item_bet_select.view.*

class SelectBetScreenAdapter (private val betPresets: List<BetPresets>) :
    RecyclerView.Adapter<SelectBetScreenAdapter.ViewHolder>()  {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(betPresets: BetPresets) {
            itemView.tvBetTitle.text = betPresets.title
            itemView.tvBetDesc.text = betPresets.description
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