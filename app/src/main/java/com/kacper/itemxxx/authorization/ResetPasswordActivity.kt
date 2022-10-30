package com.kacper.itemxxx.authorization

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kacper.itemxxx.R
import com.kacper.itemxxx.mainPanel.PanelActivity
import com.kacper.itemxxx.scanner.mainactivity.MainActivity


class ResetPasswordActivity : AppCompatActivity() {
    private var memail: EditText? = null
    private var mpass: EditText? = null
    private var mAuth: FirebaseAuth? = null
    private var mtoolbar: Toolbar? = null
    private var login: Button? = null
    var forgetpass: TextView? = null
    var loginprogress: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        mAuth = FirebaseAuth.getInstance()
        forgetpass = findViewById(R.id.forgetpass)
        loginprogress = ProgressDialog(this)
        memail = findViewById<View>(R.id.logemail) as EditText
        mpass = findViewById<View>(R.id.logpass) as EditText

        // click on forget password text
        forgetpass?.setOnClickListener(View.OnClickListener { showRecoverPasswordDialog() })
        login = findViewById<View>(R.id.logbut) as Button
        login?.setOnClickListener(View.OnClickListener {
            val email = memail!!.text.toString()
            val password = mpass!!.text.toString()
            if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
                loginprogress!!.setTitle("Logging In")
                loginprogress!!.setMessage("Please Wait ")
                loginprogress!!.setCanceledOnTouchOutside(false)
                loginprogress!!.show()
                loginUser(email, password)
            }
        })
    }

    var loadingBar: ProgressDialog? = null
    private fun showRecoverPasswordDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Recover Password")
        val linearLayout = LinearLayout(this)
        val emailet = EditText(this)

        // write the email using which you registered
        emailet.setText("Email")
        emailet.minEms = 16
        emailet.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        linearLayout.addView(emailet)
        linearLayout.setPadding(10, 10, 10, 10)
        builder.setView(linearLayout)

        // Click on Recover and a email will be sent to your registered email id
        builder.setPositiveButton("Recover",
            DialogInterface.OnClickListener { dialog, which ->
                val email = emailet.text.toString().trim { it <= ' ' }
                beginRecovery(email)
            })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
        builder.create().show()
    }

    private fun beginRecovery(email: String) {
        loadingBar = ProgressDialog(this)
        loadingBar!!.setMessage("Sending Email....")
        loadingBar!!.setCanceledOnTouchOutside(false)
        loadingBar!!.show()

        // calling sendPasswordResetEmail
        // open your email and write the new
        // password and then you can login
        mAuth!!.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            loadingBar!!.dismiss()
            if (task.isSuccessful) {
                // if isSuccessful then done message will be shown
                // and you can change the password
                Toast.makeText(this@ResetPasswordActivity, "Done sent", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@ResetPasswordActivity, "Error Occurred", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            loadingBar!!.dismiss()
            Toast.makeText(this@ResetPasswordActivity, "Error Failed", Toast.LENGTH_LONG).show()
        }
    }

    fun loginUser(email: String?, password: String?) {
        mAuth!!.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                loginprogress!!.dismiss()
                val mainIntent = Intent(this@ResetPasswordActivity, PanelActivity::class.java)
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(mainIntent)
                finish()
            } else {
                loginprogress!!.hide()
                Toast.makeText(
                    this@ResetPasswordActivity,
                    "Cannot Sign In..Please Try Again",
                    Toast.LENGTH_LONG
                )
            }
        }
    }
}