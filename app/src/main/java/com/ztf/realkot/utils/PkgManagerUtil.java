package com.ztf.realkot.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

/**
 * @author ztf
 * @date 2019/9/5
 */
public class PkgManagerUtil {
    private PackageManager manager;
    private String pkgName;
    private Context context;
    private PkgManagerUtil(Context context) {
        manager = context.getPackageManager();
        pkgName = context.getPackageName();
        this.context = context;
    }

    private static PkgManagerUtil instance;

    public static PkgManagerUtil create(Context context) {
        if (instance == null) {
            synchronized (PkgManagerUtil.class) {
                if (instance == null) {
                    instance = new PkgManagerUtil(context);
                }
            }
        }
        return instance;
    }

    //启用
    private void enableComponet(ComponentName componentName) {
        manager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    //禁用
    private void disableComponet(ComponentName componentName) {
        manager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    public void changeIcon(int flag){
        ComponentName mDefault =new ComponentName(context,pkgName+".MainActivity");
        ComponentName mNew = new ComponentName(context,pkgName+".icon1");
        if(flag==0){
            enableComponet(mDefault);
            disableComponet(mNew);
        }else {
            enableComponet(mNew);
            disableComponet(mDefault);
        }
    }
}
