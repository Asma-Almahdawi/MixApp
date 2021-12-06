package com.tuwaiq.mixapp.imdb.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tuwaiq.mixapp.imdb.api.ImdbApi
import com.tuwaiq.mixapp.imdb.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query
import java.util.concurrent.Flow

private const val TAG = "ImdbRepo"
open class ImdbRepo {

    private val retrofit:Retrofit = Retrofit.Builder()
        .baseUrl("https://www.imdb-api.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val imdbApi:ImdbApi = retrofit.create(ImdbApi::class.java)


    val getMovie:kotlinx.coroutines.flow.Flow<List<Movie>> = flow {



            val response = imdbApi.getTop250movies()
            if (response.isSuccessful){
                Log.e(TAG , "the ${response.raw()}")
                response.body()?.movies?.let { emit(it) }
            }else{
                Log.e(TAG , "the error ${response.errorBody()}")
            }


    }.flowOn(Dispatchers.IO)

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


     fun getTop250Movies():LiveData<List<Movie>>{
            return liveData(Dispatchers.IO) {
                    val response = imdbApi.getTop250movies()
                    if (response.isSuccessful){
                        response.body()?.movies?.let { emit(it) }
                    }else{
                        Log.e(TAG , "the error is ${response.errorBody()}")
                    }
                }

    }
}