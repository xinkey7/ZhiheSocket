package com.example.tecpie.ZhiheSocket.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tecpie.ZhiheSocket.R;
import com.example.tecpie.ZhiheSocket.entity.IndexEntity;
import com.example.tecpie.ZhiheSocket.entity.SocketEntity;
import com.example.tecpie.ZhiheSocket.entity.SocketsEntity;
import com.example.tecpie.ZhiheSocket.utils.NettyClient;
import com.example.tecpie.ZhiheSocket.zxing.app.CaptureActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.activity_add)
public class AddActivity extends BaseActivity{
    private final String url = "file:///android_asset/add.html";
    private static final int REQUEST_QRCODE = 0x01;
    private  String SerialNumber;

    @ViewInject(R.id.add)
    private WebView webView;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new LoginJsInterface(),"add");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_QRCODE:
                if (resultCode == Activity.RESULT_OK) {
                    //扫描后的业务逻辑
                    String code = data.getStringExtra("SCAN_RESULT");
                    if (code.contains("http") || code.contains("https")) {
                        //打开链接
                        /*Intent intent = new Intent(this, AdBrowserActivity.class);
                        intent.putExtra(AdBrowserActivity.KEY_URL, code);
                        startActivity(intent);*/
                        Toast.makeText(this, code, Toast.LENGTH_SHORT).show();

                    } else {
                        SerialNumber = code;
                        webView.reload();
                        //Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
                    }
                }else if(resultCode == 300){
                    //从本地相册扫描后的业务逻辑
                    String code = data.getStringExtra("LOCAL_PHOTO_RESULT");
                    Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }


    public class LoginJsInterface{






        @JavascriptInterface
        public void back() {
            AddActivity.this.finish();
        }

        @JavascriptInterface
        public void scan(){
            //new NettyClient("192.168.1.199", 10086, "com.example.tecpie.ZhiheSocket.utils.ModbusClientHandle", "ModbusClient").start();

            startActivityForResult(new Intent(AddActivity.this, CaptureActivity.class),REQUEST_QRCODE);
            JPushInterface.setAliasAndTags(AddActivity.this,"test",null);

        }

        @JavascriptInterface
        public String getSN(){
            if(SerialNumber!=null){
                return SerialNumber;
            }
            return "";
        }

        @JavascriptInterface
        public void confirm(String SN,String name,String type){
            //Toast.makeText(AddActivity.this,"进入confirm",Toast.LENGTH_SHORT).show();
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            int id = preferences.getInt("current-id",0);
            Gson gson = new Gson();
            String socketsJsonArray = preferences.getString("current-sockets", "");
            SocketEntity socket = new SocketEntity();


            socket.setId(id);
            id=id+1;
            editor.putInt("current-id",id);
            socket.setName(name);
            socket.setSerialNumber(SN);
            socket.setMac(preferences.getString("current-mac",""));
            socket.setType(type);
            socket.setState(0);

            if(socketsJsonArray.equals("")){

                List<SocketEntity> sockets = new ArrayList<SocketEntity>();
                sockets.add(socket);
                String newSocketsJsonArray = gson.toJson(sockets);
                editor.putString("current-sockets",newSocketsJsonArray);

            }else{
                JsonParser parser = new JsonParser();
                JsonArray jsonArray = parser.parse(socketsJsonArray).getAsJsonArray();
                List<SocketEntity> sockets = new ArrayList<SocketEntity>();
                for (JsonElement s : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    SocketEntity socketEntity = gson.fromJson(s, SocketEntity.class);
                    sockets.add(socketEntity);
                }
                sockets.add(socket);
                String newSocketsJsonArray = gson.toJson(sockets);
                editor.putString("current-sockets",newSocketsJsonArray);
            }
            editor.commit();
            //Toast.makeText(AddActivity.this,"离开confirm",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddActivity.this,SocketManageActivity.class);
            startActivity(intent);
            JPushInterface.setAliasAndTags(AddActivity.this,"test",null);




        }



        @JavascriptInterface
        public void toast(final String s){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AddActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
