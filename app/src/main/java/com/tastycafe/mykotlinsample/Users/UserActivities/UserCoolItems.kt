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
import com.tastycafe.mykotlinsample.Users.UserAdapters.CoolItemsAdapter
import kotlinx.android.synthetic.main.user_coolitems.*
import kotlinx.android.synthetic.main.user_hotitems.*

class UserCoolItems : AppCompatActivity(), View.OnClickListener {

    var coolItems: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()
    var tempcoolItems:ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_coolitems)
        supportActionBar?.hide()

        view_init()
        get_Intents()
        recycler_listeners()

    }

    private fun recycler_listeners() {
        coolitems_recycle.addOnItemTouchListener(
            RecyclerItemClickListenr(applicationContext,
                coolitems_recycle,
                object :
                    RecyclerItemClickListenr.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        var itemid: Int = coolItems[position].item_id
                        var itemname: String? = coolItems[position].item_name
                        var itemimage: String? = coolItems[position].item_img
                        var itemcateid: String? = coolItems[position].cate_id
                        var itemprice: String? = coolItems[position].item_price
                        var itemofrprice: String? = coolItems[position].item_ofr_price
                        var itemlikecount:String? = coolItems[position].item_like_count

                        if(!itemofrprice.equals("00.00")) {
                            itemprice = itemofrprice
                        }

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
        cool_back.setOnClickListener(this)
        cool_searchedit.addTextChangedListener(object : TextWatcher {

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
        coolItems.clear()

        if(s != null) {
            if(s.equals("")) {
                coolItems.addAll(tempcoolItems)
            } else  {

                for(i in tempcoolItems.indices) {
                    if(tempcoolItems.get(i).item_name?.toLowerCase()!!.startsWith
                            ("" + str.toLowerCase()) ) {

                        var item = ItemDatasList()
                        item.item_id = tempcoolItems.get(i).item_id
                        item.cate_id = tempcoolItems.get(i).cate_id
                        item.item_name = tempcoolItems.get(i).item_name
                        item.item_img = tempcoolItems.get(i).item_img
                        item.item_price = tempcoolItems.get(i).item_price
                        item.item_ofr_price = tempcoolItems.get(i).item_ofr_price
                        item.item_like_count = tempcoolItems.get(i).item_like_count
                        item.item_shown_status = tempcoolItems.get(i).item_shown_status
                        item.item_created_date = tempcoolItems.get(i).item_created_date
                        coolItems.add(item)
                            }
                        }
                    }
                }

        coolitems_recycle.adapter?.notifyDataSetChanged()
    }

    private fun get_Intents() {
        coolItems = intent.getParcelableArrayListExtra("CoolItems")
        tempcoolItems.addAll(coolItems)

        if(coolItems != null) {
            if(coolItems.size > 0) {
                coolitems_recycle.layoutManager = LinearLayoutManager(this,
                    RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager?
                val adapter =
                    CoolItemsAdapter(
                        applicationContext,
                        coolItems
                    )
                coolitems_recycle.adapter = adapter
                    }
                }
            }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.cool_back -> onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
    }

}