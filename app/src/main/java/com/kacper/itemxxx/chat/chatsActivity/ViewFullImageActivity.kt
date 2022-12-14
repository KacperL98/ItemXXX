package com.kacper.itemxxx.chat.chatsActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kacper.itemxxx.databinding.ActivityViewFullImageBinding
import com.squareup.picasso.Picasso

class ViewFullImageActivity : AppCompatActivity() {
    private var imageUrl: String = ""
    private var _binding: ActivityViewFullImageBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityViewFullImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageUrl = intent.getStringExtra("url")!!
        Picasso.get().load(imageUrl).into(binding.imageViewer)

    }
}
