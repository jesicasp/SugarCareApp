package com.pa.sugarcare.presentation.feature.userprofile.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.models.response.UserResponse
import com.pa.sugarcare.repository.network.UserRepository
import com.pa.sugarcare.utility.Resources
import kotlinx.coroutines.launch

class EditProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _dataUser = MutableLiveData<Resources<UserResponse>>()
    val dataUser: LiveData<Resources<UserResponse>> = _dataUser

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    fun getDetailUser() {
        Log.d("DETAIL USER", "viewmodel getDetailUser()")

        viewModelScope.launch {
            _dataUser.postValue(Resources.Loading)
            try {
                val response = userRepository.getDetail()
                if (response.isSuccessful) {
                    response.body()?.let { user ->
                        _dataUser.postValue(Resources.Success(user))
                        _name.postValue(user.data.name)
                        _email.postValue(user.data.email)
                    } ?: run {
                        _dataUser.postValue(Resources.Error("Response body null"))
                    }
                } else {
                    _dataUser.postValue(Resources.Error("Gagal mengambil data user: ${response.message()}"))
                }

            } catch (e: Exception) {
                Log.e(TAG, "Exception occurred: ${e.message}")
                _dataUser.postValue(Resources.Error(e.message ?: "Unexpected error occurred"))
            }


        }

    }

    fun setName(newName: String) {
        _name.value = newName
    }

    fun setEmail(newEmail: String) {
        _email.value = newEmail
    }

    companion object {
        private const val TAG = "EditProfileVm"
    }
}