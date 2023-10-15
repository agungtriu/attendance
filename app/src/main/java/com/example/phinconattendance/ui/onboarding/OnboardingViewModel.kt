package com.example.phinconattendance.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phinconattendance.data.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val dataStore: DataStoreManager) : ViewModel() {

    fun saveOnboardingStatus() {
        viewModelScope.launch {
            dataStore.saveOnboardingStatus()
        }
    }
}