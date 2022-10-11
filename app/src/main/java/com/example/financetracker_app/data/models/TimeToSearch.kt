package com.example.financetracker_app.data.models

import com.squareup.moshi.JsonClass

enum class TimeInterval {
    DAY, WEEK, MONTH, YEAR
}

@JsonClass(generateAdapter = true)
data class FromNow(
    val numOf: Long,
    val timeInterval: TimeInterval
)

@JsonClass(generateAdapter = true)
data class ReadableDateRange(
    val startDate: String,
    val endDate: String
)

@JsonClass(generateAdapter = true)
data class TimeToSearch(
    val dateRange: ReadableDateRange? = null,
    val fromNow: FromNow? = null
)