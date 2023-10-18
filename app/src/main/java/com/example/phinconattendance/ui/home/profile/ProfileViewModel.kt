package com.example.phinconattendance.ui.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.phinconattendance.data.RepositoryImp
import com.example.phinconattendance.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repositoryImp: RepositoryImp) : ViewModel() {
    fun getCurrentFullName(): LiveData<Resource<String>> = repositoryImp.getFullName()

    fun signOut() {
        repositoryImp.signOut()
    }
}