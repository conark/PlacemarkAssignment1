package org.wit.placemark.views.placemarklist

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.placemark.views.placemark.PlacemarkView
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.PlacemarkModel
import org.wit.placemark.models.UserModel
//import org.wit.placemark.models.ProviderType
import org.wit.placemark.views.map.PlacemarkMapView
import org.wit.placemark.views.user.UserPageActivity


class PlacemarkListPresenter(val view: PlacemarkListView) {

    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private var position: Int = 0





    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }

    fun getPlacemarks() = app.placemarks.findAll()

    fun doAddPlacemark() {
        val launcherIntent = Intent(view, PlacemarkView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditPlacemark(placemark: PlacemarkModel, pos: Int) {
        val launcherIntent = Intent(view, PlacemarkView::class.java)
        launcherIntent.putExtra("placemark_edit", placemark)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doShowPlacemarksMap() {
        val launcherIntent = Intent(view, PlacemarkMapView::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun doShowUserPage() {
        val launcherIntent = Intent(view, UserPageActivity::class.java)
//        launcherIntent.putExtra("user_edit", user)
//        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun searchPlacemarks(query: String) {
        val results = app.placemarks.search(query)
        view.showPlacemarks(results)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) view.onRefresh()
                else // Deleting
                    if (it.resultCode == 99) view.onDelete(position)
            }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }

//    fun filterPlacemarksByProviderType(providerType: ProviderType) {
//        val placemarks = app.placemarks.filter {  providerType == providerType }
//        view.showPlacemarks(placemarks)
//    }

}