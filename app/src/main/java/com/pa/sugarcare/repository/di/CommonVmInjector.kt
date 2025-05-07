package com.pa.sugarcare.repository.di

import android.content.Context
import com.pa.sugarcare.repository.network.ProductRepository
import com.pa.sugarcare.repository.network.UserRepository
import com.pa.sugarcare.utility.CommonViewModelFactory
import com.pa.sugarcare.utility.datasupport.StateAppPreference
import com.pa.sugarcare.utility.datasupport.datastore

object CommonVmInjector {
    fun common(context: Context) : CommonViewModelFactory {
        val userRepo = UserRepository.getInstance()
        val state = StateAppPreference(context.datastore)
        val productRepo = ProductRepository.getInstance()


        return CommonViewModelFactory(userRepo, state, productRepo)
    }
}