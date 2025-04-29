package com.example.test.Ui.Movie

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.test.Ui.GetUsers.PagingSource.LoadingState.LoadStateFooterAdapter

import com.example.test.Ui.Movie.Adapter.MoviePagingAdapter
import com.example.test.ViewModel.UserViewModel
import com.example.test.databinding.ActivityMovieInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieInfo : AppCompatActivity() {
    lateinit var binding: ActivityMovieInfoBinding
    private val viewModel: UserViewModel by viewModels()
    private lateinit var movieAdapter: MoviePagingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbars(binding)
        setSupportActionBar(binding.toolbar.root)
        movieAdapter = MoviePagingAdapter()
        binding.movieRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.movieRecyclerView.adapter = movieAdapter
        binding.movieRecyclerView.adapter = movieAdapter.withLoadStateHeaderAndFooter(
            header = LoadStateFooterAdapter { movieAdapter.retry() },
            footer = LoadStateFooterAdapter { movieAdapter.retry() }
        )

        lifecycleScope.launch {
            viewModel.moviePagingFlow.collectLatest {
                movieAdapter.submitData(it)
            }
        }


    }

    fun setUpToolbars(binding: ActivityMovieInfoBinding) {
        binding.toolbar.title.text = "Movies"
    }

}