package com.tuwaiq.mixapp.imdb.api

import com.tuwaiq.mixapp.imdb.models.MovieResponse
import com.tuwaiq.mixapp.imdb.models.SearchMovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ImdbApi {

    @GET("/en/API/Top250Movies/k_5lbkeqi2")
   suspend fun getTop250movies(): Response<MovieResponse>

    @GET("/en/API/SearchMovie/k_95e4lc0l/{expression}")
    fun searchMovie(@Path("expression") query: String):Call<SearchMovieResponse>
}