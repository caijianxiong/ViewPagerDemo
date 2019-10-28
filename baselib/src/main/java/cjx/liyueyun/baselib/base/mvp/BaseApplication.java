package cjx.liyueyun.baselib.base.mvp;

import android.app.Application;

/**
 * @author caicai
 * @create 2019/10/28
 * @Describe
 */
public class BaseApplication extends Application {
public static BaseApplication appContext;

    public static BaseApplication getAppContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext=this;
    }
}
