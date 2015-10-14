package com.sky.wydc.utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.sky.wydc.bean.User;
import com.sky.wydc.config.Config;

public class CurrentUserUitl {
	// 获得当前缓存的用户，将SharePreference中存储的json数据解析为UserBean
	public static User getCurUser(Context context) {
		User user = new User();
		String userinfo = new SharePreferenceUtil(context, Config.SHAREDINFO)
				.getSTRINGUSERINFO();
		try {
			JSONObject json = new JSONObject(userinfo);
			user.setId(json.getInt("Id"));
			user.setUsername(json.getString("username"));
			user.setPassword(json.getString("password"));
			user.setAvatar(json.getString("avatar"));
			user.setNickname(json.getString("nickname"));
			user.setAddress(json.getString("address"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}
}
