package com.tastycafe.mykotlinsample.Users.UserFragments


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.Admin.AdminModels.CategoryList
import com.tastycafe.mykotlinsample.Admin.AdminModels.ItemDatasList
import com.tastycafe.mykotlinsample.Admin.AdminModels.LikesList
import com.tastycafe.mykotlinsample.Admin.AdminSupportClasses.RecyclerItemClickListenr
import com.tastycafe.mykotlinsample.Database.DBHelper
import com.tastycafe.mykotlinsample.R
import com.tastycafe.mykotlinsample.Users.UserActivities.UserItemDetails
import com.tastycafe.mykotlinsample.Users.UserAdapters.LikesAdapter
import kotlinx.android.synthetic.main.fragment_favo.*

class FavoFragment : Fragment() {

    private val sharedPrefFile = "coffee_preference"
    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    var strName: String = ""
    var user_email: String? = null

    internal lateinit var db: DBHelper
    var likedDatas: ArrayList<LikesList> = ArrayList<LikesList>()
    var myLikeditems: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()
    var onemyLikeditems: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()
    var cateList:ArrayList<CategoryList> = ArrayList<CategoryList>()
    lateinit var intent: Intent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        get_preferences()
    }


    private fun get_preferences() {
        db = DBHelper(requireContext())

        sharedPref = activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
        strName = sharedPref.getString("user_name", "").toString()
        user_email = sharedPref.getString("user_email", "")
        get_my_Favourites()
    }

    private fun get_my_Favourites() {
        likedDatas.clear()
        likedDatas = db.getMyLikes( "" + user_email )
        db.close()

        myLikeditems.clear()
        for(i in likedDatas.indices) {
            onemyLikeditems = db.getSingleItem("" + likedDatas.get(i).like_id)
            myLikeditems.addAll(onemyLikeditems)
        }

        for(pos in myLikeditems.indices) {
            cateList.clear()
            cateList = db.getthisCategories("" + myLikeditems.get(pos).cate_id)
            myLikeditems.get(pos).cate_id = cateList.get(0).cate_name
        }

        if(myLikeditems != null) {
            if(myLikeditems.size > 0) {
                noresult_layout.visibility = View.GONE
                resultLayout.visibility = View.VISIBLE

                favo_recycle.layoutManager = LinearLayoutManager(requireContext(),
                    RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager?
                val adapter =
                    LikesAdapter(
                        requireContext(),
                        myLikeditems)
                    {
                            position ->

                        var item_id: Int = myLikeditems.get(position).item_id

                        db.deleteLikes("" + item_id ,"" + user_email)
                        db.close()

                        myLikeditems.removeAt(position)
                        favo_recycle.adapter?.notifyDataSetChanged()

                        if(myLikeditems.size == 0) {
                            noresult_layout.visibility = View.VISIBLE
                            resultLayout.visibility = View.GONE
                        }
                    }
                favo_recycle.adapter = adapter
            } else {
                noresult_layout.visibility = View.VISIBLE
                resultLayout.visibility = View.GONE
            }
        }
        listerners()
    }

    private fun listerners() {
        favo_recycle.addOnItemTouchListener(
            RecyclerItemClickListenr(requireContext(),
                favo_recycle,
                object :
                    RecyclerItemClickListenr.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        var itemid: Int = myLikeditems[position].item_id
                        var itemname: String? = myLikeditems[position].item_name
                        var itemimage: String? = myLikeditems[position].item_img
                        var itemcateid: String? = likedDatas[position].cate_id
                        var itemprice: String? = myLikeditems[position].item_price
                        var itemofrprice: String? = myLikeditems[position].item_ofr_price
                        var itemlikecount:String? = myLikeditems[position].item_like_count

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
                        intent.putExtra("shownstatus", "1")

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
}
