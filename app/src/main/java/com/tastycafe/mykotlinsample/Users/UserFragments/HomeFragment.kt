package com.tastycafe.mykotlinsample.Users.UserFragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tastycafe.mykotlinsample.Admin.AdminActivities.Admin_HiddenItems
import com.tastycafe.mykotlinsample.Admin.AdminModels.CategoryList
import com.tastycafe.mykotlinsample.Admin.AdminModels.ItemDatasList
import com.tastycafe.mykotlinsample.Admin.AdminSupportClasses.RecyclerItemClickListenr
import com.tastycafe.mykotlinsample.Database.DBHelper
import com.tastycafe.mykotlinsample.R
import com.tastycafe.mykotlinsample.Users.UserActivities.UserAllItems
import com.tastycafe.mykotlinsample.Users.UserActivities.UserAllOffers
import com.tastycafe.mykotlinsample.Users.UserAdapters.OfferedUserItemsAdapter
import com.tastycafe.mykotlinsample.Users.UserAdapters.UserCateAdapter
import com.tastycafe.mykotlinsample.Users.UserAdapters.UserItemsAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), View.OnClickListener {

    private val sharedPrefFile = "coffee_preference"
    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    var strName: String = ""
    var categorylist: ArrayList<CategoryList> = ArrayList<CategoryList>()
    var itemList: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()
    var offeredItemList: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()
    var allItemsList: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()

    internal lateinit var db: DBHelper
    var selectpos: Int = 0
    lateinit var adapter: UserCateAdapter
    lateinit var itemadapter : UserItemsAdapter
    lateinit var offersadapter: OfferedUserItemsAdapter
    var cid: Int = 0
    lateinit var intent: Intent


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        get_preferences()
        userName.setText(strName)
        show_categories()

        search_layout.setOnClickListener(this)
        offers_seeall.setOnClickListener(this)
        user_logout.setOnClickListener(this)

        get_OfferedItems()
            }

    private fun get_OfferedItems() {
        offeredItemList.clear()
        offeredItemList = db.getOfferedItems() as ArrayList<ItemDatasList>
        db.close()

        Log.e("sampleApp", "Size: " + offeredItemList.size)

        if(offeredItemList != null) {
            if(offeredItemList.size > 0) {
                offers_seeall.visibility = View.VISIBLE
                offers_recycle.layoutManager = LinearLayoutManager(
                    this.context, RecyclerView.HORIZONTAL,
                    false
                )

                offersadapter =
                    OfferedUserItemsAdapter(
                        requireContext(),
                        offeredItemList
                    )

                offers_recycle.adapter = offersadapter
                    } else {
                offers_seeall.visibility = View.GONE
                    }
                }

            }

    private fun show_categories() {

        show_category_datas()

        cate_recycle.addOnItemTouchListener(
            RecyclerItemClickListenr(requireActivity(),
                cate_recycle,
                object :
                    RecyclerItemClickListenr.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        adapter.select_pos = position
                        cate_recycle.adapter!!.notifyDataSetChanged()
                        get_item_datas(position)
                            }

                    override fun onItemLongClick(view: View?, position: Int) {
                    }
                })
            )
        }

    private fun get_item_datas(cid: Int) {
        var cateid: Int = cid + 1

        itemList.clear()
        itemList = db.getAllItems("" + cateid) as ArrayList<ItemDatasList>
        db.close()

        if(itemList != null) {
            if(itemList.size > 0) {
                no_items_layout.visibility = View.GONE
                cate_items_recycle.visibility = View.VISIBLE

                cate_items_recycle.layoutManager = LinearLayoutManager(
                    this.context, RecyclerView.HORIZONTAL,
                    false
                            )

                itemadapter =
                    UserItemsAdapter(
                        requireContext(),
                        itemList
                            )

                cate_items_recycle.adapter = itemadapter
                    } else {
                no_items_layout.visibility = View.VISIBLE
                cate_items_recycle.visibility = View.GONE
                    }
                }
            }

    private fun show_category_datas() {
        categorylist.clear()
        categorylist = db.getCategories() as ArrayList<CategoryList>
        db.close()

        if (categorylist != null) {
            if (categorylist.size > 0) {
                get_item_datas(cid)
                cate_recycle.layoutManager = LinearLayoutManager(
                    this.context, RecyclerView.HORIZONTAL,
                    false
                )

                adapter =
                    UserCateAdapter(
                        requireContext(),
                        categorylist,
                        selectpos
                    )

                cate_recycle.adapter = adapter
                    } else {

                    }
                }
            }

    private fun get_preferences() {
        db = DBHelper(requireContext())

        sharedPref = activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
        strName = sharedPref.getString("user_name", "").toString()
            }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.user_logout -> userLogout()
            R.id.offers_seeall -> seeAllOffers()
            R.id.search_layout -> getAllItems(v)
                }
            }

    private fun getAllItems(v: View) {
        allItemsList.clear();
        allItemsList = db.getCompleteItems() as ArrayList<ItemDatasList>

        if(allItemsList.size > 0) {
            intent = Intent(requireContext(), UserAllItems::class.java)
            intent.putParcelableArrayListExtra("AllItems", allItemsList)
            startActivityForResult(intent, 7)
            activity?.overridePendingTransition(
                R.anim.slide_up,
                R.anim.no_animation
                            )
                    } else {
            Snackbar.make(v, "No Items found", Snackbar.LENGTH_SHORT).show()
                }
            }

    private fun seeAllOffers() {
        intent = Intent(requireContext(), UserAllOffers::class.java)
        intent.putParcelableArrayListExtra("offeredItems", offeredItemList)
        startActivityForResult(intent, 7)
        activity?.overridePendingTransition(
            R.anim.slide_up,
            R.anim.no_animation
                    )
                }

    private fun userLogout() {
        editor!!.clear()
        editor!!.commit()
        activity?.finish()
            }
}
