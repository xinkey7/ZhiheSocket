package com.example.tecpie.ZhiheSocket.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.tecpie.ZhiheSocket.R;
import com.example.tecpie.ZhiheSocket.pojo.DMonEntrieBean;
import com.example.tecpie.ZhiheSocket.pojo.DMonEntries;
import com.example.tecpie.ZhiheSocket.pojo.FBoxBean;
import com.example.tecpie.ZhiheSocket.pojo.ValueBean;
import com.example.tecpie.ZhiheSocket.pojo.ValueBody;
import com.example.tecpie.ZhiheSocket.utils.FBoxUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.activity_dmonentries)
public class DMonEntrieActivity extends BaseActivity {
    private final String log = "DMonEntrieActivity";
    private final String url = "file:///android_asset/index.html";
    @ViewInject(R.id.controlwv)
    private WebView webView;
    private WebSettings webSettings;
    private String index;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        index = getIntent().getStringExtra("index");
        name =  getIntent().getStringExtra("name");
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient(){

        });
        webView.addJavascriptInterface(new DMonEntrieJsInterface(),"entrieindex");
    }

    class DMonEntrieJsInterface{


        @JavascriptInterface
        public void back() {
            DMonEntrieActivity.this.finish();
        }




    }
}
