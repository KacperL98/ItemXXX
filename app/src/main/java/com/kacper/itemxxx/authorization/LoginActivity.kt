package com.kacper.itemxxx.authorization

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kacper.itemxxx.databinding.ActivityLoginBinding
import com.kacper.itemxxx.helpers.AuthenticationHelper.auth
import com.kacper.itemxxx.helpers.AuthenticationHelper.getUserAuth
import com.kacper.itemxxx.helpers.toastCustom
import com.kacper.itemxxx.mainPanel.PanelActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openRegisterScreen()
        openForgotPassScreen()
        getUserAuth()
        binding.btnLogin.setOnClickListener {
            loginUser()
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
                            val intent = Intent(this@LoginActivity, PanelActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()

                        } else {
                            toastCustom("Error:" + task.exception!!.message.toString())
                        }
                    }
            }
        }
    }

    private fun openRegisterScreen() {
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun openForgotPassScreen() {
        binding.btnForgotPass.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }
    }

    companion object {
        fun prepareIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}
