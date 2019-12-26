package com.tastycafe.mykotlinsample.Users.UserActivities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.Admin.AdminModels.ItemDatasList
import com.tastycafe.mykotlinsample.R
import com.tastycafe.mykotlinsample.Users.UserAdapters.AllOfferItemsAdapter
import kotlinx.android.synthetic.main.user_all_offers.*

class UserAllOffers: AppCompatActivity() , View.OnClickListener{

    var alloffersList: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()
    var alloffersListTemp: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()
    lateinit var alloffersAdapter: AllOfferItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_all_offers)
        supportActionBar?.hide()

        view_init()
        get_intent()

            }

    private fun view_init() {
        offers_back.setOnClickListener(this)
        search_edit.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                Log.e("sampleApp","Text: " + s);
                short_data(s)
                        }
                    })
            }

    private fun short_data(s: CharSequence) {
        var str: String = "" + s
        alloffersList.clear()

        if(s != null) {
            if(s.equals("")) {
                alloffersList.addAll(alloffersListTemp)
                    } else  {

                for(i in alloffersListTemp.indices) {
                    if(alloffersListTemp.get(i).item_name?.toLowerCase()!!.startsWith
                            ("" + str.toLowerCase()) ) {

                        var item = ItemDatasList()
                        item.item_id = alloffersListTemp.get(i).item_id
                        item.cate_id = alloffersListTemp.get(i).cate_id
                        item.item_name = alloffersListTemp.get(i).item_name
                        item.item_img = alloffersListTemp.get(i).item_img
                        item.item_price = alloffersListTemp.get(i).item_price
                        item.item_ofr_price = alloffersListTemp.get(i).item_ofr_price
                        item.item_shown_status = alloffersListTemp.get(i).item_shown_status
                        item.item_created_date = alloffersListTemp.get(i).item_created_date
                        alloffersList.add(item)
                                }
                            }
                        }
                    }

        alloffers_recycle.adapter?.notifyDataSetChanged()
            }

    private fun get_intent() {
        alloffersList = intent.getParcelableArrayListExtra("offeredItems")
        alloffersListTemp.addAll(alloffersList)

        if(alloffersList != null) {
            if(alloffersList.size > 0) {
                alloffers_recycle.layoutManager = LinearLayoutManager(this,
                    RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager?
                val adapter =
                    AllOfferItemsAdapter(
                        applicationContext,
                        alloffersList
                    )
                alloffers_recycle.adapter = adapter
                    }
                }
            }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.offers_back -> onBackPressed()
                }
            }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
            }
}