package com.lukman.simplechatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: TextView
    private lateinit var btnSignup: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        etNama = findViewById(R.id.et_nama)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.et_login)
        btnSignup = findViewById(R.id.bt_daftar)

        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnSignup.setOnClickListener {
            val nama = etNama.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            register(nama, email, password)
        }
    }

    private fun register(nama: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUser(nama, email, mAuth.currentUser?.uid!!)
                    val intent = Intent(this@Register, MainActivity::class.java)
                    startActivity(intent)
                    
                } else {
                    Toast.makeText(this@Register, "Error", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUser(nama: String, email: String, uid: String) {
        mDatabase = FirebaseDatabase.getInstance().getReference()

        mDatabase.child("users").child(uid).setValue(User(nama, email, uid))
    }
}