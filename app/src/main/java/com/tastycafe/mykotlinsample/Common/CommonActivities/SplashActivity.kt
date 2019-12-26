package com.tastycafe.mykotlinsample.Common.CommonActivities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tastycafe.mykotlinsample.Admin.AdminActivities.Admin_Dashboard
import com.tastycafe.mykotlinsample.R
import com.tastycafe.mykotlinsample.Users.UserActivities.UserDashboard

class SplashActivity : AppCompatActivity() {

    private val sharedPrefFile = "coffee_preference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        supportActionBar?.hide()

        var sharedPref: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPref.edit()

        var email_str = sharedPref.getString("user_email", "")
        if(!email_str.equals("")) {

            if(email_str.equals("admin@admin.com") ) {
                Handler().postDelayed({
                    intent = Intent(applicationContext, Admin_Dashboard::class.java)
                    startActivity(intent)

                    overridePendingTransition(
                        R.anim.slide_up,
                        R.anim.no_animation
                    );

                }, 1000)
                    } else {
                Handler().postDelayed({
                    intent = Intent(applicationContext, UserDashboard::class.java)
                    startActivityForResult(intent, 103)

                    overridePendingTransition(
                        R.anim.slide_up,
                        R.anim.no_animation
                    );

                }, 1000)
                }

            Toast.makeText(applicationContext, "Already Logged in", Toast.LENGTH_SHORT).show()



            }

        /*Handler().postDelayed({
            intent = Intent(applicationContext, LoginActivity::class.java)
            finish()
        }, SPLASH_TIME_OUT)*/

        val button_click = findViewById<Button>(R.id.button_click);
        button_click.setOnClickListener() {
            intent = Intent(applicationContext, RegisterActivity::class.java);
            startActivity(intent)
            overridePendingTransition(
                R.anim.slide_up,
                R.anim.no_animation
                    );
                }
            }
        }