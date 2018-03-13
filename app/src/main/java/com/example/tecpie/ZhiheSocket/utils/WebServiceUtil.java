/**
 *2015-11-9上午08:47:36
 *WebServiceUtil.java
 *@author:huajian 
 */
package com.example.tecpie.ZhiheSocket.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author HuaJian 2015-11-9上午08:47:36
 */
public class WebServiceUtil
{
	private static String result;

	public static String webService(String action, String message)
	{

		String soapRequestData = loadUrl(action, message);
		try
		{
			String url="http://58.214.239.34:3089/ZigbeeGateway/webservice/syncApp?wsdl";
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

	public static String loadUrl(String action, String message)
	{
		Log.i("-->", action+" "+message);
		String web = "";
		web = "<web:" + action + ">" + "<arg0>" + message + "</arg0>"
				+ "</web:" + action + ">";
		// webservice请求地址
		String soapRequestData = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://service.ws.wizpower.com/\">"
				+ "<soapenv:Header/>"
				+ "<soapenv:Body>"
				+ web
				+ "</soapenv:Body>" + "</soapenv:Envelope>";
		return soapRequestData;
	}

	public static String encode(String str)
	{
		System.out.println("返回值:"+str);
		Document doc = DOMUtils.parseXMLDocument(str);
		Element root = doc.getDocumentElement();
		String childname = root.getFirstChild().getNodeName();
		NodeList schedulers = doc.getElementsByTagName("return");
		String content = schedulers.item(0).getTextContent();
		System.out.println("返回Json---:"+content);
		/*try
		{
			content = new String(content.getBytes("GBK"), "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("返回Json:"+content);*/
		return content;
	}
}
