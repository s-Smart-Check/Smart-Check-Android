package com.example.smartattendancecheckapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.smartattendancecheckapp.databinding.FragmentJoinBinding

class JoinFragment: Fragment() {

    private lateinit var binding: FragmentJoinBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJoinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)

        binding.btnJoinLogin.setOnClickListener {
            navController.navigate(R.id.action_joinFragment_to_initFragment)
        }
    }
}