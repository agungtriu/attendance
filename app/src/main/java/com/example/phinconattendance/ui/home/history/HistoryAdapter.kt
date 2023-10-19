package com.example.phinconattendance.ui.home.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.phinconattendance.R
import com.example.phinconattendance.data.firebase.AttendanceEntity
import com.example.phinconattendance.databinding.ItemListBinding
import com.example.phinconattendance.helper.Utils

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private val listHistories = ArrayList<AttendanceEntity>()

    fun setHistory(listLocation: List<AttendanceEntity>) {
        val diffCallback = AccountDiffCallback(this.listHistories, listLocation)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        if (this.listHistories.isNotEmpty()) {
            this.listHistories.clear()
        }
        this.listHistories.addAll(listLocation)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryAdapter.HistoryViewHolder = HistoryViewHolder(
        ItemListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: HistoryAdapter.HistoryViewHolder, position: Int) {
        val location = listHistories[position]
        holder.bind(location)
    }

    override fun getItemCount(): Int = listHistories.size

    inner class HistoryViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AttendanceEntity) {
            with(binding) {
                ivItemList.setImageResource(Utils.Location[item.locationId - 1].image)
                val time = Utils.millisToTime(item.dateTime)
                val hour = if (time.split(":")[0].toInt() < 12) "AM" else "PM"
                val title = "${item.status} - ${item.title} - $time $hour"
                tvItemListTitle.text = title
                tvItemListDesc.text = item.address
                cvLocation.setBackgroundResource(R.drawable.all_rectangle_rounded_10dp)
            }
        }
    }
}