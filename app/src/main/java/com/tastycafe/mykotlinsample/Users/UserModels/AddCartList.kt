package com.tastycafe.mykotlinsample.Users.UserModels

import android.os.Parcel
import android.os.Parcelable

class AddCartList(): Parcelable {

    var cart_id: Int = 0
    var cart_cate_id: String? = null
    var cart_item_id: String? = null
    var cart_item_name: String? = null
    var cart_item_price: String? = null
    var cart_item_image: String? = null
    var cart_item_count: String? = null
    var cart_user_id: String? = null

    constructor(parcel: Parcel) : this() {
        cart_id = parcel.readInt()
        cart_cate_id = parcel.readString()
        cart_item_id = parcel.readString()
        cart_item_name = parcel.readString()
        cart_item_price = parcel.readString()
        cart_item_image = parcel.readString()
        cart_item_count = parcel.readString()
        cart_user_id = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(cart_id)
        parcel.writeString(cart_cate_id)
        parcel.writeString(cart_item_id)
        parcel.writeString(cart_item_name)
        parcel.writeString(cart_item_price)
        parcel.writeString(cart_item_image)
        parcel.writeString(cart_item_count)
        parcel.writeString(cart_user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AddCartList> {
        override fun createFromParcel(parcel: Parcel): AddCartList {
            return AddCartList(parcel)
        }

        override fun newArray(size: Int): Array<AddCartList?> {
            return arrayOfNulls(size)
        }
    }

}