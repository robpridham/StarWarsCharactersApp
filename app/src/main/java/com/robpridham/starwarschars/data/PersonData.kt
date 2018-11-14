package com.robpridham.starwarschars.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class MultiPersonResponse(val count: Int, val next: String?, val previous: String?, val results: List<PersonData>)

@Parcelize
data class PersonData(val name: String, val height: String, val mass: String, val created: String): Parcelable {
    companion object {
        //source format example: 2014-12-09T13:50:51.644000Z
        const val SOURCE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"
    }
}