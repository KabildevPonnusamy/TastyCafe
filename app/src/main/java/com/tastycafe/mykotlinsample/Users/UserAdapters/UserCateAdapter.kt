package com.tastycafe.mykotlinsample.Users.UserAdapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.Admin.AdminModels.CategoryList
import com.tastycafe.mykotlinsample.R

class UserCateAdapter(var context: Context, val catelist: ArrayList<CategoryList>, var select_pos: Int) :
    RecyclerView.Adapter<UserCateAdapter.ViewHolder> (){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cate_name: TextView

        init {
            cate_name = itemView.findViewById(R.id.cateName) as TextView
                   }
                }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.user_cate_items, parent, false)
        return ViewHolder(v)
                }

    override fun getItemCount(): Int {
          return catelist.size
                }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cItems : CategoryList = catelist[position]
        holder.cate_name.text = cItems.cate_name

        if(select_pos == position) {
            holder.cate_name.setTextColor(Color.parseColor("#000000"))
                    } else {
            holder.cate_name.setTextColor(Color.parseColor("#80000000"))
                    }
                }
        
    }
