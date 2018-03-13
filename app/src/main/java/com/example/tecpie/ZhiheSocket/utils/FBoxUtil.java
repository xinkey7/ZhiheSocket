package com.example.tecpie.ZhiheSocket.utils;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.example.tecpie.ZhiheSocket.bean.ValueSetBody;
import com.example.tecpie.ZhiheSocket.pojo.ValueBody;
import com.google.gson.Gson;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.Response;


public class FBoxUtil {
	public static boolean isConneted = false;
	public static String token;
	public static Future<Response> f;
	public static Response r;

	public static JSONObject jsonObject;
	public static JSONArray jsonArray;;
	public static int status = 0;
	public static String body="";
	public static String groupid = "";
	public static String url="";
	public static String boxid = "";
	public static String apiBaseUrl ="";
	public static String uid = "";

	public static JSONObject login(String username,String pwd){
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

		try {

			// 2.登录账号请求token
			//System.out.println("登录账号请求token");
			BoundRequestBuilder b = asyncHttpClient
					.preparePost("https://account.flexem.com/core/connect/token");
			b.addParameter("username", username);
			b.addParameter("password", pwd);
			//b.addParameter("client_id", "fboxAndroidClient");
			b.addParameter("client_id", "wizpower");
			//b.addParameter("client_secret", "ea242a1e65504122a49b7778123464ae");
			b.addParameter("client_secret", "76168ced87354da78141ccd953f65aa9");
			b.addParameter("grant_type", "password");
			b.addParameter("scope", "openid offline_access fbox email profile");
			// b.addParameter("username", "cxq123");
			//System.out.println("Login");
			f = b.execute();
			r = f.get();

			status = r.getStatusCode();
			body = r.getResponseBody();
			jsonObject = new JSONObject(body);
			jsonObject.put("status", status);


		} catch (IOException e1) {

			e1.printStackTrace();
		} catch (InterruptedException e) {

			e.printStackTrace();
		} catch (ExecutionException e) {

			e.printStackTrace();
		}catch (JSONException e) {
			e.printStackTrace();
		}



		asyncHttpClient.close();
		asyncHttpClient=null;

		return jsonObject;

	}
	public static JSONArray getFBoxs(String token){
		// 获取盒子列表信息
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

		try {
		BoundRequestBuilder b = asyncHttpClient
					.prepareGet("http://fbox360.com/api/client/box/grouped");
		b.addHeader("Authorization","Bearer "+token);
		b.addHeader("X-FBox-ClientId", "wizpower12345678");
		b.setBody("");
		// b.addParameter("username", "cxq123");

		f = b.execute();
		r = f.get();

		status = r.getStatusCode();
		body = r.getResponseBody();
		String utf = new String(body.getBytes("iso-8859-1"), "utf-8");
		//System.out.println("body"+utf);
		jsonArray = new JSONArray(utf);


		} catch (IOException e1) {

			e1.printStackTrace();
		} catch (InterruptedException e) {

			e.printStackTrace();
		} catch (ExecutionException e) {

			e.printStackTrace();
		}catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;

	}

