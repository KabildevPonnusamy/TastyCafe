package com.tastycafe.mykotlinsample.Admin.AdminModels

import android.os.Parcel
import android.os.Parcelable

class ItemDatasList() : Parcelable {

    var item_id: Int = 0
    var cate_id: String? = null
    var item_name: String? = null
    var item_img: String? = null
    var item_hot_or_cold: String? = null
    var item_price: String? = null
    var item_ofr_price: String? = null
    var item_like_count: String? = null
    var item_shown_status: String? = null
    var item_created_date: String? = null

    constructor(parcel: Parcel) : this() {
        item_id = parcel.readInt()
        cate_id = parcel.readString()
        item_name = parcel.readString()
        item_img = parcel.readString()
        item_hot_or_cold = parcel.readString()
        item_price = parcel.readString()
        item_ofr_price = parcel.readString()
        item_like_count = parcel.readString()
        item_shown_status = parcel.readString()
        item_created_date = parcel.readString()
                }

    private fun readFromParcel(parcel: Parcel){
        item_id = parcel.readInt()
        cate_id = parcel.readString()
        item_name = parcel.readString()
        item_img = parcel.readString()
        item_hot_or_cold = parcel.readString()
        item_price = parcel.readString()
        item_ofr_price = parcel.readString()
        item_like_count = parcel.readString()
        item_shown_status = parcel.readString()
        item_created_date = parcel.readString()
            }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(item_id)
        parcel.writeString(cate_id)
        parcel.writeString(item_name)
        parcel.writeString(item_img)
        parcel.writeString(item_hot_or_cold)
        parcel.writeString(item_price)
        parcel.writeString(item_ofr_price)
        parcel.writeString(item_like_count)
        parcel.writeString(item_shown_status)
        parcel.writeString(item_created_date)
                }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemDatasList> {
        override fun createFromParcel(parcel: Parcel): ItemDatasList {
            return ItemDatasList(
                parcel
            )
        }

        override fun newArray(size: Int): Array<ItemDatasList?> {
            return arrayOfNulls(size)
        }
    }
}