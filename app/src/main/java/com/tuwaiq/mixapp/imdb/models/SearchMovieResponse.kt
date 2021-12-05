package com.tuwaiq.mixapp.imdb.models

import com.google.gson.annotations.SerializedName

class SearchMovieResponse {
    @SerializedName("results")
    lateinit var movies:List<Movie>
}