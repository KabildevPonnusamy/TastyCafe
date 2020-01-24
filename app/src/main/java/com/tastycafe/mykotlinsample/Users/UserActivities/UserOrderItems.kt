package com.tastycafe.mykotlinsample.Users.UserActivities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.Database.DBHelper
import com.tastycafe.mykotlinsample.R
import com.tastycafe.mykotlinsample.Users.UserAdapters.MyCartAdapter
import com.tastycafe.mykotlinsample.Users.UserAdapters.OrderItemsAdapter
import com.tastycafe.mykotlinsample.Users.UserModels.OrderItemList
import kotlinx.android.synthetic.main.user_addcart.*
import kotlinx.android.synthetic.main.user_orderitems.*

class UserOrderItems : AppCompatActivity() {

    var user_email: String = ""
    private val sharedPrefFile = "coffee_preference"
    internal lateinit var db: DBHelper
    var orderItemList: ArrayList<OrderItemList> = ArrayList<OrderItemList>()
    var totalamt: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_orderitems)
        supportActionBar?.hide()

        db = DBHelper(applicationContext)
        getMyPreferences()
        getIntents()
    }

    private fun getIntents() {
        orderItemList = intent.getParcelableArrayListExtra("orderItemsList")
        totalamt = intent.getStringExtra("orderPrice")
        totalprice.setText("Total: $" + totalamt)
        showOrderItems()
    }

    private fun showOrderItems() {
        if(orderItemList != null) {
            if(orderItemList.size > 0) {
                oitems_recycle.layoutManager = LinearLayoutManager(
                    this, RecyclerView.VERTICAL,
                    false
                )
                val adapter =
                    OrderItemsAdapter(
                        this, orderItemList
                    )
                oitems_recycle.adapter = adapter
            }
        }
    }

    private fun getMyPreferences() {
        var sharedPref: SharedPreferences =
            this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPref.edit()
        user_email = "" + sharedPref.getString("user_email", "")
    }
}