package com.tastycafe.mykotlinsample.Users.UserAdapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.Admin.AdminModels.ItemDatasList
import com.tastycafe.mykotlinsample.R

class HotItemsAdapter(var context: Context, val cateitemslist: ArrayList<ItemDatasList>) :
    RecyclerView.Adapter<HotItemsAdapter.ViewHolder> (){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item_name: TextView
        var item_price: TextView
        var item_img: ImageView

        init {
            item_name = itemView.findViewById(R.id.item_name) as TextView
            item_price = itemView.findViewById(R.id.item_price) as TextView
            item_img = itemView.findViewById(R.id.item_img) as ImageView
                   }
                }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_all_items, parent, false)
        return ViewHolder(v)
                }

    override fun getItemCount(): Int {
          return cateitemslist.size
                }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cItems : ItemDatasList = cateitemslist[position]
        holder.item_name.text = cItems.item_name

        if(!cItems.item_ofr_price.equals("00.00")) {
            holder?.item_price.setText("$ " + cItems.item_ofr_price)
                    } else {
            holder?.item_price.setText("$ " + cItems.item_price)
                 }

        val bitmap: Bitmap = BitmapFactory.decodeFile(cItems.item_img)
        holder.item_img!!.setImageBitmap(bitmap)
                }
        
    }
