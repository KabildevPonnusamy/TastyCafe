package com.tastycafe.mykotlinsample.Users.UserModels

import android.os.Parcel
import android.os.Parcelable

class OrderItemList(): Parcelable {

    var id: Int = 0
    var userid: String? = null
    var parent_id:String? = null
    var cate_id:String? = null
    var item_id: String? = null
    var item_name:String? = null
    var item_price:String? = null
    var item_count:String? = null
    var item_image:String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        userid = parcel.readString()
        parent_id = parcel.readString()
        cate_id = parcel.readString()
        item_id = parcel.readString()
        item_name = parcel.readString()
        item_price = parcel.readString()
        item_count = parcel.readString()
        item_image = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(userid)
        parcel.writeString(parent_id)
        parcel.writeString(cate_id)
        parcel.writeString(item_id)
        parcel.writeString(item_name)
        parcel.writeString(item_price)
        parcel.writeString(item_count)
        parcel.writeString(item_image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderItemList> {
        override fun createFromParcel(parcel: Parcel): OrderItemList {
            return OrderItemList(parcel)
        }

        override fun newArray(size: Int): Array<OrderItemList?> {
            return arrayOfNulls(size)
        }
    }

}