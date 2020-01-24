package com.tastycafe.mykotlinsample.Users.UserFragments


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.Admin.AdminSupportClasses.RecyclerItemClickListenr
import com.tastycafe.mykotlinsample.Database.DBHelper
import com.tastycafe.mykotlinsample.R
import com.tastycafe.mykotlinsample.Users.UserActivities.UserOrderItems
import com.tastycafe.mykotlinsample.Users.UserAdapters.OrdersAdapter
import com.tastycafe.mykotlinsample.Users.UserModels.OrderItemList
import com.tastycafe.mykotlinsample.Users.UserModels.OrdersList
import kotlinx.android.synthetic.main.fragment_history.*

/**
 * A simple [Fragment] subclass.
 */
class HistoryFragment : Fragment() {

    var ordersList: ArrayList<OrdersList> = ArrayList<OrdersList>()
    var orderItemList: ArrayList<OrderItemList> = ArrayList<OrderItemList>()
    var user_email: String = ""
    private val sharedPrefFile = "coffee_preference"
    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    internal lateinit var db: DBHelper
    lateinit var intent: Intent

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
            }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        get_preferences()
        get_orders()
    }

    private fun get_preferences() {
        db = DBHelper(requireContext())
        sharedPref = activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
        user_email = "" + sharedPref.getString("user_email", "")
        Log.e("appSample", "Email: " + user_email)
    }

    private fun get_orders() {
        ordersList.clear()
        ordersList = db.getOrdersList(user_email)
        Log.e("appSample", "OrderListSize: " + ordersList.size)
        if(ordersList != null) {
            if(ordersList.size > 0) {

                orders_recycle.layoutManager = LinearLayoutManager(requireContext(),
                    RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager?
                val adapter =
                    OrdersAdapter(
                        requireContext(),
                        ordersList
                    )
                orders_recycle.adapter = adapter
                }
            }

        orderListeners()
        }

    private fun orderListeners() {
        orders_recycle.addOnItemTouchListener(
            RecyclerItemClickListenr(requireContext(),
                orders_recycle,
                object :
                    RecyclerItemClickListenr.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        var itemid: Int = ordersList[position].id
                        var totalprice: String = "" + ordersList[position].order_amount

                        orderItemList.clear()
                        orderItemList = db.getOrderItemsList(user_email, "" + itemid)
                        db.close()

                        intent = Intent(activity, UserOrderItems::class.java)
                        intent.putParcelableArrayListExtra("orderItemsList", orderItemList)
                        intent.putExtra("orderPrice", totalprice)
                        startActivityForResult(intent, 10)
                        activity?.overridePendingTransition(
                            R.anim.slide_up,
                            R.anim.no_animation
                        )

                    }
                    override fun onItemLongClick(view: View?, position: Int) {
                    }
                })
        )
    }
}
