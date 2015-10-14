package com.sky.wydc.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 通用SharedPreferences编辑器
 */
@SuppressLint("CommitPrefEdits")
public class SharePreferenceUtil {
	// SharedPreferences对象
	private SharedPreferences mSharedPreferences;
	// SharedPreferences编辑器
	private static SharedPreferences.Editor editor;

	public SharePreferenceUtil(Context context, String name) {
		// 设置只可当前程序访问修改，
		// 当SharedPreferences文件不存在时，自动创建一个新的文件
		mSharedPreferences = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		// 获得编辑器
		editor = mSharedPreferences.edit();
	}

	// 是否第一次使用
	private String IS_FRIST_IN = "shared_key_isfrist";
	// 是否登录
	private String IS_LOGIN = "shared_key_islogin";
	// 缓存用户的JSON信息
	private String STRINGUSERINFO = "shared_key_userinfo";

	// 是否第一次使用
	public boolean isFristIn() {
		return mSharedPreferences.getBoolean(IS_FRIST_IN, true);
	}

	public void setisFristIn(boolean flag) {
		editor.putBoolean(IS_FRIST_IN, flag);
		editor.commit();
	}

	// 是否第一次登录
	public int getIS_FRIST_LOGIN() {
		return mSharedPreferences.getInt(IS_LOGIN, 0);
	}

	public void setIS_FRIST_LOGIN(int userid) {
		editor.putInt(IS_LOGIN, userid);
		editor.commit();
	}

	// 得到当前用户的缓存信息
	public String getSTRINGUSERINFO() {
		return mSharedPreferences.getString(STRINGUSERINFO, "");
	}

	// 编辑缓存信息
	public void setSTRINGUSERINFO(String sTRINGUSERINFO) {
		editor.putString(STRINGUSERINFO, sTRINGUSERINFO);
		editor.commit();
	}
}
