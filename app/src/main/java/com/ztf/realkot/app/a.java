package com.ztf.realkot.app;

import com.ztf.realkot.bean.User;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ztf
 * @date 2019/9/6
 */
public class a {
    public a() {
        final List<User> users = new ArrayList<>();
        User user = new User();
        user.setUserAge(20);
        user.setUserName("aaa");
        users.add(user);
        user = new User();
        user.setUserAge(10);
        user.setUserName("ccc");
        users.add(user);
        user = new User();
        user.setUserAge(30);
        user.setUserName("bbb");
        users.add(user);
        Observable.from(users)
                .filter(new Func1<User, Boolean>() {
            @Override
            public Boolean call(User user) {
                return user.getUserAge()>10;
            }
        }).map(new Func1<User, String>() {
            @Override
            public String call(User user) {
                return user.getUserName();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        });
    }
    public static void main(String[] args){
        new a();
    }
}
