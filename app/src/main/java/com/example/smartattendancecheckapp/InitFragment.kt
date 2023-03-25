package com.example.smartattendancecheckapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.smartattendancecheckapp.databinding.FragmentInitBinding

class InitFragment: Fragment() {

    private lateinit var binding: FragmentInitBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)

        binding.btnJoin.setOnClickListener {
            navController.navigate(R.id.action_initFragment_to_joinFragment)
        }

        binding.btnLogin.setOnClickListener {
            navController.navigate(R.id.action_initFragment_to_loginFragment)
        }
    }
}