package com.tastycafe.mykotlinsample.Users.UserActivities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.R
import com.tastycafe.mykotlinsample.Users.UserAdapters.MyCartAdapter
import com.tastycafe.mykotlinsample.Users.UserModels.AddCartList
import kotlinx.android.synthetic.main.user_addcart.*

class UserMyCart : AppCompatActivity(), View.OnClickListener {

    var mycartList: ArrayList<AddCartList> = ArrayList<AddCartList>()
    var user_email: String = ""
    private val sharedPrefFile = "coffee_preference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_addcart)
        supportActionBar?.hide()

        getMyPreferences()
        init_views()
        getIntents()

    }

    private fun getMyPreferences() {
        var sharedPref: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPref.edit()
        user_email = "" + sharedPref.getString("user_email", "")
    }

    private fun getIntents() {
        mycartList = intent.getParcelableArrayListExtra("myCartList")
        if(mycartList != null) {
            if(mycartList.size > 0) {
                mycart_recycle.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,
                    false)
                val adapter =
                    MyCartAdapter(
                        this, mycartList,
                        user_email
                    )
                mycart_recycle.adapter = adapter
            }
        }

        showTotal(mycartList)
    }

    private fun init_views() {
        mycart_back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mycart_back -> onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }

    public fun showTotal (cartList: ArrayList<AddCartList>) {
        Log.e("appSample" , "Calling")
        var total: Float? = 00.00f

        for(items in cartList.indices) {

            var count_val: Float? = cartList[items].cart_item_count?.toFloat()
            var price_val: Float? = cartList[items].cart_item_price?.toFloat()
            var value: Float? = count_val!!.times(price_val!!)

            total = total?.plus(value!!.toFloat())
            Log.e("appSample" , "Name: " + cartList[items].cart_item_name + " Count: " +
                    cartList[items].cart_item_count)
                    }

        pay_btn.setText( "Pay $" + "%.2f".format(total))
    }
}