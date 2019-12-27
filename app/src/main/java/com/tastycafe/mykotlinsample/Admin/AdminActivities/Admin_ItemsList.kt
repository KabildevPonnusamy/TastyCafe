package com.tastycafe.mykotlinsample.Admin.AdminActivities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.Admin.AdminAdapters.ItemsAdapter
import com.tastycafe.mykotlinsample.Admin.AdminModels.ItemDatasList
import com.tastycafe.mykotlinsample.Admin.AdminSupportClasses.RecyclerItemClickListenr
import com.tastycafe.mykotlinsample.Database.DBHelper
import com.tastycafe.mykotlinsample.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.admin_fragcategory.*
import kotlinx.android.synthetic.main.admin_itemsact.*

class Admin_ItemsList : AppCompatActivity() , View.OnClickListener {

    internal lateinit var db: DBHelper
    var itemList: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()
    var hiddenitemList: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()

    var cateid: Int? = null
    var catename: String? = null
    var catestatus: String? = null
    var cateimage: String? = null

    lateinit var item_recycleView: RecyclerView
    lateinit var add_items: ImageView
    lateinit var arrow_back: ImageView
    lateinit var item_name: TextView
    lateinit var item_image: ImageView
    lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_itemsact)
        supportActionBar ?.hide()

        db = DBHelper(applicationContext)
        init_values()

        item_recycle.addOnItemTouchListener(
            RecyclerItemClickListenr(this,
                item_recycle,
                object :
                    RecyclerItemClickListenr.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {

                    }

                    override fun onItemLongClick(view: View?, position: Int) {
                        var itemid: Int = itemList[position].item_id
                        var itemname: String? = itemList[position].item_name
                        var itemimage: String? = itemList[position].item_img
                        var itemhotorcold: String? = itemList[position].item_hot_or_cold
                        var itemcateid: String? = itemList[position].cate_id
                        var itemprice: String? = itemList[position].item_price
                        var itemofrprice: String? = itemList[position].item_ofr_price
                        var itemshownstatus: String? = itemList[position].item_shown_status
                        var itemcreateddate: String? = itemList[position].item_created_date
                        var itemlikecount:String? = itemList[position].item_like_count

                        Log.e("sample", "LongClkItm: " + itemname);
                        delete_item(
                            itemid, itemname, itemimage, itemcateid, itemprice, itemofrprice,
                            itemshownstatus, itemcreateddate, itemhotorcold, itemlikecount
                                )
                            }
                        })
                    )

        get_intents()
        get_Items(item_recycle)
               }

    private fun init_values() {
        item_recycleView = findViewById(R.id.item_recycle)
        add_items = findViewById(R.id.add_items)
        arrow_back = findViewById(R.id.arrow_back)
        item_name = findViewById(R.id.item_name)
        item_image = findViewById(R.id.item_image)
        fab = findViewById(R.id.fab)
                }

    private fun delete_item(itemid: Int, itemname: String?, itemimage: String?, itemcateid: String?, itemprice: String?,
                            itemofrprice: String?, itemshownstatus: String?, itemcreatedate: String?,
                            itemhotorcold: String?, itemlikecount: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Item $itemname")
        builder.setMessage("Do you want to do operation?")

        builder.setPositiveButton("Delete"){dialogInterface, which ->
            dialogInterface.dismiss()
            db.deleteItems("" + itemid)
            db.close()
            get_Items(item_recycle)
                 }

        builder.setNegativeButton("Edit"){dialogInterface, which ->
            dialogInterface.dismiss()
            intent = Intent(applicationContext, Admin_Updated_Item::class.java)
            intent.putExtra("cateid", cateid)
            intent.putExtra("itemhotorcold", itemhotorcold)
            intent.putExtra("itemid", "" + itemid)
            intent.putExtra("itemname", itemname)
            intent.putExtra("itemimage", itemimage)
            intent.putExtra("itemcateid", itemcateid)
            intent.putExtra("itemprice", itemprice)
            intent.putExtra("itemofrprice", itemofrprice)
            intent.putExtra("itemshownstatus", itemshownstatus)
            intent.putExtra("itemcreatedate", itemcreatedate)
            intent.putExtra("itemlikecount", itemlikecount)

            startActivityForResult(intent, 5)
            overridePendingTransition(
                R.anim.slide_up,
                R.anim.no_animation
                    )
                 }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
                }

    private fun get_intents() {
        cateid = intent.getIntExtra("cateid", 0)
        catename = intent.getStringExtra("catename")
        catestatus = intent.getStringExtra("catestatus")
        cateimage = intent.getStringExtra("cateimage")
        item_name.setText(catename)
        val bitmap: Bitmap = BitmapFactory.decodeFile(cateimage)
        item_image!!.setImageBitmap(bitmap)

        add_items.setOnClickListener(this)
        fab.setOnClickListener(this)
        arrow_back.setOnClickListener(this)

        show_hidden_items()
               }

    private fun get_Items(itemRecycleview: RecyclerView) {
        itemList.clear()
        itemList = db.getAllItems("" + cateid) as ArrayList<ItemDatasList>
        db.close()

        itemRecycleview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter =
            ItemsAdapter(
                applicationContext,
                itemList
            )
        itemRecycleview.adapter = adapter
                }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 3) {
            if(resultCode == 4) {
                Log.e("sample", "Callback Done");
                get_Items(item_recycle)
                show_hidden_items()
                    }
                }

        if(requestCode == 5) {
            if(resultCode == 6) {
                get_Items(item_recycle)
                show_hidden_items()
                    }
                }

        if(requestCode == 7) {
            if(resultCode == 8) {
                get_Items(item_recycle)
                show_hidden_items()
                    }
                }
            }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
            }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.fab -> move_hidden_items()
            R.id.add_items -> move_to_add_items()
            R.id.arrow_back -> onBackPressed()
                }
            }

    private fun move_to_add_items() {
        intent = Intent(applicationContext, Admin_Add_Items::class.java)
        intent.putExtra("catename", catename)
        intent.putExtra("cateid", cateid)
        startActivityForResult(intent, 3)
        overridePendingTransition(
            R.anim.slide_up,
            R.anim.no_animation
                    )
                }

    private fun move_hidden_items() {
        intent = Intent(this, Admin_HiddenItems::class.java)
        intent.putParcelableArrayListExtra("hiddenItems", hiddenitemList)
        intent.putExtra("cateid", cateid)
        startActivityForResult(intent, 7)
        overridePendingTransition(
            R.anim.slide_up,
            R.anim.no_animation
                    )
            }

    private fun show_hidden_items() {
        hiddenitemList.clear()
        hiddenitemList = db.getHidenItems("" + cateid) as ArrayList<ItemDatasList>
        db.close()

        if(hiddenitemList.size > 0) {
            fab.show()
                  } else {
            fab.hide()
                }
            }
}