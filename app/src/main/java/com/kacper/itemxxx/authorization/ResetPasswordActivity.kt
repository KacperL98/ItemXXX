package com.kacper.itemxxx.authorization

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.kacper.itemxxx.databinding.ActivityResetPasswordBinding
import com.kacper.itemxxx.helpers.AuthenticationHelper.auth
import com.kacper.itemxxx.helpers.AuthenticationHelper.getUserAuth
import com.kacper.itemxxx.helpers.toastCustom

class ResetPasswordActivity : AppCompatActivity() {
    private var _binding: ActivityResetPasswordBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUserAuth()
        resetPassword()
        backBtn()
    }

    private fun resetPassword() {
        binding.btnResetPassword.setOnClickListener {
            val email = binding.edtResetEmail.text.toString().trim()

            if (TextUtils.isEmpty(email)) {
                toastCustom("Enter your email!")
            } else {
                auth?.sendPasswordResetEmail(email)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            toastCustom("Check your email!")
                        } else {
                            toastCustom("Email Failed")
                        }
                    }
            }
        }
    }

    private fun backBtn() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}