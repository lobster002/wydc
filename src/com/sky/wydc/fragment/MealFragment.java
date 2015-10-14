package com.sky.wydc.fragment;

import java.util.ArrayList;
import java.util.List;

import com.sky.wydc.R;
import com.sky.wydc.adapter.MealListAdapter;
import com.sky.wydc.bean.Meal;
import com.sky.wydc.config.Config;
import com.sky.wydc.httputils.HttpUtils;
import com.sky.wydc.httputils.JsonUtils;
import com.sky.wydc.ui.mealDetail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MealFragment extends FragmentBase implements OnItemClickListener {

	private ListView meal_list;// listview组件
	private List<Meal> data = new ArrayList<Meal>();// 数据源;
	private MealListAdapter meal_list_adapter;// 适配器

	private ProgressDialog dialog = null;

	private static final int FINISHED = 1;// 消息状态码

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == FINISHED) {
				// 通过工具类将收到消息的JsonArray转化为List<Meal>
				data = JsonUtils.JsonArray2Meallist((String) msg.obj);
				mApplication.setMeal_list_data(data);// 设置缓存
				meal_list_adapter.setList(data);// 重新设置数据源
				dialog.dismiss();
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_meal, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initEvent();
	}

	// 初始化布局，获得UI组件
	private void initView() {
		initOnlyTitle("套餐展示");
		meal_list = (ListView) findViewById(R.id.meal_list);

		dialog = new ProgressDialog(getActivity());
		dialog.setMessage("信息加载中");
		dialog.setCanceledOnTouchOutside(false);

	}

	// 初始化所有的事件，并设置监听
	private void initEvent() {
		data = mApplication.getMeal_list_data();
		Log.i("meal", "data.size" + data.size());

		if (data.size() == 0) {
			new Thread(new MyThread()).start();
			dialog.show();
		}
		meal_list_adapter = new MealListAdapter(getActivity(), data);
		meal_list.setAdapter(meal_list_adapter);
		meal_list.setOnItemClickListener(this);
	}

	private class MyThread implements Runnable {

		@Override
		public void run() {
			// 访问网络并拿到返回的JSON数据
			String str = HttpUtils.getRequest(Config.IP_ADDRESS
					+ "/android/GetMealList", null);// 没有参数，传递null
			// 获得消息，设置消息，并发送消息
			Message message = Message.obtain();
			message.obj = str;
			message.what = FINISHED;// 设置消息状态码
			handler.sendMessage(message);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent mIntent = new Intent(getActivity(), mealDetail.class);
		mIntent.putExtra("meal", data.get(position));// 序列化对象的传输
		startActivity(mIntent);
	}
}
