package com.tastycafe.mykotlinsample.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.tastycafe.mykotlinsample.Admin.AdminModels.CategoryList
import com.tastycafe.mykotlinsample.Admin.AdminModels.ItemDatasList
import com.tastycafe.mykotlinsample.Admin.AdminModels.LikesList
import com.tastycafe.mykotlinsample.Common.CommonModels.ProfileDatas
import com.tastycafe.mykotlinsample.Users.UserModels.AddCartList
import com.tastycafe.mykotlinsample.Users.UserModels.OrderItemList
import com.tastycafe.mykotlinsample.Users.UserModels.OrdersList

class DBHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME, null,
    DATABASE_VER
) {
    companion object {
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "BAKERY.db"

        //Profile Tables
        private val PROFILE_TABLE = "Profile"
        private val COL_ID = "id"
        private val COL_EMAIL = "email"
        private val COL_PASS = "password"
        private val COL_NAME = "name"
        private val COL_MOBILE = "mobile"

        //Category Table
        private val CATEGORY_TABLE = "Category"
        private val CATE_ID = "cate_id"
        private val CATE_NAME = "cate_name"
        private val CATE_IMAGE = "cate_img"
        private val CATE_SHOW_STATUS = "cate_show_status"
        private val CATE_CREATED_DATE = "cate_created_date"

        private val ITEM_TABLE = "Items"
        private val ITEM_ID = "item_id"
        private val ITEM_CATE_ID = "item_cate_id"
        private val ITEM_NAME = "item_name"
        private val ITEM_IMAGE = "item_image"
        private val ITEM_HOT_or_COLD = "item_hot_or_cold"
        private val ITEM_PRICE = "item_price"
        private val ITEM_OFFER_PRICE = "item_offer_price"
        private val ITEM_LIKES_COUNT = "item_likes_count"
        private val ITEM_SHOWN_STATUS = "item_shown_status"
        private val ITEM_CREATED_DATE = "item_created_date"

        // Likes Table
        private val LIKE_TABLE = "Likes"
        private val LIKE_ID = "like_id"
        private val LIKE_ITEM_ID = "like_item_id"
        private val LIKE_CATE_ID = "like_cate_id"
        private val LIKE_USER_ID = "like_user_id"

        // Cart Table
        private val CART_TABLE = "Carts"
        private val CART_ID = "cart_id"
        private val CART_CATE_ID = "cart_cate_id"
        private val CART_ITEM_ID = "cart_item_id"
        private val CART_ITEM_NAME = "cart_item_name"
        private val CART_ITEM_PRICE = "cart_item_price"
        private val CART_ITEM_IMAGE = "cart_item_img"
        private val CART_ITEM_COUNT = "cart_item_count"
        private val CART_USER_ID = "cart_user_id"

        // Orders Table
        private val ORDERS_TABLE = "Orders"
        private val ORDER_ID = "order_id"
        private val ORDER_USER_ID = "order_user_id"
        private val ORDER_NAME = "order_name"
        private val ORDER_ITEMS_COUNT = "order_items_count"
        private val ORDER_AMOUNT = "order_amount"
        private val ORDER_DATE = "order_date"

        // Ordered Items Table
        private val ORDER_ITEMS_TABLE = "OrderItems"
        private val ORDER_ITEM_ID = "order_item_id"
        private val PARENT_ORDER_ID = "parent_order_id"
        private val ORDER_ITEM_USERID = "order_item_userid"
        private val ORDER_CATE_ID = "order_cate_id"
        private val ITEM_ORDER_ID = "item_order_id"
        private val ITEM_ORDER_NAME = "item_order_name"
        private val ITEM_ORDER_PRICE = "item_order_price"
        private val ITEM_ORDER_COUNT = "item_order_count"
        private val ITEM_ORDER_IMAGE = "item_order_image"

    }

    val CREATE_PROFILE_TABLE =
        ("CREATE TABLE $PROFILE_TABLE ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_EMAIL TEXT, $COL_PASS TEXT, $COL_NAME TEXT, $COL_MOBILE TEXT)")

    val CREATE_CATEGORY_TABLE =
        ("CREATE TABLE $CATEGORY_TABLE ($CATE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$CATE_NAME TEXT, $CATE_IMAGE TEXT, $CATE_SHOW_STATUS TEXT, $CATE_CREATED_DATE TEXT) ")

    val CREATE_ITEM_TABLE =
        ("CREATE TABLE $ITEM_TABLE($ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$ITEM_CATE_ID TEXT, $ITEM_NAME TEXT, $ITEM_IMAGE TEXT, $ITEM_HOT_or_COLD TEXT, " +
                "$ITEM_PRICE TEXT, $ITEM_OFFER_PRICE TEXT, $ITEM_LIKES_COUNT text, $ITEM_SHOWN_STATUS TEXT, " +
                "$ITEM_CREATED_DATE TEXT) ")

    val CREATE_LIKE_TABLE =
        ("CREATE TABLE $LIKE_TABLE ($LIKE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$LIKE_ITEM_ID TEXT, $LIKE_CATE_ID TEXT, $LIKE_USER_ID TEXT) ")

    val CREATE_CART_TABLE =
        ("CREATE TABLE $CART_TABLE ($CART_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$CART_CATE_ID TEXT, $CART_ITEM_ID TEXT, $CART_ITEM_NAME TEXT, $CART_ITEM_PRICE TEXT, " +
                "$CART_ITEM_IMAGE TEXT, $CART_ITEM_COUNT TEXT, $CART_USER_ID TEXT) ")

    val CREATE_ORDERS_TABLE =
        ("CREATE TABLE $ORDERS_TABLE ($ORDER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$ORDER_USER_ID TEXT, $ORDER_NAME TEXT, $ORDER_ITEMS_COUNT TEXT, $ORDER_AMOUNT TEXT, " +
                "$ORDER_DATE TEXT) ")

    val CREATE_ORDER_ITEMS_TABLE =
        ("CREATE TABLE $ORDER_ITEMS_TABLE($ORDER_ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$PARENT_ORDER_ID TEXT, $ORDER_ITEM_USERID TEXT, $ORDER_CATE_ID TEXt, $ITEM_ORDER_ID TEXT," +
                " $ITEM_ORDER_NAME TEXT, $ITEM_ORDER_PRICE TEXT, $ITEM_ORDER_COUNT TEXT, $ITEM_ORDER_IMAGE TEXT) ")

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_PROFILE_TABLE)
        db!!.execSQL(CREATE_CATEGORY_TABLE)
        db!!.execSQL(CREATE_ITEM_TABLE)
        db!!.execSQL(CREATE_LIKE_TABLE)
        db!!.execSQL(CREATE_CART_TABLE)
        db!!.execSQL(CREATE_ORDERS_TABLE)
        db!!.execSQL(CREATE_ORDER_ITEMS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $PROFILE_TABLE")
        db!!.execSQL("DROP TABLE IF EXISTS $CATEGORY_TABLE")
        db!!.execSQL("DROP TABLE IF EXISTS $ITEM_TABLE")
        db!!.execSQL("DROP TABLE IF EXISTS $LIKE_TABLE")
        db!!.execSQL("DROP TABLE IF EXISTS $CART_TABLE")
        db!!.execSQL("DROP TABLE IF EXISTS $CREATE_ORDERS_TABLE")
        db!!.execSQL("DROP TABLE IF EXISTS $CREATE_ORDER_ITEMS_TABLE")
    }

    fun addToOrders(
        user_id: String, order_name: String, order_item_count: String,
        order_amt: String, order_date: String
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ORDER_USER_ID, user_id)
        values.put(ORDER_NAME, order_name)
        values.put(ORDER_ITEMS_COUNT, order_item_count)
        values.put(ORDER_AMOUNT, order_amt)
        values.put(ORDER_DATE, order_date)
        db.insert(ORDERS_TABLE, null, values)
        db.close()
    }

    fun addToOrderItems(parent_orderid: String, user_id: String, order_cateid: String, item_orderid: String,
        item_name: String, item_price: String, item_count: String, item_img: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(PARENT_ORDER_ID, parent_orderid)
        values.put(ORDER_ITEM_USERID, user_id)
        values.put(ORDER_CATE_ID, order_cateid)
        values.put(ITEM_ORDER_ID, item_orderid)
        values.put(ITEM_ORDER_NAME, item_name)
        values.put(ITEM_ORDER_PRICE, item_price)
        values.put(ITEM_ORDER_COUNT, item_count)
        values.put(ITEM_ORDER_IMAGE, item_img)
        db.insert(ORDER_ITEMS_TABLE, null, values)
        db.close()
    }

    fun getOrderItemsList(user_id: String, parent_order_id: String): ArrayList<OrderItemList> {
        val ordersItmList = ArrayList<OrderItemList>()
        val selectQuery =
            "SELECT * FROM $ORDER_ITEMS_TABLE Where $ORDER_ITEM_USERID = '$user_id' and " +
                    "$PARENT_ORDER_ID = '$parent_order_id'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val items = OrderItemList()
                items.id = cursor.getInt(cursor.getColumnIndex(ORDER_ITEM_ID))
                items.parent_id = cursor.getString(cursor.getColumnIndex(PARENT_ORDER_ID))
                items.userid = cursor.getString(cursor.getColumnIndex(ORDER_ITEM_USERID))
                items.cate_id = cursor.getString(cursor.getColumnIndex(ORDER_CATE_ID))
                items.item_id = cursor.getString(cursor.getColumnIndex(ITEM_ORDER_ID))
                items.item_name = cursor.getString(cursor.getColumnIndex(ITEM_ORDER_NAME))
                items.item_price = cursor.getString(cursor.getColumnIndex(ITEM_ORDER_PRICE))
                items.item_count = cursor.getString(cursor.getColumnIndex(ITEM_ORDER_COUNT))
                items.item_image = cursor.getString(cursor.getColumnIndex(ITEM_ORDER_IMAGE))
                ordersItmList.add(items)
            } while (cursor.moveToNext())
        }
        return ordersItmList
    }

    fun checkOrderList(user_id: String, order_name: String): ArrayList<OrdersList> {
        val ordersList = ArrayList<OrdersList>()
        val selectQuery = "SELECT * FROM $ORDERS_TABLE where $ORDER_USER_ID = '$user_id' and " +
                "$ORDER_NAME = '$order_name' "
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val items = OrdersList()
                items.id = cursor.getInt(cursor.getColumnIndex(ORDER_ID))
                items.user_id = cursor.getString(cursor.getColumnIndex(ORDER_USER_ID))
                items.order_name = cursor.getString(cursor.getColumnIndex(ORDER_NAME))
                items.order_items_count = cursor.getString(cursor.getColumnIndex(ORDER_ITEMS_COUNT))
                items.order_amount = cursor.getString(cursor.getColumnIndex(ORDER_AMOUNT))
                items.order_date = cursor.getString(cursor.getColumnIndex(ORDER_DATE))
                ordersList.add(items)
            } while (cursor.moveToNext())
        }
        return ordersList
    }

    fun getOrdersList(user_id: String): ArrayList<OrdersList> {
        val ordersList = ArrayList<OrdersList>()
        val selectQuery = "SELECT * FROM $ORDERS_TABLE where $ORDER_USER_ID = '$user_id'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val items = OrdersList()
                items.id = cursor.getInt(cursor.getColumnIndex(ORDER_ID))
                items.user_id = cursor.getString(cursor.getColumnIndex(ORDER_USER_ID))
                items.order_name = cursor.getString(cursor.getColumnIndex(ORDER_NAME))
                items.order_items_count = cursor.getString(cursor.getColumnIndex(ORDER_ITEMS_COUNT))
                items.order_amount = cursor.getString(cursor.getColumnIndex(ORDER_AMOUNT))
                items.order_date = cursor.getString(cursor.getColumnIndex(ORDER_DATE))
                ordersList.add(items)
            } while (cursor.moveToNext())
        }

        return ordersList
    }

    // Add cart Table
    fun addTocart(
        cart_cate_id: String, cart_item_id: String, cart_item_name: String,
        cart_item_price: String, cart_item_img: String, cart_item_count: String,
        cart_user_id: String
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CART_CATE_ID, cart_cate_id)
        values.put(CART_ITEM_ID, cart_item_id)
        values.put(CART_ITEM_NAME, cart_item_name)
        values.put(CART_ITEM_PRICE, cart_item_price)
        values.put(CART_ITEM_IMAGE, cart_item_img)
        values.put(CART_ITEM_COUNT, cart_item_count)
        values.put(CART_USER_ID, cart_user_id)
        db.insert(CART_TABLE, null, values)
        db.close()
    }

    // Get Cart Datas
    fun getCartDatas(cart_user_id: String): ArrayList<AddCartList> {
        val cartList = ArrayList<AddCartList>()
        val selectQuery = "SELECT * FROM $CART_TABLE where $CART_USER_ID = '$cart_user_id' "
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val cartDatas = AddCartList()
                cartDatas.cart_id = cursor.getInt(cursor.getColumnIndex(CART_ID))
                cartDatas.cart_cate_id = cursor.getString(cursor.getColumnIndex(CART_CATE_ID))
                cartDatas.cart_item_id = cursor.getString(cursor.getColumnIndex(CART_ITEM_ID))
                cartDatas.cart_item_name = cursor.getString(cursor.getColumnIndex(CART_ITEM_NAME))
                cartDatas.cart_item_price = cursor.getString(cursor.getColumnIndex(CART_ITEM_PRICE))
                cartDatas.cart_item_count = cursor.getString(cursor.getColumnIndex(CART_ITEM_COUNT))
                cartDatas.cart_item_image = cursor.getString(cursor.getColumnIndex(CART_ITEM_IMAGE))
                cartDatas.cart_user_id = cursor.getString(cursor.getColumnIndex(CART_USER_ID))
                cartList.add(cartDatas)
            } while (cursor.moveToNext())
        }
        return cartList
    }

    //Get Particular Item on Cart
    fun getItemFromCart(cart_user_id: String, cart_item_id: String): ArrayList<AddCartList> {
        val cartList = ArrayList<AddCartList>()
        val selectQuery = "SELECT * FROM $CART_TABLE where $CART_USER_ID = '$cart_user_id' and " +
                "$CART_ITEM_ID = '$cart_item_id'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val cartDatas = AddCartList()
                cartDatas.cart_id = cursor.getInt(cursor.getColumnIndex(CART_ID))
                cartDatas.cart_cate_id = cursor.getString(cursor.getColumnIndex(CART_CATE_ID))
                cartDatas.cart_item_id = cursor.getString(cursor.getColumnIndex(CART_ITEM_ID))
                cartDatas.cart_item_name = cursor.getString(cursor.getColumnIndex(CART_ITEM_NAME))
                cartDatas.cart_item_price = cursor.getString(cursor.getColumnIndex(CART_ITEM_PRICE))
                cartDatas.cart_item_count = cursor.getString(cursor.getColumnIndex(CART_ITEM_COUNT))
                cartDatas.cart_item_image = cursor.getString(cursor.getColumnIndex(CART_ITEM_IMAGE))
                cartDatas.cart_user_id = cursor.getString(cursor.getColumnIndex(CART_USER_ID))
                cartList.add(cartDatas)
            } while (cursor.moveToNext())
        }
        return cartList
    }

    //Delete Cart Items
    fun deleteCartItems(cart_user_id: String, cart_id: String) {
        val db = writableDatabase
        db.delete(CART_TABLE, "$CART_ID=? and $CART_USER_ID=?", arrayOf(cart_id, cart_user_id))
        db.close()
    }

    //Delete Cart Items
    fun deleteAllCartItems(cart_user_id: String) {
        val db = writableDatabase
        db.delete(CART_TABLE, "$CART_USER_ID=?", arrayOf(cart_user_id))
        db.close()
    }

    // Add Like Users
    fun addLikes(item_id: String, cate_id: String, user_email: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(LIKE_ITEM_ID, item_id)
        values.put(LIKE_CATE_ID, cate_id)
        values.put(LIKE_USER_ID, user_email)

        db.insert(LIKE_TABLE, null, values)
        db.close()
    }

    fun deleteLikes(item_id: String, user_email: String) {
        val db = writableDatabase
        db.delete(LIKE_TABLE, "$LIKE_ITEM_ID=? and $LIKE_USER_ID=?", arrayOf(item_id, user_email))
        db.close()
    }

    fun getLike(item_id: String, user_email: String): ArrayList<LikesList> {
        val myLike = ArrayList<LikesList>()
        val selectQuery = "SELECT * FROM $LIKE_TABLE where $LIKE_ITEM_ID = '$item_id' and " +
                "$LIKE_USER_ID = '$user_email'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val mydatas = LikesList()
                mydatas.id = cursor.getInt(cursor.getColumnIndex(LIKE_ID))
                mydatas.like_id = cursor.getString(cursor.getColumnIndex(LIKE_ITEM_ID))
                mydatas.cate_id = cursor.getString(cursor.getColumnIndex(LIKE_CATE_ID))
                mydatas.user_id = cursor.getString(cursor.getColumnIndex(LIKE_USER_ID))
                myLike.add(mydatas)
            } while (cursor.moveToNext())
        }
        return myLike
    }

    fun getMyLikes(user_email: String): ArrayList<LikesList> {
        val myLike = ArrayList<LikesList>()
        val selectQuery = "SELECT * FROM $LIKE_TABLE where $LIKE_USER_ID = '$user_email'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val mydatas = LikesList()
                mydatas.id = cursor.getInt(cursor.getColumnIndex(LIKE_ID))
                mydatas.like_id = cursor.getString(cursor.getColumnIndex(LIKE_ITEM_ID))
                mydatas.cate_id = cursor.getString(cursor.getColumnIndex(LIKE_CATE_ID))
                mydatas.user_id = cursor.getString(cursor.getColumnIndex(LIKE_USER_ID))
                myLike.add(mydatas)
            } while (cursor.moveToNext())
        }
        return myLike
    }

    fun totalLike(item_id: String): ArrayList<LikesList> {
        val myLike = ArrayList<LikesList>()
        val selectQuery = "SELECT * FROM $LIKE_TABLE where $LIKE_ITEM_ID = '$item_id' "
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val mydatas = LikesList()
                mydatas.id = cursor.getInt(cursor.getColumnIndex(LIKE_ID))
                mydatas.like_id = cursor.getString(cursor.getColumnIndex(LIKE_ITEM_ID))
                mydatas.cate_id = cursor.getString(cursor.getColumnIndex(LIKE_CATE_ID))
                mydatas.user_id = cursor.getString(cursor.getColumnIndex(LIKE_USER_ID))
                myLike.add(mydatas)
            } while (cursor.moveToNext())
        }
        return myLike
    }

    // Get user By Username and Passsword
    fun getMyUser(username: String, password: String): List<ProfileDatas> {
        val myprof = ArrayList<ProfileDatas>()
        val selectQuery = "SELECT * FROM $PROFILE_TABLE where $COL_EMAIL = '$username' and " +
                "$COL_PASS = '$password'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val mydatas =
                    ProfileDatas()
                mydatas.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                mydatas.email = cursor.getString(cursor.getColumnIndex(COL_EMAIL))
                mydatas.password = cursor.getString(cursor.getColumnIndex(COL_PASS))
                mydatas.name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                mydatas.mobile = cursor.getString(cursor.getColumnIndex(COL_MOBILE))
//                mydatas.created_date = cursor.getString(cursor.getColumnIndex(COL_CREATED_DATE))

                myprof.add(mydatas)
            } while (cursor.moveToNext())
        }

        return myprof
    }

    //Search user by Email
    fun searchBEmail(username: String): List<ProfileDatas> {
        val myprof = ArrayList<ProfileDatas>()
        val selectQuery = "SELECT * FROM $PROFILE_TABLE where $COL_EMAIL = '$username' "
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val mydatas =
                    ProfileDatas()
                mydatas.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                mydatas.email = cursor.getString(cursor.getColumnIndex(COL_EMAIL))
                mydatas.password = cursor.getString(cursor.getColumnIndex(COL_PASS))
                mydatas.name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                mydatas.mobile = cursor.getString(cursor.getColumnIndex(COL_MOBILE))
//                mydatas.created_date = cursor.getString(cursor.getColumnIndex(COL_CREATED_DATE))

                myprof.add(mydatas)
            } while (cursor.moveToNext())
        }
        return myprof
    }

    //Get All Category
    fun getCategories(): List<CategoryList> {
        val mycates = ArrayList<CategoryList>()
        val selectQuery = "SELECT * FROM $CATEGORY_TABLE where $CATE_SHOW_STATUS = '1'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val mydatas =
                    CategoryList()
                mydatas.cate_id = cursor.getInt(cursor.getColumnIndex(CATE_ID))
                mydatas.cate_name = cursor.getString(cursor.getColumnIndex(CATE_NAME))
                mydatas.cate_img = cursor.getString(cursor.getColumnIndex(CATE_IMAGE))
                mydatas.cate_show_status = cursor.getString(cursor.getColumnIndex(CATE_SHOW_STATUS))
                mydatas.cate_created = cursor.getString(cursor.getColumnIndex(CATE_CREATED_DATE))
                mycates.add(mydatas)
            } while (cursor.moveToNext())
        }
        return mycates
    }

    //Get All Category
    fun getHiddenCategories(): List<CategoryList> {
        val mycates = ArrayList<CategoryList>()
        val selectQuery = "SELECT * FROM $CATEGORY_TABLE where $CATE_SHOW_STATUS = '0'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val mydatas =
                    CategoryList()
                mydatas.cate_id = cursor.getInt(cursor.getColumnIndex(CATE_ID))
                mydatas.cate_name = cursor.getString(cursor.getColumnIndex(CATE_NAME))
                mydatas.cate_img = cursor.getString(cursor.getColumnIndex(CATE_IMAGE))
                mydatas.cate_show_status = cursor.getString(cursor.getColumnIndex(CATE_SHOW_STATUS))
                mydatas.cate_created = cursor.getString(cursor.getColumnIndex(CATE_CREATED_DATE))
                mycates.add(mydatas)
            } while (cursor.moveToNext())
        }
        return mycates
    }

    //Get particular category details
    fun getthisCategories(cate_id: String): ArrayList<CategoryList> {
        val mycates = ArrayList<CategoryList>()
        val selectQuery =
            "SELECT * FROM $CATEGORY_TABLE where $CATE_SHOW_STATUS = '1' and $CATE_ID = $cate_id"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val mydatas =
                    CategoryList()
                mydatas.cate_id = cursor.getInt(cursor.getColumnIndex(CATE_ID))
                mydatas.cate_name = cursor.getString(cursor.getColumnIndex(CATE_NAME))
                mydatas.cate_img = cursor.getString(cursor.getColumnIndex(CATE_IMAGE))
                mydatas.cate_show_status = cursor.getString(cursor.getColumnIndex(CATE_SHOW_STATUS))
                mydatas.cate_created = cursor.getString(cursor.getColumnIndex(CATE_CREATED_DATE))
                mycates.add(mydatas)
            } while (cursor.moveToNext())
        }
        return mycates
    }

    //Get All Users
    val getUser: List<ProfileDatas>
        get() {
            val myprof = ArrayList<ProfileDatas>()
            val selectQuery = "SELECT * FROM $PROFILE_TABLE"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val mydatas =
                        ProfileDatas()
                    mydatas.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                    mydatas.email = cursor.getString(cursor.getColumnIndex(COL_EMAIL))
                    mydatas.password = cursor.getString(cursor.getColumnIndex(COL_PASS))
                    mydatas.name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                    mydatas.mobile = cursor.getString(cursor.getColumnIndex(COL_MOBILE))
//                mydatas.created_date = cursor.getString(cursor.getColumnIndex(COL_CREATED_DATE))
                    myprof.add(mydatas)
                } while (cursor.moveToNext())
            }
            return myprof
        }

    // Add Users
    fun addUser(email: String, password: String, name: String, mobile: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_EMAIL, email)
        values.put(COL_PASS, password)
        values.put(COL_NAME, name)
        values.put(COL_MOBILE, mobile)

        db.insert(PROFILE_TABLE, null, values)
        db.close()
    }

    // Update Password
    fun updateUser(email: String, password: String): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_PASS, password)

        return db.update(PROFILE_TABLE, values, "$COL_EMAIL=?", arrayOf(email))
    }

    // Delete User
    fun deleteUser(profdatas: ProfileDatas) {
        val db = this.writableDatabase
        db.delete(PROFILE_TABLE, "$COL_EMAIL=?", arrayOf(profdatas.email.toString()))
        db.close()
    }

    /* Category Tables */
    //Add Category
    fun addCategory(catename: String, cateimg: String, cateshown: String, catecreated: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CATE_NAME, catename)
        values.put(CATE_IMAGE, cateimg)
        values.put(CATE_SHOW_STATUS, cateshown)
        values.put(CATE_CREATED_DATE, catecreated)
        db.insert(CATEGORY_TABLE, null, values)
        db.close()
    }

    //Delete Category
    fun deleteCategory(cate_id: String) {
        val db = this.writableDatabase
        db.delete(CATEGORY_TABLE, "$CATE_ID=?", arrayOf(cate_id))
        db.close()
    }

    //Update Category
    fun updateCategory(
        cate_name: String,
        cate_img: String,
        cate_status: String,
        cate_id: String
    ): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CATE_NAME, cate_name)
        values.put(CATE_IMAGE, cate_img)
        values.put(CATE_SHOW_STATUS, cate_status)
        return db.update(CATEGORY_TABLE, values, "$CATE_ID=?", arrayOf(cate_id))
    }

    fun updateShownCategories(item_id: String, item_status: String): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CATE_SHOW_STATUS, item_status)
        return db.update(CATEGORY_TABLE, values, "$CATE_ID=?", arrayOf(item_id))
    }

    /******************************  Items  ********************************************/
    /*Add Items*/
    fun addItems(
        cate_id: String,
        item_name: String,
        item_image: String,
        item_price: String,
        item_ofr_price: String,
        item_status: String,
        item_created_date: String,
        hot_or_cold: String
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ITEM_CATE_ID, cate_id)
        values.put(ITEM_NAME, item_name)
        values.put(ITEM_IMAGE, item_image)
        values.put(ITEM_HOT_or_COLD, hot_or_cold)
        values.put(ITEM_PRICE, item_price)
        values.put(ITEM_OFFER_PRICE, item_ofr_price)
        values.put(ITEM_LIKES_COUNT, "0")
        values.put(ITEM_SHOWN_STATUS, item_status)
        values.put(ITEM_CREATED_DATE, item_created_date)
        db.insert(ITEM_TABLE, null, values)
        db.close()
    }

    /*Update Items */
    fun updateItems(
        item_id: String, item_name: String, item_image: String, item_price: String,
        item_ofr_price: String, item_status: String, hot_or_cold: String,
        like_count: String
    ): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ITEM_NAME, item_name)
        values.put(ITEM_IMAGE, item_image)
        values.put(ITEM_HOT_or_COLD, hot_or_cold)
        values.put(ITEM_PRICE, item_price)
        values.put(ITEM_OFFER_PRICE, item_ofr_price)
        values.put(ITEM_LIKES_COUNT, like_count)
        values.put(ITEM_SHOWN_STATUS, item_status)
        return db.update(ITEM_TABLE, values, "$ITEM_ID=?", arrayOf(item_id))
    }

    /*Delete Items */
    fun deleteItems(item_id: String) {
        val db = this.writableDatabase
        db.delete(ITEM_TABLE, "$ITEM_ID=?", arrayOf(item_id))
        db.close()
    }

    /*Get Items List*/
    fun getAllItems(cate_id: String): List<ItemDatasList> {
        val myitems = ArrayList<ItemDatasList>()
        val selectQuery = "SELECT * FROM $ITEM_TABLE where $ITEM_SHOWN_STATUS = '1' " +
                "and $ITEM_CATE_ID = $cate_id"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val mydatas =
                    ItemDatasList()
                mydatas.item_id = cursor.getInt(cursor.getColumnIndex(ITEM_ID))
                mydatas.cate_id = cursor.getString(cursor.getColumnIndex(ITEM_CATE_ID))
                mydatas.item_name = cursor.getString(cursor.getColumnIndex(ITEM_NAME))
                mydatas.item_img = cursor.getString(cursor.getColumnIndex(ITEM_IMAGE))
                mydatas.item_hot_or_cold = cursor.getString(cursor.getColumnIndex(ITEM_HOT_or_COLD))
                mydatas.item_price = cursor.getString(cursor.getColumnIndex(ITEM_PRICE))
                mydatas.item_ofr_price = cursor.getString(cursor.getColumnIndex(ITEM_OFFER_PRICE))
                mydatas.item_like_count = cursor.getString(cursor.getColumnIndex(ITEM_LIKES_COUNT))
                mydatas.item_shown_status = cursor.getString(
                    cursor.getColumnIndex(
                        ITEM_SHOWN_STATUS
                    )
                )
                mydatas.item_created_date = cursor.getString(
                    cursor.getColumnIndex(
                        ITEM_CREATED_DATE
                    )
                )
                myitems.add(mydatas)
            } while (cursor.moveToNext())
        }
        return myitems
    }

    /* Get Particular item */
    fun getSingleItem(item_id: String): ArrayList<ItemDatasList> {
        val myitems = ArrayList<ItemDatasList>()
        val selectQuery = "SELECT * FROM $ITEM_TABLE where $ITEM_SHOWN_STATUS = '1' " +
                "and $ITEM_ID = $item_id"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val mydatas =
                    ItemDatasList()
                mydatas.item_id = cursor.getInt(cursor.getColumnIndex(ITEM_ID))
                mydatas.cate_id = cursor.getString(cursor.getColumnIndex(ITEM_CATE_ID))
                mydatas.item_name = cursor.getString(cursor.getColumnIndex(ITEM_NAME))
                mydatas.item_img = cursor.getString(cursor.getColumnIndex(ITEM_IMAGE))
                mydatas.item_hot_or_cold = cursor.getString(cursor.getColumnIndex(ITEM_HOT_or_COLD))
                mydatas.item_price = cursor.getString(cursor.getColumnIndex(ITEM_PRICE))
                mydatas.item_ofr_price = cursor.getString(cursor.getColumnIndex(ITEM_OFFER_PRICE))
                mydatas.item_like_count = cursor.getString(cursor.getColumnIndex(ITEM_LIKES_COUNT))
                mydatas.item_shown_status = cursor.getString(
                    cursor.getColumnIndex(
                        ITEM_SHOWN_STATUS
                    )
                )
                mydatas.item_created_date = cursor.getString(
                    cursor.getColumnIndex(
                        ITEM_CREATED_DATE
                    )
                )
                myitems.add(mydatas)
            } while (cursor.moveToNext())
        }
        return myitems
    }

    /* Get Hot Items */
    fun getHotItems(): List<ItemDatasList> {
        val myitems = ArrayList<ItemDatasList>()
        val selectQuery = "SELECT * FROM $ITEM_TABLE where $ITEM_SHOWN_STATUS = '1' and " +
                "$ITEM_HOT_or_COLD = '1' "
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val mydatas =
                    ItemDatasList()
                mydatas.item_id = cursor.getInt(cursor.getColumnIndex(ITEM_ID))
                mydatas.cate_id = cursor.getString(cursor.getColumnIndex(ITEM_CATE_ID))
                mydatas.item_name = cursor.getString(cursor.getColumnIndex(ITEM_NAME))
                mydatas.item_img = cursor.getString(cursor.getColumnIndex(ITEM_IMAGE))
                mydatas.item_hot_or_cold = cursor.getString(cursor.getColumnIndex(ITEM_HOT_or_COLD))
                mydatas.item_price = cursor.getString(cursor.getColumnIndex(ITEM_PRICE))
                mydatas.item_ofr_price = cursor.getString(cursor.getColumnIndex(ITEM_OFFER_PRICE))
                mydatas.item_like_count = cursor.getString(cursor.getColumnIndex(ITEM_LIKES_COUNT))
                mydatas.item_shown_status = cursor.getString(
                    cursor.getColumnIndex(
                        ITEM_SHOWN_STATUS
                    )
                )
                mydatas.item_created_date = cursor.getString(
                    cursor.getColumnIndex(
                        ITEM_CREATED_DATE
                    )
                )
                myitems.add(mydatas)
            } while (cursor.moveToNext())
        }
        return myitems
    }

    /* Get Cool Items*/
    fun getCoolItems(): List<ItemDatasList> {
        val myitems = ArrayList<ItemDatasList>()
        val selectQuery =
            "SELECT * FROM $ITEM_TABLE where $ITEM_SHOWN_STATUS = '1' and $ITEM_HOT_or_COLD = '0' "
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val mydatas =
                    ItemDatasList()
                mydatas.item_id = cursor.getInt(cursor.getColumnIndex(ITEM_ID))
                mydatas.cate_id = cursor.getString(cursor.getColumnIndex(ITEM_CATE_ID))
                mydatas.item_name = cursor.getString(cursor.getColumnIndex(ITEM_NAME))
                mydatas.item_img = cursor.getString(cursor.getColumnIndex(ITEM_IMAGE))
                mydatas.item_hot_or_cold = cursor.getString(cursor.getColumnIndex(ITEM_HOT_or_COLD))
                mydatas.item_price = cursor.getString(cursor.getColumnIndex(ITEM_PRICE))
                mydatas.item_ofr_price = cursor.getString(cursor.getColumnIndex(ITEM_OFFER_PRICE))
                mydatas.item_like_count = cursor.getString(cursor.getColumnIndex(ITEM_LIKES_COUNT))
                mydatas.item_shown_status = cursor.getString(
                    cursor.getColumnIndex(
                        ITEM_SHOWN_STATUS
                    )
                )
                mydatas.item_created_date = cursor.getString(
                    cursor.getColumnIndex(
                        ITEM_CREATED_DATE
                    )
                )
                myitems.add(mydatas)
            } while (cursor.moveToNext())
        }
        return myitems
    }

    //get complete Items
    fun getCompleteItems(): List<ItemDatasList> {
        val myitems = ArrayList<ItemDatasList>()
        val selectQuery = "SELECT * FROM $ITEM_TABLE where $ITEM_SHOWN_STATUS = '1' "
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val mydatas =
                    ItemDatasList()
                mydatas.item_id = cursor.getInt(cursor.getColumnIndex(ITEM_ID))
                mydatas.cate_id = cursor.getString(cursor.getColumnIndex(ITEM_CATE_ID))
                mydatas.item_name = cursor.getString(cursor.getColumnIndex(ITEM_NAME))
                mydatas.item_img = cursor.getString(cursor.getColumnIndex(ITEM_IMAGE))
                mydatas.item_hot_or_cold = cursor.getString(cursor.getColumnIndex(ITEM_HOT_or_COLD))
                mydatas.item_price = cursor.getString(cursor.getColumnIndex(ITEM_PRICE))
                mydatas.item_ofr_price = cursor.getString(cursor.getColumnIndex(ITEM_OFFER_PRICE))
                mydatas.item_like_count = cursor.getString(cursor.getColumnIndex(ITEM_LIKES_COUNT))
                mydatas.item_shown_status = cursor.getString(
                    cursor.getColumnIndex(
                        ITEM_SHOWN_STATUS
                    )
                )
                mydatas.item_created_date = cursor.getString(
                    cursor.getColumnIndex(
                        ITEM_CREATED_DATE
                    )
                )
                myitems.add(mydatas)
            } while (cursor.moveToNext())
        }
        return myitems
    }

    /*Get Items List*/
    fun getHidenItems(cate_id: String): List<ItemDatasList> {
        val myitems = ArrayList<ItemDatasList>()
        val selectQuery = "SELECT * FROM $ITEM_TABLE where $ITEM_SHOWN_STATUS = '0' and" +
                " $ITEM_CATE_ID = $cate_id"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val mydatas =
                    ItemDatasList()
                mydatas.item_id = cursor.getInt(cursor.getColumnIndex(ITEM_ID))
                mydatas.cate_id = cursor.getString(cursor.getColumnIndex(ITEM_CATE_ID))
                mydatas.item_name = cursor.getString(cursor.getColumnIndex(ITEM_NAME))
                mydatas.item_img = cursor.getString(cursor.getColumnIndex(ITEM_IMAGE))
                mydatas.item_hot_or_cold = cursor.getString(cursor.getColumnIndex(ITEM_HOT_or_COLD))
                mydatas.item_price = cursor.getString(cursor.getColumnIndex(ITEM_PRICE))
                mydatas.item_ofr_price = cursor.getString(cursor.getColumnIndex(ITEM_OFFER_PRICE))
                mydatas.item_like_count = cursor.getString(cursor.getColumnIndex(ITEM_LIKES_COUNT))
                mydatas.item_shown_status = cursor.getString(
                    cursor.getColumnIndex(
                        ITEM_SHOWN_STATUS
                    )
                )
                mydatas.item_created_date = cursor.getString(
                    cursor.getColumnIndex(
                        ITEM_CREATED_DATE
                    )
                )
                myitems.add(mydatas)
            } while (cursor.moveToNext())
        }
        return myitems
    }

    // Get Offered Items
    fun getOfferedItems(): List<ItemDatasList> {
        val offeredItems = ArrayList<ItemDatasList>()
        val selectQuery = "SELECT * from $ITEM_TABLE where $ITEM_OFFER_PRICE != ''  "
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val mydatas =
                    ItemDatasList()
                mydatas.item_id = cursor.getInt(cursor.getColumnIndex(ITEM_ID))
                mydatas.cate_id = cursor.getString(cursor.getColumnIndex(ITEM_CATE_ID))
                mydatas.item_name = cursor.getString(cursor.getColumnIndex(ITEM_NAME))
                mydatas.item_img = cursor.getString(cursor.getColumnIndex(ITEM_IMAGE))
                mydatas.item_hot_or_cold = cursor.getString(cursor.getColumnIndex(ITEM_HOT_or_COLD))
                mydatas.item_price = cursor.getString(cursor.getColumnIndex(ITEM_PRICE))
                mydatas.item_ofr_price = cursor.getString(cursor.getColumnIndex(ITEM_OFFER_PRICE))
                mydatas.item_like_count = cursor.getString(cursor.getColumnIndex(ITEM_LIKES_COUNT))
                mydatas.item_shown_status = cursor.getString(
                    cursor.getColumnIndex(
                        ITEM_SHOWN_STATUS
                    )
                )
                mydatas.item_created_date = cursor.getString(
                    cursor.getColumnIndex(
                        ITEM_CREATED_DATE
                    )
                )
                offeredItems.add(mydatas)
            } while (cursor.moveToNext())
        }
        return offeredItems
    }

    fun updateShownItems(item_id: String, item_status: String): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ITEM_SHOWN_STATUS, item_status)
        return db.update(ITEM_TABLE, values, "$ITEM_ID=?", arrayOf(item_id))
    }


}