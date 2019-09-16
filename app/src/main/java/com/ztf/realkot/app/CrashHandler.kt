package com.ztf.realkot.app

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.telephony.TelephonyManager
import com.ztf.realkot.utils.DateUtil
import com.ztf.realkot.utils.SdCardTools

import java.io.*
import java.lang.Thread.UncaughtExceptionHandler

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 *
 * @author user
 */
class CrashHandler
// 用来存储设备信息和异常信息
//    private Map<String, String> infos = new HashMap<String, String>();

// 用于格式化日期,作为日志文件名的一部分
//    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

/**
 * 保证只有一个CrashHandler实例
 */
private constructor() : UncaughtExceptionHandler {

    // 系统默认的UncaughtException处理类
    private var mDefaultHandler: UncaughtExceptionHandler? = null
    // 程序的Context对象
    private var mContext: Context? = null

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    //    public void collectDeviceInfo(Context ctx) {
    //        try {
    //            PackageManager pm = ctx.getPackageManager();
    //            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
    //                    PackageManager.GET_ACTIVITIES);
    //            if (pi != null) {
    //                String versionName = pi.versionName == null ? "null"
    //                        : pi.versionName;
    //                String versionCode = pi.versionCode + "";
    //                infos.put("versionName", versionName);
    //                infos.put("versionCode", versionCode);
    //            }
    //        } catch (NameNotFoundException e) {
    //            Log.e(TAG, "an error occured when collect package info", e);
    //        }
    //        Field[] fields = Build.class.getDeclaredFields();
    //        for (Field field : fields) {
    //            try {
    //                field.setAccessible(true);
    //                infos.put(field.getFileName(), field.get(null).toString_YMD());
    //                Log.d(TAG, field.getFileName() + " : " + field.get(null));
    //            } catch (Exception e) {
    //                Log.e(TAG, "an error occured when collect crash info", e);
    //            }
    //        }
    //    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    internal var pm: PackageManager? = null
    internal var teleManager: TelephonyManager? = null
    internal var stringBuffer: StringBuffer? = null
    internal var versionName: String = ""
    internal var versionCode: String = ""

    /**
     * 初始化
     *
     * @param context
     */
    fun init(context: Context) {
        mContext = context
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler!!.uncaughtException(thread, ex)

        } else {
            //            try {
            //                Thread.sleep(3000);
            //            } catch (InterruptedException e) {
            //                LogUtils.e(TAG, e.getMessage());
            //            }
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(0)
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null) {
            return false
        }
        // 收集设备参数信息
        //        collectDeviceInfo(mContext);
        // 保存日志文件
        try {
            saveCrashInfo2File(ex)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return true
    }

    private fun getDeviceInfo(context: Context) {
        try {
            stringBuffer = StringBuffer()
            pm = context.packageManager
            teleManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val pi = pm!!.getPackageInfo(
                context.packageName,
                PackageManager.GET_ACTIVITIES
            )
            if (pi != null) {
                versionName = if (pi.versionName == null)
                    "null"
                else
                    pi.versionName
                versionCode = pi.versionCode.toString() + ""
            }
            val brand = Build.BRAND//手机品牌
            val type = Build.DEVICE//手机品牌
            val sysVersion = Build.VERSION.RELEASE
            stringBuffer!!.append("手机品牌：").append(brand).append("\r\n手机型号：").append(type).append("\r\n系统版本：")
                .append(sysVersion).append("\r\nversionName：").append(versionName).append("\r\nversionCode：")
                .append(versionCode)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

    }

    private fun getLocation() {
        try {
            val result = ""
            //            if (LocationUtils.openGPSSettings(mContext)) {
            //            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            //            result = UILApplication.getInstance().getLocation();
            //            }
            stringBuffer!!.append("\r\nLocation：\r\n$result\r\n")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun saveCrashInfo2File(ex: Throwable) {
        getDeviceInfo(mContext!!)
        // 定位。
        //        getLocation();

        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause: Throwable? = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        try {
            stringBuffer!!.append("\r\n原因：").append(ex.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            stringBuffer!!.append("\r\nMethod：").append(ex.stackTrace[0].methodName)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        var lineNum = 0
        try {
            val cls = ex.stackTrace[0].fileName
            lineNum = ex.stackTrace[0].lineNumber
            stringBuffer!!.append("\r\nLocation：").append(cls).append("---").append(lineNum)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            stringBuffer!!.append("\r\nDetail：").append(result)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        var fos: FileOutputStream? = null
        try {
            var time = ""
            try {
                val timestamp = System.currentTimeMillis()
                time = DateUtil.instance.longToYMDHMS(timestamp)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val fileName = "Crash-$time-$lineNum.txt"
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                val path = SdCardTools.rootPath + "/dumbo" + "/crash/"
                val dir = File(path + fileName)
                if (!dir.exists()) {
                    dir.parentFile.mkdirs()
                    dir.createNewFile()
                }
                fos = FileOutputStream(dir)
                val str = stringBuffer.toString()
                fos.write(str.toByteArray(charset("GBK")))
                fos.flush()
                fos.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    companion object {

        val TAG = "CrashHandler"
        // CrashHandler实例
        /**
         * 获取CrashHandler实例 ,单例模式
         */
        val instance = CrashHandler()
    }
}
