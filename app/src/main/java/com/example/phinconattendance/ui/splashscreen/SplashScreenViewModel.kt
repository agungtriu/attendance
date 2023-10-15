package com.example.phinconattendance.ui.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.phinconattendance.data.datastore.DataStoreManager
import com.example.phinconattendance.data.firebase.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val dataStore: DataStoreManager, private val firebase: Firebase) : ViewModel() {
    fun getOnboarding(): LiveData<Boolean> {
        return dataStore.getOnboardingStatus().asLiveData()
    }
    fun isLogin(): LiveData<Boolean> = firebase.isLogin()
}