package com.example.twistedbets.adapter.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twistedbets.R
import com.example.twistedbets.adapter.BetScreenAdapter
import com.example.twistedbets.models.bet.BetList
import com.example.twistedbets.models.bet.BetPresets
import kotlinx.android.synthetic.main.item_betlist_parent.view.*
import kotlinx.android.synthetic.main.item_history_betlist_parent.view.*
import java.text.SimpleDateFormat
import java.util.*

class BetHistoryAdapter (private val betLists : List<BetList>, private val onClick: (BetList) -> Unit)  :
    RecyclerView.Adapter<BetHistoryAdapter.ViewHolder>()  {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var betHistoryChildAdapater: BetHistoryChildAdapater

        init {
            itemView.setOnClickListener { onClick(betLists[adapterPosition]) }
        }



        fun bind(betLists: BetList) {
            itemView.tvHistoryBetListTitle.text = betLists.summoner.name
            itemView.tvCreditsWon.text = betLists.wonCredits.toString()
            val context = itemView.context
            var childRecyclerview = itemView.findViewById<RecyclerView>(R.id.rvItemHistoryBets)
            childRecyclerview.layoutManager =  GridLayoutManager(context , 1)
            betHistoryChildAdapater = BetHistoryChildAdapater(
                betLists.selectedBets.filter { it.amount != 0 },
                ::ClickOnBet)
            childRecyclerview.adapter = betHistoryChildAdapater

        }
    }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_history_betlist_parent, parent, false)
        )
    }



    override fun getItemCount(): Int = betLists.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(betLists[position])

    /**
     * Return date in specified format.
     * @param milliSeconds Date in milliseconds
     * @param dateFormat Date format
     * @return String representing date in specified format
     */
    fun getDate(milliSeconds: Long?, dateFormat: String?): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar: Calendar = Calendar.getInstance()
        if (milliSeconds != null) {
            calendar.timeInMillis = milliSeconds
        }
        return formatter.format(calendar.time)
    }
    fun ClickOnBet(betPresets: BetPresets){
        println(betPresets)
    }
}