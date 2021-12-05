package com.tuwaiq.mixapp.imdb.repo

import android.util.Log
import com.tuwaiq.mixapp.imdb.api.ImdbApi
import com.tuwaiq.mixapp.imdb.models.Movie
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

private const val TAG = "ImdbRepo"
class ImdbRepo {

    private val retrofit:Retrofit = Retrofit.Builder()
        .baseUrl("https://www.imdb-api.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val imdbApi:ImdbApi = retrofit.create(ImdbApi::class.java)


    suspend fun searchForMovies(query: String):List<Movie>{
        var movies:List<Movie> = emptyList()

        val response = imdbApi.searchMovie(query).awaitResponse()
        if (response.isSuccessful){
            movies = response.body()?.movies ?: emptyList()
        }else{
            Log.e(TAG, "the error = ${response.errorBody()}")
        }

        return movies
    }


    suspend fun getTop250Movies():List<Movie>{
        var moviesList:List<Movie> = emptyList()

        val response = imdbApi.getTop250movies().awaitResponse()

        if (response.isSuccessful){
            moviesList = response.body()?.movies ?: emptyList()

        }else{
            Log.e(TAG , "the error is ${response.errorBody()}")
        }

        return moviesList
    }
}