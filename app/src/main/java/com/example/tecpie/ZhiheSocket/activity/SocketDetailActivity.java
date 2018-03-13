package com.example.tecpie.ZhiheSocket.activity;

import android.content.Intent;
import android.os.Bundle;
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
@ContentView(R.layout.activity_socket_detail)
public class SocketDetailActivity extends BaseActivity{
    private final String url = "file:///android_asset/socketdetail.html";
    @ViewInject(R.id.socketdetail)
    private WebView webView;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new LoginJsInterface(),"socketDetail");
    }


    public class LoginJsInterface{


        @JavascriptInterface
        public void detail(){



            Intent intent = new Intent(SocketDetailActivity.this,ModifyActivity.class);
            startActivity(intent);
            JPushInterface.setAliasAndTags(SocketDetailActivity.this,"test",null);




        }





        @JavascriptInterface
        public void back() {
            SocketDetailActivity.this.finish();
        }

        @JavascriptInterface
        public void add(){




            Intent intent = new Intent(SocketDetailActivity.this,AddActivity.class);
            startActivity(intent);
            JPushInterface.setAliasAndTags(SocketDetailActivity.this,"test",null);




        }

        @JavascriptInterface
        public void toast(final String s){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SocketDetailActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
