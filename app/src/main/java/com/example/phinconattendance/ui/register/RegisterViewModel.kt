package com.example.phinconattendance.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.phinconattendance.data.firebase.Firebase
import com.example.phinconattendance.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val firebase: Firebase) : ViewModel() {
    fun registerUser(
        email: String,
        fullName: String,
        password: String
    ): LiveData<Resource<String>> = firebase.registerUser(email, fullName, password)
}