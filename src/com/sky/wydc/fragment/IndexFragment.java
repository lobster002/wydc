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

	private ListView dishes_list;// 主页显示的LISTVIEW
	private DishesListAdapter dishes_list_adapter;// LISVIEW适配器
	private List<Dishes> data = new ArrayList<Dishes>();// 数据源

	private static int FINISH = 1;// Message状态码

	private ProgressDialog dialog = null;// 进度条

	private ArcMenu mArcMenu = null;// 自定义卫星菜单

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == FINISH) {
				// 通过工具类将收到消息的JsonArray转化为List<Dishes>
				data = JsonUtils.JsonArray2Dishlist((String) msg.obj);
				mApplication.setDishes_list_data(data);// 设置缓存
				dishes_list_adapter.setList(data);// 重新设置数据源
				dialog.dismiss();
				dishes_list_adapter.setAllsource(data);

			}
		}
	};

	// 创建要显示的视图
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_index, container, false);
	}

	// FragMent启动时触发的事件
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initEvent();
	}

	// 初始化布局
	private void initView() {
		initOnlyTitle("单品展示");
		dishes_list = (ListView) findViewById(R.id.dishes_list);

		dialog = new ProgressDialog(getActivity());
		dialog.setMessage("信息加载中");
		dialog.setCanceledOnTouchOutside(false);
		mArcMenu = (ArcMenu) findViewById(R.id.arcmenu);

	}

	// 初始化事件
	private void initEvent() {
		// 先从Application对象中获取缓存的单品信息
		data = mApplication.getDishes_list_data();
		// 如果没有缓冲信息再从网络上加载
		if (data.size() == 0) {
			dialog.show();
			new Thread(new MyThread()).start();
		}
		dishes_list_adapter = new DishesListAdapter(getActivity(), data);// 新建适配器
		dishes_list.setAdapter(dishes_list_adapter);// 设置适配器
		// 设置里item的点击事件
		dishes_list.setOnItemClickListener(this);

		mArcMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				dishes_list_adapter.setFlag(position);
				dishes_list_adapter.notifyDataSetChanged();
			}
		});
	}

	// 子线程，用于访问网络
	class MyThread implements Runnable {

		@Override
		public void run() {
			// 访问网络并拿到返回的JSON数据
			String str = HttpUtils.getRequest(Config.IP_ADDRESS
					+ "/android/GetDishList", null);// 没有参数，传递null
			// 获得消息，设置消息，并发送消息
			Message message = Message.obtain();
			message.obj = str;
			message.what = FINISH;// 设置消息状态码
			handler.sendMessage(message);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent mIntent = new Intent(getActivity(), DishesDetaliActivity.class);
		mIntent.putExtra("dishes", data.get(position));// 序列化对象传输
		startActivity(mIntent);
	}
}
