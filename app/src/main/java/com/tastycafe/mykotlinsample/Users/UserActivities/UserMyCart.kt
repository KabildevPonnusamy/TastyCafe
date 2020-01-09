package com.tastycafe.mykotlinsample.Users.UserActivities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tastycafe.mykotlinsample.R
import com.tastycafe.mykotlinsample.Users.UserModels.AddCartList
import kotlinx.android.synthetic.main.user_addcart.*

class UserMyCart : AppCompatActivity(), View.OnClickListener {

    var mycartList: ArrayList<AddCartList> = ArrayList<AddCartList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_addcart)
        supportActionBar?.hide()

        init_views()
        getIntents()
    }

    private fun getIntents() {
        mycartList = intent.getParcelableArrayListExtra("myCartList")
        if(mycartList != null) {
            if(mycartList.size > 0) {

            }
        }
    }

    private fun init_views() {
        mycart_back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mycart_back -> onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
    }
}