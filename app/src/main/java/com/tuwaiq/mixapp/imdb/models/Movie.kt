package com.tuwaiq.mixapp.imdb.models

import com.google.gson.annotations.SerializedName

data class Movie(
    val id:String = "",
    val rank:String = "",
    val title:String = "",
    val year:String = "",
    @SerializedName("image")
    val img_url :String = "",
    val crew:String = "",
    val imDbRating:String ="",
)