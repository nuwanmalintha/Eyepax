package com.eyepax.eyepax.utils

import java.text.SimpleDateFormat
import java.util.*

class MainUtility {
    companion object {
        fun geStringDateFormatted(date: String?): String? {
            var day: String? = ""
            if (date != null) {
                try {
                    val date1 =
                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(date)
                    day =
                        date1?.let { SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH).format(it) }
                } catch (e: Exception) {
                }
            }
            return day
        }
    }
}