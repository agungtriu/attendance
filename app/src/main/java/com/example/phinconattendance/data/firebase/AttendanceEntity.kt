package com.example.phinconattendance.data.firebase

data class AttendanceEntity(
    val title: String,
    val status: String,
    val address: String,
    val locationId: Int,
    val userId: String,
    val dateTime: Long
)