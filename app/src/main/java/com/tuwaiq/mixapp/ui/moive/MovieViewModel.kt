package com.tuwaiq.mixapp.ui.moive

import androidx.lifecycle.*
import com.tuwaiq.mixapp.imdb.models.Movie
import com.tuwaiq.mixapp.imdb.repo.ImdbRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.Query

class MovieViewModel : ViewModel() {

    private val repo:ImdbRepo = ImdbRepo()

    private val searchTermLiveData:MutableLiveData<String> = MutableLiveData()

    init {
        searchTermLiveData.value = ""
    }
    fun getMovies():LiveData<List<Movie>>{
        var tempList:List<Movie> = emptyList()
        val moviesLiveData:MutableLiveData<List<Movie>> = MutableLiveData()

       return  Transformations.switchMap(searchTermLiveData){term->
           viewModelScope.launch(Dispatchers.IO){
               tempList = if (term.isEmpty()){
                   repo.getTop250Movies()
               }else{
                   repo.searchForMovies(term)
               }
           }.invokeOnCompletion {
               viewModelScope.launch {
                   moviesLiveData.value = tempList
               }
           }

            moviesLiveData
        }


    }

    fun sendQuery(query: String){
        searchTermLiveData.value = query
    }

}