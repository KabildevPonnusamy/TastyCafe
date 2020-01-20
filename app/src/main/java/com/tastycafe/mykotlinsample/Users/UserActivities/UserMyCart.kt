package com.tastycafe.mykotlinsample.Users.UserActivities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tastycafe.mykotlinsample.Database.DBHelper
import com.tastycafe.mykotlinsample.R
import com.tastycafe.mykotlinsample.Users.UserAdapters.MyCartAdapter
import com.tastycafe.mykotlinsample.Users.UserAdapters.SimilarAdapter
import com.tastycafe.mykotlinsample.Users.UserModels.AddCartList
import com.tastycafe.mykotlinsample.Users.UserModels.OrdersList
import kotlinx.android.synthetic.main.namesheetlayout.*
import kotlinx.android.synthetic.main.user_addcart.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class UserMyCart : AppCompatActivity(), View.OnClickListener {

    var mycartList: ArrayList<AddCartList> = ArrayList<AddCartList>()
    var user_email: String = ""
    private val sharedPrefFile = "coffee_preference"
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    var finalamt: String = ""
    internal lateinit var db: DBHelper
    var ordersList: ArrayList<OrdersList> = ArrayList<OrdersList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_addcart)
        supportActionBar?.hide()

        db = DBHelper(applicationContext)
        getMyPreferences()
        init_views()
        getIntents()
        bottomsheetcallbacks()
    }

    private fun bottomsheetcallbacks() {
        bottomSheetBehavior.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // React to state change
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // React to dragging events
            }
        })
    }

    private fun getMyPreferences() {
        var sharedPref: SharedPreferences =
            this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPref.edit()
        user_email = "" + sharedPref.getString("user_email", "")
    }

    private fun getIntents() {
        mycartList = intent.getParcelableArrayListExtra("myCartList")
        if (mycartList != null) {
            if (mycartList.size > 0) {
                mycart_recycle.layoutManager = LinearLayoutManager(
                    this, RecyclerView.VERTICAL,
                    false
                )
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
        bottomSheetBehavior = BottomSheetBehavior.from<LinearLayout>(bottom_sheet_layout)
        mycart_back.setOnClickListener(this)
        pay_btn.setOnClickListener(this)
        close_sheet.setOnClickListener (this)
        order_btn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mycart_back -> onBackPressed()
            R.id.pay_btn -> orderNow()
            R.id.order_btn -> validateName()
            R.id.close_sheet -> close_my_sheet()
        }
    }

    private fun close_my_sheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
    }

    private fun validateName() {
        var strOder: String = ordername.text.toString().trim()
        if(strOder.equals("")) {
            Toast.makeText(applicationContext, "Enter Order name", Toast.LENGTH_SHORT).show()
            return
        }

        val sdf = SimpleDateFormat("dd-M-yyyy hh:mm:ss")
        var currentDate = sdf.format(Date())

        ordersList.clear()
        ordersList = db.checkOrderList(user_email, strOder)
        if(ordersList.size > 0) {
            Toast.makeText(applicationContext, "Order name already present", Toast.LENGTH_SHORT).show()
            return
        }

        db.addToOrders(user_email, strOder, "" + mycartList.size ,
            finalamt, currentDate);

        ordersList.clear()
        ordersList = db.getOrdersList(user_email)

        for(i in mycartList.indices) {
            db.addToOrderItems( "" + ordersList.get(ordersList.size - 1).id, user_email,
                "" + mycartList.get(i).cart_cate_id, "" + mycartList.get(i).cart_item_id,
                "" + mycartList.get(i).cart_item_name, "" + mycartList.get(i).cart_item_price,
                "" + mycartList.get(i).cart_item_count, "" + mycartList.get(i).cart_item_image)

            Log.e("appSample", "Name: " + mycartList.get(i).cart_item_name)
            Log.e("appSample", "Count: " + mycartList.get(i).cart_item_count)
        }

        db.deleteAllCartItems(user_email)
        db.close()

        Toast.makeText(applicationContext, "Thanks for your order", Toast.LENGTH_SHORT).show()
        onBackPressed()
    }

    private fun orderNow() {
        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }

    public fun showTotal(cartList: ArrayList<AddCartList>) {
        Log.e("appSample", "Calling")
        var total: Float? = 00.00f

        for (items in cartList.indices) {

            var count_val: Float? = cartList[items].cart_item_count?.toFloat()
            var price_val: Float? = cartList[items].cart_item_price?.toFloat()
            var value: Float? = count_val!!.times(price_val!!)

            total = total?.plus(value!!.toFloat())
            Log.e(
                "appSample", "Name: " + cartList[items].cart_item_name + " Count: " +
                        cartList[items].cart_item_count
            )
        }

        pay_btn.setText("Pay $" + "%.2f".format(total))
        finalamt = "" + total
    }
}