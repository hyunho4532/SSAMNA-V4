package com.app.presentation.component.util

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.domain.model.calcul.FormatImpl
import com.app.domain.model.entry.KcalEntry
import com.app.domain.model.entry.KmEntry
import com.app.domain.model.entry.StepEntry
import com.app.domain.model.location.Coordinate
import java.time.DayOfWeek
import java.time.LocalDate

/**
 * 비례 관계를 이용하여 card의 width를 조정하는 함수
 */
fun calculatorActivateCardWeight(
    minHeight: Int,
    maxHeight: Int,
    data: State<List<Any>>
): Dp {

    val size = data.value.size

    return if (size > 0) {
        /**
         * 비례 관계
         */
        (maxHeight * (size / 2f)).coerceIn(minHeight.toFloat(), maxHeight.toFloat()).dp
    } else {
        0.dp
    }
}

/**
 * coordsList 사이즈에 따라 width를 조정한다.
 */
fun calculatorActivateChartWidth(coordsList: List<Coordinate>): Dp {
    val width = 480

    val renderingWidth = if (coordsList.size > 1) {
        (coordsList.size - 1) * (width / coordsList.size)
    } else {
        width
    }

    return renderingWidth.dp
}

/**
 * 이번 주 km, kcal 계산 함수
 * type 파라미터에 따라서 계산함
 */
@SuppressLint("NewApi")
fun getThisWeek(
    type: String,
    kcalList: List<KcalEntry> = emptyList(),
    kmList: List<KmEntry> = emptyList(),
    stepList: List<StepEntry> = emptyList()
): Double {

    var sumList = 0.0

    val today = LocalDate.now()

    val startOfWeek = today.with(DayOfWeek.MONDAY)
    val endOfWeek = today.with(DayOfWeek.SUNDAY)

    when (type) {
        "kcal" -> {
            sumList = kcalList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfWeek..endOfWeek
            }.sumOf { it.kcal }
        }
        "km" -> {
            sumList = kmList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfWeek..endOfWeek
            }.sumOf { it.km }
        }
        "step" -> {
            sumList = stepList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfWeek..endOfWeek
            }.sumOf { it.step }.toDouble()
        }
    }

    return sumList
}

/**
 * 저번 주 km, kcal 계산 함수
 * type 파라미터에 따라서 계산함
 */
@SuppressLint("NewApi")
fun getLastWeek(
    type: String,
    kcalList: List<KcalEntry> = emptyList(),
    kmList: List<KmEntry> = emptyList(),
    stepList: List<StepEntry> = emptyList()
): Double {

    var sumList = 0.0

    val today = LocalDate.now()
    val startOfLastWeek = today.with(DayOfWeek.MONDAY).minusDays(7)
    val endOfLastWeek = today.with(DayOfWeek.SUNDAY).minusDays(7)

    when (type) {
        "kcal" -> {
            sumList = kcalList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfLastWeek..endOfLastWeek
            }.sumOf { it.kcal }
        }
        "km" -> {
            sumList = kmList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfLastWeek..endOfLastWeek
            }.sumOf { it.km }
        }
        "step" -> {
            sumList = stepList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfLastWeek..endOfLastWeek
            }.sumOf { it.step }.toDouble()
        }
    }

    return sumList
}

/**
 * 이번 달 km, kcal 계산 함수
 * type 파라미터에 따라서 계산함
 */
@SuppressLint("NewApi")
fun getThisMonth(
    type: String,
    kcalList: List<KcalEntry> = emptyList(),
    kmList: List<KmEntry> = emptyList(),
    stepList: List<StepEntry> = emptyList()
): Double {
    var sumList = 0.0

    val today = LocalDate.now()

    val startOfMonth = today.withDayOfMonth(1)
    val endOfMonth = today.withDayOfMonth(today.lengthOfMonth())

    when (type) {
        "kcal" -> {
            sumList = kcalList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfMonth..endOfMonth
            }.sumOf { it.kcal }
        }
        "km" -> {
            sumList = kmList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfMonth..endOfMonth
            }.sumOf { it.km }
        }
        "step" -> {
            sumList = stepList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfMonth..endOfMonth
            }.sumOf { it.step }.toDouble()
        }
    }

    return sumList
}

