package com.sky.wydc.fragment;

import java.util.ArrayList;
import java.util.List;

import com.sky.wydc.R;
import com.sky.wydc.adapter.DishesListAdapter;
import com.sky.wydc.bean.Dishes;
import com.sky.wydc.config.Config;
import com.sky.wydc.httputils.HttpUtils;
import com.sky.wydc.httputils.JsonUtils;
import com.sky.wydc.ui.DishesDetaliActivity;
import com.sky.wydc.view.ArcMenu;
import com.sky.wydc.view.ArcMenu.OnMenuItemClickListener;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class IndexFragment extends FragmentBase implements OnItemClickListener {

	private ListView dishes_list;// ��ҳ��ʾ��LISTVIEW
	private DishesListAdapter dishes_list_adapter;// LISVIEW������
	private List<Dishes> data = new ArrayList<Dishes>();// ����Դ

	private static int FINISH = 1;// Message״̬��

	private ProgressDialog dialog = null;// ������

	private ArcMenu mArcMenu = null;// �Զ������ǲ˵�

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == FINISH) {
				// ͨ�������ཫ�յ���Ϣ��JsonArrayת��ΪList<Dishes>
				data = JsonUtils.JsonArray2Dishlist((String) msg.obj);
				mApplication.setDishes_list_data(data);// ���û���
				dishes_list_adapter.setList(data);// ������������Դ
				dialog.dismiss();
				dishes_list_adapter.setAllsource(data);

			}
		}
	};

	// ����Ҫ��ʾ����ͼ
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_index, container, false);
	}

	// FragMent����ʱ�������¼�
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initEvent();
	}

	// ��ʼ������
	private void initView() {
		initOnlyTitle("��Ʒչʾ");
		dishes_list = (ListView) findViewById(R.id.dishes_list);

		dialog = new ProgressDialog(getActivity());
		dialog.setMessage("��Ϣ������");
		dialog.setCanceledOnTouchOutside(false);
		mArcMenu = (ArcMenu) findViewById(R.id.arcmenu);

	}

	// ��ʼ���¼�
	private void initEvent() {
		// �ȴ�Application�����л�ȡ����ĵ�Ʒ��Ϣ
		data = mApplication.getDishes_list_data();
		// ���û�л�����Ϣ�ٴ������ϼ���
		if (data.size() == 0) {
			dialog.show();
			new Thread(new MyThread()).start();
		}
		dishes_list_adapter = new DishesListAdapter(getActivity(), data);// �½�������
		dishes_list.setAdapter(dishes_list_adapter);// ����������
		// ������item�ĵ���¼�
		dishes_list.setOnItemClickListener(this);

		mArcMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				dishes_list_adapter.setFlag(position);
				dishes_list_adapter.notifyDataSetChanged();
			}
		});
	}

	// ���̣߳����ڷ�������
	class MyThread implements Runnable {

		@Override
		public void run() {
			// �������粢�õ����ص�JSON����
			String str = HttpUtils.getRequest(Config.IP_ADDRESS
					+ "/android/GetDishList", null);// û�в���������null
			// �����Ϣ��������Ϣ����������Ϣ
			Message message = Message.obtain();
			message.obj = str;
			message.what = FINISH;// ������Ϣ״̬��
			handler.sendMessage(message);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent mIntent = new Intent(getActivity(), DishesDetaliActivity.class);
		mIntent.putExtra("dishes", data.get(position));// ���л�������
		startActivity(mIntent);
	}
}
