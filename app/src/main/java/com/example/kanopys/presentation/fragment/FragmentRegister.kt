package com.example.kanopys.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.kanopys.R
import com.example.kanopys.databinding.FragmentRegisterBinding
import com.example.kanopys.presentation.KanopysApplication
import com.example.kanopys.presentation.viewmodel.RegisterViewModel
import com.example.kanopys.presentation.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentRegister : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentRegisterBinding = null")

    private val component by lazy {
        (requireActivity().application as KanopysApplication).component
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        changingTheInputDataUser()
    }

    private fun changingTheInputDataUser() {
        binding.edName.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorName()
                binding.tilUserName.error = null
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.edPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorPassword()
                binding.tilUserPassword.error = null
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.edRepeatPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorRepeatPassword()
                binding.tilUserRepeatPassword.error = null
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun observeViewModel() {
        binding.btnRegisterUser.setOnClickListener {
            val name = binding.edName.text.toString()
            val password = binding.edPassword.text.toString()
            val repeatPassword = binding.edRepeatPassword.text.toString()
            when {
                password == repeatPassword -> {
                    checkingTheEnteredData(name, password)
                }
                password != repeatPassword -> {
                    binding.tilUserPassword.apply {
                        error = getString(R.string.passwords_not_match)
                    }
                    binding.tilUserRepeatPassword.apply {
                        error = getString(R.string.passwords_not_match)
                    }
                }
            }
        }
    }

    private fun checkingTheEnteredData(name: String, password: String, repeatPassword: String = "") {
        when {
            name.isNotEmpty() && password.isNotEmpty() -> {
                lifecycleScope.launch {
                    viewModel.registerUser(name, password)
                }
            }
            name.isEmpty() && password.isEmpty() && repeatPassword.isEmpty() -> {
                viewModel.showError(name, password, repeatPassword)
                binding.apply {
                    tilUserName.error = getString(R.string.error_name)
                    tilUserPassword.error = getString(R.string.error_password)
                    tilUserRepeatPassword.error = getString(R.string.error_repeat_password)
                }
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}