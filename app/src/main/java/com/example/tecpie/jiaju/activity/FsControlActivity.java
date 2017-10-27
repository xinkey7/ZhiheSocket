package com.example.tecpie.jiaju.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tecpie.jiaju.R;
import com.example.tecpie.jiaju.bean.Bean_state;
import com.example.tecpie.jiaju.bean.Response_QueryDeviceState;
import com.example.tecpie.jiaju.utils.HonyarWebServiceUtil;
import com.example.tecpie.jiaju.utils.WebServiceUtil;
import com.google.gson.Gson;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Date;

/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.activity_fscontrol)
public class FsControlActivity extends BaseActivity {
    private final String log = "FsControlActivity";
    private final String url = "file:///android_asset/fscontrol.html";
    @ViewInject(R.id.controlwv)
    private WebView webView;
    private WebSettings webSettings;
    private String deviceId;
    private String endpointid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceId = getIntent().getStringExtra("deviceId");
        Log.i("-->", "onCreate "+deviceId);
        endpointid = getIntent().getStringExtra("endpointid");
        Log.i("-->", "onCreate "+endpointid);
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
            Log.i("-->", "init "+endpointid);
            String action = "queryDeviceState";
            String message = "{" +
                    "\"gateway\":\"" + BaseActivity.gateway + "\"," +
                    "\"id\":\"" + deviceId + "\"," +
                    "\"endpointid\":\"" + endpointid + "\"" +
                    "}";
            Log.i("-->", message);
            //String result = WebServiceUtil.webService(action, message);
            Response_QueryDeviceState state=new Response_QueryDeviceState();
            state.setResult(true);
            Bean_state s=new Bean_state();
            s.setIs_on(1);
//            s.setColortemp(20);
            state.setState(s);
            Gson gson=new Gson();
            String result=gson.toJson(state);
            Log.i("-->", result);
            return result;
        }

        @JavascriptInterface
        public String power(int flag){//0:关;1:开
            boolean powerFlag = false;
            deviceId = getIntent().getStringExtra("deviceId");
            int id = Integer.parseInt(deviceId);
            if(flag==0){
                powerFlag =true;
            }else if(flag == 1 ){
                powerFlag = false;
            }
//            String action = "execute";
//            String message = "{"+
//                    "\"time\":"+new Date().getTime()+","+      //执+行时间long类型
//                    "\"devices\":["+
//                    "{"+
//                    "\"id\":\""+deviceId+"\","+
//                    "\"control\":{\"on\":"+powerFlag+"}"+   //参数可选，详情见附1
//                    "}]"+
//                    "}";
            String action = "airconditionOnOff";
            String message = "{" +
                    "\"aircondition\":"+id+"," +
                    "\"on\":" + powerFlag + "," +      //执+行时间long类型
                    "\"user\":\"测试\"" +
                    "}";
            Log.i(log, message);
            return HonyarWebServiceUtil.webService(action, message);
        }

        @JavascriptInterface
        public void back() {
            FsControlActivity.this.finish();
        }

        @JavascriptInterface
        public void dl() {
            Intent intent = new Intent();
            intent.putExtra("deviceId",deviceId);
            intent.putExtra("endpointid",endpointid);
            intent.setClass(FsControlActivity.this, DlActivity.class);
            startActivity(intent);
        }

        @JavascriptInterface
        public void fh() {
            Intent intent = new Intent();
            intent.putExtra("deviceId",deviceId);
            intent.putExtra("endpointid",endpointid);
            intent.setClass(FsControlActivity.this, FhActivity.class);
            startActivity(intent);
        }
    }
}
