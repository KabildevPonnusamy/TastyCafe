package com.tastycafe.mykotlinsample.Users.UserAdapters

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.Admin.AdminModels.ItemDatasList
import com.tastycafe.mykotlinsample.Database.DBHelper
import com.tastycafe.mykotlinsample.R
import com.tastycafe.mykotlinsample.Users.UserActivities.UserMyCart
import com.tastycafe.mykotlinsample.Users.UserModels.AddCartList
import java.lang.Math.round
import java.math.BigDecimal
import java.math.RoundingMode

class MyCartAdapter(var context: Context, val cartList: ArrayList<AddCartList>,
                    var user_email:String) :
    RecyclerView.Adapter<MyCartAdapter.ViewHolder> (){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ic_image: ImageView
        var ic_name: TextView
        var ic_plus: ImageView
        var ic_qty: TextView
        var ic_minus: ImageView
        var ic_total: TextView
        var ic_remove:ImageView
        internal lateinit var db: DBHelper

        init {
            ic_image = itemView.findViewById(R.id.ic_image) as ImageView
            ic_name = itemView.findViewById(R.id.ic_name) as TextView
            ic_plus = itemView.findViewById(R.id.ic_plus) as ImageView
            ic_qty = itemView.findViewById(R.id.ic_qty) as TextView
            ic_minus = itemView.findViewById(R.id.ic_minus) as ImageView
            ic_total = itemView.findViewById(R.id.ic_total) as TextView
            ic_remove = itemView.findViewById(R.id.ic_remove) as ImageView
            db = DBHelper(itemView.context)
                   }
                }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_mycart, parent, false)
        return ViewHolder(v)
                }

    override fun getItemCount(): Int {
          return cartList.size
                }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cItems : AddCartList = cartList[position]
        holder.ic_name.text = cItems.cart_item_name
        holder.ic_qty.text = cItems.cart_item_count
        var count_val: Float? = cItems.cart_item_count?.toFloat()
        var price_val: Float? = cItems.cart_item_price?.toFloat()

        var value: Float? = count_val!!.times(price_val!!)
        holder.ic_total.text = "$" + "%.2f".format(value)

        holder.ic_remove.setOnClickListener {
            holder.db.deleteCartItems("" + user_email, "" + cItems.cart_id)
            holder.db.close()
            cartList.removeAt(position)

            (context as UserMyCart).showTotal(cartList)
            notifyDataSetChanged()
        }

        holder.ic_minus.setOnClickListener {
            if(Integer.parseInt("" + cItems.cart_item_count ) > 1 ) {
                var tempVal: Int = Integer.parseInt("" + cItems.cart_item_count) - 1
                cartList[position].cart_item_count = "" + tempVal
                notifyDataSetChanged()
            }
            (context as UserMyCart).showTotal(cartList)
        }

        holder.ic_plus.setOnClickListener {
            if(Integer.parseInt( "" + cItems.cart_item_count ) < 1) {

            } else if(Integer.parseInt( "" + cItems.cart_item_count ) < 9) {
                var tempVal: Int = Integer.parseInt("" + cItems.cart_item_count) + 1
                cartList[position].cart_item_count = "" + tempVal
                notifyDataSetChanged()
            }

            (context as UserMyCart).showTotal(cartList)
        }

        val bitmap: Bitmap = BitmapFactory.decodeFile(cItems.cart_item_image)
        holder.ic_image!!.setImageBitmap(bitmap)
                }
        
    }
