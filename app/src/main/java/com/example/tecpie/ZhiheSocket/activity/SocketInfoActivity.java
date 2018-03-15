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

import com.example.tecpie.ZhiheSocket.Cutil.bean.BaseResponse;
import com.example.tecpie.ZhiheSocket.Cutil.bean.ChannelBean;
import com.example.tecpie.ZhiheSocket.Cutil.bean.DispatchSocketRequest;
import com.example.tecpie.ZhiheSocket.Cutil.bean.ReadSocketResponse;
import com.example.tecpie.ZhiheSocket.Cutil.bean.SocketBean;
import com.example.tecpie.ZhiheSocket.Cutil.bean.SocketDataRequest;
import com.example.tecpie.ZhiheSocket.Cutil.bean.SocketDataResponse;
import com.example.tecpie.ZhiheSocket.Cutil.service.RequestService;
import com.example.tecpie.ZhiheSocket.Cutil.serviceImpl.RequestServiceImpl;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by AmeKing on 2016/7/27.
 */
@ContentView(R.layout.activity_socket_info)
public class SocketInfoActivity extends BaseActivity{
    private final String url = "file:///android_asset/recordinfo.html";
    @ViewInject(R.id.socketinfo)
    private WebView webView;
    private WebSettings webSettings;
    private File sdCardDir;
    private File saveFile;
    private File readFile;
    private FileOutputStream outStream;
    private FileInputStream inStream;
    private String AbsolutePath;
    private File readDir;
    private File saveDir;
    private RequestService requestService = new RequestServiceImpl();
    private static final int REQUEST_QRCODE = 0x06;
    private static final int REQUEST_MODIFYCODE = 0x07;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView.loadUrl(url);
        webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        //webSettings.setCacheMode(webSettings.LOAD_NO_CACHE);
        webView.addJavascriptInterface(new LoginJsInterface(),"socketInfo");
        //得到SD卡路径
        sdCardDir = Environment.getExternalStorageDirectory();

        AbsolutePath = (String) sdCardDir.getAbsolutePath();
        saveDir = new File(AbsolutePath+"/zigBeeOutput");
        //Toast.makeText(this, "路径为"+AbsolutePath+"sdCardDir::"+sdCardDir, Toast.LENGTH_LONG).show();

