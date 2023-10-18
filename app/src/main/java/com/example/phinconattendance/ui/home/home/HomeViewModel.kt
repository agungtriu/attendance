package com.example.phinconattendance.ui.home.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.phinconattendance.data.RepositoryImp
import com.example.phinconattendance.data.datastore.CheckInModel
import com.example.phinconattendance.data.firebase.Entity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repositoryImp: RepositoryImp
) : ViewModel() {
    fun checkIn(location: Entity, position: Int) {
        repositoryImp.checkIn(location)
        viewModelScope.launch {
            repositoryImp.saveCheckInStatus(CheckInModel(isCheckIn = true, position = position))
        }
    }

    fun getCheckInStatus(): LiveData<CheckInModel> {
        return repositoryImp.getCheckInStatus().asLiveData()
    }

    fun checkOut(location: Entity) {
        repositoryImp.checkOut(location)
        viewModelScope.launch {
            repositoryImp.saveCheckOutStatus()
        }
    }
}