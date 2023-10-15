package com.example.phinconattendance.ui.home.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.phinconattendance.R
import com.example.phinconattendance.databinding.FragmentHistoryBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        listener()

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.vpHistory.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tbHistory, binding.vpHistory) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        return root
    }

    private fun listener() {
        binding.ivHistoryNotification.setOnClickListener {
            Toast.makeText(activity, getString(R.string.all_coming_soon), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.day,
            R.string.week,
            R.string.month,
            R.string.year
        )
    }
}