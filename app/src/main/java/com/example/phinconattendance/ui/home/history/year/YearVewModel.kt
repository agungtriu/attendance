package com.example.phinconattendance.ui.home.history.year

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.phinconattendance.data.RepositoryImp
import com.example.phinconattendance.data.firebase.AttendanceEntity
import com.example.phinconattendance.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class YearVewModel @Inject constructor(private val repositoryImp: RepositoryImp) : ViewModel() {
    fun getYearHistory(): LiveData<Resource<List<AttendanceEntity>>> {
        val years = LocalDateTime.now().minusYears(1)
            .minusHours(LocalDateTime.now().hour.toLong())
            .minusMinutes(LocalDateTime.now().minute.toLong())
            .minusSeconds(LocalDateTime.now().second.toLong())

        val millis = years.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return repositoryImp.getHistory(millis)
    }
}