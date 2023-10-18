package com.example.phinconattendance.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.phinconattendance.data.RepositoryImp
import com.example.phinconattendance.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repositoryImp: RepositoryImp) : ViewModel() {
    fun login(
        email: String,
        password: String
    ): LiveData<Resource<String>> = repositoryImp.login(email, password)
}