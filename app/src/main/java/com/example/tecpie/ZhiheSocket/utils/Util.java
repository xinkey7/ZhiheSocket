package com.example.tecpie.ZhiheSocket.utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.Toast;

public class Util {
	public static void showToast(final Context context, final String text) {
		final long time =2000;

		((Activity) context).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				final Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
				toast.show();
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					public void run() {
						toast.cancel();
					}
				}, time);
			}
		});
	}
	
	public static boolean putStringToSharedPreferences(Context context, String name, String key, String val){
		SharedPreferences sharedPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key,val);
		return editor.commit();
	}
	public static String getStringFromSharedPreferences(Context context, String name, String key){
		SharedPreferences sharedPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
		return sharedPreferences.getString(key,"");
	}
}
