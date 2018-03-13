package com.example.tecpie.ZhiheSocket.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tecpie.ZhiheSocket.R;
import com.example.tecpie.ZhiheSocket.activity.DMonEntrieActivity;
import com.example.tecpie.ZhiheSocket.pojo.DMonEntrieBean;
import com.example.tecpie.ZhiheSocket.pojo.DMonEntries;
import com.example.tecpie.ZhiheSocket.pojo.FBoxBean;
import com.example.tecpie.ZhiheSocket.pojo.LoadBean;
import com.example.tecpie.ZhiheSocket.pojo.PostEqInfoBean;
import com.example.tecpie.ZhiheSocket.pojo.PostProfitInfoBean;
import com.example.tecpie.ZhiheSocket.pojo.RespondProfitInfoBean;
import com.example.tecpie.ZhiheSocket.pojo.ValueBean;
import com.example.tecpie.ZhiheSocket.pojo.ValueBody;
import com.example.tecpie.ZhiheSocket.utils.FBoxUtil;
import com.example.tecpie.ZhiheSocket.utils.WebServiceUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.fragment_index)
public class IndexFragment extends BaseFragment {
    private final String log = "IndexFragment";
    private final String url = "file:///android_asset/mindex.html";
    @ViewInject(R.id.indexwv)
    private WebView webView;
    private WebSettings webSettings;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
        });

        webView.addJavascriptInterface(new IndexJsInterface(), "index");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public class IndexJsInterface {


        @JavascriptInterface
        public void toast(final String s) {
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
