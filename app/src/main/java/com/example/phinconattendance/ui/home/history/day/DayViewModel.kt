package com.example.phinconattendance.ui.home.history.day

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.phinconattendance.data.RepositoryImp
import com.example.phinconattendance.data.firebase.Entity
import com.example.phinconattendance.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(private val repositoryImp: RepositoryImp) : ViewModel() {
    fun getDayHistory(): LiveData<Resource<List<Entity>>> {
        val days = LocalDateTime.now()
            .minusHours(LocalDateTime.now().hour.toLong())
            .minusMinutes(LocalDateTime.now().minute.toLong())
            .minusSeconds(LocalDateTime.now().second.toLong())

        val millis = days.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return repositoryImp.getHistory(millis)
    }
}