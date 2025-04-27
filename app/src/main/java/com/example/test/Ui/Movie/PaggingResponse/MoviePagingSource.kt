package com.example.test.Ui.Movie.PaggingResponse

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.test.Api.Services
import com.example.test.Ui.Movie.Model.MovieItem
import javax.inject.Named

class MoviePagingSource(@Named("MovieService") private val movieService: Services) :
    PagingSource<Int, MovieItem>() {
    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        return try {
            val page = params.key ?: 1
            val response = movieService.getMovieList(page)
            val movies = response.items

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}