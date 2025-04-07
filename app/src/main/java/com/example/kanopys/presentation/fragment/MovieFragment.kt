package com.example.kanopys.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.kanopys.databinding.FragmentMovieBinding
import com.example.kanopys.presentation.KanopysApplication
import com.example.kanopys.presentation.viewmodel.MovieViewModel
import com.example.kanopys.presentation.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
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
        setMovieAfterReceivingIt()
        addMovieToFavorite()
    }

    private fun setMovieAfterReceivingIt() = with(binding) {
        lifecycleScope.launch {
            viewModel.getMovieById(args.idMovie).onEach {
                Glide.with(requireView())
                    .load(it.poster.url)
                    .into(imMovie)
                tvMovieTitle.text = it.name
                tvDescrMovie.text = it.description
            }.collect()
        }
    }

    private fun addMovieToFavorite() {
        binding.btnAddMovieToFavorite.setOnClickListener {
            lifecycleScope.launch {
                viewModel.getMovieById(args.idMovie).onEach {
                    viewModel.addMovieToFavorite(it)
                }.collect()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}