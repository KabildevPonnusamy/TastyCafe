package com.tastycafe.mykotlinsample.Users.UserAdapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.R
import com.tastycafe.mykotlinsample.Users.UserModels.OrdersList
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


class OrdersAdapter(var context: Context, val ordersList: ArrayList<OrdersList>) :
    RecyclerView.Adapter<OrdersAdapter.ViewHolder> (){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var order_name: TextView
        var item_counts: TextView
        var item_booked_date: TextView
        var item_total_amt: TextView

        init {
            order_name = itemView.findViewById(R.id.order_name) as TextView
            item_counts = itemView.findViewById(R.id.item_counts) as TextView
            item_booked_date = itemView.findViewById(R.id.item_booked_date) as TextView
            item_total_amt = itemView.findViewById(R.id.item_total_amt) as TextView
                   }
                }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_orders, parent, false)
        return ViewHolder(v)
                }

    override fun getItemCount(): Int {
          return ordersList.size
                }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cItems : OrdersList = ordersList[position]
        holder.order_name.text = cItems.order_name
        holder.item_counts.text = cItems.order_items_count + " Items"
        holder.item_total_amt.text =  "$ " + cItems.order_amount

        val formatter = DateTimeFormatter.ofPattern("dd-M-yyyy hh:mm:ss", Locale.ENGLISH)
        val dateTime = LocalDate.parse(cItems.order_date, formatter)

        holder.item_booked_date.text = "" + dateTime
                }
        
    }
