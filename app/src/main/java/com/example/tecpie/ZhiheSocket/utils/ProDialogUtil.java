package com.example.tecpie.ZhiheSocket.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

public class ProDialogUtil {
	private static ProgressDialog instance;

	
	public static void showProDialog(Context context, String text, boolean canTouchOutside){
		instance = new ProgressDialog(context);
		instance.setMessage(text);
		instance.setCanceledOnTouchOutside(canTouchOutside);
		((Activity) context).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				instance.show();
			}
		});
	}
	
	public static void cancel(){
		instance.cancel();
	}

}
