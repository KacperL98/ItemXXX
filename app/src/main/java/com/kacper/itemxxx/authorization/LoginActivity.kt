package com.kacper.itemxxx.authorization

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.kacper.itemxxx.databinding.ActivityLoginBinding
import com.kacper.itemxxx.helpers.AuthenticationHelper.auth
import com.kacper.itemxxx.helpers.AuthenticationHelper.getUserAuth
import com.kacper.itemxxx.helpers.toastCustom
import com.kacper.itemxxx.mainPanel.PanelActivity


class LoginActivity : AppCompatActivity() {

    private var userEmail: String = ""
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openRegisterScreen()
        getUserAuth()
        binding.btnLogin.setOnClickListener {
            loginUser()
        }
    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().currentUser?.let {
            Intent(this@LoginActivity, PanelActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun loginUser() {
        val email: String = binding.etLoginEmail.text.toString()
        val password: String = binding.etLoginPassword.text.toString()

        when {
            email.isEmpty() || password.isEmpty() -> toastCustom("Please fill each form")
            else -> {
                auth?.signInWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            updateFirebaseUserDisplayName()
                        } else {
                            toastCustom("Error:" + task.exception!!.message.toString())
                        }
                    }
            }
        }
    }

    private fun updateFirebaseUserDisplayName() {
        FirebaseAuth.getInstance().currentUser?.apply {
            val profileUpdates: UserProfileChangeRequest =
                UserProfileChangeRequest.Builder().setDisplayName(userEmail).build()
            updateProfile(profileUpdates).addOnCompleteListener {
                when (it.isSuccessful) {
                    true -> apply {
                        Intent(this@LoginActivity, PanelActivity::class.java).apply {
                            startActivity(this)
                            finish()
                        }
                    }
                    false -> toastCustom("Login has failed")
                }
            }
        }
    }

    private fun openRegisterScreen() {
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    companion object {
        fun prepareIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}
