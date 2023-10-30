package org.wit.placemark.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import org.wit.placemark.R
import org.wit.placemark.views.user.SignInActivity
import org.wit.placemark.views.user.SignUpActivity

class Title : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)

        //hide action bar
        supportActionBar?.hide()


        //put delay
        Handler (Looper.getMainLooper()).postDelayed( {

            val intent = Intent (this, SignInActivity::class.java)
            startActivity(intent)

            // user to destroy current activity
            finish ()

        }, 3000) // 3000 mill is = 3 sec

    }
}