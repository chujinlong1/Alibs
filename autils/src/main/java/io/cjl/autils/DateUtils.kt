package io.cjl.autils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    fun getFormatTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        return sdf.format(Date())
    }

    fun getFormatTime(time: Long): String {
        if (time > 0) {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
            return sdf.format(time)
        } else {
            return "-"
        }
    }

    fun getFormatTimeHour(time: Long): String {
        val sdf = SimpleDateFormat("MM-dd HH:mm", Locale.CHINA)
        return sdf.format(Date(time * 1000))
    }

    fun getFormatTimeMonth(time: Long): String {
        val sdf = SimpleDateFormat("MM月dd日", Locale.CHINA)
        return sdf.format(Date(time * 1000))
    }

    fun getFormatDuration(time1: Long, time2: Long): String {
        val nd = 1000 * 24 * 60 * 60
        val nh = 1000 * 60 * 60
        val nm = 1000 * 60
        val diff = time2 - time1
        val day = diff / nd
        val hour = diff % nd / nh
        val minute = diff % nd % nh / nm
        return "${day}天${hour}小时${minute}分钟"
    }

    fun getLastDayFromToday(lastDate: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        try {
            val lastTime = sdf.parse(lastDate).time
            if (lastTime.equals(0)) {
                return ""
            }
            val spaceT = (System.currentTimeMillis() - lastTime) / (24 * 3600000)
            if (spaceT <= 1) {
                return "今天"
            } else if (spaceT <= 31) {
                return "${spaceT}天前"
            } else {
                return "${spaceT / 30 + 1}月前"
            }
        } catch (e: Exception) {
            return ""
        }
    }

    fun getDateFromToday(day: Int): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        val currTime = System.currentTimeMillis();
        val dayTM = 24 * 60 * 60 * 1000;
        return try {
            sdf.format(currTime - dayTM * day);
        } catch (e: Exception) {
            ""
        }
    }
}