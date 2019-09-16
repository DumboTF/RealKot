package com.ztf.realkot.utils

import android.text.TextUtils

/**
 * @author ztf
 * @date 2019/7/30
 */
object StringUtils {
    fun isEmpty(str: String): Boolean {
        return TextUtils.isEmpty(str)
    }

    /**
     * 判断是否为中文字符
     *
     * @param c
     * @return
     */
    private fun isChinese(c: Char): Boolean {
        val ub = Character.UnicodeBlock.of(c)
        return (ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub === Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub === Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub === Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub === Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
    }


    //Unicode转中文
    fun decodeUnicode(unicode: String): String {
        val string = StringBuilder()

        val hex = unicode.split("\\\\u".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        for (hex1 in hex) {

            try {
                // 汉字范围 \u4e00-\u9fa5 (中文)
                if (hex1.length >= 4) {//取前四个，判断是否是汉字
                    val chinese = hex1.substring(0, 4)
                    try {
                        val chr = Integer.parseInt(chinese, 16)
                        val isChinese = isChinese(chr.toChar())
                        //转化成功，判断是否在  汉字范围内
                        if (isChinese) {//在汉字范围内
                            // 追加成string
                            string.append(chr.toChar())
                            //并且追加  后面的字符
                            val behindString = hex1.substring(4)
                            string.append(behindString)
                        } else {
                            string.append(hex1)
                        }
                    } catch (e1: NumberFormatException) {
                        string.append(hex1)
                    }

                } else {
                    string.append(hex1)
                }
            } catch (e: NumberFormatException) {
                string.append(hex1)
            }

        }

        return string.toString()
    }
}
