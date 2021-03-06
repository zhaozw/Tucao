package me.sweetll.tucao.model.json

import android.os.Parcel
import android.os.Parcelable

data class Video(val title: String,
                 val type: String,
                 val vid: String?,
                 var checked: Boolean = false) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Video> = object : Parcelable.Creator<Video> {
            override fun createFromParcel(source: Parcel): Video = Video(source)
            override fun newArray(size: Int): Array<Video?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString(), 1 == source.readInt())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(title)
        dest?.writeString(type)
        dest?.writeString(vid)
        dest?.writeInt((if (checked) 1 else 0))
    }
}
