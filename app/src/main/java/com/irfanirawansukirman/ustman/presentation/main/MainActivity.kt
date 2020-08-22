package com.irfanirawansukirman.ustman.presentation.main

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.irfanirawansukirman.extensions.navigation
import com.irfanirawansukirman.ustman.R
import com.irfanirawansukirman.ustman.presentation.main.qibla.QiblaFragment
import net.time4j.SystemClock
import net.time4j.calendar.HijriCalendar
import net.time4j.engine.StartOfDay
import net.time4j.format.expert.ChronoFormatter
import net.time4j.format.expert.PatternType
import java.util.*

// source : https://stackoverflow.com/a/43493951
class MainActivity : AppCompatActivity() {

    val islamicMonths = listOf(
        "Muharram : Al-Muharram",
        "Safar : Safar",
        "Rabi' I : Rabiul Awal",
        "Rabi' II : Rabiul Akhir",
        "Jumada I : Jumadil Awal",
        "Jumada II : Jumadil Akhir",
        "Rajab : Rajab",
        "Sha'ban : Sya'ban",
        "Ramadan : Ramadhan",
        "Shawwal : Syawal",
        "Dhu'l-Qi'dah : Dzulqa'dah",
        "Dhul'l-Hijjah : Dzulhijjah"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, QiblaFragment.newInstance())
            .commit()

        val hijriFormat = ChronoFormatter.setUp(HijriCalendar.family(), Locale.ENGLISH)
            .addEnglishOrdinal(HijriCalendar.DAY_OF_MONTH)
            .addPattern(" MMMM yyyy", PatternType.CLDR)
            .build()
            .withCalendarVariant(HijriCalendar.VARIANT_UMALQURA)

        // conversion from gregorian to hijri-umalqura valid at noon
        // (not really valid in the evening when next islamic day starts)
        val today: HijriCalendar = SystemClock.inLocalView().today().transform(
            HijriCalendar::class.java,
            HijriCalendar.VARIANT_UMALQURA
        )
        println(hijriFormat.format(today)) // 22nd Rajab 1438

        // taking into account the specific start of day for Hijri calendar
        val todayExact: HijriCalendar = SystemClock.inLocalView().now(
            HijriCalendar.family(),
            HijriCalendar.VARIANT_UMALQURA,
            StartOfDay.EVENING // simple approximation => 18:00
        ).toDate()
        println(hijriFormat.format(todayExact)) // 22nd Rajab 1438 (23rd after 18:00)
//        txtMainDate.text = hijriFormat.format(todayExact)
//                .replace("st", "")
//                .replace("nd", "")
//                .replace("rd", "")
//                .replace("Muharram", "Al-Muharram")

        val islamicMonths = listOf(
            "Muharram : Al-Muharram",
            "Safar : Safar",
            "Rabi' I : Rabiul Awal",
            "Rabi' II : Rabiul Akhir",
            "Jumada I : Jumadil Awal",
            "Jumada II : Jumadil Akhir",
            "Rajab : Rajab",
            "Sha'ban : Sya'ban",
            "Ramadan : Ramadhan",
            "Shawwal : Syawal",
            "Dhu'l-Qi'dah : Dzulqa'dah",
            "Dhul'l-Hijjah : Dzulhijjah"
        )
    }
}