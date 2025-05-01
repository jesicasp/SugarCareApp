package com.pa.sugarcare.utility

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pa.sugarcare.presentation.feature.signin.vm.SigninViewModel
import com.pa.sugarcare.repository.network.UserRepository
import com.pa.sugarcare.utility.datasupport.StateAppPreference

class CommonViewModelFactory(
    private val userRepo: UserRepository,
    private val state: StateAppPreference,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            SigninViewModel::class.java -> {
                SigninViewModel(userRepo) as T
            }

//            LoginViewModel::class.java -> {
//                LoginViewModel(repo, localDataSource, state) as T
//            }


            else -> throw IllegalArgumentException("Class does't match")
        }
    }
}