package com.example.tecpie.jiaju.activity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tecpie.jiaju.R;
import com.example.tecpie.jiaju.pojo.LoadBean;
import com.example.tecpie.jiaju.pojo.PostEqInfoBean;
import com.example.tecpie.jiaju.utils.HonyarWebServiceUtil;
import com.example.tecpie.jiaju.utils.WebServiceUtil;
import com.google.gson.Gson;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@ContentView(R.layout.activity_dl)
public class DlActivity extends BaseActivity {
    private final String log="DlActivity";
    private final String url ="file:///android_asset/dl.html";
    @ViewInject(R.id.dlwv)
    private WebView webView;
    private WebSettings webSettings;
    private String deviceId;
    private String endpointid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceId = getIntent().getStringExtra("deviceId");
        endpointid = getIntent().getStringExtra("endpointid");
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new SceneJsInterface(),"dl");
    }

    public class SceneJsInterface{
        @JavascriptInterface
        public void toast(final String s){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DlActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }

        @JavascriptInterface
        public void back() {
            DlActivity.this.finish();
        }

        @JavascriptInterface
        public String getElectricity(String date) {
            Log.i(log, date);
            Log.i(log, deviceId+"");
            Log.i(log, endpointid+"");
            PostEqInfoBean postEqInfoBean = new PostEqInfoBean();
            //设置id
            postEqInfoBean.setId(deviceId+"");
//            postEqInfoBean.setEndpointid(Integer.parseInt(endpointid));
            HonyarWebServiceUtil webServiceUtil = new HonyarWebServiceUtil();
            Gson gson = new Gson();
            /*****************************************/
            String postMessage;
            String respond;
            /*****************************************/
            //调用webservice请求
            postEqInfoBean.setDate(date);
            postMessage = gson.toJson(postEqInfoBean);

//            respond = webServiceUtil.webService("eqInfo", postMessage);
            Log.i(log, postMessage);
            respond = webServiceUtil.webService("loadHistoryDataYdl", postMessage);
            Log.i(log, respond);
            LoadBean bean = null;
            try {
                bean = gson.fromJson(respond, LoadBean.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Map map = new HashMap();
            map.put("data", bean.getYlist());

            map.put("label", bean.getXlist());

            Gson response = new Gson();
            String result = response.toJson(map).toString();
            Log.i(log, result);
            return result;
        }
    }
}
