package com.example.tecpie.ZhiheSocket.fragment;

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
import com.example.tecpie.ZhiheSocket.bean.AlarmShowBean;
import com.example.tecpie.ZhiheSocket.bean.AlarmShowList;
import com.example.tecpie.ZhiheSocket.pojo.AlarmBean;
import com.example.tecpie.ZhiheSocket.utils.FBoxUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.fragment_record)
public class RecordFragment extends BaseFragment {
    private final String url = "file:///android_asset/record.html";
    private final String log = "RecordFragment";
    @ViewInject(R.id.recordwv)
    private WebView webView;
    private WebSettings webSettings;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = super.onCreateView(inflater, container, savedInstanceState);
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
        });

        webView.addJavascriptInterface(new RecordFragment.RecordJsInterface(), "record");
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    public class RecordJsInterface {

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
