package com.example.phinconattendance.helper

import android.annotation.SuppressLint
import android.text.Selection
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.example.phinconattendance.R
import com.example.phinconattendance.data.firebase.LocationEntity
import java.text.SimpleDateFormat
import java.util.Date

class Utils {
    companion object {
        fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
            val spannableString = SpannableString(this.text)
            var startIndexOfLink = -1
            for (link in links) {
                val clickableSpan = object : ClickableSpan() {
                    override fun updateDrawState(textPaint: TextPaint) {
                        // use this to change the link color
                        textPaint.color = textPaint.linkColor
                        // toggle below value to enable/disable
                        // the underline shown below the clickable text
//                        textPaint.isUnderlineText = true
                    }

                    override fun onClick(view: View) {
                        Selection.setSelection((view as TextView).text as Spannable, 0)
                        view.invalidate()
                        link.second.onClick(view)
                    }
                }
                startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
                if (startIndexOfLink == -1) continue // if you want to verify your texts contains links text
                spannableString.setSpan(
                    clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            this.movementMethod =
                LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
            this.setText(spannableString, TextView.BufferType.SPANNABLE)
        }

        val Location = listOf(
            LocationEntity(
                1,
                "PT Phincon",
                "88 @Kasablanka Office Tower, 8th Floor Jl Casablanca Raya Kav 88, Tebet, Jakarta 12870, Indonesia",
                R.mipmap.ic_phincon
            ),
            LocationEntity(
                2,
                "Telkomsel Smart Office",
                "Jl. Gatot Subroto No.Kav. 52, RT.6/RW.1, Kuningan Bar., Kec. Mampang Prpt., Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12710",
                R.mipmap.ic_telkomsel
            ),
            LocationEntity(3, "Rumah", "Jakarta", R.mipmap.ic_rumah)
        )

        @SuppressLint("SimpleDateFormat")
        fun millisToTime(timeInMillis: Long): String {
            val timeFormat = SimpleDateFormat("HH:mm")

            // Convert the timestamp to a Date object
            val time = Date(timeInMillis)

            // Format the date as a string
            return timeFormat.format(time)

        }

        @SuppressLint("SimpleDateFormat")
        fun millisToDate(timeInMillis: Long): String {
            // Customize the time format using SimpleDateFormat
            val dateFormat = SimpleDateFormat("dd MMMM yyyy")
            val date = Date(timeInMillis)
            return dateFormat.format(date)
        }
    }
}