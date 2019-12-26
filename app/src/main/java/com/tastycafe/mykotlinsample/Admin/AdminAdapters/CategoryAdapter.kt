package com.tastycafe.mykotlinsample.Admin.AdminAdapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.Admin.AdminModels.CategoryList
import com.tastycafe.mykotlinsample.R

class CategoryAdapter(var context: Context, val catelist: ArrayList<CategoryList>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder> (){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cate_name: TextView
        var cate_image: ImageView

        init {
            cate_name = itemView.findViewById(R.id.cate_name) as TextView
            cate_image = itemView.findViewById(R.id.cate_image) as ImageView
                   }
                }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.admin_cate_item, parent, false)
        return ViewHolder(
            v
        )
                }

    override fun getItemCount(): Int {
          return catelist.size
                }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cItems : CategoryList = catelist[position]
        holder.cate_name.text = cItems.cate_name
        val bitmap: Bitmap = BitmapFactory.decodeFile(cItems.cate_img)
        holder.cate_image!!.setImageBitmap(bitmap)
                }
        
    }
