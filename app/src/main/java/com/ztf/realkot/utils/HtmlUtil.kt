package com.ztf.realkot.utils

/**
 * @author ztf
 * @date 2019/8/13
 */
object HtmlUtil {
    fun lightText(src: String, lightStr: String): String {
        val sb = StringBuilder()
        val len = lightStr.length
        var remain = src
        while (true) {
            val index = remain.indexOf(lightStr)
            if (index != -1) {
                sb.append(remain.substring(0, index))
                remain = remain.substring(index + len)
                sb.append(setString(lightStr))
            } else {
                sb.append(remain)
                break
            }
        }
        return sb.toString()
    }

    fun lightTextIgnoreCase(src: String, lightStr: String): String {
        val sb = StringBuilder()
        val len = lightStr.length
        var srcLower = src.toLowerCase()
        var remain = src
        val lightLower = lightStr.toLowerCase()
        while (true) {
            val index = srcLower.indexOf(lightLower)
            if (index != -1) {
                sb.append(remain.substring(0, index))
                val tmp = remain.substring(index, index + len)
                sb.append(setString(tmp))
                remain = remain.substring(index + len)
                srcLower = srcLower.substring(index + len)
            } else {
                sb.append(remain)
                break
            }
        }
        return sb.toString()
    }

    fun setString(lightStr: String): String {
        return "<font color=\"#FF7700\">$lightStr</font>"
    }

    fun replaceEnter(src: String): String {
        return src.replace("\n", "<br>")
    }
}
