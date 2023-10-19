package com.example.phinconattendance.ui.home.history

import androidx.recyclerview.widget.DiffUtil
import com.example.phinconattendance.data.firebase.AttendanceEntity

class AccountDiffCallback(
    private val oldList: List<AttendanceEntity>,
    private val newList: List<AttendanceEntity>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].dateTime == newList[newItemPosition].dateTime
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldAccount = oldList[oldItemPosition]
        val newAccount = newList[newItemPosition]
        return oldAccount.dateTime == newAccount.dateTime && oldAccount.title == newAccount.title
    }

}