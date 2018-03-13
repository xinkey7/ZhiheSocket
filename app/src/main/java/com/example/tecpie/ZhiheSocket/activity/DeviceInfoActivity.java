package com.example.tecpie.ZhiheSocket.activity;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tecpie.ZhiheSocket.R;
import com.example.tecpie.ZhiheSocket.utils.FBoxUtil;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;



@ContentView(R.layout.activity_device_info)
public class DeviceInfoActivity extends BaseActivity {
    private final String log="DeviceInfoActivity";
    private final String url ="file:///android_asset/deviceinfo.html";
    @ViewInject(R.id.diwv)
    private WebView webView;
    private WebSettings webSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new DeviceInfoActivity.DeviceInfoJsInterface(),"deviceInfo");
    }

    public class DeviceInfoJsInterface{
        @JavascriptInterface
        public void toast(final String s){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DeviceInfoActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }

        @JavascriptInterface
        public void back() {
            DeviceInfoActivity.this.finish();
        }






    }
}
