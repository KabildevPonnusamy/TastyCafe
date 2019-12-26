package com.tastycafe.mykotlinsample.Admin.AdminActivities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.tastycafe.mykotlinsample.Admin.AdminInterfaces.Admin_Interface
import com.tastycafe.mykotlinsample.Admin.AdminInterfaces.Admin_Interface.Companion.IMAGE_PICK_CODE
import com.tastycafe.mykotlinsample.Admin.AdminInterfaces.Admin_Interface.Companion.PERMISSION_CODE
import com.tastycafe.mykotlinsample.Database.DBHelper
import com.tastycafe.mykotlinsample.R
import java.text.SimpleDateFormat
import java.util.*

class Admin_Add_Category : AppCompatActivity(), View.OnClickListener, Admin_Interface {

    internal lateinit var db: DBHelper
    private var mediaPath: String = ""

    lateinit var added_image : ImageView
    lateinit var addcate_back: ImageView
    lateinit var add_image: ImageView
    lateinit var catename: EditText
    lateinit var create_cate_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_addcategory)
        supportActionBar ?.hide()

        db = DBHelper(this)
        init_views()
             }

    private fun init_views() {
        added_image = findViewById(R.id.added_image)
        add_image = findViewById(R.id.add_image)
        catename = findViewById(R.id.catename)
        create_cate_btn = findViewById(R.id.create_cate_btn)
        addcate_back = findViewById(R.id.addcate_back)

        addcate_back.setOnClickListener(this)
        add_image.setOnClickListener (this)
        create_cate_btn.setOnClickListener(this)
            }

    private fun pickImageFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent,IMAGE_PICK_CODE)
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
                       val showtext: (String, View) -> String = {message, view -> "$message"}
                       errormsg("Permission Denied", add_image, showtext)
                            }
                        }
                    }
               }

    fun err_enter_fun(msg: String, view: View) {
        var errmsg: (String, View) -> String = {message, view ->
            resources.getString(R.string.please_enter) + " $message"
                }
        errormsg(msg, view, errmsg)
            }

    fun err_select_fun(msg: String, v: View) {
        var errmsg: (String, View) -> String = {message, view ->
            resources.getString(R.string.please_select_an) + " $message"
                }
        errormsg(msg, v, errmsg)
            }

    fun errormsg(msg: String, v: View, shown: (String, View) -> String) {
        var result = shown(msg, v)
        Snackbar.make(v, result, Snackbar.LENGTH_SHORT).show()
            }

    private fun create_category(v: View) {
        var vatenameStr: String = catename.text.trim().toString()
        if(mediaPath.trim() == "") {
            err_select_fun("Image", v)
            return
                }

        if(vatenameStr == null || vatenameStr.trim() == "") {
            catename.requestFocus()
            err_enter_fun("Category Name", v)
            return
                }

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        db.addCategory(vatenameStr, mediaPath, "1", currentDate)
        db.close()

        setResult(1)
        finish()
          }

    private fun add_your_image() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED) {
                // Permission Denide
                val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE )
            } else {
                // Permission Already Denied
                pickImageFromGallery()
            }
        } else {
            // Lower Versions
            pickImageFromGallery()
                }
            }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.add_image -> add_your_image()
            R.id.create_cate_btn -> create_category(v)
            R.id.addcate_back -> onBackPressed()
                    }
                }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
            }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_PICK_CODE) {
            if (data != null) {
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
}