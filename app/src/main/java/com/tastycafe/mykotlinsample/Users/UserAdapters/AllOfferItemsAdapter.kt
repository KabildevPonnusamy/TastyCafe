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

class AllOfferItemsAdapter(var context: Context, val cateitemslist: ArrayList<ItemDatasList>) :
    RecyclerView.Adapter<AllOfferItemsAdapter.ViewHolder> (){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var offer_name: TextView
        var offer_price: TextView
        var offer_image: ImageView

        init {
            offer_name = itemView.findViewById(R.id.offer_name) as TextView
            offer_price = itemView.findViewById(R.id.offer_price) as TextView
            offer_image = itemView.findViewById(R.id.offer_image) as ImageView
                   }
                }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_all_offers, parent, false)
        return ViewHolder(v)
                }

    override fun getItemCount(): Int {
          return cateitemslist.size
                }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cItems : ItemDatasList = cateitemslist[position]
        holder.offer_name.text = cItems.item_name

        if(!cItems.item_ofr_price.equals("00.00")) {
            holder?.offer_price.setText("$ " + cItems.item_ofr_price)
                    } else {
            holder?.offer_price.setText("$ " + cItems.item_price)
                 }

        val bitmap: Bitmap = BitmapFactory.decodeFile(cItems.item_img)
        holder.offer_image!!.setImageBitmap(bitmap)
                }
        
    }
