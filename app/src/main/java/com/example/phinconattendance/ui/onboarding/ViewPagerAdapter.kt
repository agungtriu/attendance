package com.example.phinconattendance.ui.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.phinconattendance.R


class ViewPagerAdapter(private val context: Context) : PagerAdapter() {

    private var sliderImage = arrayListOf(
        R.mipmap.ic_time_attendance_clocks,
        R.mipmap.ic_employee_self_service,
        R.mipmap.ic_mask
    )
    private var sliderTitle = arrayListOf(
        R.string.onboarding_title_one,
        R.string.onboarding_title_two,
        R.string.onboarding_title_three
    )
    private var sliderDesc = arrayListOf(
        R.string.onboarding_desc_one,
        R.string.onboarding_desc_two,
        R.string.onboarding_desc_three
    )

    override fun getCount(): Int = sliderTitle.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.slider_onboarding, container, false)

        val sliderImage = view.findViewById<View>(R.id.iv_onboarding) as ImageView
        val sliderTitle = view.findViewById<View>(R.id.tv_onboarding_title) as TextView
        val sliderDesc = view.findViewById<View>(R.id.tv_onboarding_desc) as TextView
        sliderImage.setImageResource(this.sliderImage[position])
        sliderTitle.setText(this.sliderTitle[position])
        sliderDesc.setText(this.sliderDesc[position])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}