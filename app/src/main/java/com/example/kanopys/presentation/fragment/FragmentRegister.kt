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
import com.example.kanopys.R
import com.example.kanopys.databinding.FragmentRegisterBinding
import com.example.kanopys.presentation.KanopysApplication
import com.example.kanopys.presentation.TextWatcherAdapter
import com.example.kanopys.presentation.state.StateAuthentication
import com.example.kanopys.presentation.viewmodel.RegisterViewModel
import com.example.kanopys.presentation.viewmodel.ViewModelFactory
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.delay
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

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        observeViewModel()
        changingTheInputDataUser()
    }

    private fun changingTheInputDataUser() {
        binding.edName.addTextChangedListener(object : TextWatcherAdapter() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tilUserName.error = null
            }
        })
        binding.edPassword.addTextChangedListener(object : TextWatcherAdapter() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tilUserPassword.error = null
            }
        })
        binding.edRepeatPassword.addTextChangedListener(object : TextWatcherAdapter() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tilUserRepeatPassword.error = null
            }
        })
        binding.edEmail.addTextChangedListener(object : TextWatcherAdapter() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tilUserEmail.error = null
            }
        })
    }

    private fun createAccount(name: String, password: String, email: String) {
        showProgressBar()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Регистрация прошла успешно!",
                        Toast.LENGTH_SHORT,
                    ).show()
                    viewModel.registerUser(name, email, password)
                    val user = Firebase.auth.currentUser
                    val profileUpdate = userProfileChangeRequest {
                        displayName = name
                    }
                    user?.updateProfile(profileUpdate)?.addOnCompleteListener {update->
                        if (update.isSuccessful) {
                            Log.d(TAG, user.displayName.toString())
                        }
                    }
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", it.exception)
                    Toast.makeText(
                        requireContext(),
                        "Регистрация не успешна!",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun showProgressBar() {
        lifecycleScope.launch {
            binding.progressBar2.visibility = View.VISIBLE
            delay(1200)
            binding.progressBar2.visibility = View.GONE
        }
    }

    private fun observeViewModel() {
        binding.btnRegisterUser.setOnClickListener {
            val name = binding.edName.text.toString()
            val password = binding.edPassword.text.toString()
            val repeatPassword = binding.edRepeatPassword.text.toString()
            val email = binding.edEmail.text.toString().trim()
            when {
                password == repeatPassword -> {
                    checkingTheEnteredData(name = name, password = password, email = email)
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
            checkingEmailUser(email)
        }
    }

    private fun checkingEmailUser(email: String) {
        lifecycleScope.launch {
            val result = viewModel.checkForEmail(email)
            if (result) {
                binding.tilUserEmail.apply {
                    error = getString(R.string.this_mail_has_already_been_register)
                }
                binding.progressBar2.visibility = View.GONE
            }
            if (!result && email.isNotEmpty()) {
                binding.tilUserEmail.apply {
                    error = null
                }
            }
        }
    }

    private fun getStateError() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                StateAuthentication.Error -> {
                    binding.apply {
                        tilUserName.error = getString(R.string.error_name)
                        tilUserPassword.error = getString(R.string.error_password)
                        tilUserRepeatPassword.error = getString(R.string.error_repeat_password)
                        tilUserEmail.error = getString(R.string.error_email)
                    }
                }
            }
        }
    }

    private fun checkingTheEnteredData(
        name: String,
        password: String,
        repeatPassword: String = "",
        email: String
    ) {
        when {
            name.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() -> {
                if (password.length < 6 && repeatPassword.length < 6) {
                    binding.tilUserPassword.error = getString(R.string.min_of_6_characters)
                    binding.tilUserRepeatPassword.error = getString(R.string.min_of_6_characters)
                } else {
                    lifecycleScope.launch {
                        createAccount(name, password, email)
                    }
                }
            }

            name.isEmpty() && password.isEmpty() && repeatPassword.isEmpty() && email.isEmpty() -> {
                viewModel.showError(name, password, repeatPassword, email)
                getStateError()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "TESTREGISTER"
    }
}