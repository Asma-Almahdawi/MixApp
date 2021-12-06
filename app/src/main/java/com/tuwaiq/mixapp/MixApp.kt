package com.tuwaiq.mixapp

import android.app.Application
import com.tuwaiq.mixapp.database.DatabaseRepo

class MixApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseRepo.init(this)
    }
}