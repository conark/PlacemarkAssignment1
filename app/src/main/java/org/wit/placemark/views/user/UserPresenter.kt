package org.wit.placemark.views.user


import android.app.Activity.RESULT_OK
import org.wit.placemark.models.UserModel
import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.firebase.auth.FirebaseAuth
import org.wit.placemark.databinding.ActivityUserPageBinding
import org.wit.placemark.main.MainApp



class UserPresenter (private val view: UserPageActivity) {
    private lateinit var auth: FirebaseAuth
    var user = UserModel()
    var app: MainApp = view.application as MainApp
    var binding: ActivityUserPageBinding = ActivityUserPageBinding.inflate(view.layoutInflater)
    var edit = false;


    init {
        if (view.intent.hasExtra("user_edit")) {
            edit = true
            user = view.intent.extras?.getParcelable("user_edit")!!
            view.showUser(user)
        }
    }


    fun doAddOrSave(
        id: String, firstName: String, lastName: String, phoneNumber: Long,
        address: String, provider: Boolean,
        DOB: String, ppsNumber: String) {
        user.id= id
        user.firstName = firstName
        user.lastName = lastName
        user.phoneNumber = phoneNumber
        user.address = address
        user.provider = provider
        user.DOB = DOB
        user.ppsNumber = ppsNumber

        if (edit) {
                app.users.updateUser(user)
            } else {
                app.users.createUser(user)
            }
            view.setResult(RESULT_OK)
            view.finish()
        }

    fun doAddUser(
        id: String, firstName: String, lastName: String, phoneNumber: Long,
        address: String, provider: Boolean,
        DOB: String, ppsNumber: String
    ) {
        user.id = id
        user.firstName = firstName
        user.lastName = lastName
        user.phoneNumber = phoneNumber
        user.address = address
        user.provider = provider
        user.DOB = DOB
        user.ppsNumber = ppsNumber

        app.users.createUser(user)
        view.setResult(RESULT_OK)
        view.finish()
    }

    fun doUpdateUser(
        id: String, firstName: String, lastName: String, phoneNumber: Long,
        address: String, provider: Boolean,
        DOB: String, ppsNumber: String
    ) {
        user.id = id
        user.firstName = firstName
        user.lastName = lastName
        user.phoneNumber = phoneNumber
        user.address = address
        user.provider = provider
        user.DOB = DOB
        user.ppsNumber = ppsNumber

        app.users.updateUser(user)
        view.setResult(RESULT_OK)
        view.finish()
    }

//    fun doAddOrSave(
//        id: String, firstName: String, lastName: String, phoneNumber: Long,
//        address: String, provider: Boolean,
//        DOB: String, ppsNumber: String) {
//        user.id= id
//        user.firstName = firstName
//        user.lastName = lastName
//        user.phoneNumber = phoneNumber
//        user.address = address
//        user.provider = provider
//        user.DOB = DOB
//        user.ppsNumber = ppsNumber
//
//        if (edit) {
//            app.users.updateUser(user)
//        } else {
//            app.users.createUser(user)
//        }
//        view.setResult(RESULT_OK)
//        view.finish()
//    }

    fun cacheUser(
        id: String, firstName: String, lastName: String, phoneNumber: Long,
        address: String, provider: Boolean,
        DOB: String, ppsNumber: String
    ) {
        user.id = id
        user.firstName = firstName
        user.lastName = lastName
        user.phoneNumber = phoneNumber
        user.address = address
        user.provider = provider
        user.DOB = DOB
        user.ppsNumber = ppsNumber
    }
}





