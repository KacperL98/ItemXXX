package com.kacper.itemxxx.email

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kacper.itemxxx.databinding.ActivityEmailBinding

class EmailActivity : AppCompatActivity() {
    private var _binding: ActivityEmailBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)

            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(binding.editText1.text.toString()))
            intent.putExtra(Intent.EXTRA_SUBJECT, binding.editText2.text.toString())
            intent.putExtra(Intent.EXTRA_TEXT, binding.editText3.text.toString())
            intent.type = "message/rfc822"

            startActivity(Intent.createChooser(intent, "Choose an Email client :"))
        }
    }
}