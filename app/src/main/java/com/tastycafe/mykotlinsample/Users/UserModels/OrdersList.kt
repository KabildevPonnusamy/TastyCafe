package com.tastycafe.mykotlinsample.Users.UserModels

import android.os.Parcel
import android.os.Parcelable

class OrdersList(): Parcelable {

    var id: Int = 0
    var user_id:String? = null
    var order_name : String? = null
    var order_items_count:String? = null
    var order_amount:String? = null
    var order_date:String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        user_id = parcel.readString()
        order_name = parcel.readString()
        order_items_count = parcel.readString()
        order_amount = parcel.readString()
        order_date = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(user_id)
        parcel.writeString(order_name)
        parcel.writeString(order_items_count)
        parcel.writeString(order_amount)
        parcel.writeString(order_date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrdersList> {
        override fun createFromParcel(parcel: Parcel): OrdersList {
            return OrdersList(parcel)
        }

        override fun newArray(size: Int): Array<OrdersList?> {
            return arrayOfNulls(size)
        }
    }


}