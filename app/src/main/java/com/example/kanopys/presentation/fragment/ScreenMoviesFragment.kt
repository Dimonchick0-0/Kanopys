package com.example.kanopys.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kanopys.R
import com.example.kanopys.databinding.FragmentScreenMoviesBinding
import com.example.kanopys.presentation.KanopysApplication
import com.example.kanopys.presentation.navigationscreeninterface.KanopysNavigation
import com.example.kanopys.presentation.rcv.MovieScreenAdapter
import com.example.kanopys.presentation.viewmodel.MovieScreenViewModel
import com.example.kanopys.presentation.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

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
        setAdapter()
        launchMovieFragment()
    }

    private fun setAdapter() {
        binding.rcvSelectedMovie.apply {
            layoutManager = GridLayoutManager(context, 1)
            movieAdapter = MovieScreenAdapter()
            adapter = movieAdapter
        }
        setList()
    }

    private fun setList() {
        viewModel.loadAllFavoriteMovies().onEach {
            movieAdapter.submitList(it)
        }.distinctUntilChanged().launchIn(lifecycleScope)
    }

    override fun screenNavigation() {
        binding.btnNavMenu.apply {
            selectedItemId = R.id.kanopysHome
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.kanopysProfile -> launchProfileFragment()
                    R.id.kanopysSearch -> launchSearchFragment()
                }
                true
            }
        }
    }

    private fun launchProfileFragment() {
        findNavController().navigate(R.id.action_screenMoviesFragment_to_profileFragment)
    }

    private fun launchSearchFragment() {
        findNavController().navigate(R.id.action_screenMoviesFragment_to_searchMovieFragment)
    }

    private fun launchMovieFragment() {
        movieAdapter.onClickGetMovie = {
            findNavController().navigate(
                ScreenMoviesFragmentDirections.actionScreenMoviesFragmentToMovieFragment(it.id)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "TESTRETROFIT"
    }
}