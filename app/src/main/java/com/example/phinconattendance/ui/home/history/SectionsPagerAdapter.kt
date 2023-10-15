package com.example.phinconattendance.ui.home.history

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.phinconattendance.ui.home.history.day.DayFragment
import com.example.phinconattendance.ui.home.history.month.MonthFragment
import com.example.phinconattendance.ui.home.history.week.WeekFragment
import com.example.phinconattendance.ui.home.history.year.YearFragment

class SectionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragment = listOf(DayFragment(), WeekFragment(), MonthFragment(), YearFragment())

    override fun getItemCount(): Int {
        return fragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragment[position]
    }

}