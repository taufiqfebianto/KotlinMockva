package com.example.mockvakotlin

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.history_item.view.*
import model.LogTransactionResponse

class HistoryViewAdapter(
    private val logs: List<LogTransactionResponse>,
    private val adapterOnClick: (LogTransactionResponse) -> Unit
) : RecyclerView.Adapter<HistoryViewAdapter.HistoryHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): HistoryHolder {
        return HistoryHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.history_item, viewGroup, false)
        )
    }

    override fun getItemCount(): Int = logs.size

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        holder.bindHistory(logs[position])

    }

    inner class HistoryHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindHistory(history: LogTransactionResponse) {
            itemView.apply {
                txtDate.text = "Date :" + history.transactionTimestamp
                txtAmount.text = "Amount :" + history.amount.toString()
                txtRef.text = "Ref :" + history.clientRef
                txtDestination.text = "Destination : " + history.accountDstId

                setOnClickListener {
                    adapterOnClick(history)
                    Log.e("TAG","CLICKED")

                }
            }
        }
    }
}