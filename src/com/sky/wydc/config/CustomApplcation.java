package com.sky.wydc.config;

import java.util.ArrayList;
import java.util.List;

import com.sky.wydc.bean.Dishes;
import com.sky.wydc.bean.Meal;
import com.sky.wydc.utils.SharePreferenceUtil;

import android.app.Application;
import android.app.NotificationManager;

/**
 * 自定义全局Applcation类
 */
public class CustomApplcation extends Application {

	public static CustomApplcation mInstance;
	// 通过application对象中定义数据，可以起到缓存的作用，减少网络访问次数
	private List<Dishes> dishes_list_data;
	public List<Meal> meal_list_data;

	public List<Dishes> getDishes_list_data() {
		if (dishes_list_data != null)
			return dishes_list_data;
		// 防止空指针异常
		return new ArrayList<Dishes>();
	}

	public void setDishes_list_data(List<Dishes> dishes_list_data) {
		this.dishes_list_data = dishes_list_data;
	}

	public List<Meal> getMeal_list_data() {
		if (meal_list_data != null) {
			return meal_list_data;
		}
		// 防止空指针异常
		return new ArrayList<Meal>();
	}

	public void setMeal_list_data(List<Meal> meal_list_data) {
		this.meal_list_data = meal_list_data;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;// 应用第一次启动创建对象
		init();
	}

	private void init() {
		mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
	}

	// 通过静态方法来返回对象创建的Application对象
	public static CustomApplcation getInstance() {
		return mInstance;
	}

	// 单例模式，才能及时返回数据
	SharePreferenceUtil mSpUtil;
	public static final String PREFERENCE_NAME = Config.SHAREDINFO;

	// 设置为单例模式，返回SharePreferenceUtil的操作类
	public synchronized SharePreferenceUtil getSpUtil() {
		if (mSpUtil == null) {
			mSpUtil = new SharePreferenceUtil(this, PREFERENCE_NAME);
		}
		return mSpUtil;
	}

	// Android状态栏通知，暂时无用
	NotificationManager mNotificationManager;

	public NotificationManager getNotificationManager() {
		if (mNotificationManager == null)
			mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		return mNotificationManager;
	}
}
