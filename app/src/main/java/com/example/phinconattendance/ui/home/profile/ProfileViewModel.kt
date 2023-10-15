package com.example.phinconattendance.ui.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.phinconattendance.data.firebase.Firebase
import com.example.phinconattendance.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val firebase: Firebase) : ViewModel() {
    fun getCurrentFullName(): LiveData<Resource<String>> = firebase.getFullName()

    fun signOut() {
        firebase.signOut()
    }
}