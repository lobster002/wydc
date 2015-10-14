package com.sky.wydc.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CommonUtils {

	/** ����Ƿ������� */
	public static boolean isNetworkAvailable(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		if (info != null) {
			return info.isAvailable();// ֻҪ�����磬�ͷ���true,����ʲô����
		}
		return false;
	}

	/** ����Ƿ���WIFI */
	public static boolean isWifi(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		if (info != null) {
			if (info.getType() == ConnectivityManager.TYPE_WIFI)// TYPE_WIFIΪWIFI��������
				return true;
		}
		return false;
	}

	// ����Ƿ����ƶ�����
	public static boolean isMobile(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		if (info != null) {
			if (info.getType() == ConnectivityManager.TYPE_MOBILE)// TYPE_MOBILEΪ�ƶ���������
				return true;
		}
		return false;
	}

	// ��ȡ��ǰ��������Ϣ
	private static NetworkInfo getNetworkInfo(Context context) {
		// ϵͳ��װ�ķ���
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo();
	}

	// ���SD���Ƿ����
	public static boolean checkSdCard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

}
