package com.example.test.Repo


import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.test.Api.Services
import com.example.test.RoomDB.AppDao
import com.example.test.RoomDB.UserResponseRoomDB
import com.example.test.Ui.CreateNewUser.Request.RequestUserInfo
import com.example.test.Ui.CreateNewUser.Response.CreateUserResponse

import com.example.test.Ui.GetUsers.PagingSource.UserPagingSource
import com.example.test.Ui.GetUsers.Model.User
import com.example.test.Ui.Movie.Model.MovieItem
import com.example.test.Ui.Movie.PaggingResponse.MoviePagingSource


import retrofit2.Response

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class Repository @Inject constructor(
    @Named("ReqresService") private val reqresService: Services,
    @Named("MovieService") private val movieService: Services,
    private val dao: AppDao,
) {
    fun getUsers(): Pager<Int, User> {
        return Pager(
            config = PagingConfig(pageSize = 6),
            pagingSourceFactory = { UserPagingSource(reqresService) }
        )
    }

    suspend fun postData(request: RequestUserInfo): Response<CreateUserResponse> {
        val response = reqresService.createUser(request)

        if (response.isSuccessful) {
            response.body()?.let {
                val entity = UserResponseRoomDB(
                    name = it.name,
                    job = it.job,
                    id = it.id,
                    createdAt = it.createdAt
                )
                dao.insertUser(entity)
            }
        }

        return response
    }

    fun getUsersFromDb(): LiveData<List<UserResponseRoomDB>> {
        return dao.getAllUsers()
    }

    fun getMovies(): Pager<Int, MovieItem> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { MoviePagingSource(movieService) }
        )
    }

}