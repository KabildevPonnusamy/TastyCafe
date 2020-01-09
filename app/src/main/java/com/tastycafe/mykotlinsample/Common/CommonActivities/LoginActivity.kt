package com.tastycafe.mykotlinsample.Common.CommonActivities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.tastycafe.mykotlinsample.Admin.AdminActivities.Admin_Dashboard
import com.tastycafe.mykotlinsample.Database.DBHelper
import com.tastycafe.mykotlinsample.Common.CommonModels.ProfileDatas
import com.tastycafe.mykotlinsample.R
import com.google.android.material.snackbar.Snackbar
import com.tastycafe.mykotlinsample.Users.UserActivities.UserDashboard
import kotlinx.android.synthetic.main.login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    internal lateinit var db: DBHelper
    internal var profile_datas:List<ProfileDatas> = ArrayList<ProfileDatas>()
    private val sharedPrefFile = "coffee_preference"

    lateinit var emailView: EditText
    lateinit var passwordView: EditText
    lateinit var img_back_arrow: ImageView
    lateinit var login_btn: Button

    lateinit var sharedPref: SharedPreferences
    lateinit var editor : SharedPreferences.Editor
    lateinit var register_view: TextView
    lateinit var forgot_view: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        supportActionBar ?.hide()

        db = DBHelper(this)
        sharedPref = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        editor = sharedPref.edit()

        view_init()
            }

    private fun view_init() {
        emailView = findViewById(R.id.emailView)
        passwordView = findViewById(R.id.passwordView)
        img_back_arrow = findViewById<ImageView>(R.id.img_back_arrow)
        login_btn = findViewById(R.id.login_btn)
        register_view = findViewById(R.id.register_view)
        forgot_view = findViewById(R.id.forgot_view)
        register_view.setOnClickListener(this)
        forgot_view.setOnClickListener(this)
        img_back_arrow.setOnClickListener(this)
        login_btn.setOnClickListener(this)
        this.hideKeyboard(emailView)
            }

    private fun showSuccessAlert() {
        emailView.setText("")
        passwordView.setText("")
        emailView.requestFocus()

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.login_success)
        builder.setMessage(R.string.welcome_login_msg)

        builder.setPositiveButton("Ok"){dialogInterface, which ->
            dialogInterface.dismiss()
            intent = Intent(this, UserDashboard::class.java)
            startActivityForResult(intent, 51)
            overridePendingTransition(
                R.anim.slide_up,
                R.anim.no_animation
                        );
                    }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
                }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                }

    fun Activity.hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
            }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.img_back_arrow -> onBackPressed()
            R.id.login_btn -> validate_login(v)
            R.id.register_view -> move_register_activity()
            R.id.forgot_view -> move_forgot_activity()
                }
            }

    private fun move_forgot_activity() {
        intent = Intent(applicationContext, ForgotActivity::class.java)
        startActivity(intent)
        overridePendingTransition(
            R.anim.slide_up,
            R.anim.no_animation
        );
    }

    private fun move_register_activity() {
        intent = Intent(applicationContext, RegisterActivity::class.java)
        startActivity(intent)
        overridePendingTransition(
            R.anim.slide_up,
            R.anim.no_animation
        );
    }

    private fun validate_login(v: View) {
        this.hideKeyboard(emailView)

        val emailstr = emailView.text.toString()
        val passstr = passwordView.text.toString()

        if(emailstr == null || emailstr.trim() == "") {
            val errmsg: (String, View) -> String = {message, view ->
                resources.getString(R.string.please_enter) + " $message"
                    }
            errshown("Email", v, errmsg)
            emailView.requestFocus()
            return
                }

        if(!isEmailValid(emailstr)) {
            val errmsg: (String, View) -> String = {
                message, view -> resources.getString(R.string.please_enter) + " $message"
                    }
            errshown("valid email", v, errmsg)
            emailView.requestFocus()
            return
                }

        if(passstr == null || passstr.trim() == "") {
            val errmsg: (String, View) -> String = {
                    message, view -> resources.getString(R.string.please_enter) + " $message"
                    }
            errshown("password", v, errmsg)
            passwordView.requestFocus()
            return
        }
        if (emailstr.equals("admin@admin.com") && passstr.equals("admin")) {
            emailView.setText("")
            passwordView.setText("")
            emailView.requestFocus()

            editor.putString("user_email", emailstr)
            editor.putString("user_pass", passstr)
            editor.putString("login_status", "0")
            editor.apply()
            editor.commit()

            intent = Intent(this, Admin_Dashboard::class.java)
            startActivityForResult(intent, 51)
            overridePendingTransition(
                R.anim.slide_up,
                R.anim.no_animation
            )

        } else {

            profile_datas = db.getMyUser(emailstr, passstr)
            db.close()

            if(profile_datas.size > 0) {
                editor.putString("user_email", profile_datas.get(0).email)
                editor.putString("user_pass", profile_datas.get(0).password)
                editor.putString("user_name", profile_datas.get(0).name)
                editor.putString("user_mobile", profile_datas.get(0).mobile)
                editor.putString("login_status", "0")
                editor.apply()
                editor.commit()

                showSuccessAlert()
            } else {
                val errmsg: (String, View) -> String = {message, view ->
                    "please check your $message"}
                errshown("login credentials", v, errmsg)
                return
                    }
                }
            }

    fun errshown(message: String, view: View, shown: (String, View) -> String) {
        val result = shown(message, view)
        Snackbar.make(view, result, Snackbar.LENGTH_SHORT).show()
                }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 51) {
          if(resultCode == 52) {
              finish()
          } else {

          }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}