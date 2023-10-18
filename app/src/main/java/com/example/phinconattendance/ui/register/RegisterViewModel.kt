package com.example.phinconattendance.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.phinconattendance.data.RepositoryImp
import com.example.phinconattendance.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repositoryImp: RepositoryImp) :
    ViewModel() {
    fun registerUser(
        email: String,
        fullName: String,
        password: String
    ): LiveData<Resource<String>> = repositoryImp.registerUser(email, fullName, password)
}