package com.example.tecpie.ZhiheSocket.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.tecpie.ZhiheSocket.Cutil.AppClient;
import com.example.tecpie.ZhiheSocket.Cutil.AppClientHandle;
import com.example.tecpie.ZhiheSocket.Cutil.bean.BaseResponse;
import com.example.tecpie.ZhiheSocket.Cutil.bean.ChannelBean;
import com.example.tecpie.ZhiheSocket.Cutil.bean.LoginResponse;
import com.example.tecpie.ZhiheSocket.Cutil.service.RequestService;
import com.example.tecpie.ZhiheSocket.Cutil.serviceImpl.RequestServiceImpl;
import com.example.tecpie.ZhiheSocket.R;
import com.example.tecpie.ZhiheSocket.entity.NetEntity;
import com.example.tecpie.ZhiheSocket.utils.ParseGson;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity{
    private final String url = "file:///android_asset/setting.html";
    private static final int REQUEST_QRCODE2 = 0x02;
    @ViewInject(R.id.loginwv)
    private WebView webView;
    private WebSettings webSettings;
    private File sdCardDir;
    private File saveFile;
    private File readFile;
    private FileOutputStream outStream;
    private FileInputStream inStream;
    private String AbsolutePath;
    private RequestService requestService = new RequestServiceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断缓存中是否存在配置中的插座，如果没有，把序号归为1
        SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        String sockets = preferences.getString("current-sockets", "");
        if(sockets.equals("")){
            editor.putInt("current-id",1);
        }
        WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        editor.putString("current-wifiName",wifiInfo.getSSID().replace("\"", ""));
        editor.commit();
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new LoginJsInterface(),"login");
        //得到SD卡路径
        sdCardDir = Environment.getExternalStorageDirectory();
        AbsolutePath = (String) sdCardDir.getAbsolutePath();
        //Toast.makeText(this, "路径为"+AbsolutePath+"sdCardDir::"+sdCardDir, Toast.LENGTH_LONG).show();
        File saveDir = new File(AbsolutePath+"/zigBeeOutput");
        if(!saveDir.exists()){
            saveDir.mkdirs();
        }
        File readDir = new File(AbsolutePath+"/zigBeeInput");
        if(!readDir.exists()){
            readDir.mkdirs();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_QRCODE2:
                if (resultCode == Activity.RESULT_OK) {
                    //扫描后的业务逻辑
                    String path = data.getStringExtra("path");
                    if (path.equals("")) {



                    } else {
                        //Toast.makeText(LoginActivity.this,path,Toast.LENGTH_SHORT).show();
                        readFile = new File(path);
                        Read_Files(readFile);



                        webView.reload();
                        //Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }

    }


    public class LoginJsInterface{



        @JavascriptInterface
        public void detail(String title){
            Toast.makeText(LoginActivity.this,title,Toast.LENGTH_SHORT).show();
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            String netEntitiesJason = preferences.getString("collection-netEntities", "");
            List<NetEntity> netEntities = ParseGson.parseNetEntityJasonArray(netEntitiesJason);
            Log.i("detail",netEntities.toString());
            if(netEntities.get(Integer.valueOf(title).intValue()).getIsOk()==1){
                Intent intent = new Intent(LoginActivity.this,SocketInfoActivity.class);
                intent.putExtra("title",title);
                startActivity(intent);
                JPushInterface.setAliasAndTags(LoginActivity.this,"test",null);
            }

        }

        @JavascriptInterface
        public void deleteNet(String bindex){
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            String netEntitiesJason = preferences.getString("collection-netEntities", "");
            SharedPreferences.Editor editor=preferences.edit();
            Gson gson = new Gson();
            String index = bindex.substring(1,bindex.length());
            List<NetEntity> netEntities = ParseGson.parseNetEntityJasonArray(netEntitiesJason);
            for(int i=0;i<netEntities.size();i++){
                if(i==Integer.valueOf(index).intValue()){
                    netEntities.remove(i);
                    break;
                }
            }
            editor.putString("collection-netEntities",gson.toJson(netEntities));
            editor.commit();
            LoginActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.reload();
                }
            });



        }

        @JavascriptInterface
        public void importFile(){
            startActivityForResult(new Intent(LoginActivity.this, FileListActivity.class),REQUEST_QRCODE2);

            JPushInterface.setAliasAndTags(LoginActivity.this,"test",null);
        }

        @JavascriptInterface
        public void connect(){
            //Toast.makeText(LoginActivity.this,"连接中...",Toast.LENGTH_SHORT).show();
            new AppClient().start();
            ChannelHandlerContext ctx = ChannelBean.getChannel("channel");


            /*if(ctx!=null){
                Toast.makeText(LoginActivity.this,"连接成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(LoginActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
            }*/

            Toast.makeText(LoginActivity.this,"连接成功",Toast.LENGTH_SHORT).show();




        }

        @JavascriptInterface
        public void login(){

            ChannelHandlerContext ctx = ChannelBean.getChannel("channel");
            requestService.baseRequest(ctx, "0001", "wizpower");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LoginResponse loginResponse = ChannelBean.getLoginResponse();
            Log.i("loginResponse",loginResponse.getGateway());
            //loginResponse.getResult()==1
            if(loginResponse.getResult()==1){

                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("current-mac",loginResponse.getMac());
                editor.putString("current-panid",loginResponse.getPanid());
                editor.putString("current-profile",loginResponse.getProfile());
                editor.putString("current-channel",loginResponse.getChannel());
                editor.putString("current-gateway",loginResponse.getGateway());
                editor.putString("current-data",loginResponse.getData());
                editor.commit();
                boolean exist = false;
                String netEntityJasonArray = preferences.getString("collection-netEntities","");

                if(netEntityJasonArray.equals("")){
                    LoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.reload();
                        }
                    });
                    automation();
                    return;
                }else{
                    List<NetEntity> netEntities = ParseGson.parseNetEntityJasonArray(netEntityJasonArray);
                    for(int i=0;i<netEntities.size();i++){
                        if(netEntities.get(i).getMac().equals(preferences.getString("current-mac",""))){
                            LoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    webView.reload();
                                }
                            });
                            exist = true;
                            Intent intent = new Intent(LoginActivity.this,SocketInfoActivity.class);
                            intent.putExtra("title",""+i);
                            startActivity(intent);
                            JPushInterface.setAliasAndTags(LoginActivity.this,"test",null);
                        }

                    }
                }
                if(!exist){
                    LoginActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.reload();
                        }
                    });
                    automation();
                    return;
                }
            }else{
                Toast.makeText(LoginActivity.this,"登录失败，请先建立连接",Toast.LENGTH_SHORT).show();
            }



        }

        @JavascriptInterface
        public void disconnect(){
            AppClientHandle appClientHandle = new AppClientHandle();
            ChannelHandlerContext ctx = ChannelBean.getChannel("channel");
            try {
                appClientHandle.channelInactive(ctx);

                clearCurrent();
                Toast.makeText(LoginActivity.this,"连接已断开",Toast.LENGTH_SHORT).show();
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.reload();
                    }
                });
            } catch (Exception e) {
                Toast.makeText(LoginActivity.this,"断开失败",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }

        @JavascriptInterface
        public String getGroups(){
           /* List<NetEntity> netlists = new ArrayList<NetEntity>();
            List<SocketEntity> sockets = new ArrayList<SocketEntity>();
            NetEntity net1 = new NetEntity();
            net1.setName("网关1");
            net1.setMac("mac地址(00:01:6C:06:A6:21)");
            net1.setWifi("WIZ-AP");

            for(int i=1;i<5;i++){
                SocketEntity socket = new SocketEntity();
                socket.setName(""+i);
                socket.setId(i);
                socket.setMac("00:01:6C:06:A6:2"+i);
                socket.setCurrent("1A");
                socket.setPower("220w");
                socket.setEq("3kwh");
                socket.setVoltage("220v");
                sockets.add(socket);

            }
            net1.setSockets(sockets);
            NetEntity net2 = new NetEntity();
            net2.setName("网关2");
            net2.setMac("mac地址(00:01:6C:06:A6:22)");
            net2.setWifi("WIZ-AP2");
            net2.setSockets(sockets);
            netlists.add(net1);
            netlists.add(net2);
            Gson gson = new Gson();
            String str = gson.toJson(netlists);*/


            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            String netEntitiesJsonArray = preferences.getString("collection-netEntities", "");
            if(netEntitiesJsonArray.equals("")){
                return "";
            }
            Gson gson = new Gson();
            List<NetEntity> netEntities = ParseGson.parseNetEntityJasonArray(netEntitiesJsonArray);
            for(int i=0;i<netEntities.size();i++){
                if(netEntities.get(i).getMac().equals(preferences.getString("current-mac",""))){
                    netEntities.get(i).setIsOk(1);
                }else{
                    netEntities.get(i).setIsOk(0);
                }
            }
            String netEntitiesResult = gson.toJson(netEntities);
            editor.putString("collection-netEntities",netEntitiesResult);
            editor.commit();
            Log.i("netEntities",netEntitiesResult);
            return netEntitiesResult;
            //Log.i("str",str);
           // return str;

        }


        @JavascriptInterface
        public void open(){
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            if(preferences.getString("current-mac","").equals("")){
                Toast.makeText(LoginActivity.this,"请先建立连接并通讯",Toast.LENGTH_SHORT).show();
                return;
            }
            // Toast.makeText(SocketInfoActivity.this,"开网中",Toast.LENGTH_SHORT).show();

            //获取单个插座
            ChannelHandlerContext ctx = ChannelBean.getChannel("channel");
            requestService.baseRequest(ctx, "0002", "gateway");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            BaseResponse baseResponse = ChannelBean.getBaseResponse();
            if(baseResponse.getResult()==1){
                Toast.makeText(LoginActivity.this,"开网成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(LoginActivity.this,"开网失败",Toast.LENGTH_SHORT).show();
            }
        }

        @JavascriptInterface
        public void close(){
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            if(preferences.getString("current-mac","").equals("")){
                Toast.makeText(LoginActivity.this,"请先建立连接并通讯",Toast.LENGTH_SHORT).show();
                return;
            }
            // Toast.makeText(SocketInfoActivity.this,"开网中",Toast.LENGTH_SHORT).show();

            //获取单个插座
            ChannelHandlerContext ctx = ChannelBean.getChannel("channel");
            requestService.baseRequest(ctx, "0003", "gateway");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            BaseResponse baseResponse = ChannelBean.getBaseResponse();
            if(baseResponse.getResult()==1){
                Toast.makeText(LoginActivity.this,"关网成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(LoginActivity.this,"关网失败",Toast.LENGTH_SHORT).show();
            }
        }


        @JavascriptInterface
        public void toast(final String s){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void automation(){

        Intent intent = new Intent(LoginActivity.this,SocketManageActivity.class);
        startActivity(intent);
        JPushInterface.setAliasAndTags(LoginActivity.this,"test",null);

    }

    private void Read_Files(File file)
    {
        String filename = file.getName();
        if(!filename.endsWith(".txt")){
            Toast.makeText(LoginActivity.this,"请选择zigBeeOutput文件夹中的txt文件",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //初始化 流对象
            try{
                inStream = new FileInputStream(file);
                InputStreamReader reader = new InputStreamReader(inStream,"GBK");
                BufferedReader br = new BufferedReader(reader);
                String line;

                while ((line = br.readLine()) != null) {
                    //Toast.makeText(LoginActivity.this,line,Toast.LENGTH_SHORT).show();

                    Log.i("import",line);
                    //解析jason字符
                    Gson gson = new Gson();
                    SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    String netsJsonArray = preferences.getString("collection-netEntities", "");
                    List<NetEntity> netEntities = new ArrayList<NetEntity>();
                    if(!netsJsonArray.equals("")){
                        JsonParser parser = new JsonParser();
                        JsonArray jsonArray = parser.parse(netsJsonArray).getAsJsonArray();

                        for (JsonElement n : jsonArray) {
                            //使用GSON，直接转成Bean对象
                            NetEntity netEn = gson.fromJson(n, NetEntity.class);
                            netEntities.add(netEn);
                        }
                    }
                    NetEntity newNetEntity = gson.fromJson(line,NetEntity.class);
                    boolean added = false;
                    if(netEntities.size()>0){
                        for(NetEntity n :netEntities){
                            if(n.getMac().equals(newNetEntity.getMac())){
                                added=true;
                            }
                        }
                    }
                    if(added){
                        Toast.makeText(LoginActivity.this,"该网关已存在",Toast.LENGTH_SHORT).show();
                    }else{
                        netEntities.add(newNetEntity);
                    }

                    String newNetsJsonArray = gson.toJson(netEntities);
                    editor.putString("collection-netEntities",newNetsJsonArray);
                    editor.commit();


                }


                br.close();
                reader.close();
            }catch (Exception e){
                Toast.makeText(this, "IOException！", Toast.LENGTH_SHORT).show();
                return ;
            }

            try{
                //关闭输入流和输出流
                inStream.close();

                Log.v("Read", "读成功！");
                Log.v("Disp", "文本显示！");
            }catch (IOException e){
                Toast.makeText(this, "读 异常！", Toast.LENGTH_SHORT).show();
            }






        }else{
            Toast.makeText(this, "内存卡不存在！", Toast.LENGTH_SHORT).show();
            return ;
        }
    }
    private  void clearCurrent() {
        SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt("current-id",1);
        editor.putInt("current-selectedId",0);
        editor.putString("current-wifiName","");
        editor.putString("current-netName","");
        editor.putString("current-sockets","");
        editor.putString("current-selectedSN","");
        editor.putString("current-selectedName","");
        editor.putString("current-selectedType","");
        editor.putString("current-mac","");
        editor.putString("current-panid","");
        editor.putString("current-profile","");
        editor.putString("current-channel","");
        editor.putString("current-gateway","");
        editor.putString("current-data","");
        editor.commit();
    }


}
