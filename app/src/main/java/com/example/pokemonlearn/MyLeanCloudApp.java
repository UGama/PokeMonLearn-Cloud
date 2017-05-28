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
        AVOSCloud.initialize(this,"vuVTAFSIycc7XPJxLXLGCnTv-gzGzoHsz","YQwRAndPNiRlHSvjIqYMwAPL");
        AVOSCloud.setDebugLogEnabled(true);
    }
}

