package com.example.tecpie.ZhiheSocket.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tecpie.ZhiheSocket.Cutil.bean.SocketDataResponse;
import com.example.tecpie.ZhiheSocket.R;
import com.example.tecpie.ZhiheSocket.entity.NetEntity;
import com.example.tecpie.ZhiheSocket.entity.SocketEntity;
import com.example.tecpie.ZhiheSocket.utils.ParseGson;
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
@ContentView(R.layout.activity_socket_detail)
public class SocketDetailActivity extends BaseActivity{
    private final String url = "file:///android_asset/socketdetail.html";
    @ViewInject(R.id.socketdetail)
    private WebView webView;
    private static final int REQUEST_QRCODE = 0x04;
    private WebSettings webSettings;
    private  String SerialNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new LoginJsInterface(),"socketDetail");
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
        public void confirm(String SN,String name,String type){
            //Toast.makeText(AddActivity.this,"进入confirm",Toast.LENGTH_SHORT).show();
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            Gson gson = new Gson();
            int id = Integer.valueOf(getIntent().getStringExtra("socketId")).intValue();
            List<NetEntity> netEntities = ParseGson.parseNetEntityJasonArray(preferences.getString("collection-netEntities",""));
            List<SocketEntity> sockets = new ArrayList<SocketEntity>();
            for(NetEntity n:netEntities){
                if(n.getMac().equals(preferences.getString("current-mac",""))){
                    sockets = n.getSockets();
                    break;
                }
            }
            for(int i=0;i<sockets.size();i++){
                if(sockets.get(i).getId()==id){
                    sockets.get(i).setName(name);
                    sockets.get(i).setSerialNumber(SN);
                    sockets.get(i).setType(type);
                    break;
                }
            }
            for(int i=0;i<netEntities.size();i++){
                if(netEntities.get(i).getMac().equals(preferences.getString("current-mac",""))){
                    netEntities.get(i).setSockets(sockets);
                    break;
                }
            }
            editor.putString("collection-netEntities",gson.toJson(netEntities));
            editor.commit();
            //Toast.makeText(AddActivity.this,"离开confirm",Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }


        @JavascriptInterface
        public void deleteSocket(){
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            Gson gson = new Gson();
            int id = Integer.valueOf(getIntent().getStringExtra("socketId")).intValue();
            List<NetEntity> netEntities = ParseGson.parseNetEntityJasonArray(preferences.getString("collection-netEntities",""));
            List<SocketEntity> sockets = new ArrayList<SocketEntity>();
            for(NetEntity n:netEntities){
                if(n.getMac().equals(preferences.getString("current-mac",""))){
                    sockets = n.getSockets();
                    break;
                }
            }
            for(int i=0;i<sockets.size();i++){
                if(sockets.get(i).getId()==id){
                    sockets.remove(i);
                }
            }
            for(int i=0;i<sockets.size();i++){
                if(sockets.get(i).getId()>id){
                    sockets.get(i).setId(sockets.get(i).getId()-1);
                }
            }

            for(int i=0;i<netEntities.size();i++){
                if(netEntities.get(i).getMac().equals(preferences.getString("current-mac",""))){
                    netEntities.get(i).setSockets(sockets);
                    break;
                }
            }

            editor.putString("collection-netEntities",gson.toJson(netEntities));
            editor.commit();
            //Toast.makeText(AddActivity.this,"离开confirm",Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();

        }
        @JavascriptInterface
        public void scan(){

            startActivityForResult(new Intent(SocketDetailActivity.this, CaptureActivity.class),REQUEST_QRCODE);
            JPushInterface.setAliasAndTags(SocketDetailActivity.this,"test",null);

        }





        @JavascriptInterface
        public void back() {
            SocketDetailActivity.this.finish();
        }

        @JavascriptInterface
        public String getSN(){
            if(SerialNumber!=null){
                return SerialNumber;
            }
            Gson gson = new Gson();
            String responseJson = getIntent().getStringExtra("socketData");
            if(responseJson==null){
                return "";
            }
            SocketDataResponse socketDataResponse = gson.fromJson(responseJson,SocketDataResponse.class);
            if(socketDataResponse.getIeee()==null){
                return getIntent().getStringExtra("socketSN");
            }
            if(socketDataResponse.getIeee().length()%2==1 || socketDataResponse.getIeee().length()<=2 ){
                return socketDataResponse.getIeee();
            }
            return transfer(socketDataResponse.getIeee().substring(2,socketDataResponse.getIeee().length()));
        }
        @JavascriptInterface
        public String getName(){

            String responseJson = getIntent().getStringExtra("socketData");
            return getIntent().getStringExtra("socketName");
        }

        @JavascriptInterface
        public String getType(){

            String responseJson = getIntent().getStringExtra("socketData");
            return getIntent().getStringExtra("socketType");
        }
        @JavascriptInterface
        public String getState(){
            Gson gson = new Gson();
            String responseJson = getIntent().getStringExtra("socketData");
            if(responseJson==null){
                return "";
            }
            SocketDataResponse socketDataResponse = gson.fromJson(responseJson,SocketDataResponse.class);
            return ""+socketDataResponse.getOnline();
        }
        @JavascriptInterface
        public String getPower(){
            Gson gson = new Gson();
            String responseJson = getIntent().getStringExtra("socketData");
            if(responseJson==null){
                return "";
            }
            SocketDataResponse socketDataResponse = gson.fromJson(responseJson,SocketDataResponse.class);
            return ""+socketDataResponse.getPower();
        }
        @JavascriptInterface
        public String getConsum(){
            Gson gson = new Gson();
            String responseJson = getIntent().getStringExtra("socketData");
            if(responseJson==null){
                return "";
            }
            SocketDataResponse socketDataResponse = gson.fromJson(responseJson,SocketDataResponse.class);
            return ""+socketDataResponse.getConsum();
        }
        @JavascriptInterface
        public String getVol(){
            Gson gson = new Gson();
            String responseJson = getIntent().getStringExtra("socketData");
            if(responseJson==null){
                return "";
            }
            SocketDataResponse socketDataResponse = gson.fromJson(responseJson,SocketDataResponse.class);
            return ""+socketDataResponse.getVol();
        }
        @JavascriptInterface
        public String getCur(){
            Gson gson = new Gson();
            String responseJson = getIntent().getStringExtra("socketData");
            if(responseJson==null){
                return "";
            }
            SocketDataResponse socketDataResponse = gson.fromJson(responseJson,SocketDataResponse.class);
            return ""+socketDataResponse.getCur();
        }

        @JavascriptInterface
        public void toast(final String s){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SocketDetailActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public  String transfer(String str){
        String[] strs = str.split("");
        String result = "";
        for(int i=strs.length;i<=strs.length;i--){
            result +=strs[i-2]+strs[i-1];
            i--;
            if(i<3){
                break;
            }
        }
        return result;
    }


}
