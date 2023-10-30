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
import org.wit.placemark.databinding.ActivitySignInBinding
import org.wit.placemark.main.MainApp

class SignInActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //remove action bar
        supportActionBar?.hide()

        auth = Firebase.auth

        binding.btnSignIn.setOnClickListener {
            //if true
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (checkAllField()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //already sign in
                        Toast.makeText(this, "Successfuly Sign In", Toast.LENGTH_SHORT).show()
                        //go to another activity
                        //start activity
                        val intent = Intent(this, UserPageActivity::class.java)
                        startActivity(intent)
                        //destroy activitUserPageActivity
                        finish()

                    } else {
                        Toast.makeText(this, "Sign In Error: ", Toast.LENGTH_SHORT).show()
                        Log.e("error", it.exception.toString())
                    }
                }

            }
        }
        binding.tvCreateAccount.setOnClickListener{
            val intent = Intent (this, SignUpActivity::class.java)
            startActivity (intent)
            finish()
        }
        binding.tvForgotPassword.setOnClickListener{
            val intent = Intent (this, ForgotPasswordActivity::class.java)
            startActivity (intent)
            finish()

        }
    }

    private fun checkAllField ():Boolean {
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

        return true
}
}