package com.example.test.Ui.Movie

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.test.R
import com.example.test.ViewModel.UserViewModel
import com.example.test.databinding.ActivityMovieInfoBinding
import com.example.test.databinding.ActivityMoviesDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class MoviesDetails : AppCompatActivity() {
    lateinit var binding: ActivityMoviesDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbars(binding)
        setSupportActionBar(binding.movieDetailsToolbar.root)

        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("discription")

        binding.movieDetails.Title.text = title
        binding.movieDetails.description.text = description

    }

    fun setUpToolbars(binding: ActivityMoviesDetailsBinding) {
        binding.movieDetailsToolbar.title.text = "Movies"
    }
}