package com.example.phinconattendance.data

import androidx.lifecycle.LiveData
import com.example.phinconattendance.data.datastore.CheckInModel
import com.example.phinconattendance.data.firebase.AttendanceEntity
import com.example.phinconattendance.data.firebase.LocationEntity
import com.example.phinconattendance.vo.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun registerUser(
        email: String, fullName: String, password: String
    ): LiveData<Resource<String>>

    fun login(email: String, password: String): LiveData<Resource<String>>
    fun isLogin(): LiveData<Boolean>
    fun getFullName(): LiveData<Resource<String>>
    fun forgotPassword(email: String): LiveData<Resource<String>>
    fun signOut()
    fun checkIn(location: LocationEntity): LiveData<Resource<String>>
    fun checkOut(location: LocationEntity): LiveData<Resource<String>>
    fun getHistory(targetDate: Long): LiveData<Resource<List<AttendanceEntity>>>

    fun getOnboardingStatus(): Flow<Boolean>
    suspend fun saveOnboardingStatus()
    fun getCheckInStatus(): Flow<CheckInModel>
    suspend fun saveCheckInStatus(checkInModel: CheckInModel)
    suspend fun saveCheckOutStatus()
}