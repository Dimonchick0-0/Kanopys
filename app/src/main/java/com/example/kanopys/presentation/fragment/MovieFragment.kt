package com.example.kanopys.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kanopys.R
import com.example.kanopys.databinding.FragmentMovieBinding
import com.example.kanopys.domain.entity.Movie
import com.example.kanopys.presentation.KanopysApplication
import com.example.kanopys.presentation.rcv.PersonAdapter
import com.example.kanopys.presentation.state.StateMovie
import com.example.kanopys.presentation.viewmodel.MovieViewModel
import com.example.kanopys.presentation.viewmodel.ViewModelFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentMovieBinding = null")

    private val args by navArgs<MovieFragmentArgs>()

    private val component by lazy {
        (requireActivity().application as KanopysApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MovieViewModel::class.java]
    }

    private lateinit var personAdapter: PersonAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendTheIdToTheChannel()
        stateUIMovie()
        setPersonAdapter()
    }

    private fun sendTheIdToTheChannel() {
        lifecycleScope.launch {
            viewModel.channelMovie.send(args.idMovie)
        }
    }

    private fun stateUIMovie() {
        viewModel.state.onEach {
            when (it) {
                StateMovie.InitialState -> {
                    Log.d(TAG, "Начальное состояние")
                }

                StateMovie.LoadingMovie -> {
                    Log.d(TAG, "Пошла загрузка")
                }

                is StateMovie.UploadedMovie -> {
                    settingMovie(it.movie)
                }

                is StateMovie.AddMovieToFavorite -> {
                    addMovieToFavorite(it.movie)
                }

                is StateMovie.DeleteMovieFromFavorite -> {
                    deleteMovieFromFavorite(it.movie)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun settingMovie(movie: Movie) = with(binding) {
        movie.apply {
            Glide.with(requireView())
                .load(poster.url)
                .into(imMovie)
            tvMovieTitle.text = name
            tvDescrMovie.text = description
            tvRating.text = String.format("%s", rating.imdb)
            settingTheTextColorAccordingToTheCondition(tvRating, rating.imdb)
            checkMovieFavoriteAndChangeStateButton(id)
            personAdapter.submitList(persons)
        }
    }

    private fun setPersonAdapter() {
        binding.personAdaper.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            personAdapter = PersonAdapter()
            adapter = personAdapter
        }
    }

    private fun settingTheTextColorAccordingToTheCondition(rating: TextView, movieRating: Float) {
        if (movieRating < 5.0) {
            rating.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
        } else {
            rating.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
    }

    private fun addMovieToFavorite(movie: Movie) {
        binding.btnAddMovieToFavorite.setOnClickListener {
            lifecycleScope.launch {
                viewModel.addMovieToFavorite(movie)
                Toast.makeText(
                    requireContext(),
                    "Фильм: ${movie.name} добавлен в избранные",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        checkMovieFavoriteAndChangeStateButton(movie.id)
    }

    private fun deleteMovieFromFavorite(movie: Movie) {
        binding.btnDeleteMovieToFavorite.setOnClickListener {
            lifecycleScope.launch {
                viewModel.deleteMovieFromFavorite(movie.id)
                Toast.makeText(
                    requireContext(),
                    "Фильм: ${movie.name} был удалён из избранных",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun checkMovieFavoriteAndChangeStateButton(id: Int) = with(binding) {
        viewModel.checkMovieFavoriteById(id).onEach {
            if (it) {
                btnAddMovieToFavorite.visibility = View.GONE
            }
            if (!it) {
                btnDeleteMovieToFavorite.visibility = View.GONE
            }
        }.launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "MOVIEFRAGMENT"
    }
}