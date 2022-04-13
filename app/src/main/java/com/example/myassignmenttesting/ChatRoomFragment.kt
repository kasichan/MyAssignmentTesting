package com.example.myassignmenttesting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import android.content.Intent
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_chat_room.view.*


class ChatRoomFragment : Fragment() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<com.example.myassignmenttesting.model.Message>
    private lateinit var mDbRef: DatabaseReference
    private lateinit var actionBar: ActionBar

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val intent = Intent(activity, UserAdapter::class.java)
        val binding = inflater.inflate(R.layout.fragment_chat_room, container, false)
        val username = intent.getStringExtra("username")
        val receiverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().getReference()

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid


        actionBar = (activity as AppCompatActivity?)!!.supportActionBar!!
        actionBar.title = username
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        chatRecyclerView = binding.chatRecyclerView

        messageBox = binding.messageBox
        sendButton = binding.sendButton
        messageList = ArrayList()
        messageAdapter = MessageAdapter(requireContext(), messageList)

        chatRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        chatRecyclerView.adapter = messageAdapter

        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for (postSnapshot in snapshot.children) {

                        val message =
                            postSnapshot.getValue(com.example.myassignmenttesting.model.Message::class.java)
                        messageList.add(message!!)

                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        //adding the message to database
        sendButton.setOnClickListener {

            val message = messageBox.text.toString()
            val messageObject = com.example.myassignmenttesting.model.Message(message, senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")
        }
        return binding
    }
}


