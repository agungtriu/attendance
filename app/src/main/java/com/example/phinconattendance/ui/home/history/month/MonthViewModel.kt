package com.example.phinconattendance.ui.home.history.month

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.phinconattendance.data.firebase.Entity
import com.example.phinconattendance.data.firebase.Firebase
import com.example.phinconattendance.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MonthViewModel @Inject constructor(private val firebase: Firebase) : ViewModel() {
    fun getMonthHistory(): LiveData<Resource<List<Entity>>> {
        val months = LocalDateTime.now().minusMonths(1)
            .minusHours(LocalDateTime.now().hour.toLong())
            .minusMinutes(LocalDateTime.now().minute.toLong())
            .minusSeconds(LocalDateTime.now().second.toLong())

        return firebase.getHistory(months)
    }
}