        if(!saveDir.exists()){
            saveDir.mkdirs();
        }
        readDir = new File(AbsolutePath+"/zigBeeInput");
        if(!readDir.exists()){
            readDir.mkdirs();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_QRCODE:
                Toast.makeText(SocketInfoActivity.this,"更新中请稍候",Toast.LENGTH_SHORT).show();
                if (resultCode == Activity.RESULT_OK) {

                    webView.reload();

                }
                break;
            case REQUEST_MODIFYCODE:
                Toast.makeText(SocketInfoActivity.this,"更新中请稍候",Toast.LENGTH_SHORT).show();
                if (resultCode == Activity.RESULT_OK) {
                    SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
                    //网关下发
                    ChannelHandlerContext ctx = ChannelBean.getChannel("channel");
                    DispatchSocketRequest request = new DispatchSocketRequest();
                    if(preferences.getString("current-mac","").equals("")){
                        Toast.makeText(SocketInfoActivity.this,"请先建立连接并通讯",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    request.setMac(preferences.getString("current-mac",""));

                    request.setGateway(preferences.getString("current-gateway",""));
                    request.setCode("0004");
                    List<NetEntity> netEntities = ParseGson.parseNetEntityJasonArray(preferences.getString("collection-netEntities",""));
                    List<SocketEntity> sockets = new ArrayList<SocketEntity>();
                    SocketEntity socket = new SocketEntity();
                    NetEntity netEntity = new NetEntity();
                    for(NetEntity n: netEntities){
                        if(n.getMac().equals(preferences.getString("current-mac",""))){
                            sockets = n.getSockets();
                            request.setName(n.getName());
                            break;
                        }
                    }
                    List<SocketBean> datas = new ArrayList<SocketBean>();
                    for(SocketEntity s:sockets){
                        SocketBean sb = new SocketBean();
                        sb.setName(s.getName());
                        sb.setIeee("0x"+transfer(s.getSerialNumber()));
                        if(s.getType().equals("10")){
                            sb.setDeviceid(1001);
                        }
                        if(s.getType().equals("16")){
                            sb.setDeviceid(1002);
                        }
                        sb.setDisplayid(s.getId());


                        datas.add(sb);
                    }
                    request.setData(datas);
                    requestService.dispatchSocketRequest(ctx, request, "gateway");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    BaseResponse response = ChannelBean.getBaseResponse();
                    if(response.getResult()==1){
                        Toast.makeText(SocketInfoActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(SocketInfoActivity.this,"更新失败",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    webView.reload();

                }
                break;
        }

    }


    public class LoginJsInterface{


        @JavascriptInterface
        public void open(){
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            if(preferences.getString("current-mac","").equals("")){
                Toast.makeText(SocketInfoActivity.this,"请先建立连接并通讯",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(SocketInfoActivity.this,"开网成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(SocketInfoActivity.this,"开网失败",Toast.LENGTH_SHORT).show();
            }
        }

        @JavascriptInterface
        public void close(){
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            if(preferences.getString("current-mac","").equals("")){
                Toast.makeText(SocketInfoActivity.this,"请先建立连接并通讯",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(SocketInfoActivity.this,"关网成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(SocketInfoActivity.this,"关网失败",Toast.LENGTH_SHORT).show();
            }
        }


        @JavascriptInterface
        public void detail(String id){
            Gson gson = new Gson();
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            Toast.makeText(SocketInfoActivity.this,id,Toast.LENGTH_SHORT).show();
            //获取单个插座
            ChannelHandlerContext ctx = ChannelBean.getChannel("channel");
            SocketDataRequest request = new SocketDataRequest();
            request.setCode("0007");
            List<NetEntity> netEntities = ParseGson.parseNetEntityJasonArray(preferences.getString("collection-netEntities",""));
            List<SocketEntity> sockets = new ArrayList<SocketEntity>();
            SocketEntity socket = new SocketEntity();
            NetEntity netEntity = new NetEntity();
            for(NetEntity n: netEntities){
                if(n.getMac().equals(preferences.getString("current-mac",""))){
                    sockets = n.getSockets();
                }
            }
            String socketname="";
            int state = 0;
            String type="";
            String ieee = "";
            for(SocketEntity s : sockets){
                if(s.getId()==Integer.valueOf(id).intValue()){
                    ieee = "0x"+transfer(s.getSerialNumber());
                    request.setIeee(ieee);
                    socketname = s.getName();
                    state = s.getState();
                    type = s.getType();

                }

            }
            SocketDataResponse socketDataResponse = new SocketDataResponse();
            if(state!=0){
                requestService.socketDataRequest(ctx, request, "gateway");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                 socketDataResponse = ChannelBean.getSocketDataResponse();
            }else{
                socketDataResponse.setIeee(ieee);
                socketDataResponse.setOnline(0);
            }


            //Log.i("debugs","ieee"+socketDataResponse.getIeee());
            String socketDataResponseJsonArray = gson.toJson(socketDataResponse);
            Log.i("debugs",socketDataResponseJsonArray);
            Log.i("debugs",""+socketname+"/"+type+"/"+id);
            Intent intent = new Intent(SocketInfoActivity.this,SocketDetailActivity.class);
            intent.putExtra("socketData",socketDataResponseJsonArray);
            intent.putExtra("socketName",socketname);
            intent.putExtra("socketType",type);
            intent.putExtra("socketId",id);


            startActivityForResult(intent,REQUEST_MODIFYCODE);
            JPushInterface.setAliasAndTags(SocketInfoActivity.this,"test",null);




        }

        //获取
        @JavascriptInterface
        public void upload(){
            Toast.makeText(SocketInfoActivity.this,"开始获取",Toast.LENGTH_SHORT).show();
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            Gson gson = new Gson();
            List<SocketEntity> sockets = new ArrayList<SocketEntity>();
            if(preferences.getString("current-mac","").equals("")){
                Toast.makeText(SocketInfoActivity.this,"请先建立连接并通讯",Toast.LENGTH_SHORT).show();
                return;
            }

            //网关上传
            ChannelHandlerContext ctx = ChannelBean.getChannel("channel");

            Log.i("debugs","ctx"+ctx.toString());
            requestService.baseRequest(ctx, "0005", "gateway");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ReadSocketResponse readSocketResponse = ChannelBean.getReadSocketResponse();
            List<SocketBean> data = readSocketResponse.getData();
            Log.i("debugs","data"+data.toString());
            for(SocketBean sb : data){
                SocketEntity socketEntity = new SocketEntity();
                socketEntity.setId(sb.getDisplayid());
                //socketEntity.setId(1);
                socketEntity.setMac(preferences.getString("current-mac",""));
                socketEntity.setName(sb.getName());
                String ieee = sb.getIeee();

                socketEntity.setSerialNumber(transfer(ieee.substring(2,ieee.length())));
                socketEntity.setState(sb.getOnline());
                String type ="";
                if(sb.getDeviceid()!=null){
                    if(sb.getDeviceid()==1001){
                        type = "10";
                    }else if(sb.getDeviceid()==1002){
                        type ="16";
                    }
                }

                socketEntity.setType(type);
                sockets.add(socketEntity);
            }
            String macAddress = preferences.getString("current-mac","");
            List<NetEntity> netEntities = ParseGson.parseNetEntityJasonArray(preferences.getString("collection-netEntities",""));
            for(int i=0;i<netEntities.size();i++){
                if(netEntities.get(i).getMac().equals(macAddress)){
                    netEntities.get(i).setSockets(sockets);
                    break;
                }
            }
            editor.putString("collection-netEntities",gson.toJson(netEntities));


            if(readSocketResponse.getResult()==1){
                Toast.makeText(SocketInfoActivity.this,"读取成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(SocketInfoActivity.this,"读取失败",Toast.LENGTH_SHORT).show();
                return;
            }


            Log.i("upload",netEntities.toString());
            editor.commit();
            SocketInfoActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.reload();
                }
            });


        }
        @JavascriptInterface
        public String getTitleSockets(){
            Intent intent = getIntent();
            String title = intent.getStringExtra("title");
            int index= Integer.valueOf(title).intValue();
            Gson gson = new Gson();
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            String netsJsonArray = preferences.getString("collection-netEntities", "");

            //获取缓存中的插座
            List<NetEntity> netEntities = ParseGson.parseNetEntityJasonArray(netsJsonArray);
            List<SocketEntity> socketEntitiesCache = new ArrayList<SocketEntity>();
            for(NetEntity n :netEntities){
                if(n.getMac().equals(preferences.getString("current-mac",""))){
                    socketEntitiesCache = netEntities.get(index).getSockets();
                }
            }

            List<SocketEntity> socketEntitiesUpload = new ArrayList<SocketEntity>();
            //获取该网关中的插座
            ChannelHandlerContext ctx = ChannelBean.getChannel("channel");

            Log.i("debugs","ctx"+ctx.toString());
            requestService.baseRequest(ctx, "0005", "gateway");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ReadSocketResponse readSocketResponse = ChannelBean.getReadSocketResponse();
            List<SocketBean> data = readSocketResponse.getData();
            Log.i("debugs","data"+data.toString());
            for(SocketBean sb : data){
                SocketEntity socketEntity = new SocketEntity();
                socketEntity.setId(sb.getDisplayid());
                //socketEntity.setId(1);
                socketEntity.setMac(preferences.getString("current-mac",""));
                socketEntity.setName(sb.getName());
                String ieee = sb.getIeee();

                socketEntity.setSerialNumber(transfer(ieee.substring(2,ieee.length())));
                socketEntity.setState(sb.getOnline());
                String type ="";
                if(sb.getDeviceid()!=null){
                    if(sb.getDeviceid()==1001){
                        type = "10";
                    }else if(sb.getDeviceid()==1002){
                        type ="16";
                    }
                }

                socketEntity.setType(type);
                socketEntitiesUpload.add(socketEntity);
            }
            String macAddress = preferences.getString("current-mac","");
            //合并
            List<SocketEntity> socketsToAdd = new ArrayList<SocketEntity>();
            for(int i=0;i<socketEntitiesUpload.size();i++){
                boolean add = true;
                for(int j=0;j<socketEntitiesCache.size();j++){

                    if(socketEntitiesCache.get(j).getSerialNumber().equals(socketEntitiesUpload.get(i).getSerialNumber())){
                        add=false;
                    }
                }
                if(add){
                    socketsToAdd.add(socketEntitiesUpload.get(i));
                }
            }
            socketEntitiesCache.addAll(socketsToAdd);

            for(int i=0;i<netEntities.size();i++){
                if(netEntities.get(i).getMac().equals(macAddress)){
                    netEntities.get(i).setSockets(socketEntitiesCache);
                    break;
                }
            }
            editor.putString("collection-netEntities",gson.toJson(netEntities));
            editor.commit();
            Log.i("socketEntities",gson.toJson(socketEntitiesCache));
           return gson.toJson(socketEntitiesCache);



        }

        @JavascriptInterface
        public void netinfo(){

            Intent intent = new Intent(SocketInfoActivity.this,NetDetailActivity.class);
            startActivity(intent);
        }

        @JavascriptInterface
        public void save(){
            String title = getIntent().getStringExtra("title");
            int index= Integer.valueOf(title).intValue();
            Gson gson = new Gson();
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            String netsJsonArray = preferences.getString("collection-netEntities", "");


            List<NetEntity> netEntities = ParseGson.parseNetEntityJasonArray(netsJsonArray);

            List<SocketEntity> socketEntities = netEntities.get(index).getSockets();
            String macAddress = preferences.getString("current-mac","");
            saveFile = new File(saveDir,"saveFile"+macAddress+".txt");
            Log.i("save","saveFile"+macAddress+".txt");
            NetEntity netEntity = new NetEntity();
            netEntity.setSockets(socketEntities);
            netEntity.setName(netEntities.get(index).getName());
            netEntity.setWifi(preferences.getString("current-wifiName", ""));
            netEntity.setMac(preferences.getString("current-mac", ""));
            netEntity.setIsOk(0);
            netEntity.setChannel(preferences.getString("current-channel", ""));
            netEntity.setGateway(preferences.getString("current-gateway", ""));
            netEntity.setPanid(preferences.getString("current-panid",""));
            netEntity.setProfile(preferences.getString("current-profile", ""));
            Log.i("save",saveFile.getPath());
            Write_Files(gson.toJson(netEntity),saveFile);
            Toast.makeText(SocketInfoActivity.this,"读写完毕",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SocketInfoActivity.this,LoginActivity.class);
            startActivity(intent);

        }

        @JavascriptInterface
        public void handon(){
            Gson gson = new Gson();
            SharedPreferences preferences=getSharedPreferences("zigBee", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();

            String netsEntitiesJsonArray = preferences.getString("collection-netEntities", "");

            List<NetEntity> nets = ParseGson.parseNetEntityJasonArray(netsEntitiesJsonArray);

            List<SocketEntity> sockets = new ArrayList<SocketEntity>();
            for(NetEntity n: nets){
                if(n.getMac().equals(preferences.getString("current-mac",""))){
                    sockets = n.getSockets();
                }
            }


            //网关下发
            ChannelHandlerContext ctx = ChannelBean.getChannel("channel");
            DispatchSocketRequest request = new DispatchSocketRequest();
            if(preferences.getString("current-mac","").equals("")){
                Toast.makeText(SocketInfoActivity.this,"请先建立连接并通讯",Toast.LENGTH_SHORT).show();
                return;
            }
            request.setMac(preferences.getString("current-mac",""));
            request.setName(preferences.getString("current-netName",""));
            request.setGateway(preferences.getString("current-gateway",""));
            request.setCode("0004");
            List<SocketBean> data = new ArrayList<SocketBean>();
            for(SocketEntity s:sockets){
                Log.i("handon",s.toString());
                SocketBean sb = new SocketBean();
                sb.setName(s.getName());
                sb.setIeee("0x"+transfer(s.getSerialNumber()));
                sb.setDeviceid(1001);
                if(s.getType().equals("10")){
                    sb.setDeviceid(1001);
                }
                if(s.getType().equals("16")){
                    sb.setDeviceid(1002);
                }
                sb.setDisplayid(s.getId());


                data.add(sb);
            }
            request.setData(data);
            Log.i("xiafa",request.toString());
            requestService.dispatchSocketRequest(ctx, request, "gateway");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BaseResponse response = ChannelBean.getBaseResponse();
            Log.i("xiafa",response.toString());
            if(response.getResult()==1){
                Toast.makeText(SocketInfoActivity.this,"下发成功",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(SocketInfoActivity.this,"下发失败",Toast.LENGTH_SHORT).show();
                return;
            }
            //List<IndexEntity> indexs = new ArrayList<IndexEntity>();
            try {



                //写入collection-netEntites
                /*NetEntity netEntity = new NetEntity();
                netEntity.setSockets(sockets);
                netEntity.setName(preferences.getString("current-netName", ""));
                netEntity.setWifi(preferences.getString("current-wifiName", ""));
                netEntity.setMac(preferences.getString("current-mac", ""));
                String netsJsonArray = preferences.getString("collection-netEntities", "");
                if(netsJsonArray.equals("")){
                    List<NetEntity> netEntities = new ArrayList<NetEntity>();
                    netEntities.add(netEntity);
                    String newNetsJsonArray = gson.toJson(netEntities);
                    editor.putString("collection-netEntities",newNetsJsonArray);
                }else{
                    List<NetEntity> netEntities = ParseGson.parseNetEntityJasonArray(netsJsonArray);
                    boolean added = false;
                    for(NetEntity n: netEntities){
                        if(n.getMac().equals(n.getMac())){
                            added = true;
                        }
                    }
                    if(!added){
                        netEntities.add(netEntity);
                    }
                    String newNetsJsonArray = gson.toJson(netEntities);
                    editor.putString("collection-netEntities",newNetsJsonArray);
                }*/
                editor.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(SocketInfoActivity.this,LoginActivity.class);
            startActivity(intent);
            JPushInterface.setAliasAndTags(SocketInfoActivity.this,"test",null);

        }





        @JavascriptInterface
        public void back() {
            SocketInfoActivity.this.finish();
        }

        @JavascriptInterface
        public void add(){
            startActivityForResult(new Intent(SocketInfoActivity.this, Add2Activity.class),REQUEST_QRCODE);
        }

        @JavascriptInterface
        public void toast(final String s){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SocketInfoActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void Write_Files(String content,File saveFile)
    {

        //从EditText的到输入的字符串

        //监测SD卡是否挂载
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //创建流对象
            try{
                outStream = new FileOutputStream(saveFile);
            }catch (FileNotFoundException e){
                Toast.makeText(SocketInfoActivity.this, "Start SD writing operation", Toast.LENGTH_SHORT).show();
                return ;
            }
            //写入文件
            try {
                outStream.write(content.getBytes());
            }catch(FileNotFoundException e) {
                // TODO Auto-generated catch block
                Toast.makeText(SocketInfoActivity.this, "FileNotFoundException!", Toast.LENGTH_SHORT).show();
            } catch (IOException e){
                // TODO Auto-generated catch block
                Toast.makeText(SocketInfoActivity.this, "IOException!", Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e){
                Toast.makeText(SocketInfoActivity.this, "NullPointerException!", Toast.LENGTH_SHORT).show();
            }
            finally{
                try{
                    //关闭输出流
                    outStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(SocketInfoActivity.this, "关闭 IO 异常", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(SocketInfoActivity.this, "写入完毕", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(SocketInfoActivity.this, "SD 没有挂载或不存在", Toast.LENGTH_SHORT).show();
            return ;
        }
    }

    private void Read_Files()
    {

        int len;
        //创建一个字节数组输出流
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //初始化 流对象
            try{
                inStream = new FileInputStream(readFile);
                Log.v("Instance inStream", "ʵ��inStream��");
            }catch (FileNotFoundException e){
                Toast.makeText(SocketInfoActivity.this, "实例化inStream！", Toast.LENGTH_SHORT).show();
                return ;
            }
            try{
                Log.v("Read", "读数据");
                //�� buffer
                while((
                        len=inStream.read(buffer)
                )!=-1){
                    ostream.write(buffer, 0, len);
                }
                Log.v("Read", "读成功");
            }catch (IOException e){
                Toast.makeText(SocketInfoActivity.this, "IOException", Toast.LENGTH_SHORT).show();
            }
            try{
                //关闭输入流和输出流
                inStream.close();
                ostream.close();
                Log.v("Read", "读成功！");
            }catch (IOException e){
                Toast.makeText(SocketInfoActivity.this, "读 异常！", Toast.LENGTH_SHORT).show();
            }

            String str = new String(ostream.toByteArray());
            Log.v("Disp", "文本显示！");
            Toast.makeText(SocketInfoActivity.this, str, Toast.LENGTH_SHORT).show();


        }else{
            Toast.makeText(SocketInfoActivity.this, "内存卡不存在！", Toast.LENGTH_SHORT).show();
            return ;
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
