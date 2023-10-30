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
import org.wit.placemark.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var  auth: FirebaseAuth
    private lateinit var  binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set view binging
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnSignUp.setOnClickListener{
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (checkAllField()) {
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    // if success
                    if(it.isSuccessful){
                        auth.signOut()
                        Toast.makeText (this, "Account created successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent (this, SignInActivity::class.java)
                        startActivity (intent)
                        finish()
                    }
                    else {
                        Log.e ("error: ", it.exception.toString())
                    }

                }
            }

        }
    }

    private fun checkAllField():Boolean {
        val email = binding.etEmail.text.toString()
        if(binding.etEmail.text.toString() == ""){
            binding.textInputLayoutEmail.error = "This is required field"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.textInputLayoutEmail.error = "Check Email format "
            return false
        }
        if (binding.etPassword.text.toString() ==""){
            binding.textInputLayoutPassword.error = "This is require field "
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }
        // password length
        if (binding.etPassword.length() <= 6 ){
            binding.textInputLayoutPassword.error = "More than 6 char needs "
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }

        if (binding.etConfirmPassword.text.toString() ==""){
            binding.textInputLayoutConfirmPassword.error = "This is require field "
            binding.textInputLayoutConfirmPassword.errorIconDrawable = null
            return false
        }
        if (binding.etPassword.text.toString() != binding.etConfirmPassword.text.toString()){
            binding.textInputLayoutPassword.error = "Password do not match"
            return false
        }

        return true
    }
}