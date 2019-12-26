package com.tastycafe.mykotlinsample.Users.UserActivities

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tastycafe.mykotlinsample.R
import com.tastycafe.mykotlinsample.Users.UserFragments.FavoFragment
import com.tastycafe.mykotlinsample.Users.UserFragments.HistoryFragment
import com.tastycafe.mykotlinsample.Users.UserFragments.HomeFragment
import com.tastycafe.mykotlinsample.Users.UserFragments.ProfileFragment

class UserDashboard : AppCompatActivity() {

    lateinit var bottom_navigation: BottomNavigationView

    lateinit var homeFragment: HomeFragment
    lateinit var favoFragment: FavoFragment
    lateinit var historyFragment: HistoryFragment
    lateinit var profileFragment: ProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_dashboard)
        supportActionBar ?.hide()

            init_views()
            show_home_fragment()

                }

    private fun show_home_fragment() {
        homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                    }

    private fun init_views() {
        bottom_navigation = findViewById(R.id.bottom_navigation)
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.menu_home -> {
                    homeFragment = HomeFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, homeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                            }
                R.id.menu_favourite -> {
                    favoFragment = FavoFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, favoFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                            }
                R.id.menu_profile -> {
                    profileFragment = ProfileFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, profileFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                            }
                R.id.menu_history -> {
                    historyFragment = HistoryFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, historyFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
                            }

                    }
            true
                }
            }
}