/**
 * 저번 달 km, kcal 계산 함수
 * type 파라미터에 따라서 계산함
 */
@SuppressLint("NewApi")
fun getLastMonth(
    type: String,
    kcalList: List<KcalEntry> = emptyList(),
    kmList: List<KmEntry> = emptyList(),
    stepList: List<StepEntry> = emptyList()
): Double {
    var sumList = 0.0

    val today = LocalDate.now()
    val lastMonth = today.minusMonths(1)

    val startOfLastMonth = lastMonth.withDayOfMonth(1)
    val endOfLastMonth = lastMonth.withDayOfMonth(lastMonth.lengthOfMonth())

    when (type) {
        "kcal" -> {
            sumList = kcalList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfLastMonth..endOfLastMonth
            }.sumOf { it.kcal }
        }
        "km" -> {
            sumList = kmList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfLastMonth..endOfLastMonth
            }.sumOf { it.km }
        }
        "step" -> {
            sumList = stepList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate in startOfLastMonth..endOfLastMonth
            }.sumOf { it.step }.toDouble()
        }
    }

    return sumList
}

/**
 * 올해 km, kcal 계산 함수
 * type 파라미터에 따라서 계산함
 */
@SuppressLint("NewApi")
fun getThisYear(
    type: String,
    kcalList: List<KcalEntry> = emptyList(),
    kmList: List<KmEntry> = emptyList(),
    stepList: List<StepEntry> = emptyList()
): Double {
    var sumList = 0.0

    val todayYear = LocalDate.now().year

    when (type) {
        "kcal" -> {
            sumList = kcalList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate.year == todayYear
            }.sumOf { it.kcal }
        }
        "km" -> {
            sumList = kmList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate.year == todayYear
            }.sumOf { it.km }
        }
        "step" -> {
            sumList = stepList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate.year == todayYear
            }.sumOf { it.step }.toDouble()
        }
    }

    return sumList
}

/**
 * 작년 km, kcal 계산 함수
 * type 파라미터에 따라서 계산함
 */
@SuppressLint("NewApi")
fun getLastYear(
    type: String,
    kcalList: List<KcalEntry> = emptyList(),
    kmList: List<KmEntry> = emptyList(),
    stepList: List<StepEntry> = emptyList()
): Double {
    var sumList = 0.0

    val lastYear = LocalDate.now().year - 1

    when (type) {
        "kcal" -> {
            sumList = kcalList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate.year == lastYear
            }.sumOf { it.kcal }
        }
        "km" -> {
            sumList = kmList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate.year == lastYear
            }.sumOf { it.km }
        }
        "step" -> {
            sumList = stepList.filter { entry ->
                val entryDate = FormatImpl("YY:MM:DD").parseMonthDaysDate(entry.date)
                entryDate.year == lastYear
            }.sumOf { it.step }.toDouble()
        }
    }

    return sumList
}

/**
 * 시간 변환 함수
 * 00:00 형식의 시간을 분 단위 또는 초 단위로 변환하는 함수
 */
fun convertTimeToSeconds(time: String): Int {
    val parts = time.split(":").map { it.toInt() }
    return parts[0] * 60 + parts[1]
}

/**
 * 페이즈 및 칼로리 계산
 */
fun analyzeRunningFeedback(time: String, distance: Double, calories: Double, onPaceReceive: (Double) -> Unit): String {
    val timeInMinutes = convertTimeToSeconds(time) / 60.0
    val pace = if (distance > 0) timeInMinutes / distance else 0.0

    onPaceReceive(pace)

    return when (pace) {
        in 0.1 .. 5.0 -> "현재 페이스가 매우 빠릅니다!\n너무 무리하지 않도록 조절하세요.🔥"
        in 5.0..7.0 -> "완벽한 페이스입니다!\n이 속도를 유지하면서 즐겁게 달려보세요. 🏃‍♂️"
        in 7.0..10.0 -> "조금 더 속도를 올리면 좋겠어요!\n하지만 꾸준히 달리는 게 가장 중요합니다. 😊"
        0.0 -> "페이스를 분석할 수 없습니다!"
        else -> "현재 속도가 느립니다. 하지만 걱정하지 마세요!\n조금씩 속도를 올려보는 건 어떨까요? 🚀"
    }
}

/**
 * 진행률 계산
 */
