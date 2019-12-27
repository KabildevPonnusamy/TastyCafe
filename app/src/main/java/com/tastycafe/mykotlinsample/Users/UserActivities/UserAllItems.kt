package com.tastycafe.mykotlinsample.Users.UserActivities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.Admin.AdminModels.ItemDatasList
import com.tastycafe.mykotlinsample.Admin.AdminSupportClasses.RecyclerItemClickListenr
import com.tastycafe.mykotlinsample.R
import com.tastycafe.mykotlinsample.Users.UserAdapters.AllItemsAdapter
import com.tastycafe.mykotlinsample.Users.UserAdapters.AllOfferItemsAdapter
import kotlinx.android.synthetic.main.user_all_items.*
import kotlinx.android.synthetic.main.user_all_offers.*
import kotlinx.android.synthetic.main.user_all_offers.offers_back
import kotlinx.android.synthetic.main.user_all_offers.search_edit
import kotlinx.android.synthetic.main.user_coolitems.*

class UserAllItems: AppCompatActivity() , View.OnClickListener{

    var allitemsList: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()
    var allitemsListTemp: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_all_items)
        supportActionBar?.hide()

        view_init()
        get_intent()
        recycler_listeners()
            }

    private fun recycler_listeners() {
        allitems_recycle.addOnItemTouchListener(
            RecyclerItemClickListenr(applicationContext,
                allitems_recycle,
                object :
                    RecyclerItemClickListenr.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        var itemid: Int = allitemsList[position].item_id
                        var itemname: String? = allitemsList[position].item_name
                        var itemimage: String? = allitemsList[position].item_img
                        var itemcateid: String? = allitemsList[position].cate_id
                        var itemprice: String? = allitemsList[position].item_price
                        var itemofrprice: String? = allitemsList[position].item_ofr_price
                        var itemlikecount:String? = allitemsList[position].item_like_count

                        intent = Intent(applicationContext, UserItemDetails::class.java)
                        intent.putExtra("itemid", "" + itemid)
                        intent.putExtra("itemname", itemname)
                        intent.putExtra("itemimage", itemimage)
                        intent.putExtra("itemcateid", itemcateid)
                        intent.putExtra("itemprice", itemprice)
                        intent.putExtra("itemofrprice", itemofrprice)
                        intent.putExtra("itemlikecount", itemlikecount)

                        startActivityForResult(intent, 11)
                        overridePendingTransition(
                            R.anim.slide_up,
                            R.anim.no_animation
                        )
                    }
                    override fun onItemLongClick(view: View?, position: Int) {
                                }
                        })
                  )
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
        allitemsList.clear()

        if(s != null) {
            if(s.equals("")) {
                allitemsList.addAll(allitemsListTemp)
                    } else  {

                for(i in allitemsListTemp.indices) {
                    if(allitemsListTemp.get(i).item_name?.toLowerCase()!!.startsWith
                            ("" + str.toLowerCase()) ) {

                        var item = ItemDatasList()
                        item.item_id = allitemsListTemp.get(i).item_id
                        item.cate_id = allitemsListTemp.get(i).cate_id
                        item.item_name = allitemsListTemp.get(i).item_name
                        item.item_img = allitemsListTemp.get(i).item_img
                        item.item_price = allitemsListTemp.get(i).item_price
                        item.item_ofr_price = allitemsListTemp.get(i).item_ofr_price
                        item.item_shown_status = allitemsListTemp.get(i).item_shown_status
                        item.item_created_date = allitemsListTemp.get(i).item_created_date
                        item.item_like_count = allitemsListTemp.get(i).item_like_count
                        allitemsList.add(item)
                                }
                            }
                        }
                    }

        allitems_recycle.adapter?.notifyDataSetChanged()
            }

    private fun get_intent() {
        allitemsList = intent.getParcelableArrayListExtra("AllItems")
        allitemsListTemp.addAll(allitemsList)

        if(allitemsList != null) {
            if(allitemsList.size > 0) {
                allitems_recycle.layoutManager = LinearLayoutManager(this,
                    RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager?
                val adapter =
                    AllItemsAdapter(
                        applicationContext,
                        allitemsList
                    )
                allitems_recycle.adapter = adapter
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