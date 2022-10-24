package com.kacper.itemxxx.tutorialFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.kacper.itemxxx.R
import com.kacper.itemxxx.databinding.FragmentChatBinding
import com.kacper.itemxxx.databinding.FragmentFirstBinding
import com.kacper.itemxxx.helpers.toastFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.view.*

class FirstTutorialFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageViewLottie.setOnClickListener {
            viewPager?.currentItem = 1
        }
    }
}