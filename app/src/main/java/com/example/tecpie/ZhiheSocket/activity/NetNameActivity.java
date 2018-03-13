package com.example.tecpie.ZhiheSocket.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tecpie.ZhiheSocket.R;
import com.example.tecpie.ZhiheSocket.pojo.FBoxLoginBean;
import com.example.tecpie.ZhiheSocket.utils.FBoxUtil;
import com.google.gson.Gson;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.activity_net_name)
public class NetNameActivity extends BaseActivity{
    private final String url = "file:///android_asset/netName.html";
    @ViewInject(R.id.netname)
    private WebView webView;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new LoginJsInterface(),"net");
    }


    public class LoginJsInterface{
        @JavascriptInterface
        public void automation(){
            Toast.makeText(NetNameActivity.this,"进入网关配置...",Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(NetNameActivity.this,MainActivity.class);
                startActivity(intent);
                JPushInterface.setAliasAndTags(NetNameActivity.this,"test",null);




        }



        @JavascriptInterface
        public String getSSID(){
            WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            Log.d("wifiInfo", wifiInfo.toString());
            Log.d("SSID",wifiInfo.getSSID());
            return wifiInfo.getSSID().replace("\"", "");
        }

        @JavascriptInterface
        public String getName(){
            SharedPreferences sharedPreferences = getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            String netName = sharedPreferences.getString("current-netName", "");
            return netName;
        }

        @JavascriptInterface
        public void back() {
            NetNameActivity.this.finish();
        }

        @JavascriptInterface
        public void next(String wifiName,String netName){
            SharedPreferences sharedPreferences = getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("current-wifiName",wifiName);
            editor.putString("current-netName",netName);
            editor.commit();
            Intent intent = new Intent(NetNameActivity.this,SocketManageActivity.class);
            startActivity(intent);
        }

        @JavascriptInterface
        public void netinfo(){

            Intent intent = new Intent(NetNameActivity.this,NetDetailActivity.class);
            intent.putExtra("current",true);
            startActivity(intent);
        }

        @JavascriptInterface
        public void toast(final String s){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(NetNameActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
