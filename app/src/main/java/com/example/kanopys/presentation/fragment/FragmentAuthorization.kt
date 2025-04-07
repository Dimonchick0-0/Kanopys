package com.example.kanopys.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kanopys.R
import com.example.kanopys.databinding.FragmentAuthorizationBinding
import com.example.kanopys.presentation.KanopysApplication
import com.example.kanopys.presentation.TextWatcherAdapter
import com.example.kanopys.presentation.state.StateAuthentication
import com.example.kanopys.presentation.viewmodel.MainViewModel
import com.example.kanopys.presentation.viewmodel.ViewModelFactory
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
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

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        launchRegisterFragment()
        checkingTheEnteredData()
        changingTheInputDataUser()
    }

    private fun authenticationUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    lifecycleScope.launch {
                        viewModel.authUser(email, password)
                        findNavController().navigate(R.id.action_fragmentAuthorization_to_profileFragment)
                    }
                } else {
                    Log.w(TAG, "signInWithEmail:failure", it.exception)
                    Toast.makeText(
                        requireContext(),
                        "Пользователь не найден! Проверьте данные.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun launchRegisterFragment() {
        binding.goToRegister.setOnClickListener {
            findNavController().apply {
                navigate(R.id.action_fragmentAuthorization_to_fragmentRegister)
            }
        }
    }

    private fun checkingTheEnteredData() {
        binding.buttonAuthUser.setOnClickListener {
            val email = binding.emailUserEd.text.toString()
            val password = binding.passwordUser.text.toString()
            when {
                email.isEmpty() && password.isEmpty() -> {
                    viewModel.showError(email, password)
                    observeViewModel()
                }
                email.isNotEmpty() && password.isNotEmpty() -> {
                    if (password.length < 6) {
                        binding.tilPassword.error = getString(R.string.min_of_6_characters)
                    } else {
                        authenticationUser(email, password)
                    }
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                StateAuthentication.Error -> {
                    binding.tilEmailAuth.error = getString(R.string.error_email)
                    binding.tilPassword.error = getString(R.string.error_password)
                }
            }
        }
    }

    private fun changingTheInputDataUser() {
        binding.emailUserEd.addTextChangedListener(object : TextWatcherAdapter() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tilEmailAuth.error = null
            }
        })

        binding.passwordUser.addTextChangedListener(object: TextWatcherAdapter() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tilPassword.error = null
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "TESTAUTH"
    }
}