package com.example.phinconattendance.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.phinconattendance.data.firebase.Firebase
import com.example.phinconattendance.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val firebase: Firebase) : ViewModel() {
    fun login(
        email: String,
        password: String
    ): LiveData<Resource<String>> = firebase.login(email, password)
}