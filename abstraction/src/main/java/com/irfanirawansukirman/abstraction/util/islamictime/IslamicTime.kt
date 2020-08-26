package com.irfanirawansukirman.abstraction.util.islamictime

import net.time4j.SystemClock
import net.time4j.calendar.HijriCalendar
import net.time4j.engine.StartOfDay
import net.time4j.format.expert.ChronoFormatter
import net.time4j.format.expert.PatternType
import java.util.*

// source : https://stackoverflow.com/a/43493951
object IslamicTime {

    fun getCurrentIslamicDate(dateTimeFormat: String = "MMMM yyyy"): String {
        val hijriFormat = ChronoFormatter.setUp(HijriCalendar.family(), Locale.ENGLISH)
            .addEnglishOrdinal(HijriCalendar.DAY_OF_MONTH)
            .addPattern(" $dateTimeFormat", PatternType.CLDR)
            .build()
            .withCalendarVariant(HijriCalendar.VARIANT_UMALQURA)

        val todayExact: HijriCalendar = SystemClock.inLocalView().now(
            HijriCalendar.family(),
            HijriCalendar.VARIANT_UMALQURA,
            StartOfDay.EVENING // simple approximation => 18:00
        ).toDate()

        return hijriFormat.format(todayExact).clearTimeLabel().setToLocaleIslamicMonth()
    }

    private fun String.setToLocaleIslamicMonth(): String {
        return when {
            contains("Muharram") -> replace("Muharram", "Muharram")
            contains("Safar") -> replace("Safar", "Safar")
            contains("Rabi' I") -> replace("Rabi' I", "Rabiul Awal")
            contains("Rabi' II") -> replace("Rabi' II", "Rabiul Akhir")
            contains("Jumada I") -> replace("Jumada I", "Jumadil Awal")
            contains("Jumada II") -> replace("Jumada II", "Jumadil Akhir")
            contains("Rajab") -> replace("Rajab", "Rajab")
            contains("Sha'ban") -> replace("Sha'ban", "Sya'ban")
            contains("Ramadan") -> replace("Ramadan", "Ramadhan")
            contains("Shawwal") -> replace("Shawwal", "Syawal")
            contains("Dhu'l-Qi'dah") -> replace("Dhu'l-Qi'dah", "Dzulqa'dah")
            else -> replace("", "Dzulhijjah")
        }
    }

    private fun String.clearTimeLabel(): String {
        return replace("st", "")
            .replace("nd", "")
            .replace("rd", "")
            .replace("th", "")
    }
}