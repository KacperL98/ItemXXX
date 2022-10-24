package com.kacper.itemxxx.tutorialFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kacper.itemxxx.authorization.LoginActivity
import com.kacper.itemxxx.authorization.RegisterActivity
import com.kacper.itemxxx.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        openLoginScreen()
        openRegisterScreen()
    }

    private fun openLoginScreen() {
        binding.login.setOnClickListener {
            activity?.finish()
            startActivity(
                LoginActivity.prepareIntent(
                    requireContext()
                )
            )
        }
    }

    private fun openRegisterScreen() {
        binding.register2.setOnClickListener {
            activity?.finish()
            startActivity(
                RegisterActivity.prepareIntent(
                    requireContext()
                )
            )
        }
    }
}
