package com.tuwaiq.mixapp.ui.room.details

import androidx.lifecycle.*
import com.tuwaiq.mixapp.database.DatabaseRepo
import com.tuwaiq.mixapp.database.Name
import kotlinx.coroutines.flow.first

class RoomDetailsViewModel : ViewModel() {

    private val repo = DatabaseRepo.getInstance()


    private val loadingLiveData:MutableLiveData<Int> = MutableLiveData()


    val nameLiveData:LiveData<Name> =
        Transformations.switchMap(loadingLiveData){
            liveData { val names =  repo.getName(it).first()
                 emit(names)
            }
        }

    fun addName(name: Name) = repo.insertName(name)

    fun updateName(name: Name) = repo.updateName(name)

    fun loadName(id: Int){
        loadingLiveData.value = id
    }

}