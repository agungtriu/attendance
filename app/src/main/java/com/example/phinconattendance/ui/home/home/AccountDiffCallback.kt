package com.example.phinconattendance.ui.home.home

import androidx.recyclerview.widget.DiffUtil
import com.example.phinconattendance.data.firebase.LocationEntity

class AccountDiffCallback(
    private val oldList: List<LocationEntity>,
    private val newList: List<LocationEntity>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldAccount = oldList[oldItemPosition]
        val newAccount = newList[newItemPosition]
        return oldAccount.id == newAccount.id && oldAccount.title == newAccount.title
    }

}