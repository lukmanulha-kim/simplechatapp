package com.lukman.simplechatapp

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseMessagingService: FirebaseMessagingService() {

    fun generateNotif(tittle: String, message: String){
        val intent= Intent(this, ChatActivity::class.java)

    }
}