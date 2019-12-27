package com.tastycafe.mykotlinsample.Users.UserActivities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.Admin.AdminModels.ItemDatasList
import com.tastycafe.mykotlinsample.Database.DBHelper
import com.tastycafe.mykotlinsample.R
import com.tastycafe.mykotlinsample.Users.UserAdapters.SimilarAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.user_item_details.*

class UserItemDetails : AppCompatActivity(), View.OnClickListener {

    var str_fid: String = ""
    var str_fname: String = ""
    var str_fimage: String = ""
    var str_fcate_id: String = ""
    var str_fprice:String = ""
    var str_offprice:String = ""
    var str_ftotallikes: String = ""
    var str_flike_status: String = "0"

    var similarItems: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()
    internal lateinit var db: DBHelper
    lateinit var similaradapter: SimilarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_item_details)
        supportActionBar?.hide()

        db = DBHelper(applicationContext)
        init_view()
        get_intents()
            }

    private fun get_intents() {
        str_fid = intent.getStringExtra("itemid")
        str_fname = intent.getStringExtra("itemname")
        str_fimage = intent.getStringExtra("itemimage")
        str_fcate_id = intent.getStringExtra("itemcateid")
        str_fprice = intent.getStringExtra("itemprice")
        str_offprice = intent.getStringExtra("itemofrprice")
        str_ftotallikes = intent.getStringExtra("itemlikecount")

        if(str_ftotallikes == null || str_ftotallikes.equals("")) {
            str_ftotallikes = "0"
                    }

        val bitmap: Bitmap = BitmapFactory.decodeFile(str_fimage)
        food_image.setImageBitmap(bitmap)

        food_name.setText(str_fname)
        food_like_count.setText(str_ftotallikes)

        if(!str_offprice.equals("00.00")) {
            food_price.setText("$ " + str_offprice)
                    } else {
            food_price.setText("$ " + str_fprice)
                }

        getSimilarItems()
            }

    private fun getSimilarItems() {
        similarItems.clear()
        similarItems = db.getAllItems(str_fcate_id) as ArrayList<ItemDatasList>
        db.close()

        if(similarItems != null) {
            if(similarItems.size > 0) {
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
            }

    private fun init_view() {
        food_back.setOnClickListener(this)
        food_like.setOnClickListener(this)
        addcart_layout.setOnClickListener(this)
            }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.food_back -> onBackPressed()
            R.id.food_like -> update_like(v)
            R.id.addcart_layout -> addtoCart(v)
                }
            }

    private fun addtoCart(v: View) {

            }

    private fun update_like(v: View) {
        if(str_flike_status.equals("0")) {
            str_flike_status = "1"
            food_like.setColorFilter(ContextCompat.getColor(applicationContext, R.color.red),
                android.graphics.PorterDuff.Mode.SRC_IN);
                    } else {
            str_flike_status = "0"
            food_like.setColorFilter(ContextCompat.getColor(applicationContext, R.color.bgnd_color),
                android.graphics.PorterDuff.Mode.SRC_IN);
                }
          }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
    }
}