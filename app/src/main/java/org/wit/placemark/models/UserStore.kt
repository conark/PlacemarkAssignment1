package org.wit.placemark.models

import org.wit.placemark.models.UserModel


interface UserStore {
    fun findAllUser(): List<UserModel>
    fun findUserById(id:Long) : UserModel?
    fun createUser(user: UserModel)
    fun updateUser(user: UserModel)
    fun deleteUser(user: UserModel)

}