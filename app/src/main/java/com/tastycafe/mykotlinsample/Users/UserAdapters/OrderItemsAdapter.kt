package com.tastycafe.mykotlinsample.Users.UserAdapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.R
import com.tastycafe.mykotlinsample.Users.UserModels.OrderItemList
import com.tastycafe.mykotlinsample.Users.UserModels.OrdersList
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


class OrderItemsAdapter(var context: Context, val ordersItemsList: ArrayList<OrderItemList>) :
    RecyclerView.Adapter<OrderItemsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item_name: TextView
        var item_cate_counts: TextView
        var item_price: TextView
        var item_img: ImageView

        init {
            item_name = itemView.findViewById(R.id.item_name) as TextView
            item_cate_counts = itemView.findViewById(R.id.item_cate_counts) as TextView
            item_price = itemView.findViewById(R.id.item_price) as TextView
            item_img = itemView.findViewById(R.id.item_img) as ImageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent?.context).inflate(R.layout.item_orderitems, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return ordersItemsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cItems: OrderItemList = ordersItemsList[position]
        holder.item_name.text = cItems.item_name
        holder.item_cate_counts.text = "In " + cItems.cate_id + " (" + cItems.item_count + " Items)"
        var count_val: Float? = cItems.item_count?.toFloat()
        var price_val: Float? = cItems.item_price?.toFloat()

        var value: Float? = count_val!!.times(price_val!!)
        holder.item_price.text = "$" + "%.2f".format(value)

//        holder.item_price.text = "$ " + cItems.item_price

        val bitmap: Bitmap = BitmapFactory.decodeFile(cItems.item_image)
        holder.item_img!!.setImageBitmap(bitmap)
    }
}
