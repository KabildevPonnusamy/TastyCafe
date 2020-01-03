package com.tastycafe.mykotlinsample.Users.UserFragments


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tastycafe.mykotlinsample.Admin.AdminModels.ItemDatasList
import com.tastycafe.mykotlinsample.Admin.AdminModels.LikesList
import com.tastycafe.mykotlinsample.Database.DBHelper

import com.tastycafe.mykotlinsample.R
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment(), View.OnClickListener {

    private val sharedPrefFile = "coffee_preference"
    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    var strName: String = ""
    var strEmail: String = ""
    internal lateinit var db: DBHelper

    var likedDatas: ArrayList<LikesList> = ArrayList<LikesList>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init_views()
        get_preferences()
        get_Likes()
        get_Orders()

    }

    private fun init_views() {
        likes_layout.setOnClickListener(this)
        orders_layout.setOnClickListener(this)
    }

    private fun get_Orders() {

    }

    private fun get_Likes() {
        likedDatas.clear()
        likedDatas = db.getMyLikes( "" + strEmail )
        db.close()

        likes_count.setText("" + likedDatas.size)
    }

    private fun get_preferences() {
        db = DBHelper(requireContext())

        sharedPref = activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
        strName = sharedPref.getString("user_name", "").toString()
        strEmail = sharedPref.getString("user_email", "").toString()
        pro_name.setText(strName)
        pro_email.setText(strEmail)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.likes_layout -> moves_likes()
            R.id.orders_layout -> move_orders()
        }
    }

    private fun move_orders() {

    }

    private fun moves_likes() {

    }
}
