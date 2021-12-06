package com.tuwaiq.mixapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myNames")
data class Name(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0
    ,
    var name:String = ""
)
