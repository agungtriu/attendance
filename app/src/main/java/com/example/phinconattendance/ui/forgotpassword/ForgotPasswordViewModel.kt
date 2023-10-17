package com.example.phinconattendance.ui.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.phinconattendance.data.firebase.Firebase
import com.example.phinconattendance.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val firebase: Firebase) : ViewModel() {
    fun forgotPassword(email: String): LiveData<Resource<String>> = firebase.forgotPassword(email)
}