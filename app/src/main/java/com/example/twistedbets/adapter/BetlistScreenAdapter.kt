package com.example.twistedbets.adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twistedbets.R
import com.example.twistedbets.models.bet.BetList
import com.example.twistedbets.models.bet.BetPresets
import kotlinx.android.synthetic.main.item_betlist_parent.view.*
import java.text.SimpleDateFormat
import java.util.*

class BetlistScreenAdapter (private val betLists : List<BetList> , private val onClick: (BetList) -> Unit)  :
    RecyclerView.Adapter<BetlistScreenAdapter.ViewHolder>()  {
    //PARENT
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var betScreenAdapter : BetScreenAdapter

        init {
            itemView.btnRetrieveCredits.setOnClickListener { onClick(betLists[adapterPosition]) }
        }



        fun bind(betLists: BetList) {
            itemView.tvBetListTitle.text = betLists.summoner.name
            itemView.tvDate.text = getDate(betLists.lastMatch?.timestamp , "yyyy-MM-dd HH:mm:ss")
            val context = itemView.context
            var childRecyclerview = itemView.findViewById<RecyclerView>(R.id.rvItemBets)
            childRecyclerview.layoutManager =  GridLayoutManager(context , 1)
            betScreenAdapter = BetScreenAdapter(betLists.selectedBets.filter { it.amount != 0 } , ::ClickOnBet)
            childRecyclerview.adapter = betScreenAdapter

            if (betLists.isBetResolved){
                println("bet is resolved greetings from the adapter " +  betLists.wonCredits)
                itemView.btnRetrieveCredits.text = context.resources.getString(R.string.Retrieve_credits ,
                    betLists.wonCredits?.toFloat()
                )
            }else {
                itemView.btnRetrieveCredits.text = context.resources.getString(R.string.check_bet_button)
            }
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