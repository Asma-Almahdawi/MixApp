package com.tuwaiq.mixapp.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NamesDao {


    @Query("SELECT * FROM myNames")
    fun getNames():Flow<List<Name>>

    @Query("SELECT * from myNames WHERE id=(:id)")
    fun getName(id:Int):Flow<Name>

    @Insert( onConflict = OnConflictStrategy.IGNORE)
    suspend fun addName(name: Name)

    @Update
    suspend fun updateName(name: Name):Int

    @Delete()
    suspend fun deleteName(name: Name)

}