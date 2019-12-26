package com.tastycafe.mykotlinsample.Admin.AdminActivities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.tastycafe.mykotlinsample.Admin.AdminInterfaces.Admin_Interface.Companion.IMAGE_PICK_CODE
import com.tastycafe.mykotlinsample.Admin.AdminInterfaces.Admin_Interface.Companion.PERMISSION_CODE
import kotlinx.android.synthetic.main.admin_addcategory.*
import kotlinx.android.synthetic.main.admin_updateitems.*
import java.text.SimpleDateFormat
import java.util.*

class Admin_Updated_Item : AppCompatActivity(), View.OnClickListener {

    var itemid: String = ""
    var itemname: String = ""
    var itemimage: String = ""
    var itemhotorcold : String = ""
    var itemcateid: String = ""
    var itemprice: String = ""
    var itemofrprice: String = ""
    var itemshownstatus: String = ""
    var itemcreatedate: String = ""

    var iDollorPrice = ""
    var iCentPrice = ""
    var iOfrDollorPrice = "00"
    var iOfrCentPrice = "00"

    internal lateinit var db: DBHelper
    lateinit var upditem_back: ImageView
    lateinit var updated_image: ImageView
    lateinit var upd_image: ImageView
    lateinit var upd_itemname: EditText
    lateinit var upd_price_dollor: EditText
    lateinit var upd_price_cents: EditText
    lateinit var upd_offer_dollor: EditText
    lateinit var upd_offer_cents: EditText
    lateinit var status_toggle: ToggleButton
    lateinit var hot_cold_toggle: ToggleButton
    lateinit var update_item_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_updateitems)
        supportActionBar ?.hide()

        db = DBHelper(this)
        get_Intents()
        set_values()
           }

    private fun set_values() {
        upditem_back = findViewById(R.id.upditem_back)
        updated_image = findViewById(R.id.updated_image)
        upd_image= findViewById(R.id.upd_image)
        upd_itemname = findViewById(R.id.upd_itemname)
        upd_price_dollor = findViewById(R.id.upd_price_dollor)
        upd_price_cents = findViewById(R.id.upd_price_cents)
        upd_offer_dollor = findViewById(R.id.upd_offer_dollor)
        upd_offer_cents = findViewById(R.id.upd_offer_cents)
        status_toggle = findViewById(R.id.status_toggle)
        hot_cold_toggle = findViewById(R.id.hot_cold_toggle)
        update_item_btn = findViewById(R.id.update_item_btn)

        upditem_back.setOnClickListener(this)
        update_item_btn.setOnClickListener(this)
        upd_image.setOnClickListener(this)

        val bitmap: Bitmap = BitmapFactory.decodeFile(itemimage)
        updated_image.setImageBitmap(bitmap)

        upd_itemname.setText(itemname)
        if(itemprice.trim().toString() != null ||
            itemname.trim().toString() != "") {
                var iPrice = itemprice.split(".")
                iDollorPrice = iPrice[0]
                iCentPrice = iPrice[1]

                upd_price_dollor.setText(iDollorPrice)
                upd_price_cents.setText(iCentPrice)
                    }

        if(itemofrprice.trim().toString() != null ||
                itemofrprice.trim().toString() != "") {
            var iOfrprice = itemofrprice.split(".")

            iOfrDollorPrice = iOfrprice[0]
            iOfrCentPrice = iOfrprice[1]

            upd_offer_dollor.setText(iOfrDollorPrice)
            upd_offer_cents.setText(iOfrCentPrice)
                    }

        if(itemshownstatus.equals("0")) {
            status_toggle.isChecked = false
                    } else {
            status_toggle.isChecked = true
                }

        if(itemhotorcold.equals("0")) {
            hot_cold_toggle.isChecked = false
                    } else {
            hot_cold_toggle.isChecked = true
                }

        status_toggle.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener {
            buttonView, isChecked ->
               if(isChecked) {
                   itemshownstatus = "1"
                    } else {
                    itemshownstatus = "0"
                        }
                    })

        hot_cold_toggle.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener {
                buttonView, isChecked ->
            if(isChecked) {
                itemhotorcold = "1"
                        } else {
                itemhotorcold = "0"
                        }
                    })

               }

    private fun get_Intents() {
        itemid = intent.getStringExtra("itemid")
        itemname = intent.getStringExtra("itemname")
        itemimage = intent.getStringExtra("itemimage")
        itemcateid = intent.getStringExtra("itemcateid")
        itemprice = intent.getStringExtra("itemprice")
        itemofrprice = intent.getStringExtra("itemofrprice")
        itemshownstatus = intent.getStringExtra("itemshownstatus")
        itemcreatedate = intent.getStringExtra("itemcreatedate")
        itemhotorcold = intent.getStringExtra("itemhotorcold")
            }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.upditem_back -> onBackPressed()
            R.id.update_item_btn -> validation(v)
            R.id.upd_image -> update_new_image(v)

                }
            }

    private fun update_new_image(v: View) {
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
                itemimage = cursor.getString(columnIndex)

                added_image.setImageBitmap(BitmapFactory.decodeFile(itemimage))
                cursor.close()
                        }
                    }
                }

    fun errshown(message: String, view: View, errmethod: (String, View) -> String) {
        val result = errmethod(message, view)
        Snackbar.make(view, result, Snackbar.LENGTH_SHORT).show()
                }

    fun err_enter_fun(message: String, view: View) {
        val errmethod: (String, View) -> String = {msg, view ->
            resources.getString(R.string.please_enter) + " $msg"
                    }
        errshown(message, view, errmethod)
                }

    private fun validation(v: View) {
        itemname = upd_itemname.text.trim().toString()
        iDollorPrice = upd_price_dollor.text.trim().toString()
        iCentPrice = upd_price_cents.text.trim().toString()
        iOfrDollorPrice = upd_offer_dollor.text.trim().toString()
        iOfrCentPrice = upd_offer_cents.text.trim().toString()

        if(iDollorPrice == null || iDollorPrice!!.trim() == "") {
            upd_price_dollor.requestFocus()
            err_enter_fun("price dollor", v)
            return
                }

        if((iDollorPrice + "." + iCentPrice).equals("00.00")) {
            upd_price_dollor.requestFocus()
            err_enter_fun("valid price", v)
            return
                }

        if(iDollorPrice.toString().toInt() > 0) {
            if(iDollorPrice.toString().length == 1) {
                iDollorPrice = "0" + iDollorPrice
                   }
                }

        if(iCentPrice == null || iCentPrice!!.trim() == "") {
            upd_price_cents.requestFocus()
            err_enter_fun("price cents", v)
            return
                }

        if(iOfrDollorPrice == null || iOfrDollorPrice!!.trim() == "") {
            iOfrDollorPrice = "00"
                }

        if(iOfrCentPrice == null || iOfrCentPrice!!.trim() == "") {
            iOfrCentPrice = "00"
                }

        db.updateItems("" + itemid, itemname, itemimage, iDollorPrice + "." + iCentPrice,
            iOfrDollorPrice + "." + iOfrCentPrice, itemshownstatus, itemhotorcold)
        db.close()

        setResult(6)
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
            }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down)
            }

}