package com.example.pokemonlearn;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Gama on 27/5/17.
 */

public class MyLeanCloudApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"DL8EN0L4Wx0AYr53cyWQKdN7-gzGzoHsz","RlRIdwye4YEihV6NDV0KdnJE");
        AVOSCloud.setDebugLogEnabled(true);
    }
}

