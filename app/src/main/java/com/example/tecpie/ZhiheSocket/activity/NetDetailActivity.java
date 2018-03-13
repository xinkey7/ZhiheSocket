package com.example.tecpie.ZhiheSocket.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tecpie.ZhiheSocket.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.activity_net_detail)
public class NetDetailActivity extends BaseActivity{
    private final String url = "file:///android_asset/netdetail.html";
    @ViewInject(R.id.netdetail)
    private WebView webView;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new LoginJsInterface(),"netDetail");
    }


    public class LoginJsInterface{

        @JavascriptInterface
        public void back() {
            NetDetailActivity.this.finish();
        }

        @JavascriptInterface
        public String getPanid() {
            Log.i("NetDetail","1");
            if(getIntent().getBooleanExtra("current",false)){
                SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
                return preferences.getString("current-panid","");
            }else{
                SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
                return preferences.getString("current-panid","");
                //return "N/";
            }

        }
        @JavascriptInterface
        public String getGateway() {
            Log.i("NetDetail","2");
            if(getIntent().getBooleanExtra("current",false)){
                SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
                return preferences.getString("current-gateway","");
            }else{
                SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
                return preferences.getString("current-gateway","");
                //return "N/";
            }

        }

        @JavascriptInterface
        public String getProfile() {
            Log.i("NetDetail","3");
            if(getIntent().getBooleanExtra("current",false)){
                SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
                return preferences.getString("current-profile","");
            }else{
                SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
                return preferences.getString("current-profile","");
                //return "N/";
            }

        }

        @JavascriptInterface
        public String getChannel() {
            Log.i("NetDetail","4");
            if(getIntent().getBooleanExtra("current",false)){
                SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
                return preferences.getString("current-channel","");
            }else{
                SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
                return preferences.getString("current-channel","");
                //return "N/";
            }

        }

        @JavascriptInterface
        public String getMac() {
            Log.i("NetDetail","5");
            if(getIntent().getBooleanExtra("current",false)){
                SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
                return preferences.getString("current-mac","");
            }else{
                SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
                return preferences.getString("current-mac","");
                //return "N/";
            }

        }






        @JavascriptInterface
        public void toast(final String s){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(NetDetailActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
