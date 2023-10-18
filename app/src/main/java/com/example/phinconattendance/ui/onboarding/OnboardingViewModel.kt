package com.example.phinconattendance.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phinconattendance.data.RepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val repositoryImp: RepositoryImp) :
    ViewModel() {

    fun saveOnboardingStatus() {
        viewModelScope.launch {
            repositoryImp.saveOnboardingStatus()
        }
    }
}