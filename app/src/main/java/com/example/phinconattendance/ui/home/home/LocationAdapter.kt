package com.example.phinconattendance.ui.home.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.phinconattendance.R
import com.example.phinconattendance.data.firebase.Entity
import com.example.phinconattendance.databinding.ItemListBinding

class LocationAdapter : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {
    private val listLocation = ArrayList<Entity>()
    private var isCheckOut = false
    private var isCheckIn = false

    fun setLocation(listLocation: List<Entity>, isCheckIn: Boolean, isCheckOut: Boolean) {
        val diffCallback = AccountDiffCallback(this.listLocation, listLocation)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        if (this.listLocation.isNotEmpty()) {
            this.listLocation.clear()
        }
        this.listLocation.addAll(listLocation)

        diffResult.dispatchUpdatesTo(this)

            this.isCheckIn = isCheckIn

            this.isCheckOut = isCheckOut
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationAdapter.LocationViewHolder = LocationViewHolder(
        ItemListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: LocationAdapter.LocationViewHolder, position: Int) {
        val location = listLocation[position]
        holder.bind(location)
    }

    override fun getItemCount(): Int = listLocation.size

    inner class LocationViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Entity) {
            with(binding) {
                if (isCheckOut){
                    itemView.isActivated = false
                }

                ivItemList.setImageResource(item.image)
                tvItemListTitle.text = item.title
                tvItemListDesc.text = item.address
                if (isCheckIn){
                    cvLocation.setBackgroundResource(R.drawable.all_rectangle_rounded_orange_10dp)
                    tvItemListTitle.setTextColor(itemView.context.getColor(R.color.white))
                    tvItemListDesc.setTextColor(itemView.context.getColor(R.color.white))
                } else {
                    cvLocation.setBackgroundResource(R.drawable.selector_item_background)
                }
            }
        }
    }
}