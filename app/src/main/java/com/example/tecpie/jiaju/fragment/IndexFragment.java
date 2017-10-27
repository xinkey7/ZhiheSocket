package com.example.tecpie.jiaju.fragment;

import android.content.Intent;
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

import com.example.tecpie.jiaju.R;
import com.example.tecpie.jiaju.activity.BaseActivity;
import com.example.tecpie.jiaju.activity.ConfigActivity;
import com.example.tecpie.jiaju.activity.ControlActivity;
import com.example.tecpie.jiaju.activity.FsControlActivity;
import com.example.tecpie.jiaju.activity.LoginActivity;
import com.example.tecpie.jiaju.activity.MainActivity;
import com.example.tecpie.jiaju.activity.TcControlActivity;
import com.example.tecpie.jiaju.bean.Bean_device;
import com.example.tecpie.jiaju.bean.Bean_device2;
import com.example.tecpie.jiaju.bean.Bean_state;
import com.example.tecpie.jiaju.bean.Bean_state2;
import com.example.tecpie.jiaju.bean.Response_QueryDevice;
import com.example.tecpie.jiaju.bean.Response_QueryDevice2;
import com.example.tecpie.jiaju.pojo.LoadBean;
import com.example.tecpie.jiaju.pojo.PostEqInfoBean;
import com.example.tecpie.jiaju.pojo.PostProfitInfoBean;
import com.example.tecpie.jiaju.pojo.PostQueryDeviceBean;
import com.example.tecpie.jiaju.pojo.RespondEqInfoBean;
import com.example.tecpie.jiaju.pojo.RespondEqInfoValuesBean;
import com.example.tecpie.jiaju.pojo.RespondProfitInfoBean;
import com.example.tecpie.jiaju.utils.DateUtil;
import com.example.tecpie.jiaju.utils.HonyarWebServiceUtil;
import com.example.tecpie.jiaju.utils.MethodReflect;
import com.example.tecpie.jiaju.utils.WebServiceUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.fragment_index)
public class IndexFragment extends BaseFragment {
    private final String log = "IndexFragment";
    private final String url = "file:///android_asset/index.html";
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

    public class IndexJsInterface {
        @JavascriptInterface
        public void add(){

            Intent intent = new Intent(getActivity(),ConfigActivity.class);
            startActivity(intent);

        }
        @JavascriptInterface
        public void control(String deviceId, String type, String endpointid) {
            if (type.equals("kt")) {
                Intent intent = new Intent(getActivity(), ControlActivity.class);
                intent.putExtra("deviceId", deviceId);
                intent.putExtra("endpointid", endpointid);
                startActivity(intent);
            }
            if (type.equals("fs")) {
                Intent intent = new Intent(getActivity(), FsControlActivity.class);
                intent.putExtra("deviceId", deviceId);
                intent.putExtra("endpointid", endpointid);
                startActivity(intent);
            }
        }

        @JavascriptInterface
        public String getDevice() {
            HonyarWebServiceUtil webServiceUtil = new HonyarWebServiceUtil();
            Gson gson = new Gson();
           String action1 = "queryDevice";
            String id ="";
            String on = "";
           String message = "{" +
                   "\"id\":"+id+"," +
                   "\"gateway\":" + on + "," +      //执+行时间long类型
                   "\"endpoint\":\"\"" +
                   "}";
            Log.i(log, "message for queryDevice"+message);
           String resultl = webServiceUtil.webService(action1, message);
           Log.i(log, resultl);
           /* PostQueryDeviceBean qdb = null;
            try{
              qdb =  gson.fromJson(resultl,PostQueryDeviceBean.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i(log, qdb.toString());*/
            Response_QueryDevice2  bean = null;
            try {
                bean = gson.fromJson(resultl,Response_QueryDevice2.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(bean.isResult()){
                Log.i(log, "ss");
            }
            String action = "airconditionRealValue";
            String postMessage;
            String respond;


            Response_QueryDevice2 devices=new Response_QueryDevice2();
            List<Bean_device2> list=new ArrayList<Bean_device2>();

            PostEqInfoBean postEqInfoBean = new PostEqInfoBean();

            Bean_state2 state=new Bean_state2();
            state.setOn(true);

//            //设置id
//            postEqInfoBean.setId("13");
//            postMessage = gson.toJson(postEqInfoBean);
//            respond = webServiceUtil.webService(action, postMessage);
//
//            Bean_device device=new Bean_device();
//            device.setId("13");
//            device.setName("测试电风扇");
//            device.setEndpointid(1);
//            device.setEquipment_type(2);
//            device.setState(state);
//            device.setMac(respond);
//            list.add(device);
//
            postEqInfoBean.setId("34");
           postMessage = gson.toJson(postEqInfoBean);
           respond = webServiceUtil.webService(action, postMessage);

            Bean_device2 device_j=new Bean_device2();
           device_j.setId("34");
            device_j.setName("景-空调");
            device_j.setEndpointid(1);
            device_j.setEquipment_type(1);
           state.setOn(true);
            device_j.setState(state);
           device_j.setMac(respond);
           list.add(device_j);
            Log.i(log, "这里"+respond);
            devices.setResult(true);
            devices.setDevices(list);
            String result=gson.toJson(devices);
            Log.i(log, result);
            return resultl;
        }

        @JavascriptInterface
        public String getLoad() {
            PostEqInfoBean postEqInfoBean = new PostEqInfoBean();
            //设置id
            postEqInfoBean.setId("1");
            postEqInfoBean.setEndpointid(1);
            WebServiceUtil webServiceUtil = new WebServiceUtil();
            Gson gson = new Gson();
            /*****************************************/
            String postMessage;
            String respond;
            /*****************************************/
            //调用webservice请求
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String todayString = formatter.format(date);
            postEqInfoBean.setDate(todayString);
            postMessage = gson.toJson(postEqInfoBean);

            respond = webServiceUtil.webService("loadInfo", postMessage);
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

        @JavascriptInterface
        public String getElectricity() {
            PostEqInfoBean postEqInfoBean = new PostEqInfoBean();
            //设置id
            postEqInfoBean.setId("1");
            postEqInfoBean.setEndpointid(1);
            WebServiceUtil webServiceUtil = new WebServiceUtil();
            Gson gson = new Gson();
            /*****************************************/
            String postMessage;
            String respond;
            /*****************************************/
            //调用webservice请求
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String todayString = formatter.format(date);
            postEqInfoBean.setDate(todayString);
            postMessage = gson.toJson(postEqInfoBean);

            respond = webServiceUtil.webService("eqInfo", postMessage);
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

        @JavascriptInterface
        public String profit() {
            List<RespondProfitInfoBean> respondProfitInfoBeanList
                    = new ArrayList<RespondProfitInfoBean>();
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            PostProfitInfoBean postProfitInfoBean = new PostProfitInfoBean();
            postProfitInfoBean.setId("0x00124b0009091dab");
            Gson gson = new Gson();
            postProfitInfoBean.setTime(formatter.format(date));
            String postMessage = gson.toJson(postProfitInfoBean);
            String respond = WebServiceUtil.webService("profitInfo", postMessage);
            return respond;
        }

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
