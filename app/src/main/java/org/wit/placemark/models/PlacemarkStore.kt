package org.wit.placemark.models

interface PlacemarkStore {
    fun findAll(): List<PlacemarkModel>
    fun findById(id:Long) : PlacemarkModel?
    fun create(placemark: PlacemarkModel)
    fun update(placemark: PlacemarkModel)
    fun delete(placemark: PlacemarkModel)
    fun search(query: String): List<PlacemarkModel>
    fun filter(function: (Any?) -> Boolean): List<PlacemarkModel>

}