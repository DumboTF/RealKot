package com.ztf.realkot.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * @author ztf
 * @date 2019/9/2
 */
@Entity
public class User {
    @Id(autoincrement = true)
    Long id;
    @Unique
    Long userId;
    String userName;
    Integer userAge;

    @Generated(hash = 247300317)
    public User(Long id, Long userId, String userName, Integer userAge) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userAge = userAge;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return this.userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
