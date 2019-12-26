package com.tastycafe.mykotlinsample.Admin.AdminActivities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.Admin.AdminAdapters.HiddenItemsAdapter
import com.tastycafe.mykotlinsample.Admin.AdminInterfaces.Admin_Interface
import com.tastycafe.mykotlinsample.Admin.AdminModels.ItemDatasList
import com.tastycafe.mykotlinsample.Admin.AdminSupportClasses.RecyclerItemClickListenr
import com.tastycafe.mykotlinsample.Database.DBHelper
import com.tastycafe.mykotlinsample.R

class Admin_HiddenItems : AppCompatActivity(), View.OnClickListener {

    var hiddenitemList: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()
    internal lateinit var db: DBHelper
    var cateid: Int? = null
    lateinit var hiddenitem_recycle: RecyclerView
    lateinit var hidden_arrow_back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_hiddenitems)
        supportActionBar?.hide()

        db = DBHelper(applicationContext)
        init_values()
        get_intents()
            }

    private fun init_values() {
        hiddenitem_recycle = findViewById(R.id.hiddenitem_recycle)
        hidden_arrow_back = findViewById(R.id.hidden_arrow_back)
        hidden_arrow_back.setOnClickListener(this)
                }

    private fun get_intents() {
        hiddenitemList = intent.getParcelableArrayListExtra("hiddenItems")
        cateid = intent.getIntExtra("cateid", 0)
        show_datas()
            }

    private fun show_datas() {
        hiddenitem_recycle.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter =
            HiddenItemsAdapter(
                applicationContext,
                hiddenitemList
            )
        hiddenitem_recycle.adapter = adapter

        hiddenitem_recycle.addOnItemTouchListener(
            RecyclerItemClickListenr(this,
                hiddenitem_recycle,
                object :
                    RecyclerItemClickListenr.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {

                    }

                    override fun onItemLongClick(view: View?, position: Int) {
                        var itemid: Int = hiddenitemList[position].item_id
                        var itemname: String? = hiddenitemList[position].item_name
                        item_shown_dialog(itemid, itemname, position)
                            }
                        })
                    )
                }

    private fun item_shown_dialog(itemid: Int, itemname: String?, position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Item $itemname")
        builder.setMessage("Do you want to show this item?")

        builder.setPositiveButton("Yes"){dialogInterface, which ->
            dialogInterface.dismiss()
            db.updateShownItems("" + itemid, "1")
            db.close()

            hiddenitemList.removeAt(position)
            hiddenitem_recycle.adapter!!.notifyDataSetChanged()
                }

        builder.setNegativeButton("No"){dialogInterface, which ->
            dialogInterface.dismiss()
                    }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
                }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.hidden_arrow_back -> onBackPressed()
                }
            }

    override fun onBackPressed() {
        setResult(Admin_Interface.AHidden_back)
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
            }
}