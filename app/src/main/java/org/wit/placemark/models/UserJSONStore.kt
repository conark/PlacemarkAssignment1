package org.wit.placemark.models

import org.wit.placemark.models.UserModel
import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.placemark.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_USERFILE = "users.json"
val gsonUserBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriUserParser())
    .create()
val userListType: Type = object : TypeToken<ArrayList<UserModel>>() {}.type

//fun generateUserId(): Long {
//    return Random().nextLong()
//}

class UserJSONStore(private val context: Context) : UserStore {

    var users = mutableListOf<UserModel>()

    init {
        if (exists(context, JSON_USERFILE)) {
            deserialize()
        }
    }

    override fun findAllUser(): MutableList<UserModel> {
        logAll()
        return users
    }

//    override fun findUserById(id:String) : UserModel? {
//        val foundUser: UserModel? = users.find { it.id == id }
//        return foundUser
//    }
    override fun findUserById(id: String): UserModel? {
        val user = users.find { it.id == id }
        return user
}

    override fun createUser(user: UserModel) {
//        user.id = generateUserId()
        users.add(user)
        serialize()
    }

    override fun updateUser(user: UserModel) {
        val usersList = findAllUser() as ArrayList<UserModel>
        var foundUser: UserModel? = usersList.find { p -> p.id == user.id }
        if (foundUser != null) {
            foundUser.firstName = user.firstName
            foundUser.lastName = user.lastName
            foundUser.phoneNumber = user.phoneNumber
            foundUser.provider = user.provider
            foundUser.address = user.address
            foundUser.DOB = user.DOB
            foundUser.ppsNumber = user.ppsNumber


        }
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonUserBuilder.toJson(users, userListType)
        write(context, JSON_USERFILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_USERFILE)
        users = gsonUserBuilder.fromJson(jsonString, userListType)
    }

    override fun deleteUser(user: UserModel) {
        users.remove(user)
        serialize()
    }

    private fun logAll() {
        users.forEach { Timber.i("$it") }
    }
}

class UriUserParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}