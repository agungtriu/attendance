package com.example.phinconattendance.ui.home.history.year

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phinconattendance.databinding.FragmentYearBinding
import com.example.phinconattendance.ui.home.history.HistoryAdapter
import com.example.phinconattendance.vo.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YearFragment : Fragment() {
    private val yearVewModel: YearVewModel by viewModels()
    private var _binding: FragmentYearBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentYearBinding.inflate(inflater, container, false)
        val root: View = binding.root

        historyAdapter = HistoryAdapter()
        binding.rvYear.layoutManager = LinearLayoutManager(view?.context)
        binding.rvYear.setHasFixedSize(true)
        binding.rvYear.adapter = historyAdapter
        getData()

        return root
    }

    private fun getData() {
        yearVewModel.getYearHistory().observe(viewLifecycleOwner) {
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
        binding.pbYear.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.pbYear.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}