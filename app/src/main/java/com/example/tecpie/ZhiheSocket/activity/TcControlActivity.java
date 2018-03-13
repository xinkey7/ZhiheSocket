package com.example.tecpie.ZhiheSocket.activity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tecpie.ZhiheSocket.R;
import com.example.tecpie.ZhiheSocket.pojo.PostExcuteDevicesBean;
import com.example.tecpie.ZhiheSocket.pojo.PostExecuteBean;
import com.example.tecpie.ZhiheSocket.pojo.PostExecuteControlBean;
import com.example.tecpie.ZhiheSocket.pojo.PostQueryDeviceStateTCBean;
import com.example.tecpie.ZhiheSocket.pojo.RespondQueryDeviceStateTCBean;
import com.example.tecpie.ZhiheSocket.utils.WebServiceUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.activity_tccontrol)
public class TcControlActivity extends BaseActivity {
    private final String url = "file:///android_asset/tccontrol.html";
    @ViewInject(R.id.controlmv)
    private WebView webView;
    private WebSettings webSettings;
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceId = getIntent().getStringExtra("deviceId");
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient(){

        });
        webView.addJavascriptInterface(new ControlJsInterface(),"control");

    }

    class ControlJsInterface{
        @JavascriptInterface
        public String init(){
            String action = "queryDeviceState";
            String message = "{"+
                    "\"deviceid\":\""+deviceId+"\""+
            "}";
            return WebServiceUtil.webService(action,message);
        }

        @JavascriptInterface
        public String onOff(int flag){
            Log.i("TcControlActivity",flag+"");
            boolean status = false;
            if(flag==0){
                status =true;
            }else if(flag == 1 ){
                status = false;
            }
            long ct = System.currentTimeMillis();
            PostExecuteBean postExecuteBean = new PostExecuteBean();
            PostExcuteDevicesBean postExcuteDevicesBean = new PostExcuteDevicesBean();
            PostExecuteControlBean postExecuteControlBean = new PostExecuteControlBean();
            if(status){
                postExecuteControlBean.setRawdata("C006100000015C1B");
            }else{
                postExecuteControlBean.setRawdata("C006100000021C1A");
            }
            postExcuteDevicesBean.setId(deviceId);
            postExcuteDevicesBean.setControl(postExecuteControlBean);
            postExecuteBean.setTime(ct);
            List<PostExcuteDevicesBean> list = new ArrayList<PostExcuteDevicesBean>();
            list.add(postExcuteDevicesBean);
            postExecuteBean.setDevices(list);
            Gson gson = new GsonBuilder().setExclusionStrategies().create();
            String jsonStr = gson.toJson(postExecuteBean);
            return WebServiceUtil.webService("execute",jsonStr);
        }


        @JavascriptInterface
        public String power(int flag){//0:关;1:开
            boolean powerFlag = false;
            if(flag==0){
                powerFlag =true;
            }else if(flag == 1 ){
                powerFlag = false;
            }
            String action = "execute";
            String message = "{"+
                    "\"time\":"+new Date().getTime()+","+      //执+行时间long类型
                    "\"devices\":["+
                    "{"+
                    "\"id\":\""+deviceId+"\","+
                    "\"control\":{\"on\":"+powerFlag+"}"+   //参数可选，详情见附1
                    "}]"+
                    "}";
            return WebServiceUtil.webService(action,message);
        }

        @JavascriptInterface
        public String refreshData(){
            Log.i("TcControlActivity","refreshData");

            String fh= load();
            Log.i("TcControlActivity","fh:"+fh);
            try {
                Thread.sleep(500);//500毫秒后请求第二个数据
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String dl= electricity();

            Log.i("TcControlActivity","dl:"+dl);
            return "{\"fh\":"+fh+",\"dl\":"+dl+"}";
        }


        private String load(){
            PostQueryDeviceStateTCBean postQueryDeviceStateTCBean = new PostQueryDeviceStateTCBean();
            RespondQueryDeviceStateTCBean respondQueryDeviceStateTCBean = new RespondQueryDeviceStateTCBean();
            //设置id
            postQueryDeviceStateTCBean.setDeviceid(deviceId);
            postQueryDeviceStateTCBean.setRawdata("0303000D0001142B");
            WebServiceUtil webServiceUtil = new WebServiceUtil();
            Gson gson = new Gson();

            String postMessage = gson.toJson(postQueryDeviceStateTCBean);

            String respond = webServiceUtil.webService("queryDeviceStateTC", postMessage);
            String fh="";
            try {
                respondQueryDeviceStateTCBean = gson.fromJson(respond, RespondQueryDeviceStateTCBean.class);
                if ( respondQueryDeviceStateTCBean.isResult() == false){
                }else{
                    fh=respondQueryDeviceStateTCBean.getRawdata();
                }
            } catch (Exception e) {
            }
            return fh;
        }

        @JavascriptInterface
        public void toast(final String s){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(TcControlActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }

        private String electricity(){
            PostQueryDeviceStateTCBean postQueryDeviceStateTCBean = new PostQueryDeviceStateTCBean();
            RespondQueryDeviceStateTCBean respondQueryDeviceStateTCBean = new RespondQueryDeviceStateTCBean();
            //设置id
            postQueryDeviceStateTCBean.setDeviceid(deviceId);
            postQueryDeviceStateTCBean.setRawdata("030300000002C5E9");
            WebServiceUtil webServiceUtil = new WebServiceUtil();
            Gson gson = new Gson();

            String postMessage = gson.toJson(postQueryDeviceStateTCBean);

            String respond = webServiceUtil.webService("queryDeviceStateTC", postMessage);
            String electricity="";
            try {
                respondQueryDeviceStateTCBean = gson.fromJson(respond, RespondQueryDeviceStateTCBean.class);
                if ( respondQueryDeviceStateTCBean.isResult() == false){
                }else{
                    electricity=respondQueryDeviceStateTCBean.getRawdata();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return electricity;
        }

    }
}
