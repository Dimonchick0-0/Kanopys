package com.example.kanopys.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.kanopys.R
import com.example.kanopys.databinding.FragmentProfileBinding
import com.example.kanopys.databinding.FragmentScreenMoviesBinding
import com.example.kanopys.domain.entity.Movie
import com.example.kanopys.domain.entity.Movies
import com.example.kanopys.presentation.KanopysApplication
import com.example.kanopys.presentation.navigationscreeninterface.KanopysNavigation
import com.example.kanopys.presentation.rcv.MovieScreenAdapter
import com.example.kanopys.presentation.viewmodel.MovieScreenViewModel
import com.example.kanopys.presentation.viewmodel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

class ScreenMoviesFragment : Fragment(), KanopysNavigation {

    private var _binding: FragmentScreenMoviesBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentScreenMoviesBinding = null")

    private val component by lazy {
        (requireActivity().application as KanopysApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MovieScreenViewModel::class]
    }

    private lateinit var movieAdapter: MovieScreenAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScreenMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        screenNavigation()
        binding.searchMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                lifecycleScope.launch {
                    val movies = test(1, 1, query)
                    movies.docs.forEach {
                        binding.textView2.text = it.name
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

    }

    private suspend fun test(page: Int, limit: Int, title: String): Movies {
        return viewModel.searchMovie(page, limit, title)
    }

    private fun setAdapter() {
        binding.rcvMovie.apply {
            layoutManager = GridLayoutManager(context, 1)
            movieAdapter = MovieScreenAdapter()
            adapter = movieAdapter
        }
    }

    override fun screenNavigation() {
        binding.btnNavMenu.apply {
            selectedItemId = R.id.kanopysHome
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.kanopysProfile -> launchProfileFragment()
                }
                true
            }
        }
    }

    private fun launchProfileFragment() {
        findNavController().navigate(R.id.action_screenMoviesFragment_to_profileFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "TESTRETROFIT"
    }
}