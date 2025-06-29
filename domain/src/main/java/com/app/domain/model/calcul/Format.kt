package com.app.domain.model.calcul

import java.time.LocalDate

sealed class Format {
    abstract fun getTodayFormatDate(): String
    abstract fun calculateDistanceToKm(steps: Int): Double
    abstract fun getMonthDays(yearMonth: LocalDate): Int
    abstract fun parseMonthDaysStr(dateStr: String): String
    abstract fun parseMonthDaysDate(dateStr: String): LocalDate
    abstract fun getFormatTime(time: Long): String
    abstract fun getSpeakTime(time: Long): String
}