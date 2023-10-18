package com.example.phinconattendance.ui.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.phinconattendance.data.RepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val repositoryImp: RepositoryImp
) : ViewModel() {
    fun getOnboarding(): Boolean {
        val status: Boolean
        runBlocking(Dispatchers.IO) {
            status = repositoryImp.getOnboardingStatus().first()
        }
        return status
    }

    fun isLogin(): LiveData<Boolean> = repositoryImp.isLogin()
}