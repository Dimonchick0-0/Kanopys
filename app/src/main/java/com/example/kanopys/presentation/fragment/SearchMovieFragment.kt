package com.example.kanopys.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kanopys.databinding.FragmentSearchMovieBinding
import com.example.kanopys.domain.entity.Movies
import com.example.kanopys.presentation.KanopysApplication
import com.example.kanopys.presentation.rcv.MovieScreenAdapter
import com.example.kanopys.presentation.viewmodel.SearchViewModel
import com.example.kanopys.presentation.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchMovieFragment : Fragment() {

    private var _binding: FragmentSearchMovieBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentSearchMovieBinding = null")

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
        ViewModelProvider(this, viewModelFactory)[SearchViewModel::class]
    }

    private lateinit var movieAdapter: MovieScreenAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMovieAdapter()
    }

    private fun setMovieAdapter() {
        binding.searchFilmsRcv.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)
            movieAdapter = MovieScreenAdapter()
            adapter = movieAdapter
        }
        requestAndReceiveAMovie()
    }

        private fun requestAndReceiveAMovie() {
        binding.searchMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                checkingAndReceiveAMovie(query)
                return false
            }

            override fun onQueryTextChange(newText: String?) = false
        })
    }

    private fun checkingAndReceiveAMovie(query: String) {
        lifecycleScope.launch {
            runCatching {
                getMovie(1, 1, query)
                    .onEach { it.docs.forEach { movie ->
                        movieAdapter.submitList(it.docs)
                        Log.d(TAG, movie.poster.url)
                    } }.collect()
            }.onFailure {
                Toast.makeText(
                    requireContext(),
                    "Отсутствует подключение к Интернету!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getMovie(page: Int, limit: Int, title: String): Flow<Movies> {
        return viewModel.searchMovie(page, limit, title)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "TESTSEARCHFILMS"
    }
}