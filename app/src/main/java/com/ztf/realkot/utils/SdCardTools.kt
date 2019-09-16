package com.ztf.realkot.utils

import android.content.Context
import android.os.Environment

import java.io.*
import java.util.ArrayList

/**
 * 检测sd卡
 */
object SdCardTools {

    /**
     * 获取sdcard路径
     *
     * @return
     */
    internal var paths: MutableList<String>? = null

    /**
     * 获取所有路径
     *
     * @return
     */
    //                        mount = mount.concat("*" + columns[1] + "\n");
    // TODO Auto-generated catch block
    // TODO Auto-generated catch block
    val outSDPaths: MutableList<String>
        get() {
            if (paths == null) {
                paths = mutableListOf()
            } else {
                return this.paths!!
            }
            try {
                val path = Environment.getExternalStorageDirectory().path
                paths!!.add(path)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            try {
                val runtime = Runtime.getRuntime()
                val proc = runtime.exec("mount")
                val `is` = proc.inputStream
                val isr = InputStreamReader(`is`)
                var line: String?

                val br = BufferedReader(isr)
                line = br.readLine()
                while (line != null) {
                    if (line.contains("secure"))
                        continue
                    if (line.contains("asec"))
                        continue

                    if (line.contains("storage")) {
                        val columns = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        if (columns.size > 1) {
                            val path = columns[1]
                            val `as` = path.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                            if (path.contains("sdcard") && !paths!!.contains(path)) {
                                paths!!.add(path)
                            }
                            if (path.contains("0") && path.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size == 4 && !paths!!.contains(
                                    path
                                )
                            ) {
                                paths!!.add(0, path)
                            }
                        }
                    }
                    line = br.readLine()
                }

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return this.paths!!
        }

    val rootPath: String
        get() = Environment.getExternalStorageDirectory().path

    /**
     * 检查是否存在SDCard
     *
     * @return
     */
    fun hasSdcard(): Boolean {
        val state = Environment.getExternalStorageState()
        return state == Environment.MEDIA_MOUNTED
    }

    fun getCachePath(context: Context): String {
        val pkgPath = context.applicationContext.packageCodePath

        val absPath = context.applicationContext.filesDir.absolutePath

        return pkgPath
    }

    fun getSDcardPath(index: Int): String {
        if (paths == null) {
            paths = outSDPaths
        }
        if (paths!!.size == 0) {
            return ""
        }
        if (paths!!.size == 1) {
            return paths!![0]
        }
        return if (index >= 2) {
            paths!![paths!!.size - 1]
        } else {
            paths!![index]
        }
        //		String path = Environment.getExternalStorageDirectory().getPath();
        //		return path;
    }

}
