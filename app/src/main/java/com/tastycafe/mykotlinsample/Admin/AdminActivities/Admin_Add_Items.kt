package com.tastycafe.mykotlinsample.Admin.AdminActivities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.tastycafe.mykotlinsample.Database.DBHelper
import com.tastycafe.mykotlinsample.R
import com.google.android.material.snackbar.Snackbar
import com.tastycafe.mykotlinsample.Admin.AdminInterfaces.Admin_Interface
import com.tastycafe.mykotlinsample.Admin.AdminInterfaces.Admin_Interface.Companion.IMAGE_PICK_CODE
import com.tastycafe.mykotlinsample.Admin.AdminInterfaces.Admin_Interface.Companion.PERMISSION_CODE
import kotlinx.android.synthetic.main.admin_addcategory.*
import kotlinx.android.synthetic.main.admin_additems.*
import java.text.SimpleDateFormat
import java.util.*

class Admin_Add_Items : AppCompatActivity(), View.OnClickListener, Admin_Interface {

    internal lateinit var db: DBHelper
    private var mediaPath: String = ""


    var cateid: Int? = null
    var catename: String? = null
    var str_Iname: String? = null
    var str_Pdollor: String? = null
    var str_Pcent: String? = null
    var str_Odollor:String? = null
    var str_Ocent: String? = null
    var str_hot_cold:String = "1"

    lateinit var additem_back: ImageView
    lateinit var added_image: ImageView
    lateinit var add_image: ImageView
    lateinit var itemname: EditText
    lateinit var price_dollor: EditText
    lateinit var price_cents:EditText
    lateinit var offer_dollor: EditText
    lateinit var offer_cents: EditText
    lateinit var create_item_btn: Button
    lateinit var hot_cold_toggle: ToggleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_additems)
        supportActionBar ?.hide()

        db = DBHelper(this)
        init_views()
        getIntents()
            }

    private fun init_views() {
        hot_cold_toggle = findViewById(R.id.hot_cold_toggle)
        additem_back = findViewById(R.id.additem_back)
        added_image = findViewById(R.id.added_image)
        add_image = findViewById(R.id.add_image)
        itemname = findViewById(R.id.itemname)
        price_dollor = findViewById(R.id.price_dollor)
        price_cents = findViewById(R.id.price_cents)
        offer_dollor = findViewById(R.id.offer_dollor)
        offer_cents = findViewById(R.id.offer_cents)
        create_item_btn = findViewById(R.id.create_item_btn)
        additem_back.setOnClickListener(this)
        add_image.setOnClickListener (this)
        create_item_btn.setOnClickListener(this)
        hot_cold_toggle.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener {
                buttonView, isChecked ->
            if(isChecked) {
                str_hot_cold = "1"
                    } else {
                str_hot_cold = "0"
                        }
                    })
                }

    private fun getIntents() {
        cateid = intent.getIntExtra("cateid", 0)
        catename = intent.getStringExtra("catename")
        cate_item_name.setText("Add " + catename)
                }

    private fun pickImageFromGallery() {
        val galleryIntent = Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent,
            IMAGE_PICK_CODE
                    )
                }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_CODE -> {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from pop up denied
                    Snackbar.make(add_image, "Permission Denied", Snackbar.LENGTH_LONG)
                        .show()
                        }
                    }
                }
            }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_PICK_CODE)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = contentResolver.query(contentURI!!, filePathColumn, null,
                    null, null)
                assert(cursor != null)
                cursor!!.moveToFirst()

                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                mediaPath = cursor.getString(columnIndex)

                added_image.setImageBitmap(BitmapFactory.decodeFile(mediaPath))
                cursor.close()
                    }
                }
            }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.additem_back -> onBackPressed()
            R.id.add_image -> add_your_image(v)
            R.id.create_item_btn -> create_your_item(v)
                }
            }

    fun errormsg(message: String, view: View, shown: (String, View) -> String) {
        val result =  shown(message, view)
        Snackbar.make(view, result, Snackbar.LENGTH_SHORT).show()
             }

    private fun create_your_item(v: View) {
        str_Iname = itemname.text.trim().toString()
        str_Pdollor = price_dollor.text.trim().toString()
        str_Pcent = price_cents.text.trim().toString()
        str_Odollor = offer_dollor.text.trim().toString()
        str_Ocent = offer_cents.text.trim().toString()

        if(mediaPath.trim() == "") {
            err_select_fun("Image", v)
            return
                }

        if(str_Iname == null || str_Iname.equals("") ) {
            itemname.requestFocus()
            err_enter_fun("Item name", v)
            return
                }

        if(str_Pdollor == null || str_Pdollor!!.trim() == "") {
            price_dollor.requestFocus()
            err_enter_fun("price dollor", v)
            return
                }

        if((str_Pdollor + "." + str_Pcent).equals("00.00")) {
            price_dollor.requestFocus()
            err_enter_fun("valid price", v)
            return
                }

        if(str_Pdollor.toString().toInt() > 0) {
            if(str_Pdollor.toString().length == 1) {
                str_Pdollor = "0" + str_Pdollor
                    }
                }

        if(str_Pcent == null || str_Pcent!!.trim() == "") {
            price_cents.requestFocus()
            err_enter_fun("price cents", v)
            return
                }

        if(str_Odollor == null || str_Odollor!!.trim() == "") {
            str_Odollor = "00"
                }

        if(str_Ocent == null || str_Ocent!!.trim() == "") {
            str_Ocent = "00"
                }

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        db.addItems("" + cateid, str_Iname!!, mediaPath, str_Pdollor + "." + str_Pcent ,
            str_Odollor + "." + str_Ocent, "1", currentDate, str_hot_cold)
        db.close()

        setResult(4)
        finish()
            }

    private fun err_select_fun(msg: String, view: View) {
        val errmethod: (String, View) -> String = {message, view ->
            resources.getString(R.string.please_select_an) + " $message"
                }
        errormsg(msg, view, errmethod)
            }

    private fun err_enter_fun(msg: String, view: View) {
        val errmethod: (String, View) -> String = {message, view ->
            resources.getString(R.string.please_enter) + " $message"}
        errormsg(msg, view, errmethod)
                }

    private fun add_your_image(v: View) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED) {
                // Permission Denied
                val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission,
                    PERMISSION_CODE
                )
            } else {
                //Permission already denied
                pickImageFromGallery()
                }
            } else {
                //Lower version
                pickImageFromGallery()
                    }
                }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
                }

}