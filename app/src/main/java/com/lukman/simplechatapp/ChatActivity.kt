package com.lukman.simplechatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var userReceive: TextView
    private lateinit var messageReceive: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>

    private lateinit var mDatabase: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null

    companion object {
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_UID = "extra_uid"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val nama = intent.getStringExtra(EXTRA_NAMA)
        val receiveruid = intent.getStringExtra(EXTRA_UID)

        val senderuid = FirebaseAuth.getInstance().currentUser?.uid

        mDatabase = FirebaseDatabase.getInstance().getReference()

        senderRoom = receiveruid + senderuid
        receiverRoom = senderuid + receiveruid


        userReceive = findViewById(R.id.user_receive)

        userReceive.text = nama

        messageReceive = findViewById(R.id.rv_chat)
        messageBox = findViewById(R.id.et_chatbox)
        sendButton = findViewById(R.id.iv_send_button)

        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        messageReceive.layoutManager = LinearLayoutManager(this)
        messageReceive.adapter = messageAdapter

        mDatabase.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for (postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }

                    messageAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        sendButton.setOnClickListener {

            val message = messageBox.text.toString()
            val messageObject = Message(message, senderuid)

            mDatabase.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDatabase.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }

            messageBox.setText("")

        }
    }
}