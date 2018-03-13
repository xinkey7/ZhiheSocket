package com.example.tecpie.ZhiheSocket.activity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tecpie.ZhiheSocket.R;
import com.example.tecpie.ZhiheSocket.utils.WebServiceUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


@ContentView(R.layout.activity_automation)
public class AutomationActivity extends BaseActivity {
    private final String log="AutomationActivity";
    private final String url = "file:///android_asset/automation.html";
    @ViewInject(R.id.automationwv)
    private WebView webView;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new AutomationJsInterface(),"automation");
    }

    public class AutomationJsInterface{
        @JavascriptInterface
        public void toast(final String s){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AutomationActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }

        @JavascriptInterface
        public String automation(){
            String action = "automation";
            String message = "{" +
                    "\"automationid\":\"1\"" +
                    "}";
            String result = WebServiceUtil.webService(action, message);
            Log.i(log,result);
            return result;
        }
    }
}
