package com.tastycafe.mykotlinsample.Users.UserActivities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.Admin.AdminModels.ItemDatasList
import com.tastycafe.mykotlinsample.Admin.AdminModels.LikesList
import com.tastycafe.mykotlinsample.Admin.AdminSupportClasses.RecyclerItemClickListenr
import com.tastycafe.mykotlinsample.Database.DBHelper
import com.tastycafe.mykotlinsample.R
import com.tastycafe.mykotlinsample.Users.UserAdapters.SimilarAdapter
import com.tastycafe.mykotlinsample.Users.UserModels.AddCartList
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.user_all_offers.*
import kotlinx.android.synthetic.main.user_item_details.*

class UserItemDetails : AppCompatActivity(), View.OnClickListener {

    var str_fid: String = ""
    var str_fname: String = ""
    var str_fimage: String = ""
    var str_fcate_id: String = ""
    var str_fprice: String = ""
    var str_offprice: String = ""
    var str_ftotallikes: String = ""
    var str_flike_status: String = "0"
    var shownstatus: String? = null
    var enabledisable:String? = null

    var user_email: String? = null

    var similarItems: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()
    var myLikesList: ArrayList<LikesList> = ArrayList<LikesList>()
    var totalLikesList: ArrayList<LikesList> = ArrayList<LikesList>()
    var mycartList: ArrayList<AddCartList> = ArrayList<AddCartList>()

    internal lateinit var db: DBHelper
    lateinit var similaradapter: SimilarAdapter
    private val sharedPrefFile = "coffee_preference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_item_details)
        supportActionBar?.hide()

        shared_Preference()
        db = DBHelper(applicationContext)
        init_view()
        get_intents()
    }

    private fun shared_Preference() {
        var sharedPref: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPref.edit()
        user_email = sharedPref.getString("user_email", "")
    }

    private fun get_intents() {
        str_fid = intent.getStringExtra("itemid")
        str_fname = intent.getStringExtra("itemname")
        str_fimage = intent.getStringExtra("itemimage")
        str_fcate_id = intent.getStringExtra("itemcateid")
        str_fprice = intent.getStringExtra("itemprice")
        str_offprice = intent.getStringExtra("itemofrprice")
        shownstatus = intent.getStringExtra("shownstatus")

        if(shownstatus != null && shownstatus != "") {
            food_like.visibility = View.GONE
             } else {
            food_like.visibility = View.VISIBLE
          }

        show_views()
        recycler_listeners()
    }

    private fun check_cart_shown() {
        mycartList.clear()
        mycartList = db.getItemFromCart("" + user_email, str_fid)
        db.close()

        if(mycartList.size > 0) {
            viewname.isClickable=false
            viewname.setText("Added")
        } else {
            viewname.setText("Add to Cart")
            viewname.isClickable=true
        }
    }

    private fun show_views() {

        getTotalLikes()
        val bitmap: Bitmap = BitmapFactory.decodeFile(str_fimage)
        food_image.setImageBitmap(bitmap)

        food_name.setText(str_fname)

        if (!str_offprice.equals("00.00")) {
            food_price.setText("$ " + str_offprice)
        } else {
            food_price.setText("$ " + str_fprice)
        }

        getSimilarItems()
        getLikedorNot()
        check_cart_shown()
    }

    private fun getLikedorNot() {
        myLikesList.clear()
        myLikesList = db.getLike(str_fid, "" + user_email )
        db.close()

        if(myLikesList.size > 0) {
            str_flike_status = "1"
            food_like.setColorFilter(
                ContextCompat.getColor(applicationContext, R.color.red),
                android.graphics.PorterDuff.Mode.SRC_IN
                    );
                } else {
            str_flike_status = "0"
            food_like.setColorFilter(
                ContextCompat.getColor(applicationContext, R.color.bgnd_color),
                android.graphics.PorterDuff.Mode.SRC_IN
                    );
                }
    }

    private fun recycler_listeners() {
        similar_recycle.addOnItemTouchListener(
            RecyclerItemClickListenr(applicationContext,
                similar_recycle,
                object :
                    RecyclerItemClickListenr.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {

                        str_fid = "" + similarItems[position].item_id
                        str_fname = similarItems[position].item_name.toString()
                        str_fimage = similarItems[position].item_img.toString()
                        str_fcate_id = similarItems[position].cate_id.toString()
                        str_fprice = similarItems[position].item_price.toString()
                        str_offprice = similarItems[position].item_ofr_price.toString()
//                        str_ftotallikes = similarItems[position].item_like_count.toString()

                        show_views()
                            }
                    override fun onItemLongClick(view: View?, position: Int) {
                    }
                })
        )
    }

    private fun getSimilarItems() {
        similarItems.clear()
        similarItems = db.getAllItems(str_fcate_id) as ArrayList<ItemDatasList>
        db.close()

        var id: Int = str_fid.toInt()
        for (item in similarItems.indices) {
            if (id == similarItems.get(item).item_id) {
                id = item
            }
        }

        similarItems.removeAt(id)

        if (similarItems != null) {
            if (similarItems.size > 0) {
                similar_recycle.layoutManager = LinearLayoutManager(
                    applicationContext, RecyclerView.HORIZONTAL,
                    false
                )

                similaradapter =
                    SimilarAdapter(
                        applicationContext,
                        similarItems
                    )

                similar_recycle.adapter = similaradapter
            }
        }

        similar_recycle.adapter?.notifyDataSetChanged()
    }

    private fun init_view() {
        food_back.setOnClickListener(this)
        food_like.setOnClickListener(this)
        viewname.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.food_back -> onBackPressed()
            R.id.food_like -> update_like(v)
            R.id.viewname -> addtoCart(v)
        }
    }

    private fun addtoCart(v: View) {
        db.addTocart(str_fcate_id, str_fid, str_fname, str_fprice, str_fimage, "1",
            "" + user_email);
        db.close()

        check_cart_shown()
    }

    private fun update_like(v: View) {
        if (str_flike_status.equals("0")) {
            str_flike_status = "1"
            food_like.setColorFilter(
                ContextCompat.getColor(applicationContext, R.color.red),
                android.graphics.PorterDuff.Mode.SRC_IN
            );
            addLike()

        } else {
            str_flike_status = "0"
            food_like.setColorFilter(
                ContextCompat.getColor(applicationContext, R.color.bgnd_color),
                android.graphics.PorterDuff.Mode.SRC_IN
            );
            deleteLike()
        }
    }

    private fun deleteLike() {
        db.deleteLikes(str_fid,"" + user_email)
        db.close()
        getTotalLikes()
    }

    private fun addLike() {
        db.addLikes(str_fid, str_fcate_id, "" + user_email)
        db.close()
        getTotalLikes()
    }

    private fun getTotalLikes() {
        totalLikesList.clear()
        totalLikesList = db.totalLike(str_fid)
        db.close()

        if(totalLikesList.size > 0) {
            str_ftotallikes = "" + totalLikesList.size
                } else {
            str_ftotallikes = "0"
            }
        food_like_count.setText(str_ftotallikes)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(12)
        finish()
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }
}