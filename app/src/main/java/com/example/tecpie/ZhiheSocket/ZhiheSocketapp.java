package com.example.tecpie.ZhiheSocket;

import android.app.Application;

import android.util.Log;

import com.example.tecpie.ZhiheSocket.pojo.CustomMessage;

import org.xutils.x;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by AmeKing on 2016/7/27.
 */
public class ZhiheSocketapp extends Application {
    public static ArrayList<CustomMessage> messageList = new ArrayList<>();

    @Override
    public void onCreate() {
        //MultiDex.install(this);
        Log.i("Test","ahaha");
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
