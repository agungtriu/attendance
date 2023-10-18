package com.example.phinconattendance.ui.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.phinconattendance.data.RepositoryImp
import com.example.phinconattendance.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val repositoryImp: RepositoryImp) :
    ViewModel() {
    fun forgotPassword(email: String): LiveData<Resource<String>> =
        repositoryImp.forgotPassword(email)
}