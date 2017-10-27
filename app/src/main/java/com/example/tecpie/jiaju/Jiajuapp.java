package com.example.tecpie.jiaju;

import android.app.Application;
import android.util.Log;

import com.example.tecpie.jiaju.pojo.CustomMessage;

import org.xutils.x;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by AmeKing on 2016/7/27.
 */
public class Jiajuapp extends Application {
    public static ArrayList<CustomMessage> messageList = new ArrayList<>();

    @Override
    public void onCreate() {

        Log.i("Test","ahaha");
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
