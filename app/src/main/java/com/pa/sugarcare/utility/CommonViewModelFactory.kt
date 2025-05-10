package com.pa.sugarcare.utility

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pa.sugarcare.presentation.feature.searchproduct.vm.SearchProductViewModel
import com.pa.sugarcare.presentation.feature.signin.vm.SigninViewModel
import com.pa.sugarcare.presentation.feature.signup.vm.SignUpViewModel
import com.pa.sugarcare.presentation.feature.sugargrade.vm.ProductResultViewModel
import com.pa.sugarcare.presentation.feature.sugargrade.vm.SugarGradeViewModel
import com.pa.sugarcare.presentation.feature.userprofile.vm.EditProfileViewModel
import com.pa.sugarcare.presentation.feature.userprofile.vm.MyConsumptionViewModel
import com.pa.sugarcare.presentation.feature.userprofile.vm.ProductHistoryViewModel
import com.pa.sugarcare.presentation.feature.userprofile.vm.UserProfileViewModel
import com.pa.sugarcare.repository.network.ProductRepository
import com.pa.sugarcare.repository.network.UserRepository
import com.pa.sugarcare.utility.datasupport.StateAppPreference

class CommonViewModelFactory(
    private val userRepo: UserRepository,
    private val state: StateAppPreference,
    private val productRepo: ProductRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            SigninViewModel::class.java -> {
                SigninViewModel(userRepo) as T
            }

            UserProfileViewModel::class.java -> {
                UserProfileViewModel(userRepo) as T
            }

            SignUpViewModel::class.java -> {
                SignUpViewModel(userRepo) as T
            }

            SearchProductViewModel::class.java -> {
                SearchProductViewModel(productRepo) as T
            }

            ProductResultViewModel::class.java -> {
                ProductResultViewModel(productRepo) as T
            }

            EditProfileViewModel::class.java -> {
                EditProfileViewModel(userRepo) as T
            }

            ProductHistoryViewModel::class.java -> {
                ProductHistoryViewModel(productRepo) as T
            }

            MyConsumptionViewModel::class.java -> {
                MyConsumptionViewModel(productRepo) as T
            }

            SugarGradeViewModel::class.java -> {
                SugarGradeViewModel(productRepo) as T
            }


            else -> throw IllegalArgumentException("Class does't match")
        }
    }
}