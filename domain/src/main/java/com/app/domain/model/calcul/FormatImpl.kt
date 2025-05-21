package com.app.domain.model.calcul

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

data class FormatImpl(
    val type: String? = "year"
) : Format() {

    @RequiresApi(Build.VERSION_CODES.O)
    val formatter: DateTimeFormatter =
        if (type == "YY:MM:DD:H") DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 a h:mm")
        else DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getTodayFormatDate(): String {
        val currentDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        return currentDateTime.format(formatter)
    }

    override fun calculateDistanceToKm(steps: Int): Double {
        /**
         * 한 걸음당 0.75미터로 판단하고 계산한다.
         */
        val stepLengthInKm = 0.75 / 1000.0

        return ((stepLengthInKm * steps) * 100.0).roundToInt() / 100.0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getMonthDays(yearMonth: LocalDate): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            yearMonth.withDayOfMonth(yearMonth.lengthOfMonth()).dayOfMonth
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun parseMonthDaysStr(dateStr: String): String {
        return LocalDate.parse(dateStr, formatter)!!.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun parseMonthDaysDate(dateStr: String): LocalDate {
        return LocalDate.parse(dateStr, formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getFormatTime(time: Long): String {
        val minutes = time / 60
        val seconds = time % 60

        return String.format("%02d:%02d", minutes, seconds)
    }
}