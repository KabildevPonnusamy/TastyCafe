package com.tastycafe.mykotlinsample.Admin.AdminModels

import android.os.Parcel
import android.os.Parcelable

class LikesList(): Parcelable {

    var id: Int = 0
    var like_id: String? = null
    var cate_id: String? = null
    var user_id: String? = null


    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        like_id = parcel.readString()
        cate_id = parcel.readString()
        user_id = parcel.readString()
    }

    private fun readFromParcel(parcel: Parcel){
        id = parcel.readInt()
        like_id = parcel.readString()
        cate_id = parcel.readString()
        user_id= parcel.readString()

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(like_id)
        parcel.writeString(cate_id)
        parcel.writeString(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LikesList> {
        override fun createFromParcel(parcel: Parcel): LikesList {
            return LikesList(
                parcel
            )
        }

        override fun newArray(size: Int): Array<LikesList?> {
            return arrayOfNulls(size)
        }
    }
}