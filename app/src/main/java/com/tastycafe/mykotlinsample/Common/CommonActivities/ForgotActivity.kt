package com.tastycafe.mykotlinsample.Common.CommonActivities

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.tastycafe.mykotlinsample.Database.DBHelper
import com.tastycafe.mykotlinsample.Common.CommonModels.ProfileDatas
import com.tastycafe.mykotlinsample.R
import com.tastycafe.mykotlinsample.SupportClasses.SupportFunctions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.forgot.*
import kotlin.collections.ArrayList

class ForgotActivity : AppCompatActivity (), View.OnClickListener {

    internal lateinit var db: DBHelper
    internal var profile_datas:List<ProfileDatas> = ArrayList<ProfileDatas>()
    lateinit var forgotEmail: EditText
    lateinit var send_btn: Button
    lateinit var back_arrow: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot)
        supportActionBar ?.hide()

        db = DBHelper(this)

        forgotEmail = findViewById(R.id.forgotEmail)
        send_btn = findViewById(R.id.send_btn)
        back_arrow = findViewById(R.id.back_arrow)
        back_arrow.setOnClickListener(this)
        send_btn.setOnClickListener(this)
        this.hideKeyboard(forgotEmail)
                }

    private fun showUpdatedAlert(valueTwo: Int, name: String?) { // 659688
        forgotEmail.setText("")

        forgotEmail.requestFocus()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Welcome $name")
        builder.setMessage("Please use $valueTwo for temperuary password")

        builder.setPositiveButton("Ok"){dialogInterface, which ->
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
            R.id.send_btn -> validate_send(v)
                }
            }

    fun errorShown(message: String, view: View, shown: (String, View) -> String) {
        val result = shown(message, view)
        Snackbar.make(view, result, Snackbar.LENGTH_SHORT).show()
            }

    private fun validate_send(v: View) {

        this.hideKeyboard(forgotEmail)

        var mailstr: String = forgotEmail.text.toString()
        if(mailstr == null || mailstr.trim() == "") {
            val errmsg: (String, View) -> String = { message, view ->
            resources.getString(R.string.please_enter) + " $message"
                    }
            errorShown("your email", v, errmsg)
            forgotEmail.requestFocus()
            return
                }

        if(!isEmailValid(mailstr)) {
            val errmsg: (String, View) -> String = {message, view ->
                resources.getString(R.string.please_enter) + " $message"
                    }
            errorShown(" valid email" , v, errmsg)
            forgotEmail.requestFocus()
            return
                }

        profile_datas = db.searchBEmail(mailstr)
        if(profile_datas.size > 0) {

            val rNum =
                SupportFunctions()
            var valueTwo = rNum.getRandomNumber()

            db.updateUser(mailstr, "" + valueTwo)
            db.close()

            var userName: String? = ""

            for (obj in profile_datas) {
                userName = obj.name
                    }

            showUpdatedAlert(valueTwo, userName)

        } else {
            val errmsg: (String, View) -> String = {message, view ->
                "$message not registered"
                        }
            errorShown("Email id", v, errmsg)
            return
                }
            }
}