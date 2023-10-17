package com.example.phinconattendance.ui.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.phinconattendance.data.datastore.DataStoreManager
import com.example.phinconattendance.data.firebase.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val dataStore: DataStoreManager,
    private val firebase: Firebase
) : ViewModel() {
    fun getOnboarding(): Boolean {
        val status: Boolean
        runBlocking(Dispatchers.IO) {
            status = dataStore.getOnboardingStatus().first()
        }
        return status
    }

    fun isLogin(): LiveData<Boolean> = firebase.isLogin()
}