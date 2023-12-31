package com.example.phinconattendance.data.firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.phinconattendance.vo.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Firebase @Inject constructor() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private fun registerFullName(fullName: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userDocument = db.collection("users").document(userId)
            userDocument.set(UserEntity(fullName = fullName), SetOptions.merge())
        }
    }

    fun registerUser(
        email: String, fullName: String, password: String
    ): LiveData<Resource<String>> {
        val result = MutableLiveData<Resource<String>>()
        result.postValue(Resource.loading(null))
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                registerFullName(fullName)
                result.postValue(Resource.success(null))
            } else {
                result.postValue(Resource.error(task.exception?.message, null))
            }
        }
        return result
    }

    fun login(email: String, password: String): LiveData<Resource<String>> {
        val result = MutableLiveData<Resource<String>>()
        result.postValue(Resource.loading(null))
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                result.postValue(Resource.success(null))
            } else {
                result.postValue(Resource.error(task.exception?.message, null))
            }
        }
        return result
    }

    fun isLogin(): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        result.postValue(auth.currentUser != null)
        return result
    }

    fun getFullName(): LiveData<Resource<UserEntity>> {
        val result = MutableLiveData<Resource<UserEntity>>()
        result.postValue(Resource.loading(null))
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userDocumentRef = db.collection("users").document(userId)
            userDocumentRef.get().addOnSuccessListener {
                if (it.exists()) {
                    val user = it.toObject(UserEntity::class.java)
                    if (user != null) {
                        result.postValue(Resource.success(user))
                    } else {
                        result.postValue(Resource.error("Data not found", null))
                    }
                }
            }.addOnFailureListener {
                result.postValue(Resource.error(it.message, null))
            }
        }
        return result
    }

    fun forgotPassword(email: String): LiveData<Resource<String>> {
        val result = MutableLiveData<Resource<String>>()
        result.postValue(Resource.loading(null))
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                result.postValue(Resource.success(null))
            } else {
                result.postValue(Resource.error(task.exception?.message, null))
            }
        }
        return result
    }

    fun signOut() {
        auth.signOut()
    }

    fun checkIn(location: LocationEntity): LiveData<Resource<String>> {
        val result = MutableLiveData<Resource<String>>()
        result.postValue(Resource.loading(null))
        val attendanceDocument = db.collection("attendance")
        val attendance = AttendanceEntity(
            title = location.title,
            status = "Check In",
            address = location.address,
            locationId = location.id,
            userId = auth.currentUser!!.uid,
            dateTime = System.currentTimeMillis()
        )
        attendanceDocument.add(attendance)
            .addOnSuccessListener {
                result.postValue(Resource.success(null))
            }
            .addOnFailureListener {
                result.postValue(Resource.error(it.message, null))
            }
        return result
    }

    fun checkOut(location: LocationEntity): LiveData<Resource<String>> {
        val result = MutableLiveData<Resource<String>>()
        result.postValue(Resource.loading(null))
        val attendanceDocument = db.collection("attendance")
        val attendance = AttendanceEntity(
            title = location.title,
            status = "Check Out",
            address = location.address,
            locationId = location.id,
            userId = auth.currentUser!!.uid,
            dateTime = System.currentTimeMillis()
        )
        attendanceDocument.add(attendance)
            .addOnSuccessListener {
                result.postValue(Resource.success(null))
            }
            .addOnFailureListener {
                result.postValue(Resource.error(it.message, null))
            }

        return result
    }

    fun getHistory(targetDate: Long): LiveData<Resource<List<AttendanceEntity>>> {
        val results = MutableLiveData<Resource<List<AttendanceEntity>>>()
        results.postValue(Resource.loading(null))
        if (auth.currentUser != null) {
            val userId = auth.currentUser!!.uid
            val attendanceDocument = db.collection("attendance")
            val query: Query = attendanceDocument.whereEqualTo("userId", userId)
                .whereGreaterThanOrEqualTo("dateTime", targetDate)
                .orderBy("dateTime", Query.Direction.DESCENDING)

            query.get().addOnSuccessListener {
                val logs = mutableListOf<AttendanceEntity>()
                for (document in it.documents) {
                    val log = document.toObject(AttendanceEntity::class.java)
                    if (log != null) {
                        logs.add(log)
                    }
                }
                results.postValue(Resource.success(logs))
            }.addOnFailureListener {
                results.postValue(Resource.error(it.message, null))
            }
        }
        return results
    }

}