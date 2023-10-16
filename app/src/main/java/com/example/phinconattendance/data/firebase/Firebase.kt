package com.example.phinconattendance.data.firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.phinconattendance.helper.Utils
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
            val userData = HashMap<String, Any>()
            userData["fullName"] = fullName
            userDocument.set(userData, SetOptions.merge())
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

    fun getFullName(): LiveData<Resource<String>> {
        val result = MutableLiveData<Resource<String>>()
        result.postValue(Resource.loading(null))
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userDocumentRef = db.collection("users").document(userId)
            userDocumentRef.get().addOnSuccessListener {
                if (it.exists()) {
                    result.postValue(Resource.success(it.getString("fullName")))
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

    fun checkIn(location: Entity) {
        val attendanceDocument = db.collection("attendance")
        val attendanceData = HashMap<String, Any>()
        attendanceData["title"] = location.title
        attendanceData["status"] = "Check In"
        attendanceData["address"] = location.address
        attendanceData["locationId"] = location.id
        attendanceData["userId"] = auth.currentUser!!.uid
        attendanceData["dateTime"] = System.currentTimeMillis()
        attendanceDocument.add(attendanceData)
    }

    fun checkOut(location: Entity) {
        val attendanceDocument = db.collection("attendance")
        val attendanceData = HashMap<String, Any>()
        attendanceData["title"] = location.title
        attendanceData["status"] = "Check Out"
        attendanceData["address"] = location.address
        attendanceData["locationId"] = location.id
        attendanceData["userId"] = auth.currentUser!!.uid
        attendanceData["dateTime"] = System.currentTimeMillis()
        attendanceDocument.add(attendanceData)
    }

    fun getHistory(targetDate: Long): LiveData<Resource<List<Entity>>> {
        val results = MutableLiveData<Resource<List<Entity>>>()
        results.postValue(Resource.loading(null))
        if (auth.currentUser != null) {
            val userId = auth.currentUser!!.uid
            val attendanceDocument = db.collection("attendance")
            val query: Query = attendanceDocument.whereEqualTo("userId", userId)
                .whereGreaterThanOrEqualTo("dateTime", targetDate)
                .orderBy("dateTime", Query.Direction.DESCENDING)

            query.get().addOnSuccessListener {
                val logs = mutableListOf<Entity>()
                for (log in it.documents) {
                    val id = log["locationId"] as Long
                    val dateTime = log["dateTime"] as Long
                    val title = "${log["status"]} - ${log["title"]} - ${Utils.millisToTime(dateTime)}"
                    val image = Utils.Location[id.toInt() - 1].image
                    logs.add(
                        Entity(
                            id = id.toInt(),
                            title = title,
                            address = log["address"] as String,
                            image = image
                        )
                    )
                }
                results.postValue(Resource.success(logs))
            }.addOnFailureListener {
                results.postValue(Resource.error(it.message, null))
            }
        }
        return results
    }

}