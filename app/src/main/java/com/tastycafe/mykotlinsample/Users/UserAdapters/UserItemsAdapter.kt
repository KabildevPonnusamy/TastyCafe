package com.tastycafe.mykotlinsample.Users.UserAdapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.Admin.AdminModels.CategoryList
import com.tastycafe.mykotlinsample.Admin.AdminModels.ItemDatasList
import com.tastycafe.mykotlinsample.R

class UserItemsAdapter(var context: Context, val cateitemslist: ArrayList<ItemDatasList>) :
    RecyclerView.Adapter<UserItemsAdapter.ViewHolder> (){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var it_name: TextView
        var it_price: TextView
        var it_img: ImageView

        init {
            it_name = itemView.findViewById(R.id.it_name) as TextView
            it_price = itemView.findViewById(R.id.it_price) as TextView
            it_img = itemView.findViewById(R.id.it_img) as ImageView
                   }
                }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.user_item_childs, parent, false)
        return ViewHolder(v)
                }

    override fun getItemCount(): Int {
          return cateitemslist.size
                }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cItems : ItemDatasList = cateitemslist[position]
        holder.it_name.text = cItems.item_name

        if(!cItems.item_ofr_price.equals("00.00")) {
            holder?.it_price.setText("$ " + cItems.item_ofr_price)
                    } else {
            holder?.it_price.setText("$ " + cItems.item_price)
                 }

        val bitmap: Bitmap = BitmapFactory.decodeFile(cItems.item_img)
        holder.it_img!!.setImageBitmap(bitmap)
                }
        
    }
