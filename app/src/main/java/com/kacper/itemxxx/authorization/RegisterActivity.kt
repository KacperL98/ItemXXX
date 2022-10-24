package com.kacper.itemxxx.authorization

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.kacper.itemxxx.databinding.ActivityRegisterBinding
import com.kacper.itemxxx.helpers.AuthenticationHelper.auth
import com.kacper.itemxxx.helpers.AuthenticationHelper.getUserAuth
import com.kacper.itemxxx.helpers.AuthenticationHelper.refUsers
import com.kacper.itemxxx.helpers.toastCustom
import com.kacper.itemxxx.mainPanel.PanelActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private var firebaseUserID: String = ""
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openLoginScreen()

        getUserAuth()
        registerSucces()
    }

    private fun registerSucces() {
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val username: String = binding.etUsernameRegister.text.toString()
        val email: String = binding.etRegisterEmail.text.toString()
        val password: String = binding.etRegisterPassword.text.toString()
        when {
            email.isEmpty() || password.isEmpty() -> toastCustom("Please all complete fields")
            else -> {
                auth?.createUserWithEmailAndPassword(email, password)!!
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            firebaseUserID = auth?.currentUser!!.uid
                            refUsers = FirebaseDatabase.getInstance().reference.child("Users")
                                .child(firebaseUserID)
                            val userHashMap = HashMap<String, Any>()
                            userHashMap["uid"] = firebaseUserID
                            userHashMap["username"] = username
                            userHashMap["profile"] =
                                "https://firebasestorage.googleapis.com/v0/b/kacperlitwinow-e2238.appspot.com/o/profile.png?alt=media&token=f710d2d3-3bf5-48d9-9ae9-6bf196edd013"
                            userHashMap["cover"] =
                                "https://firebasestorage.googleapis.com/v0/b/kacperlitwinow-e2238.appspot.com/o/cover.jpg?alt=media&token=357899d0-d397-4680-a2fe-4814ea285e31"
                            userHashMap["status"] = "offline"
                            userHashMap["search"] = username.toLowerCase()


                            refUsers?.updateChildren(userHashMap)
                                ?.addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val intent =
                                            Intent(this@RegisterActivity, PanelActivity::class.java)

                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                        startActivity(intent)
                                        finish()
                                    }
                                }

                        } else {
                            toastCustom("Error:" + task.exception!!.message.toString())
                        }
                    }
            }
        }
    }

    private fun openLoginScreen() {
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    companion object {
        fun prepareIntent(context: Context) =
            Intent(context, RegisterActivity::class.java)
    }
}

