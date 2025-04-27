package com.example.test.Ui.Movie.Model

data class MovieResponse(
    val items: List<MovieItem>,
    val meta: Meta
)

data class Meta(
    val count: Int,
    val current_page: Int,
    val items_per_page: Int,
    val number_of_pages: Int
)

data class MovieItem(
    val title: String,
    val release_date: String,
    val original_language: String,
    val popularity: Double,
    val overview: String,
    val vote_average: Double,
    val vote_count: Int
)
