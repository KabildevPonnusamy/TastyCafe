package com.tastycafe.mykotlinsample.Admin.AdminFragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.Admin.AdminActivities.*
import com.tastycafe.mykotlinsample.Admin.AdminAdapters.CategoryAdapter
import com.tastycafe.mykotlinsample.Admin.AdminModels.CategoryList
import com.tastycafe.mykotlinsample.Admin.AdminSupportClasses.RecyclerItemClickListenr
import com.tastycafe.mykotlinsample.Database.DBHelper
import com.tastycafe.mykotlinsample.R
import com.google.android.material.snackbar.Snackbar
import com.tastycafe.mykotlinsample.Admin.AdminActivities.Admin_Add_Category
import com.tastycafe.mykotlinsample.Admin.AdminActivities.Admin_HiddenCategories
import com.tastycafe.mykotlinsample.Admin.AdminActivities.Admin_ItemsList
import com.tastycafe.mykotlinsample.Admin.AdminActivities.Admin_UpdatedCategory
import kotlinx.android.synthetic.main.admin_fragcategory.*

class CategoryFragment : Fragment(), View.OnClickListener {

    var categorylist: ArrayList<CategoryList> = ArrayList<CategoryList>()
    internal lateinit var db: DBHelper

    lateinit var icon_add: ImageView
    lateinit var icon_hidden_cate: ImageView
    lateinit var bottom_layout: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view : View = inflater?.inflate(R.layout.admin_fragcategory, null)
        db = DBHelper(requireContext())

        icon_add = view.findViewById(R.id.icon_add)
        icon_hidden_cate = view.findViewById(R.id.icon_hidden_cate)
        bottom_layout = view.findViewById<LinearLayout>(R.id.bottom_layout)
        var recyclerView: RecyclerView = view.findViewById(R.id.caterecycle) as RecyclerView
        icon_hidden_cate.setOnClickListener(this)
        icon_add.setOnClickListener(this)
        get_categories(recyclerView)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dy > 0) {
                    bottom_layout.visibility = View.GONE
                         } else {
                    bottom_layout.visibility = View.VISIBLE
                        }
                super.onScrolled(recyclerView, dx, dy)
                        }
                    })

        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListenr(requireActivity(),
                recyclerView,
                object :
                    RecyclerItemClickListenr.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {

                        var cateid: Int = categorylist[position].cate_id
                        var catename: String? = categorylist[position].cate_name
                        var catestatus: String? = categorylist[position].cate_show_status
                        var cateimg: String? = categorylist[position].cate_img

                        Log.e("sample", "CateId: " + cateid)

                        val intent = Intent(
                            context,
                            Admin_ItemsList::class.java
                        )
                        intent.putExtra("cateid", cateid)
                        intent.putExtra("catename", "" + catename)
                        intent.putExtra("catestatus", "" + catestatus)
                        intent.putExtra("cateimage", cateimg)
                        startActivityForResult(intent, 10)
                        requireActivity().overridePendingTransition(
                            R.anim.slide_up,
                            R.anim.no_animation
                        )
                    }

                    override fun onItemLongClick(view: View?, position: Int) {
                        delete_category(position)
                    }
                })
        )

        return view
           }

    private fun delete_category(position: Int) {
        var cateid : Int = categorylist[position].cate_id
        var catename: String? = categorylist[position].cate_name
        var catestatus: String? = categorylist[position].cate_show_status
        var cateimg: String? = categorylist[position].cate_img

        Log.e("sample", "LongClick: " + cateid);

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Category $catename")
        builder.setMessage("Do you want to do operation?")

        builder.setPositiveButton("Delete"){dialogInterface, which ->
            dialogInterface.dismiss()
            db.deleteCategory("" + cateid)
            db.close()
            get_categories(caterecycle)
                }
        builder.setNegativeButton("Edit"){dialogInterface, which ->
            dialogInterface.dismiss()
            val intent = Intent(context, Admin_UpdatedCategory::class.java)
            intent.putExtra("cateid",cateid)
            intent.putExtra("catename",catename)
            intent.putExtra("catestatus",catestatus)
            intent.putExtra("cateimg",cateimg)
            startActivityForResult(intent, 9)
            requireActivity().overridePendingTransition(R.anim.slide_up, R.anim.no_animation)
                    }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
                }

    private fun get_categories(recyclerView: RecyclerView) {
        categorylist.clear()
        categorylist = db.getCategories() as ArrayList<CategoryList>
        db.close()

        if(categorylist != null) {
            if (categorylist.size > 0) {
                recyclerView.layoutManager = GridLayoutManager(this.context, 2)
                recyclerView.adapter =
                    CategoryAdapter(
                        requireContext(),
                        categorylist
                    )
                           }
                      }
                  }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 0) {
            if(resultCode == 1) {
                get_categories(caterecycle)
                        }
                    }

        if(requestCode == 9) {
            if(resultCode == 10) {
                get_categories(caterecycle)
                        }
                    }

        if(requestCode == 11) {
            if(resultCode == 12) {
                get_categories(caterecycle)
                        }
                    }
                }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.icon_hidden_cate -> get_hidded_categories(v)
            R.id.icon_add -> add_category(v)
                }
            }

    private fun add_category(v: View) {
        activity!!.intent = Intent(activity, Admin_Add_Category::class.java)
        startActivityForResult(activity!!.intent, 0)
        requireActivity().overridePendingTransition(
            R.anim.slide_up,
            R.anim.no_animation
                    )
                }

    private fun get_hidded_categories(v: View) {
        var hiddencategorylist: ArrayList<CategoryList> = ArrayList<CategoryList>()
        hiddencategorylist.clear()
        hiddencategorylist = db.getHiddenCategories() as ArrayList<CategoryList>
        db.close()

        if(hiddencategorylist.size > 0) {
            var intent = Intent(context, Admin_HiddenCategories::class.java)
            intent.putParcelableArrayListExtra("hiddenCates", hiddencategorylist)
            startActivityForResult(intent, 11)
            requireActivity().overridePendingTransition( R.anim.slide_up, R.anim.no_animation )
                } else {
            Snackbar.make(v, "No hidden categories found", Snackbar.LENGTH_LONG)
                .show()
            return
                }
            }
}