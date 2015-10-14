package com.sky.wydc.config;

import java.util.ArrayList;
import java.util.List;

import com.sky.wydc.bean.Dishes;
import com.sky.wydc.bean.Meal;
import com.sky.wydc.utils.SharePreferenceUtil;

import android.app.Application;
import android.app.NotificationManager;

/**
 * �Զ���ȫ��Applcation��
 */
public class CustomApplcation extends Application {

	public static CustomApplcation mInstance;
	// ͨ��application�����ж������ݣ������𵽻�������ã�����������ʴ���
	private List<Dishes> dishes_list_data;
	public List<Meal> meal_list_data;

	public List<Dishes> getDishes_list_data() {
		if (dishes_list_data != null)
			return dishes_list_data;
		// ��ֹ��ָ���쳣
		return new ArrayList<Dishes>();
	}

	public void setDishes_list_data(List<Dishes> dishes_list_data) {
		this.dishes_list_data = dishes_list_data;
	}

	public List<Meal> getMeal_list_data() {
		if (meal_list_data != null) {
			return meal_list_data;
		}
		// ��ֹ��ָ���쳣
		return new ArrayList<Meal>();
	}

	public void setMeal_list_data(List<Meal> meal_list_data) {
		this.meal_list_data = meal_list_data;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;// Ӧ�õ�һ��������������
		init();
	}

	private void init() {
		mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
	}

	// ͨ����̬���������ض��󴴽���Application����
	public static CustomApplcation getInstance() {
		return mInstance;
	}

	// ����ģʽ�����ܼ�ʱ��������
	SharePreferenceUtil mSpUtil;
	public static final String PREFERENCE_NAME = Config.SHAREDINFO;

	// ����Ϊ����ģʽ������SharePreferenceUtil�Ĳ�����
	public synchronized SharePreferenceUtil getSpUtil() {
		if (mSpUtil == null) {
			mSpUtil = new SharePreferenceUtil(this, PREFERENCE_NAME);
		}
		return mSpUtil;
	}

	// Android״̬��֪ͨ����ʱ����
	NotificationManager mNotificationManager;

	public NotificationManager getNotificationManager() {
		if (mNotificationManager == null)
			mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		return mNotificationManager;
	}
}
