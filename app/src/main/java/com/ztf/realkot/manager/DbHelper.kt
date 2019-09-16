package com.ztf.realkot.manager

import com.ztf.greendao.DaoMaster
import com.ztf.greendao.DaoSession
import com.ztf.greendao.UserDao
import com.ztf.realkot.app.App
import com.ztf.realkot.bean.User

class DbHelper private constructor() {

    var daoSession: DaoSession? = null
        private set

    init {
        createDao()
    }

    private object SingletonHolder {
        internal val INSTANCE = DbHelper()
    }

    private fun createDao() {
        //创建数据库
        val helper =
            DaoMaster.DevOpenHelper(App.appContext, if (ENCRYPTED) "notes-db-encrypted" else "notes-db")
        //获取数据库读写的权限，如果进行加密调用helper.getEncryptedWritableDb("super-secret")，参数为设置的密码
        val db = if (ENCRYPTED) helper.getEncryptedWritableDb("super-secret") else helper.writableDb
        daoSession = DaoMaster(db).newSession()
    }

    fun addUser(user: User) {
        val userDao = daoSession!!.userDao
        userDao.insert(user)
    }

    fun queryUser():List<User>{
        return daoSession!!.userDao.queryBuilder().orderAsc(UserDao.Properties.UserId).list()
    }
    companion object {

        /**
         * 数据库是否加密的标识
         */
        const val ENCRYPTED = true

        //获取单例
        val instance: DbHelper
            get() = SingletonHolder.INSTANCE
    }
}