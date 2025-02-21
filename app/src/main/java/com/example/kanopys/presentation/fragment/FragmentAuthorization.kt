package com.example.kanopys.presentation.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kanopys.R
import com.example.kanopys.databinding.FragmentAuthorizationBinding
import com.example.kanopys.presentation.KanopysApplication
import com.example.kanopys.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class FragmentAuthorization : Fragment() {

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentAuthorizationBinding = null")

    private val component by lazy {
        (requireActivity().application as KanopysApplication).component
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchRegisterFragment()
    }

    private fun launchRegisterFragment() {
        binding.goToRegister.setOnClickListener {
            findNavController().apply {
                navigate(R.id.action_fragmentAuthorization_to_fragmentRegister)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}