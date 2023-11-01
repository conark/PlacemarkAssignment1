package org.wit.placemark.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(var id: Long = 0,
                     var firstName: String = "",
                     var lastName: String = "",
                     var phoneNumber: Long =0,
                     var address: String = "",
                     var provider : Boolean = false,
                     var DOB:String = "",
                     var ppsNumber: String = "") : Parcelable