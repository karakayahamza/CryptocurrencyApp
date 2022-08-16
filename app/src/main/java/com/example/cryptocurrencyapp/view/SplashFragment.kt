package com.example.cryptocurrencyapp.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.example.cryptocurrencyapp.R
import com.example.cryptocurrencyapp.databinding.FragmentSplashBinding
class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root

        val topAnimation = AnimationUtils.loadAnimation(requireContext(),R.anim.top_anim)
        val bottomAnimation = AnimationUtils.loadAnimation(requireContext(),R.anim.bottom_anim)


        binding.logo.startAnimation(topAnimation)
        binding.logoText.startAnimation(bottomAnimation)


        Handler(Looper.myLooper()!!).postDelayed({

            findNavController().navigate(R.id.action_splashFragment_to_currenciesFragment)
        },2500)

        return view
    }
}
