package com.pa.sugarcare.presentation.feature.onboarding.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.pa.sugarcare.utility.datasupport.StateAppPreference
import kotlinx.coroutines.launch

class OnBoardViewModel(
    private val state: StateAppPreference
) : ViewModel() {

    fun getOnBoardState() : LiveData<String?> {
        return state.getOnBoardState().asLiveData()
    }

    fun updateOnBoardState() = viewModelScope.launch {
        state.updateOnBoardState()
    }

}