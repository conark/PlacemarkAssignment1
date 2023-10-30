package org.wit.placemark.models

import timber.log.Timber.Forest.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class PlacemarkMemStore : PlacemarkStore {

    val placemarks = ArrayList<PlacemarkModel>()

    override fun findAll(): List<PlacemarkModel> {
        return placemarks
    }

    override fun findById(id:Long) : PlacemarkModel? {
        val foundPlacemark: PlacemarkModel? = placemarks.find { it.id == id }
        return foundPlacemark
    }

    override fun create(placemark: PlacemarkModel) {
        placemark.id = getId()
        placemarks.add(placemark)
        logAll()
    }

    override fun update(placemark: PlacemarkModel) {
        val foundPlacemark: PlacemarkModel? = placemarks.find { p -> p.id == placemark.id }
        if (foundPlacemark != null) {
            foundPlacemark.title = placemark.title
            foundPlacemark.description = placemark.description
            foundPlacemark.image = placemark.image
            foundPlacemark.lat = placemark.lat
            foundPlacemark.lng = placemark.lng
            foundPlacemark.zoom = placemark.zoom
            logAll()
        }
    }

    private fun logAll() {
        placemarks.forEach { i("$it") }
    }

    override fun delete(placemark: PlacemarkModel) {
        placemarks.remove(placemark)
    }

    override fun search(query: String): List<PlacemarkModel> {
        return placemarks.filter {
            it.title.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)
        }
    }

    override fun filter(function: (Any?) -> Boolean): List<PlacemarkModel> {
        // 与えられた条件（function）を使ってリスト内の要素をフィルタリングし、条件を満たす要素のリストを返す
        return placemarks.filter { function(it) }
    }
}