package org.wit.placemark.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import org.wit.placemark.R
import org.wit.placemark.views.placemarklist.PlacemarkListView
import org.wit.placemark.views.user.SignInActivity
import org.wit.placemark.views.user.SignUpActivity

class Title : AppCompatActivity() {

    private lateinit var  auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)

        //hide action bar
        supportActionBar?.hide()

        auth = Firebase.auth

        //put delay
        Handler (Looper.getMainLooper()).postDelayed( {

              val user = auth.currentUser
            if(user != null) {
                // if there is already an account do to home
                val intent = Intent (this, PlacemarkListView::class.java)
                startActivity(intent)
                // user to destroy current activity
                finish ()
            } else {
                // go to sign in
                // use to start activities

                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)

                // user to destroy current activity
                finish()
            }

        }, 3000) // 3000 mill is = 3 sec

    }
}