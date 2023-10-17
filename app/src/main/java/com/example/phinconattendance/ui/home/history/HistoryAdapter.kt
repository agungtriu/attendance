package com.example.phinconattendance.ui.home.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.phinconattendance.R
import com.example.phinconattendance.data.firebase.Entity
import com.example.phinconattendance.databinding.ItemListBinding
import com.example.phinconattendance.ui.home.home.AccountDiffCallback

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private val listHistories = ArrayList<Entity>()

    fun setHistory(listLocation: List<Entity>) {
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
        fun bind(item: Entity) {
            with(binding) {
                ivItemList.setImageResource(item.image)
                tvItemListTitle.text = item.title
                tvItemListDesc.text = item.address
                cvLocation.setBackgroundResource(R.drawable.all_rectangle_rounded_10dp)
            }
        }
    }
}