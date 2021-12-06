package com.tuwaiq.mixapp.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

private const val TAG = "DatabaseRepo"
private const val DATABASE_NAME= "db_names"

class DatabaseRepo private constructor(context: Context){


   private val database:Database = Room.databaseBuilder(
        context.applicationContext,
        Database::class.java,
        DATABASE_NAME
    ).build()

   private val dao:NamesDao = database.nameDao()

    private val coroutine = CoroutineScope(Dispatchers.IO)

    fun getName(id: Int):Flow<Name> = dao.getName(id).flowOn(Dispatchers.IO)

    fun getNames():Flow<List<Name>> = dao.getNames().flowOn(Dispatchers.IO)


    fun insertName(name: Name){
        coroutine.launch {
            dao.addName(name)
        }
    }

    fun updateName(name: Name){
       coroutine.launch {
          val s = dao.updateName(name)
           Log.e(TAG,"the i $s")
       }
    }

    fun deleteName(name: Name){
        coroutine.launch {
            dao.deleteName(name)
        }
    }




    companion object{
       private var INSTANCE:DatabaseRepo? = null

        fun init(context: Context){
            if (INSTANCE == null){
                INSTANCE = DatabaseRepo(context)
            }

        }

        fun getInstance():DatabaseRepo = INSTANCE ?: throw IllegalStateException("you to init your repo first")

    }



}