package com.ztf.realkot.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class DateUtil {

    private val formatToMS = SimpleDateFormat("mm:ss", Locale.US)
    private val formatToHM = SimpleDateFormat("HH:mm", Locale.US)
    private val formatToYMD = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val formatToYMDHMS = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    private val formatToMS_CN = SimpleDateFormat("mm分ss秒", Locale.US)
    private val formatToHMS_CN = SimpleDateFormat("HH时mm分ss秒", Locale.US)

    /**
     * 得到UTC时间，类型为字符串，格式为"yyyy-MM-dd HH:mm"<br></br>
     * 如果获取失败，返回null
     *
     * @return
     */
    // 1、取得本地时间：
    // 2、取得时间偏移量：
    // 3、取得夏令时差：
    // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
    val utcTimeStr: String?
        get() {
            val UTCTimeBuffer = StringBuffer()
            val cal = Calendar.getInstance()
            val zoneOffset = cal.get(Calendar.ZONE_OFFSET)
            val dstOffset = cal.get(Calendar.DST_OFFSET)
            cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset))
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH) + 1
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val hour = cal.get(Calendar.HOUR_OF_DAY)
            val minute = cal.get(Calendar.MINUTE)
            UTCTimeBuffer.append(year).append("-").append(month).append("-").append(day)
            UTCTimeBuffer.append(" ").append(hour).append(":").append(minute)
            try {
                formatToYMD.parse(UTCTimeBuffer.toString())
                return UTCTimeBuffer.toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return null
        }

    private val H: Long = 3600000
    private val M: Long = 60000

    /**
     * yyyy-MM-dd 串 转 date
     *
     * @param str
     * @return
     */
    fun ymdToDate(str: String): Date? {
        try {
            return formatToYMD.parse(str)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * yyyy-MM-dd HH:mm:ss 串 转 date
     *
     * @param str
     * @return
     */
    fun ymdhmsToDate(str: String): Date? {
        try {
            return formatToYMDHMS.parse(str)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * date 转 mm:ss的串
     *
     * @param date
     * @return
     */
    fun dateToMS(date: Date): String {
        return formatToMS.format(date)
    }

    fun dateToMS_CN(date: Date): String {
        return formatToMS_CN.format(date)
    }

    fun dateToHMS_CN(date: Date): String {
        return formatToHMS_CN.format(date)
    }

    /**
     * date 转 HH:mm的串
     *
     * @param date
     * @return
     */
    fun dateToHM(date: Date): String {
        return formatToHM.format(date)
    }

    /**
     * date 转 ymd的串
     *
     * @param date
     * @return
     */
    fun dateToYMD(date: Date): String {
        return formatToYMD.format(date)
    }

    /**
     * date 转 ymd hms的串
     *
     * @param date
     * @return
     */
    fun dateToYMDHMS(date: Date): String {
        return formatToYMDHMS.format(date)
    }

    /**
     * date 转 ms的串
     *
     * @param time
     * @return
     */
    fun longToMS(time: Long): String {
        val date = Date(time)
        return dateToMS(date)
    }

    fun longToMS_CN(time: Long): String {
        return dateToMS_CN(Date(time))
    }

    fun longToHMS_CN(time: Long): String {
        return dateToHMS_CN(Date(time))
    }

    /**
     * date 转 hm的串
     *
     * @param time
     * @return
     */
    fun longToHM(time: Long): String {
        val date = Date(time)
        return dateToHM(date)
    }

    /**
     * date 转 ymd的串
     *
     * @param time
     * @return
     */
    fun longToYMD(time: Long): String {
        val date = Date(time)
        return dateToYMD(date)
    }

    /**
     * date 转 ymd hms的串
     *
     * @param time
     * @return
     */
    fun longToYMDHMS(time: Long): String {
        val date = Date(time)
        return dateToYMDHMS(date)
    }

    fun toUTC(date: Date): Long {
        val cal = Calendar.getInstance()
        cal.time = date
        //        2、取得时间偏移量：
        val zoneOffset = cal.get(Calendar.ZONE_OFFSET)
        //        3、取得夏令时差：
        val dstOffset = cal.get(Calendar.DST_OFFSET)
        //        4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset))
        return cal.timeInMillis
    }

    /**
     * 将UTC时间转换为东八区时间
     *
     * @param UTCTime
     * @return
     */
    fun getLocalTimeFromUTC(UTCTime: String): String? {
        var UTCDate: Date? = null
        var localTimeStr: String? = null
        try {
            UTCDate = formatToYMD.parse(UTCTime)
            formatToYMD.timeZone = TimeZone.getTimeZone("GMT-8")
            localTimeStr = formatToYMD.format(UTCDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return localTimeStr
    }

    fun getLocalTimeFromUTC(UTCTime: Long): String? {
        var date: Date? = null
        var localTimeStr: String? = null
        date = Date(UTCTime)
        formatToHMS_CN.timeZone = TimeZone.getTimeZone("GMT-8")
        localTimeStr = formatToHMS_CN.format(date)
        return localTimeStr
    }

    fun getRemainTime(time: Long): String {
        val hour = time / H//小时
        val min_remain = time % H//剩余时间
        val minute = min_remain / M//取分
        val sec_remain = min_remain % M//剩余时间
        val sec = sec_remain / 1000
        return String.format("%02d时%02d分%02d秒", hour, minute, sec)
    }

    fun getCheckDate(year: Int, month: Int, day: Int, hour: Int, minute: Int, s: Int): Long {
        val sb = StringBuffer()
        sb.append(year).append("-").append(month).append("-").append(day).append(" ").append(hour).append(":")
            .append(minute).append(":").append(s)
        try {
            return formatToYMDHMS.parse(sb.toString()).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return 0
    }

    fun getLastMonthStart(time: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        calendar.set(year, month - 1, 1, 0, 0, 0)
        return calendar.time.time
    }

    companion object {
        internal var util: DateUtil? = null

        val instance: DateUtil
            get() {
                if (util == null) {
                    synchronized(DateUtil::class.java) {
                        if (util == null) {
                            util = DateUtil()
                        }
                    }
                }
                return util!!
            }
    }
}
