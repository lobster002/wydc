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

	private ListView meal_list;// listview���
	private List<Meal> data = new ArrayList<Meal>();// ����Դ;
	private MealListAdapter meal_list_adapter;// ������

	private ProgressDialog dialog = null;

	private static final int FINISHED = 1;// ��Ϣ״̬��

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == FINISHED) {
				// ͨ�������ཫ�յ���Ϣ��JsonArrayת��ΪList<Meal>
				data = JsonUtils.JsonArray2Meallist((String) msg.obj);
				mApplication.setMeal_list_data(data);// ���û���
				meal_list_adapter.setList(data);// ������������Դ
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

	// ��ʼ�����֣����UI���
	private void initView() {
		initOnlyTitle("�ײ�չʾ");
		meal_list = (ListView) findViewById(R.id.meal_list);

		dialog = new ProgressDialog(getActivity());
		dialog.setMessage("��Ϣ������");
		dialog.setCanceledOnTouchOutside(false);

	}

	// ��ʼ�����е��¼��������ü���
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
			// �������粢�õ����ص�JSON����
			String str = HttpUtils.getRequest(Config.IP_ADDRESS
					+ "/android/GetMealList", null);// û�в���������null
			// �����Ϣ��������Ϣ����������Ϣ
			Message message = Message.obtain();
			message.obj = str;
			message.what = FINISHED;// ������Ϣ״̬��
			handler.sendMessage(message);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent mIntent = new Intent(getActivity(), mealDetail.class);
		mIntent.putExtra("meal", data.get(position));// ���л�����Ĵ���
		startActivity(mIntent);
	}
}