	public static JSONObject getFBoxInfo(String apiBaseUrl, String boxId,String token){
		// 获取盒子基本信息
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

		try {
			url = apiBaseUrl+"v2/box/"+boxId+"/info";
			BoundRequestBuilder b = asyncHttpClient
					.prepareGet(url);
			b.addHeader("Authorization","Bearer "+token);
			b.addHeader("X-FBox-ClientId", "wizpower12345678");
			b.setBody("");
			// b.addParameter("username", "cxq123");

			f = b.execute();
			r = f.get();

			status = r.getStatusCode();
			body = r.getResponseBody();
			String utf = new String(body.getBytes("iso-8859-1"), "utf-8");
			System.out.println("info  "+utf);

			jsonObject = new JSONObject(utf);


		} catch (IOException e1) {

			e1.printStackTrace();
		} catch (InterruptedException e) {

			e.printStackTrace();
		} catch (ExecutionException e) {

			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;

	}

	public static JSONArray getDMonEntries(String apiBaseUrl,String boxUid,String token){
		// 获取监控点列表
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

		try {
			System.out.println("获取监控点列表信息");
			url = apiBaseUrl+"box/"+boxUid+"/dmon/def/grouped";
			BoundRequestBuilder b = asyncHttpClient
					.prepareGet(url);
			b.addHeader("Authorization","Bearer "+token);
			b.addHeader("X-FBox-ClientId", "12345678");
			b.setBody("");
			// b.addParameter("username", "cxq123");

			f = b.execute();
			r = f.get();

			status = r.getStatusCode();
			body = r.getResponseBody();
			String utf = new String(body.getBytes("iso-8859-1"), "utf-8");
			jsonArray = new JSONArray(utf);


		} catch (IOException e1) {

			e1.printStackTrace();
		} catch (InterruptedException e) {

			e.printStackTrace();
		} catch (ExecutionException e) {

			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;

	}


	public static JSONArray getDMonEntriesV2(String apiBaseUrl,String boxUid,String token){
		// 获取监控点列表
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

		try {
			System.out.println("获取监控点列表信息");
			url = apiBaseUrl+"v2/"+"box/"+boxUid+"/dmon/grouped";
			BoundRequestBuilder b = asyncHttpClient
					.prepareGet(url);
			b.addHeader("Authorization","Bearer "+token);
			b.addHeader("X-FBox-ClientId", "12345678");
			b.setBody("");
			// b.addParameter("username", "cxq123");

			f = b.execute();
			r = f.get();

			status = r.getStatusCode();
			body = r.getResponseBody();
			String utf = new String(body.getBytes("iso-8859-1"), "utf-8");
			jsonArray = new JSONArray(utf);


		} catch (IOException e1) {

			e1.printStackTrace();
		} catch (InterruptedException e) {

			e.printStackTrace();
		} catch (ExecutionException e) {

			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;

	}


	public static JSONArray getAlarm(String apiBaseUrl,String boxUid,String token){
		// 获取报警列表
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

		try {
			System.out.println("获取报警列表信息");
			url = apiBaseUrl+"box/"+boxUid+"/alarm/def";
			BoundRequestBuilder b = asyncHttpClient
					.prepareGet(url);
			b.addHeader("Authorization","Bearer "+token);
			b.addHeader("X-FBox-ClientId", "12345678");
			b.setBody("");
			// b.addParameter("username", "cxq123");

			f = b.execute();
			r = f.get();

			status = r.getStatusCode();
			body = r.getResponseBody();
			String utf = new String(body.getBytes("iso-8859-1"), "utf-8");
			jsonArray = new JSONArray(utf);


		} catch (IOException e1) {

			e1.printStackTrace();
		} catch (InterruptedException e) {

			e.printStackTrace();
		} catch (ExecutionException e) {

			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;

	}


	public static JSONArray getHis(String apiBaseUrl,String boxUid,String token){
		// 获取历史列表
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

		try {
			System.out.println("获取历史列表信息");
			url = apiBaseUrl+"box/"+boxUid+"/hdata/def";
			BoundRequestBuilder b = asyncHttpClient
					.prepareGet(url);
			b.addHeader("Authorization","Bearer "+token);
			b.addHeader("X-FBox-ClientId", "12345678");
			b.setBody("");
			// b.addParameter("username", "cxq123");

			f = b.execute();
			r = f.get();

			status = r.getStatusCode();
			body = r.getResponseBody();
			String utf = new String(body.getBytes("iso-8859-1"), "utf-8");
			jsonArray = new JSONArray(utf);


		} catch (IOException e1) {

			e1.printStackTrace();
		} catch (InterruptedException e) {

			e.printStackTrace();
		} catch (ExecutionException e) {

			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;

	}


	public static JSONArray getValues(String apiBaseUrl,String boxId,String token,ValueBody vb){
		// 获取监控点列表
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

		try {
			System.out.println("获取监控点值");
			url = apiBaseUrl+"v2/box/"+boxId+"/dmon/value/get";
			BoundRequestBuilder b = asyncHttpClient
					.preparePost(url);
			b.addHeader("Authorization","Bearer "+token);

			b.addHeader("X-FBox-ClientId", "12345678");
			b.addHeader("Content-Type", "application/json");
			Gson gson = new Gson();
			String bodySet  = gson.toJson(vb);
			b.setBody(bodySet);
			b.setBodyEncoding("UTF-8");
			// b.addParameter("username", "cxq123");

			f = b.execute();
			r = f.get();
			status = r.getStatusCode();
			body = r.getResponseBody();
			String utf = new String(body.getBytes("iso-8859-1"), "utf-8");
			jsonArray = new JSONArray(utf);

		} catch (IOException e1) {

			e1.printStackTrace();
		} catch (InterruptedException e) {

			e.printStackTrace();
		} catch (ExecutionException e) {

			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;

	}


	public static int setValue(String apiBaseUrl,String boxId,String token,ValueSetBody vsb){
		// 获取监控点列表
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

		try {
			System.out.println("获取监控点值");
			url = apiBaseUrl+"v2/box/"+boxId+"/dmon/value";
			BoundRequestBuilder b = asyncHttpClient
					.preparePost(url);
			b.addHeader("Authorization","Bearer "+token);

			b.addHeader("X-FBox-ClientId", "12345678");
			b.addHeader("Content-Type", "application/json");
			Gson gson = new Gson();
			String bodySet  = gson.toJson(vsb);
			b.setBody(bodySet);
			b.setBodyEncoding("UTF-8");
			// b.addParameter("username", "cxq123");

			f = b.execute();
			r = f.get();
			status = r.getStatusCode();
			body = r.getResponseBody();
			System.out.println("执行成功与否    "+status);
			return status;

		} catch (IOException e1) {

			e1.printStackTrace();
		} catch (InterruptedException e) {

			e.printStackTrace();
		} catch (ExecutionException e) {

			e.printStackTrace();
		}
        return 400;

	}

	public static void main(String[] args) {
		//FBoxUtil.login("seuseu","seumima");
		//System.out.println(FBoxUtil.getFBox(token));
		System.out.println(1234567);

	}
	
	
	
}
