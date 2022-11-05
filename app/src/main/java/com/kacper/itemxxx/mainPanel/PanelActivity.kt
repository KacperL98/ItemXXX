package com.kacper.itemxxx.mainPanel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kacper.itemxxx.authorization.ChangePasswordActivity
import com.kacper.itemxxx.authorization.LoginActivity
import com.kacper.itemxxx.chat.chatsActivity.ChatActivity
import com.kacper.itemxxx.databinding.ActivityPanelBinding
import com.kacper.itemxxx.email.EmailActivity
import com.kacper.itemxxx.scanner.mainactivity.MainActivity

class PanelActivity : AppCompatActivity() {
    private var _binding: ActivityPanelBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changePassword()
        logOut()
        openScanner()
        openChat()
        openScreenWithEmail()
    }

    private fun changePassword() {
        binding.btnChange.setOnClickListener {
            val intent = Intent(this@PanelActivity, ChangePasswordActivity::class.java)
            startActivity(intent)
        }
    }

//    private fun openScreenWithEmail() {
//        binding.btnEmail.setOnClickListener {
//            val email = Intent(this@PanelActivity, EmailActivity::class.java)
//            startActivity(email)
//        }
//    }

    private fun logOut() {
        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@PanelActivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun openScanner() {
        binding.btnScanner.setOnClickListener {
            val active = Intent(this@PanelActivity, MainActivity::class.java)
            startActivity(active)
        }
    }

    private fun openChat() {
        binding.btnChat.setOnClickListener {
            val chats = Intent(this@PanelActivity, ChatActivity::class.java)
            startActivity(chats)
        }
    }
}
