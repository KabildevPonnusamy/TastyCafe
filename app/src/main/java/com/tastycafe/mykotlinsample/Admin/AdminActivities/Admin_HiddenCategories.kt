package com.tastycafe.mykotlinsample.Admin.AdminActivities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.Admin.AdminAdapters.CategoryAdapter
import com.tastycafe.mykotlinsample.Admin.AdminModels.CategoryList
import com.tastycafe.mykotlinsample.Admin.AdminSupportClasses.RecyclerItemClickListenr
import com.tastycafe.mykotlinsample.Database.DBHelper
import com.tastycafe.mykotlinsample.R
import kotlinx.android.synthetic.main.admin_hiddencategories.*
import kotlinx.android.synthetic.main.admin_hiddenitems.*

class Admin_HiddenCategories : AppCompatActivity(), View.OnClickListener {

    internal lateinit var db: DBHelper
    var hiddencategorylist: ArrayList<CategoryList> = ArrayList<CategoryList>()
    lateinit var hiddencate_recycle: RecyclerView
    lateinit var hidden_cate_back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_hiddencategories)
        supportActionBar ?.hide()

        db = DBHelper(applicationContext)
        view_inits()
        get_intents()
            }

    private fun view_inits() {
        hiddencate_recycle = findViewById(R.id.hiddencate_recycle)
        hidden_cate_back = findViewById(R.id.hidden_cate_back)
        hidden_cate_back.setOnClickListener(this)
            }

    private fun get_intents() {
        hiddencategorylist = intent.getParcelableArrayListExtra("hiddenCates")
        show_datas()
            }

    private fun show_datas() {
        hiddencate_recycle.layoutManager = GridLayoutManager(this.applicationContext, 2)
        hiddencate_recycle.adapter = CategoryAdapter(
                applicationContext, hiddencategorylist
            )

        hiddencate_recycle.addOnItemTouchListener(
            RecyclerItemClickListenr(this,
                hiddencate_recycle,
                object :
                    RecyclerItemClickListenr.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                    }

                    override fun onItemLongClick(view: View?, position: Int) {
                        var cateid: Int = hiddencategorylist[position].cate_id
                        var catename: String? = hiddencategorylist[position].cate_name
                        cate_shown_dialog(cateid, catename, position)
                                    }
                                })
                            )
                        }

    private fun cate_shown_dialog(cateid: Int, catename: String?, position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Show $catename?")
        builder.setMessage("Do you want to show this category?")

        builder.setPositiveButton("Yes"){dialogInterface, which ->
            dialogInterface.dismiss()
            db.updateShownCategories("" + cateid, "1")
            db.close()

            hiddencategorylist.removeAt(position)
            hiddencate_recycle.adapter!!.notifyDataSetChanged()
                }

        builder.setNegativeButton("No"){dialogInterface, which ->
            dialogInterface.dismiss()
                }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
            }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.hidden_cate_back -> onBackPressed()
                }
            }

    override fun onBackPressed() {
        setResult(12)
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down)
            }
}