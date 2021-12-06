package com.tuwaiq.mixapp.ui.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tuwaiq.mixapp.database.DatabaseRepo
import com.tuwaiq.mixapp.database.Name

class RoomViewModel : ViewModel() {

    private val repo = DatabaseRepo.getInstance()


    val namesLiveData:LiveData<List<Name>> = repo.getNames().asLiveData()

    fun addName(name: Name){
        repo.insertName(name)
    }
}