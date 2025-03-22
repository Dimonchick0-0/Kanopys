package com.example.kanopys.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kanopys.R
import com.example.kanopys.databinding.FragmentProfileBinding
import com.example.kanopys.presentation.KanopysApplication
import com.example.kanopys.presentation.navigationscreeninterface.KanopysNavigation
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(), KanopysNavigation {

    private var _binding: FragmentProfileBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentProfileBinding = null")

    private val component by lazy {
        (requireActivity().application as KanopysApplication).component
    }

    private lateinit var auth: FirebaseAuth

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        showProfileUser()
        exitUser()
        screenNavigation()
    }

    private fun showProfileUser() {
        lifecycleScope.launch {
            val user = Firebase.auth.currentUser
            user?.let {
                binding.tvNameUser.text = it.displayName.toString()
                binding.tvEmailUser.text = it.email.toString()
            }
        }
    }

    private fun exitUser() {
        binding.btnExitUser.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_profileFragment_to_fragmentAuthorization)
        }
    }

    override fun screenNavigation() {
        binding.btnNavMenu.apply {
            selectedItemId = R.id.kanopysProfile
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.kanopysHome -> launchHomeFragment()
                }
                true
            }
        }
    }

    private fun launchHomeFragment() {
        findNavController().navigate(R.id.action_profileFragment_to_screenMoviesFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "TESTINGPROFILE"
    }
}