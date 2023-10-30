package org.wit.placemark.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//enum class ProviderTypeDropDown {
//    GP,
//    ScanCenter,
//    Consultant
//}

@Parcelize
data class PlacemarkModel(var id: Long = 0,
                          var title: String = "",
                          var description: String = "",
                          var providerAddress: String = "",
                          var providerCity: String = "",
                          var providerCounty: String = "",
                          var providerPostcode: String = "",
                          var providerType: String = "",
                          var providerPhone: String = "",
                          var image: Uri = Uri.EMPTY,
                          var lat : Double = 0.0,
                          var lng: Double = 0.0,
                          var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable