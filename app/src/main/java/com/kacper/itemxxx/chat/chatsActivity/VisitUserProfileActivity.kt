package com.kacper.itemxxx.chat.chatsActivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kacper.itemxxx.R
import com.kacper.itemxxx.chat.model.Users
import com.kacper.itemxxx.databinding.ActivityVisitUserProfileBinding
import com.squareup.picasso.Picasso

class VisitUserProfileActivity : AppCompatActivity() {
    private var userVisitId: String = ""
    var user: Users? = null
    private var _binding: ActivityVisitUserProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVisitUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sendMessage()
        userVisitId = intent.getStringExtra("visit_id")!!
        val ref = FirebaseDatabase.getInstance().reference.child("Users").child(userVisitId)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(pO: DataSnapshot) {
                if (pO.exists()) {
                    user = pO.getValue(Users::class.java)
                    binding.usernameDisplay.text = user!!.username
                    Picasso.get().load(user!!.profile).placeholder(R.drawable.profile)
                        .into(binding.profileDisplay1)
                    Picasso.get().load(user!!.cover).placeholder(R.drawable.cover)
                        .into(binding.coverDisplay1)
                }
            }

            override fun onCancelled(pO: DatabaseError) {
            }
        })
    }

    private fun sendMessage() {
        binding.sendMsgBtn.setOnClickListener {
            val intent = Intent(this@VisitUserProfileActivity, MessageChatActivity::class.java)
            intent.putExtra("visit_id", user!!.uid)
            startActivity(intent)
        }
    }
}