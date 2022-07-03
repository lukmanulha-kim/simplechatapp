package com.lukman.simplechatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var btLogout: Button

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mAuth = FirebaseAuth.getInstance()

        btLogout = findViewById(R.id.bt_logout)

        btLogout.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            finish()
            startActivity(intent)
        }
    }
}