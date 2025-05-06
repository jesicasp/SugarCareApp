package com.pa.sugarcare

import android.app.Application
import com.pa.sugarcare.utility.TokenStorage

class SugarCareApp : Application() {
    override fun onCreate() {
        super.onCreate()
        TokenStorage.init(this)
    }
}