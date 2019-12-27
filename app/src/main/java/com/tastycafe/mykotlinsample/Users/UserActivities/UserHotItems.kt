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
import com.tastycafe.mykotlinsample.Users.UserAdapters.HotItemsAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.user_all_items.*
import kotlinx.android.synthetic.main.user_hotitems.*

class UserHotItems: AppCompatActivity(), View.OnClickListener {

    var hotitemsList: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()
    var hotitemsListTemp: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_hotitems)
        supportActionBar?.hide()

        view_init()
        get_intent()
        recycle_listeners()

            }

    private fun recycle_listeners() {
        hotitems_recycle.addOnItemTouchListener(
            RecyclerItemClickListenr(applicationContext,
                hotitems_recycle,
                object :
                    RecyclerItemClickListenr.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        var itemid: Int = hotitemsList[position].item_id
                        var itemname: String? = hotitemsList[position].item_name
                        var itemimage: String? = hotitemsList[position].item_img
                        var itemcateid: String? = hotitemsList[position].cate_id
                        var itemprice: String? = hotitemsList[position].item_price
                        var itemofrprice: String? = hotitemsList[position].item_ofr_price
                        var itemlikecount:String? = hotitemsList[position].item_like_count

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

    private fun get_intent() {
        hotitemsList = intent.getParcelableArrayListExtra("HotItems")
        hotitemsListTemp.addAll(hotitemsList)

        if(hotitemsList != null) {
            if(hotitemsList.size > 0) {
                hotitems_recycle.layoutManager = LinearLayoutManager(this,
                    RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager?
                val adapter =
                    HotItemsAdapter(
                        applicationContext,
                        hotitemsList
                    )
                hotitems_recycle.adapter = adapter
                    }
                }
            }

    private fun view_init() {
        hot_back.setOnClickListener(this)
        hot_searchedit.addTextChangedListener(object : TextWatcher {

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
        hotitemsList.clear()

        if(s != null) {
            if(s.equals("")) {
                hotitemsList.addAll(hotitemsListTemp)
            } else  {

                for(i in hotitemsListTemp.indices) {
                    if(hotitemsListTemp.get(i).item_name?.toLowerCase()!!.startsWith
                            ("" + str.toLowerCase()) ) {

                        var item = ItemDatasList()
                        item.item_id = hotitemsListTemp.get(i).item_id
                        item.cate_id = hotitemsListTemp.get(i).cate_id
                        item.item_name = hotitemsListTemp.get(i).item_name
                        item.item_img = hotitemsListTemp.get(i).item_img
                        item.item_price = hotitemsListTemp.get(i).item_price
                        item.item_ofr_price = hotitemsListTemp.get(i).item_ofr_price
                        item.item_like_count = hotitemsListTemp.get(i).item_like_count
                        item.item_shown_status = hotitemsListTemp.get(i).item_shown_status
                        item.item_created_date = hotitemsListTemp.get(i).item_created_date
                        hotitemsList.add(item)
                            }
                        }
                    }
                }

        hotitems_recycle.adapter?.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.hot_back -> onBackPressed()
                }
            }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
            }
}