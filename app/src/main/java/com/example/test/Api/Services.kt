package com.example.test.Api

import com.example.test.Ui.GetUsers.Model.ApiResponse
import com.example.test.Ui.CreateNewUser.Request.RequestUserInfo
import com.example.test.Ui.CreateNewUser.Response.CreateUserResponse
import com.example.test.Ui.Movie.Model.MovieResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers

import retrofit2.http.POST
import retrofit2.http.Query

interface Services {
    @Headers("x-api-key: reqres-free-v1")
    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): ApiResponse

    @Headers("x-api-key: reqres-free-v1")
    @POST("users")
    suspend fun createUser(@Body requestUserInfo: RequestUserInfo): Response<CreateUserResponse>


    @Headers(
        "x-apihub-key: bV0ZGg3MvUzLKfRdJKpJ208oIZD4LsIMr0c7RrqnHM9XWhx4rI",
        "x-apihub-host: TMDB-Movies-and-TV-Shows-API-by-APIRobots.allthingsdev.co",
        "x-apihub-endpoint: 85ffa74b-8298-40ac-908a-736892987ab1"
    )
    @GET("v1/tmdb")
    suspend fun getMovieList(@Query("page") page: Int): MovieResponse

}