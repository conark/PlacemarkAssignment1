package org.wit.placemark.views.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import org.wit.placemark.R
import org.wit.placemark.databinding.ActivityUserPageBinding

class UserPageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityUserPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // set view binding
        binding = ActivityUserPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

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
//        // Update Email doesn't work Firebase Setting?
//        binding.btnUpdateEmail.setOnClickListener {
//            val user = auth.currentUser
//            val email = binding.etEmail.text.toString()
//            if (checkEmailField()) {
//                user?.updateEmail(email)?.addOnCompleteListener {
//                    // if success
//                    if (it.isSuccessful) {
//                        Toast.makeText(
//                            this,
//                            "Update Email Successfully",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    } else {
//                        Log.e("error:", it.exception.toString())
//                        Toast.makeText(this, "Failed Email Update", Toast.LENGTH_SHORT)
//                            .show()
//                    }
//                }
//            }
//        }
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

//    private fun checkEmailField(): Boolean {
//        val email = binding.etEmail.text.toString()
//        if (binding.etEmail.text.toString() == "") {
//            binding.textInputLayoutEmail.error = "This is required field"
//            return false
//        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            binding.textInputLayoutEmail.error = "Check Email format "
//            return false
//        }
//        return true
//    }
}