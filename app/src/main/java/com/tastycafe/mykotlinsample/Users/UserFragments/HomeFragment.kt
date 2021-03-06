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
import com.tastycafe.mykotlinsample.Users.UserActivities.*
import com.tastycafe.mykotlinsample.Users.UserAdapters.OfferedUserItemsAdapter
import com.tastycafe.mykotlinsample.Users.UserAdapters.UserCateAdapter
import com.tastycafe.mykotlinsample.Users.UserAdapters.UserItemsAdapter
import com.tastycafe.mykotlinsample.Users.UserModels.AddCartList
import kotlinx.android.synthetic.main.admin_fragcategory.*
import kotlinx.android.synthetic.main.admin_itemsact.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.user_item_details.*

class HomeFragment : Fragment(), View.OnClickListener {

    private val sharedPrefFile = "coffee_preference"
    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    var strName: String = ""
    var str_user_id: String = ""
    var categorylist: ArrayList<CategoryList> = ArrayList<CategoryList>()
    var itemList: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()
    var offeredItemList: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()
    var allItemsList: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()
    var hotItems: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()
    var coolItems: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()

    internal lateinit var db: DBHelper
    var selectpos: Int = 0
    lateinit var adapter: UserCateAdapter
    lateinit var itemadapter : UserItemsAdapter
    lateinit var offersadapter: OfferedUserItemsAdapter
    var cid: Int = 0
    lateinit var intent: Intent
    var mycartList: ArrayList<AddCartList> = ArrayList<AddCartList>()

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
        hot_items_layout.setOnClickListener(this)
        cool_items_layout.setOnClickListener(this)
        user_cart.setOnClickListener(this)

        get_OfferedItems()
        recycle_listeners()
        check_cartShown()
            }

    private fun check_cartShown() {
        mycartList.clear()
        mycartList = db.getCartDatas("" + str_user_id)
        db.close()

        Log.e("sampleApp", "SizeValue: " + mycartList.size)

        if(mycartList.size > 0) {
            user_cart.visibility = View.VISIBLE
        } else {
            user_cart.visibility = View.GONE
        }
    }

    private fun recycle_listeners() {
        cate_items_recycle.addOnItemTouchListener(
            RecyclerItemClickListenr(requireContext(),
                cate_items_recycle,
                object :
                    RecyclerItemClickListenr.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        var itemid: Int = itemList[position].item_id
                        var itemname: String? = itemList[position].item_name
                        var itemimage: String? = itemList[position].item_img
                        var itemcateid: String? = itemList[position].cate_id
                        var itemprice: String? = itemList[position].item_price
                        var itemofrprice: String? = itemList[position].item_ofr_price
                        var itemlikecount:String? = itemList[position].item_like_count

                        if(!itemofrprice.equals("00.00")) {
                            itemprice = itemofrprice
                        }

                        intent = Intent(requireContext(), UserItemDetails::class.java)
                        intent.putExtra("itemid", "" + itemid)
                        intent.putExtra("itemname", itemname)
                        intent.putExtra("itemimage", itemimage)
                        intent.putExtra("itemcateid", itemcateid)
                        intent.putExtra("itemprice", itemprice)
                        intent.putExtra("itemofrprice", itemofrprice)
                        intent.putExtra("itemlikecount", itemlikecount)

                        startActivityForResult(intent, 10)
                        activity?.overridePendingTransition(
                            R.anim.slide_up,
                            R.anim.no_animation
                                    )
                                }
                    override fun onItemLongClick(view: View?, position: Int) {
                              }
                        })
                    )

        offers_recycle.addOnItemTouchListener(
            RecyclerItemClickListenr(requireContext(),
                offers_recycle,
                object :
                    RecyclerItemClickListenr.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        var itemid: Int = offeredItemList[position].item_id
                        var itemname: String? = offeredItemList[position].item_name
                        var itemimage: String? = offeredItemList[position].item_img
                        var itemcateid: String? = offeredItemList[position].cate_id
                        var itemprice: String? = offeredItemList[position].item_price
                        var itemofrprice: String? = offeredItemList[position].item_ofr_price
                        var itemlikecount:String? = offeredItemList[position].item_like_count

                        if(!itemofrprice.equals("00.00")) {
                            itemprice = itemofrprice
                        }

                        intent = Intent(requireContext(), UserItemDetails::class.java)
                        intent.putExtra("itemid", "" + itemid)
                        intent.putExtra("itemname", itemname)
                        intent.putExtra("itemimage", itemimage)
                        intent.putExtra("itemcateid", itemcateid)
                        intent.putExtra("itemprice", itemprice)
                        intent.putExtra("itemofrprice", itemofrprice)
                        intent.putExtra("itemlikecount", itemlikecount)

                        startActivityForResult(intent, 11)
                        activity?.overridePendingTransition(
                            R.anim.slide_up,
                            R.anim.no_animation
                                    )
                                }
                    override fun onItemLongClick(view: View?, position: Int) {
                            }
                        })
                     )
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
        str_user_id = sharedPref.getString("user_email", "").toString()
            }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.user_logout -> userLogout()
            R.id.offers_seeall -> seeAllOffers()
            R.id.search_layout -> getAllItems(v)
            R.id.hot_items_layout -> getHotItems(v)
            R.id.cool_items_layout -> getCoolItems(v)
            R.id.user_cart -> getCart(v)
                }
            }

    private fun getCart(v: View) {
        mycartList.clear()
        mycartList = db.getCartDatas("" + str_user_id)
        db.close()

        send_cartPage()
    }

    private fun send_cartPage() {
        if(mycartList.size > 0) {
            intent = Intent(activity, UserMyCart::class.java)
            intent.putParcelableArrayListExtra("myCartList", mycartList)
            startActivityForResult(intent, 51)
            activity?.overridePendingTransition(
                R.anim.slide_up,
                R.anim.no_animation
            )
        } else {

        }
    }

    private fun getCoolItems(v: View) {
        coolItems.clear()
        coolItems = db.getCoolItems() as ArrayList<ItemDatasList>

        if(coolItems.size > 0) {
            intent = Intent(requireContext(), UserCoolItems::class.java)
            intent.putParcelableArrayListExtra("CoolItems", coolItems)
            startActivityForResult(intent, 9)
            activity?.overridePendingTransition(
                R.anim.slide_up,
                R.anim.no_animation
                    )
                } else {
            Snackbar.make(v, "No Items found", Snackbar.LENGTH_SHORT).show()
                }
            }

    private fun getHotItems(v: View) {
        hotItems.clear()
        hotItems = db.getHotItems() as ArrayList<ItemDatasList>

        if(hotItems.size > 0) {
            intent = Intent(requireContext(), UserHotItems::class.java)
            intent.putParcelableArrayListExtra("HotItems", hotItems)
            startActivityForResult(intent, 8)
            activity?.overridePendingTransition(
                R.anim.slide_up,
                R.anim.no_animation
                    )
                } else {
            Snackbar.make(v, "No Items found", Snackbar.LENGTH_SHORT).show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e("sampleApp", "ActivityResult")
        check_cartShown()
    }
}
