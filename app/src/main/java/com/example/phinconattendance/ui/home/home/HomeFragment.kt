package com.example.phinconattendance.ui.home.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phinconattendance.R
import com.example.phinconattendance.databinding.FragmentHomeBinding
import com.example.phinconattendance.helper.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private val handler = Handler(Looper.getMainLooper())

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var locationAdapter: LocationAdapter
    private var selectedPosition = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        updateTime()
        listener()
        locationAdapter = LocationAdapter()
        binding.rvHomeLocation.layoutManager = LinearLayoutManager(view?.context)
        binding.rvHomeLocation.setHasFixedSize(true)
        binding.rvHomeLocation.adapter = locationAdapter
        viewModel.getCheckInStatus().observe(viewLifecycleOwner) {
            if (it.isCheckIn) {
                locationAdapter.setLocation(listOf(Utils.Location[it.position]))
                selectedPosition = it.position
                checkOutVisible()
            } else {
                checkInVisible()
                locationAdapter.setLocation(Utils.Location)
            }
        }
        return root
    }

    private fun updateTime() {
        handler.post(object : Runnable {
            override fun run() {
                val currentTime = System.currentTimeMillis()
                binding.tvHomeHour.text = Utils.millisToTime(currentTime)
                binding.tvHomeDate.text = Utils.millisToDate(currentTime)
                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun listener() {
        binding.ivHomeNotification.setOnClickListener {
            Toast.makeText(activity, getString(R.string.all_coming_soon), Toast.LENGTH_SHORT).show()
        }
        binding.btnHomeCheckIn.setOnClickListener {
            if (selectedPosition < 0) {
                Toast.makeText(activity,
                    getString(R.string.home_please_select_location), Toast.LENGTH_SHORT).show()
            } else {
                checkOutVisible()
                viewModel.checkIn(
                    Utils.Location[selectedPosition],
                    selectedPosition
                )
                locationAdapter.notifyItemChanged(selectedPosition)
                locationAdapter.setLocation(listOf(Utils.Location[selectedPosition]))
            }

        }
        binding.btnHomeCheckout.setOnClickListener {
            locationAdapter.notifyItemChanged(0)
            checkInVisible()
            viewModel.checkOut(Utils.Location[selectedPosition])
            locationAdapter.setLocation(Utils.Location)
            locationAdapter.notifyItemChanged(selectedPosition)
        }

        binding.rvHomeLocation.addOnItemTouchListener(
            RecyclerItemClickListener(requireContext(), binding.rvHomeLocation,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        // Deselect the previously selected item
                        val previousSelected =
                            binding.rvHomeLocation.findViewHolderForAdapterPosition(selectedPosition)
                        previousSelected?.itemView?.isActivated = false


                        // Select the newly clicked item
                        view.isActivated = true
                        selectedPosition = position
                    }

                    override fun onLongItemClick(view: View, position: Int) {
                    }
                })
        )
    }

    private fun checkInVisible() {
        binding.btnHomeCheckIn.visibility = View.VISIBLE
        binding.btnHomeCheckout.visibility = View.GONE
    }

    private fun checkOutVisible() {
        binding.btnHomeCheckIn.visibility = View.GONE
        binding.btnHomeCheckout.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
        _binding = null
    }
}