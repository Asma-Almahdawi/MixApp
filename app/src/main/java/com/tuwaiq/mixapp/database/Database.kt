package com.tuwaiq.mixapp.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Name::class], version = 1, exportSchema = true)
abstract class Database : RoomDatabase() {

    abstract fun nameDao():NamesDao


}