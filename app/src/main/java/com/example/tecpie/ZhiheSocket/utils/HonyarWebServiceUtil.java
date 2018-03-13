package com.example.tecpie.ZhiheSocket.utils;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by HuaJian on 2017/6/28.
 */
public class HonyarWebServiceUtil {
    private static String result;

    public static String webService(String action, String message) {

        String soapRequestData = loadUrl(action, message);
        try
        {
          String url="http://192.168.1.81:8080/MobileService/webservice/syncApp?wsdl";
           // String url="http://58.214.239.34:6080/HEMS/webservice/syncApp?wsdl";
//			String url="http://58.214.239.34:3089/ZigbeeGateway/webservice/syncApp?wsdl";
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            OutputStream out = httpURLConnection.getOutputStream();
            out.write(soapRequestData.getBytes("utf-8"));
            out.flush();
            out.close();
            int code = httpURLConnection.getResponseCode();
            if(code == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String str = "";
                while ((str = reader.readLine()) != null)
                {
                    stringBuffer.append(str);
                }
                reader.close();
                result = stringBuffer.toString();
                result = encode(result);
                return result;
            }
            return null;
            // return
            // stringBuffer.toString().substring(soapRequestData.indexOf("<return>")+8,
            // soapRequestData.indexOf("</return>")).replaceAll("&quot;", "\"");
        } catch (Exception e)
        {
            // TODO 自动生成 catch 块
            e.printStackTrace();
            return "exception";
        }
    }

    public static String loadUrl(String action, String message) {
        Log.i("WebService",action);
        System.out.println("action:" + action);
        System.out.println("message:" + message);
        String web = "";
        web = "<ser:" + action + ">" + "<arg0>" + message + "</arg0>"
                + "</ser:" + action + ">";
        // webservice请求地址
        String soapRequestData = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.webservice.wizpower.com/\">"
                + "<soapenv:Header/>"
                + "<soapenv:Body>"
                + web
                + "</soapenv:Body>" + "</soapenv:Envelope>";
        Log.i("WebService",soapRequestData);
        return soapRequestData;
    }

    public static String encode(String str) {
        Document doc = DOMUtils.parseXMLDocument(str);
        Element root = doc.getDocumentElement();
        String childname = root.getFirstChild().getNodeName();
        NodeList schedulers = doc.getElementsByTagName("return");
        String content = schedulers.item(0).getTextContent();
        try {
            System.out.println("content:"+content);
            content = new String(content.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("返回值:" + content);
        return content;
    }
}
