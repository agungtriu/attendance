package com.example.phinconattendance.ui.home.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.phinconattendance.data.datastore.CheckInModel
import com.example.phinconattendance.data.datastore.DataStoreManager
import com.example.phinconattendance.data.firebase.Entity
import com.example.phinconattendance.data.firebase.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStore: DataStoreManager,
    private val firebase: Firebase
) : ViewModel() {
    fun checkIn(location: Entity, position: Int) {
        firebase.checkIn(location)
        viewModelScope.launch {
            dataStore.saveCheckInStatus(CheckInModel(isCheckIn = true, position = position))
        }
    }

    fun getCheckInStatus(): LiveData<CheckInModel> {
        return dataStore.getCheckInStatus().asLiveData()
    }

    fun checkOut(location: Entity) {
        firebase.checkOut(location)
        viewModelScope.launch {
            dataStore.saveCheckOutStatus()
        }
    }
}