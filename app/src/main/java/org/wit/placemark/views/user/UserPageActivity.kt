package org.wit.placemark.views.user

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.squareup.picasso.Picasso
import org.wit.placemark.R
import org.wit.placemark.databinding.ActivityUserPageBinding
import org.wit.placemark.helpers.DatePickerDialogFragment
import org.wit.placemark.models.PlacemarkModel
import org.wit.placemark.models.UserModel
import org.wit.placemark.views.placemark.PlacemarkPresenter

class UserPageActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener  {

    private lateinit var auth: FirebaseAuth
    private lateinit var presenter: UserPresenter
    private lateinit var binding: ActivityUserPageBinding
    var user = UserModel ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // set view binding
        binding = ActivityUserPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        presenter = UserPresenter(this)


        binding.DOB.setOnClickListener {
                val datePicker = DatePickerDialogFragment()
                datePicker.show(supportFragmentManager, "datePicker")

        }



        binding.btnUserinfoSave.setOnClickListener {

            if (binding.firstName.text.toString().isEmpty()) {
                 Snackbar.make(binding.root, R.string.enter_name, Snackbar.LENGTH_LONG)
                  .show()
            } else {
                val phoneNumberText = binding.userPhoneNum.text.toString()
                val phoneNumber = if (phoneNumberText.isNotEmpty()) phoneNumberText.toLong() else 0L

                val dobText = binding.DOB.text.toString()

                val provider = binding.provider.isChecked

                presenter.doAddOrSave(
                    binding.firstName.text.toString(),
                   binding.lastName.text.toString(),
                   phoneNumber,
                   binding.address.text.toString(),
                   provider,
                   dobText,
                   binding.ppsNum.text.toString(),
                    )
            }
        }



        binding.btnSignOut.setOnClickListener {
            //sign out account
            auth.signOut()
            //start another activity
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            //destroy current activity
            finish()
        }
        binding.btnUpdatePassword.setOnClickListener {
            val user = auth.currentUser
            val password = binding.etPassword.text.toString()
            if (checkPasswordField()) {
                if (user != null)
                    user?.updatePassword(password)?.addOnCompleteListener {
                        //if successfully update success
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Update password Successfully", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Log.e("error:", it.exception.toString())
                            Toast.makeText(this, "Failed Update", Toast.LENGTH_SHORT).show()
                        }

                    }
            }
        }

        binding.btnDeleteAccount.setOnClickListener {
            val user = Firebase.auth.currentUser
            user?.delete()?.addOnCompleteListener {
                if (it.isSuccessful) {
                    //account alreadu deleted
                    Toast.makeText(
                        this,
                        "Account delete Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    //account already deleted, can't sign out, so thatstart new activity
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    //catch error
                    Log.e("error:", it.exception.toString())
                }
            }
        }
    }



    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val selectedDate = "$year-${month + 1}-$dayOfMonth"
        binding.DOB.setText(selectedDate)
    }


    private fun checkPasswordField(): Boolean {

        if (binding.etPassword.text.toString() == "") {
            binding.textInputLayoutPassword.error = "This is require field "
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }
        // password length
        if (binding.etPassword.length() <= 6) {
            binding.textInputLayoutPassword.error = "More than 6 char needs "
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }

        return true
    }

    fun showUser(user: UserModel) {

            binding.firstName.setText(user.firstName)
            binding.lastName.setText(user.lastName)
            binding.userPhoneNum.setText(user.phoneNumber.toString())
            binding.address.setText(user.address)
            binding.provider.isChecked = user.provider
            binding.DOB.setText(user.DOB)
            binding.ppsNum.setText(user.ppsNumber)

    }
}