package com.example.tecpie.jiaju.activity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tecpie.jiaju.R;
import com.example.tecpie.jiaju.utils.WebServiceUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


@ContentView(R.layout.activity_scene)
public class SceneActivity extends BaseActivity {
    private final String log="SceneActivity";
    private final String url = "file:///android_asset/scene.html";
    @ViewInject(R.id.scenewv)
    private WebView webView;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new SceneJsInterface(),"scene");
    }

    public class SceneJsInterface{
        @JavascriptInterface
        public void toast(final String s){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SceneActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }

        @JavascriptInterface
        public void hj(){
            String action = "exeScene";
            String message = "{" +
                    "\"userid\":\"1\"," +
                    "\"sceneid\":\"1\"" +
                    "}";
            String result = WebServiceUtil.webService(action, message);
            Log.i(log, "hj");
        }

        @JavascriptInterface
        public void lj(){
            String action = "exeScene";
            String message = "{" +
                    "\"userid\":\"1\"," +
                    "\"sceneid\":\"2\"" +
                    "}";
            String result = WebServiceUtil.webService(action, message);
            Log.i(log, "lj");
        }
    }
}
