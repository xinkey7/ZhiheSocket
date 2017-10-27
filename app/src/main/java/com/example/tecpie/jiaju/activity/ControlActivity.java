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
import com.example.tecpie.jiaju.pojo.App_request_execute;
import com.example.tecpie.jiaju.pojo.Control;
import com.example.tecpie.jiaju.pojo.ControlBean;
import com.example.tecpie.jiaju.pojo.StateBean;
import com.example.tecpie.jiaju.utils.HonyarWebServiceUtil;
import com.example.tecpie.jiaju.utils.WebServiceUtil;
import com.google.gson.Gson;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Date;

/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.activity_control)
public class ControlActivity extends BaseActivity {
    private final String log = "ControlActivity";
    private final String url = "file:///android_asset/control.html";
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
        webView.setWebChromeClient(new WebChromeClient() {

        });
        webView.addJavascriptInterface(new ControlJsInterface(), "control");
    }

    class ControlJsInterface {
        @JavascriptInterface
        public String init() {
            HonyarWebServiceUtil webServiceUtil = new HonyarWebServiceUtil();
            Log.i("-->", "init "+endpointid);
            String action = "queryDeviceState";
            String id=deviceId+"";
            String on="123";
            String message = "{" +
                    "\"id\":"+id+"," +
                    "\"gateway\":" + on + "," +      //执+行时间long类型
                    "\"endpoint\":\"\"" +
                    "}";
            Log.i("-->", message);
          String result = webServiceUtil.webService(action, message);
            Log.i("-->",result);
           /*Response_QueryDeviceState state=new Response_QueryDeviceState();
           state.setResult(true);
          Bean_state s=new Bean_state();
            s.setIs_on(1);
           s.setColortemp(20);
           state.setState(s);
          Gson gson=new Gson();
          String result=gson.toJson(state);*/
            Log.i("-->", result);
            return result;
        }


        @JavascriptInterface
        public String changeTemp(int actemp, int flag) {//0:-,1:+
            HonyarWebServiceUtil webServiceUtil = new HonyarWebServiceUtil();
            String action = "execute";
            int temp = actemp;
            Control c = new Control();
            c.setOn(true);
            c.setMode("3");
            c.setTemp("24");
            c.setWind("3");
            ControlBean controlBean = new ControlBean();
            controlBean.setId("1");
            controlBean.setControl(c);
            Gson gson = new Gson();
            String postMessage = gson.toJson(controlBean);

            String message = "{" +
                    "\"time\":" + new Date().getTime() + "," +      //执+行时间long类型
                    "\"devices\":[" +
                    "{" +
                    "\"id\":\"" + deviceId + "\"," +
                    "\"control\":{\"infraredcontrol\":" + temp + "}" +   //参数可选，详情见附1
                    "}]" +
                    "}";
            return webServiceUtil.webService(action, message);
        }

        @JavascriptInterface
        public String power(int flag) {//0:关;1:开
            deviceId = getIntent().getStringExtra("deviceId");
            HonyarWebServiceUtil webServiceUtil = new HonyarWebServiceUtil();
            StateBean stateBean = new StateBean();
            App_request_execute app_request_execute = new App_request_execute();
            app_request_execute.setId(deviceId);
            if(flag==1){
                stateBean.setOn(false);
            }else{
                stateBean.setOn(true);
            }
            app_request_execute.setControl(stateBean);

            Gson gson = new Gson();
            String postMessage = gson.toJson(app_request_execute);
            Log.i(log,"flag:"+ flag);
            String action = "airconditionOnOff";
//            String message = "{" +
//                    "\"gateway\":\"" + BaseActivity.gateway + "\"," +
//                    "\"time\":" + new Date().getTime() + "," +      //执+行时间long类型
//                    "\"endpointid\":\"" + endpointid + "\"," +
//                    "\"id\":\"" + deviceId + "\"," +
//                    "\"control\":{\"on\":" + powerFlag + "}" +   //参数可选，详情见附1
//                    "}";
//            String message = "{" +
//                    "\"aircondition\":13," +
//                    "\"on\":" + on + "," +      //执+行时间long类型
//                    "\"user\":\"测试\"" +
//                    "}";
            /*String message = "{" +
                    "\"id\":"+id+"," +
                    "\"on\":" + on + "," +      //执+行时间long类型
                    "\"user\":\"test\"" +
                    "}";*/
           // Log.i(log, message);
            Log.i("控制",postMessage);
            String respond  =HonyarWebServiceUtil.webService(action, postMessage);
            Log.i("控制",respond);
            return respond;
        }

        @JavascriptInterface
        public String changeMode(int flag) {//2:制冷;3:制热
            int modeFlag = 2;
            if (flag == 2) {
                modeFlag = 3;
            } else if (flag == 3) {
                modeFlag = 2;
            }
            String action = "execute";
            String message = "{" +
                    "\"time\":" + new Date().getTime() + "," +      //执+行时间long类型
                    "\"devices\":[" +
                    "{" +
                    "\"id\":\"" + deviceId + "\"," +
                    "\"control\":{\"infraredcontrol\":" + modeFlag + "}" +   //参数可选，详情见附1
                    "}]" +
                    "}";
            return WebServiceUtil.webService(action, message);
        }

        @JavascriptInterface
        public void dl() {
            Intent intent = new Intent();
            intent.putExtra("deviceId",deviceId);
            intent.putExtra("endpointid",endpointid);
            intent.setClass(ControlActivity.this, DlActivity.class);
            startActivity(intent);
        }

        @JavascriptInterface
        public void fh() {
            Intent intent = new Intent();
            intent.putExtra("deviceId",deviceId);
            intent.putExtra("endpointid",endpointid);
            intent.setClass(ControlActivity.this, FhActivity.class);
            startActivity(intent);
        }

        @JavascriptInterface
        public void back() {
            ControlActivity.this.finish();
        }
        @JavascriptInterface
        public void toast(String txt) {
            Toast toast = Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
