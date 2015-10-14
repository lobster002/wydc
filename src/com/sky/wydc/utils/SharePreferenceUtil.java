package com.sky.wydc.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * ͨ��SharedPreferences�༭��
 */
@SuppressLint("CommitPrefEdits")
public class SharePreferenceUtil {
	// SharedPreferences����
	private SharedPreferences mSharedPreferences;
	// SharedPreferences�༭��
	private static SharedPreferences.Editor editor;

	public SharePreferenceUtil(Context context, String name) {
		// ����ֻ�ɵ�ǰ��������޸ģ�
		// ��SharedPreferences�ļ�������ʱ���Զ�����һ���µ��ļ�
		mSharedPreferences = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		// ��ñ༭��
		editor = mSharedPreferences.edit();
	}

	// �Ƿ��һ��ʹ��
	private String IS_FRIST_IN = "shared_key_isfrist";
	// �Ƿ��¼
	private String IS_LOGIN = "shared_key_islogin";
	// �����û���JSON��Ϣ
	private String STRINGUSERINFO = "shared_key_userinfo";

	// �Ƿ��һ��ʹ��
	public boolean isFristIn() {
		return mSharedPreferences.getBoolean(IS_FRIST_IN, true);
	}

	public void setisFristIn(boolean flag) {
		editor.putBoolean(IS_FRIST_IN, flag);
		editor.commit();
	}

	// �Ƿ��һ�ε�¼
	public int getIS_FRIST_LOGIN() {
		return mSharedPreferences.getInt(IS_LOGIN, 0);
	}

	public void setIS_FRIST_LOGIN(int userid) {
		editor.putInt(IS_LOGIN, userid);
		editor.commit();
	}

	// �õ���ǰ�û��Ļ�����Ϣ
	public String getSTRINGUSERINFO() {
		return mSharedPreferences.getString(STRINGUSERINFO, "");
	}

	// �༭������Ϣ
	public void setSTRINGUSERINFO(String sTRINGUSERINFO) {
		editor.putString(STRINGUSERINFO, sTRINGUSERINFO);
		editor.commit();
	}
}
