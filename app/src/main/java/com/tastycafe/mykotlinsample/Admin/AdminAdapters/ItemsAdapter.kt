package com.tastycafe.mykotlinsample.Admin.AdminAdapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.Admin.AdminModels.ItemDatasList
import com.tastycafe.mykotlinsample.R

class ItemsAdapter (var context: Context, val itemsList: ArrayList<ItemDatasList>): RecyclerView
    .Adapter<ItemsAdapter.ViewHolder>() {

     class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val i_image = itemView.findViewById(R.id.i_image) as ImageView
        val i_name = itemView.findViewById(R.id.i_name) as TextView
        val i_price = itemView.findViewById(R.id.i_price) as TextView
        val io_price = itemView.findViewById(R.id.io_price) as TextView
                    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.admin_items_item, parent, false)
        return ViewHolder(
            v
        )
            }

    override fun getItemCount(): Int {
        return itemsList.size
                }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val iList = itemsList[position]
        holder?.i_name.text = iList.item_name
        val bitmap: Bitmap = BitmapFactory.decodeFile(iList.item_img)
        holder.i_image!!.setImageBitmap(bitmap)

        val content1 = iList.item_price + " $"

        if(!iList.item_ofr_price.equals("00.00")) {
            val spannableString1 = SpannableString(content1)
            spannableString1.setSpan(StrikethroughSpan(),0,content1.length,0)
            holder?.i_price.text = spannableString1
            holder?.io_price.text = iList.item_ofr_price + " $"
                    } else {
            holder?.i_price.text = iList.item_price + " $"
            holder?.io_price.visibility == View.GONE
                    }
                }
            }