package com.tastycafe.mykotlinsample.Admin.AdminModels

import android.os.Parcel
import android.os.Parcelable

class CategoryList(): Parcelable {

    var cate_id: Int = 0
    var cate_name: String? = null
    var cate_img: String? = null
    var cate_show_status: String? = null
    var cate_created: String? = null

    constructor(parcel: Parcel) : this() {
        cate_id = parcel.readInt()
        cate_name = parcel.readString()
        cate_img = parcel.readString()
        cate_show_status = parcel.readString()
        cate_created = parcel.readString()
            }

    private fun readFromParcel(parcel: Parcel){
        cate_id = parcel.readInt()
        cate_name = parcel.readString()
        cate_img= parcel.readString()
        cate_show_status = parcel.readString()
        cate_created = parcel.readString()
                }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(cate_id)
        parcel.writeString(cate_name)
        parcel.writeString(cate_img)
        parcel.writeString(cate_show_status)
        parcel.writeString(cate_created)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CategoryList> {
        override fun createFromParcel(parcel: Parcel): CategoryList {
            return CategoryList(
                parcel
            )
        }

        override fun newArray(size: Int): Array<CategoryList?> {
            return arrayOfNulls(size)
        }
    }
}