package com.example.phinconattendance.data

import androidx.lifecycle.LiveData
import com.example.phinconattendance.data.datastore.CheckInModel
import com.example.phinconattendance.data.datastore.DataStoreManager
import com.example.phinconattendance.data.firebase.AttendanceEntity
import com.example.phinconattendance.data.firebase.Firebase
import com.example.phinconattendance.data.firebase.LocationEntity
import com.example.phinconattendance.data.firebase.UserEntity
import com.example.phinconattendance.vo.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImp @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val firebase: Firebase
) : Repository {
    override fun registerUser(
        email: String,
        fullName: String,
        password: String
    ): LiveData<Resource<String>> = firebase.registerUser(email, fullName, password)

    override fun login(email: String, password: String): LiveData<Resource<String>> =
        firebase.login(email, password)

    override fun isLogin(): LiveData<Boolean> = firebase.isLogin()

    override fun getFullName(): LiveData<Resource<UserEntity>> = firebase.getFullName()

    override fun forgotPassword(email: String): LiveData<Resource<String>> =
        firebase.forgotPassword(email)

    override fun signOut() = firebase.signOut()

    override fun checkIn(location: LocationEntity): LiveData<Resource<String>> =
        firebase.checkIn(location)

    override fun checkOut(location: LocationEntity): LiveData<Resource<String>> =
        firebase.checkOut(location)

    override fun getHistory(targetDate: Long): LiveData<Resource<List<AttendanceEntity>>> =
        firebase.getHistory(targetDate)

    override fun getOnboardingStatus(): Flow<Boolean> = dataStoreManager.getOnboardingStatus()

    override suspend fun saveOnboardingStatus() {
        dataStoreManager.saveOnboardingStatus()
    }

    override fun getCheckInStatus(): Flow<CheckInModel> = dataStoreManager.getCheckInStatus()

    override suspend fun saveCheckInStatus(checkInModel: CheckInModel) =
        dataStoreManager.saveCheckInStatus(checkInModel)

    override suspend fun saveCheckOutStatus() = dataStoreManager.saveCheckOutStatus()
}