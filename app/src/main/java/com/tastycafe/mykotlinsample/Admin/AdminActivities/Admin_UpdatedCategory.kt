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
import com.google.android.material.snackbar.Snackbar
import com.tastycafe.mykotlinsample.Admin.AdminInterfaces.Admin_Interface.Companion.IMAGE_PICK_CODE
import com.tastycafe.mykotlinsample.Admin.AdminInterfaces.Admin_Interface.Companion.PERMISSION_CODE
import kotlinx.android.synthetic.main.admin_addcategory.*
import kotlinx.android.synthetic.main.admin_updcategory.*

class Admin_UpdatedCategory : AppCompatActivity(), View.OnClickListener {

    internal lateinit var db: DBHelper

    var cateid : Int = 0
    var catename: String? = null
    var catestatus: String? = null
    var cateimg: String? = null

    lateinit var updcate_back: ImageView
    lateinit var updated_image: ImageView
    lateinit var upd_add_image: ImageView
    lateinit var upd_catename: EditText
    lateinit var cate_status: ToggleButton
    lateinit var update_cate_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_updcategory)
        supportActionBar ?.hide()

        db = DBHelper(this)

        init_views()
        get_intents()
            }

    private fun get_intents() {
        cateid = intent.getIntExtra("cateid", 0)
        catename = intent.getStringExtra("catename")
        catestatus = intent.getStringExtra("catestatus")
        cateimg = intent.getStringExtra("cateimg")
        upd_catename.setText(catename)
        if(catestatus.equals("0")) {
            cate_status.isChecked = false
                    } else {
            cate_status.isChecked = true
                }

        val bitmap: Bitmap = BitmapFactory.decodeFile(cateimg)
        updated_image.setImageBitmap(bitmap)
        cate_status.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener {
                buttonView, isChecked ->
            if(isChecked) {
                catestatus = "1"
                     } else {
                catestatus = "0"
                      }
                 })
            }

    private fun init_views() {
        updcate_back = findViewById(R.id.updcate_back)
        updated_image = findViewById(R.id.updated_image)
        upd_add_image = findViewById(R.id.upd_add_image)
        upd_catename = findViewById(R.id.upd_catename)
        cate_status = findViewById(R.id.cate_status)
        update_cate_btn = findViewById(R.id.update_cate_btn)

        updcate_back.setOnClickListener(this)
        upd_add_image.setOnClickListener(this)
        update_cate_btn.setOnClickListener(this)
        cate_status.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener {
                buttonView, isChecked ->
            if(isChecked) {
                catestatus = "1"
                  } else {
                catestatus = "0"
                    }
                })
            }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.updcate_back -> onBackPressed()
            R.id.upd_add_image -> get_image()
            R.id.update_cate_btn -> cate_validation(v)
                }
            }

    fun errshown(message: String, view: View, showerror: (String, View) -> String) {
        val result = showerror(message, view)
        Snackbar.make(view, result, Snackbar.LENGTH_SHORT).show()
                }

    fun enter_err_fun(message: String, view: View) {
        val errormethod: (String, View) -> String = {message, view ->
            resources.getString(R.string.please_enter) + " $message"
                     }
        errshown(message, view, errormethod)
                }

    private fun cate_validation(v:View) {
        catename = upd_catename.text.trim().toString()
        if(catename == null || catename!!.trim() == "") {
            upd_catename.requestFocus()
            enter_err_fun("Category name", v)
            return
               }

        db.updateCategory("" + catename, cateimg!!, catestatus!!,cateid.toString())
        db.close()

        setResult(10)
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
            }

    private fun get_image() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED) {
                // Permission Denide
                val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission,
                    PERMISSION_CODE
                )
                    } else {
                        // Permission Already Denied
                        pickImageFromGallery()
                    }
                } else {
                    // Lower Versions
                    pickImageFromGallery()
                }
            }

    private fun pickImageFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, IMAGE_PICK_CODE )
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
                    val result: (String, View) -> String = {message, view ->
                        "$message Denied"
                        }
                    errshown("Permission", add_image, result)
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
                cateimg = cursor.getString(columnIndex)

                updated_image.setImageBitmap(BitmapFactory.decodeFile(cateimg))
                cursor.close()
                    }
                }
            }

    override fun onBackPressed() {
        setResult(10)
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
          }

}