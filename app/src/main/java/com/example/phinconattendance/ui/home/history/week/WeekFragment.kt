package com.example.phinconattendance.ui.home.history.week

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phinconattendance.databinding.FragmentWeekBinding
import com.example.phinconattendance.ui.home.history.HistoryAdapter
import com.example.phinconattendance.vo.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeekFragment : Fragment() {
    private val weekViewModel: WeekViewModel by viewModels()
    private var _binding: FragmentWeekBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeekBinding.inflate(inflater, container, false)
        val root: View = binding.root

        historyAdapter = HistoryAdapter()
        binding.rvWeek.layoutManager = LinearLayoutManager(view?.context)
        binding.rvWeek.setHasFixedSize(true)
        binding.rvWeek.adapter = historyAdapter
        getData()

        return root
    }

    private fun getData() {
        weekViewModel.getWeekHistory().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> showProgressBar()
                Status.SUCCESS -> {
                    hideProgressBar()
                    if (!it.data.isNullOrEmpty()) {
                        historyAdapter.setHistory(it.data)
                    }
                }

                Status.ERROR -> {
                    hideProgressBar()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun showProgressBar() {
        binding.pbWeek.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.pbWeek.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}