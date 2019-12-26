package com.tastycafe.mykotlinsample.Common.CommonActivities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.tastycafe.mykotlinsample.Database.DBHelper
import com.tastycafe.mykotlinsample.Common.CommonModels.ProfileDatas
import com.tastycafe.mykotlinsample.R
import com.google.android.material.snackbar.Snackbar
import com.tastycafe.mykotlinsample.Users.UserActivities.UserDashboard
import kotlinx.android.synthetic.main.register.*
import kotlin.collections.ArrayList

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    internal lateinit var db: DBHelper
    internal var profile_datas:List<ProfileDatas> = ArrayList<ProfileDatas>()
    private val sharedPrefFile = "coffee_preference"

    lateinit var usernameView: EditText
    lateinit var passwordView: EditText
    lateinit var emailaddView: EditText
    lateinit var mobView: EditText
    lateinit var register_btn: Button
    lateinit var back_arrow: ImageView
    lateinit var login_view: TextView
    lateinit var forgot_view: TextView

    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        supportActionBar?.hide()

        db = DBHelper(this)

        init_views()

        sharedPref = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
                }

    private fun init_views() {
        usernameView = findViewById(R.id.username)
        passwordView = findViewById(R.id.password)
        emailaddView = findViewById(R.id.email)
        mobView   = findViewById(R.id.mobile)
        register_btn = findViewById(R.id.register_btn)
        back_arrow = findViewById(R.id.back_arrow)

        login_view = findViewById(R.id.login_view)
        forgot_view = findViewById(R.id.forgot_view)
        this.hideKeyboard(usernameView)

        back_arrow.setOnClickListener(this)
        login_view.setOnClickListener(this)
        forgot_view.setOnClickListener(this)
        register_btn.setOnClickListener(this)
                }

    private fun showSuccessAlert() {
        username.setText("")
        password.setText("")
        email.setText("")
        mobile.setText("")
        email.requestFocus()

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.register_success)
        builder.setMessage(R.string.welcome_reg)

        builder.setPositiveButton("Ok"){dialogInterface, which ->
            intent = Intent(this, UserDashboard::class.java)
            startActivityForResult(intent, 102)
            dialogInterface.dismiss()
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
            R.id.back_arrow -> onBackPressed()
            R.id.login_view -> move_login_activity()
            R.id.forgot_view -> move_forgot_activity()
            R.id.register_btn -> send_registration(v)
                }
            }

    private fun send_registration(v: View) {

        this.hideKeyboard(usernameView)

        val userStr: String = usernameView.text.toString()
        val passStr: String = passwordView.text.toString()
        val emailStr: String = emailaddView.text.toString()
        val mobileStr: String = mobView.text.toString()

        if(emailStr == null || emailStr.trim() == "") {
            enter_err_fun("Email", v)
            email.requestFocus()
            return
        }

        if(!isEmailValid(emailStr)) {
            enter_err_fun("valid email", v)
            email.requestFocus()
            return
        }

        if(passStr == null || passStr.trim() == "") {
            enter_err_fun("password", v)
            password.requestFocus()
            return
        }

        if(passStr.length < 8) {
            enter_err_fun("above 8 charaters", v)
            password.requestFocus()
            return
              }

        if(userStr == null || userStr.trim() == "") {
            Snackbar.make(v,"Please enter Username", Snackbar.LENGTH_LONG).show()
            enter_err_fun("Username", v)
            return
        }

        if(userStr.length < 3) {
            enter_err_fun("valid name", v)
            username.requestFocus()
            return
        }

        if(mobileStr == null || mobileStr.trim() == "") {
            enter_err_fun("mobile", v)
            mobile.requestFocus()
            return
        }

        if(mobileStr.length != 10) {
            enter_err_fun("valid mobile number", v)
            mobile.requestFocus()
            return
        }

        profile_datas = db.searchBEmail(emailStr)
        if(profile_datas.size > 0) {
            val errmsg: (String, View) -> String = {message, view -> "$message already registered"}
            errShown("Email id", v, errmsg)
            return
              }

        editor.putString("user_email", emailStr)
        editor.putString("user_pass", passStr)
        editor.putString("user_name", userStr)
        editor.putString("user_mobile", mobileStr)
        editor.commit()
        editor.apply()

        db.addUser(emailStr, passStr, userStr, mobileStr)
        db.close()

        showSuccessAlert()
            }

    fun enter_err_fun(message: String, view: View) {
        val errmsg: (String, View) -> String = {message, view ->
            resources.getString(R.string.please_enter) + " $message"}
        errShown(message, view, errmsg)
                }

    fun errShown(message: String, view:View, shown: (String, View) -> String) {
        val result = shown(message, view)
        Snackbar.make(view, result, Snackbar.LENGTH_SHORT).show()
                }

    private fun move_forgot_activity() {
        intent = Intent(applicationContext, ForgotActivity::class.java)
        startActivity(intent)
        overridePendingTransition(
            R.anim.slide_up,
            R.anim.no_animation
                        );
                }

    private fun move_login_activity() {
        intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(
            R.anim.slide_up,
            R.anim.no_animation
                        );
                }
}
