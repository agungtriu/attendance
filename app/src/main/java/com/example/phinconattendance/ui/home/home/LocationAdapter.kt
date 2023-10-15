package com.example.phinconattendance.ui.home.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.phinconattendance.R
import com.example.phinconattendance.data.firebase.Entity
import com.example.phinconattendance.databinding.ItemLocationBinding

class LocationAdapter : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {
    private val listLocation = ArrayList<Entity>()

    fun setLocation(listLocation: List<Entity>) {
        val diffCallback = AccountDiffCallback(this.listLocation, listLocation)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        if (this.listLocation.isNotEmpty()) {
            this.listLocation.clear()
        }
        this.listLocation.addAll(listLocation)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationAdapter.LocationViewHolder = LocationViewHolder(
        ItemLocationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: LocationAdapter.LocationViewHolder, position: Int) {
        val location = listLocation[position]
        holder.bind(location)
    }

    override fun getItemCount(): Int = listLocation.size

    inner class LocationViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Entity) {
            with(binding) {
                ivItemLocation.setImageResource(item.image)
                tvItemLocationTitle.text = item.title
                tvItemLocationDesc.text = item.address
                if (listLocation.size==1){
                    cvLocation.setBackgroundResource(R.drawable.all_rectangle_rounded_orange_10dp)
                    tvItemLocationTitle.setTextColor(itemView.context.getColor(R.color.white))
                    tvItemLocationDesc.setTextColor(itemView.context.getColor(R.color.white))
                } else {
                    cvLocation.setBackgroundResource(R.drawable.selector_item_background)
                }
            }
        }
    }
}