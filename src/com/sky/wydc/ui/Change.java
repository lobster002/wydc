package com.sky.wydc.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.sky.wydc.R;
import com.sky.wydc.adapter.ExpandableListViewAdapter;
import com.sky.wydc.bean.Meal;
import com.sky.wydc.config.Config;
import com.sky.wydc.httputils.HttpUtils;
import com.sky.wydc.httputils.JsonUtils;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;

public class Change extends ActivityBase implements OnClickListener {
	// ��չListView
	private ExpandableListView mExpandableListView = null;
	// ��չListView��������
	private ExpandableListViewAdapter mExpandableListViewAdapter = null;
	private Meal meal = null;
	private Button meal_addcart = null;
	private Button meal_pay = null;
	private int lastposition = -1;
	private int POS = -1;
	private ProgressDialog dialog = null;
	// ������� ���ڼ�¼����Group�Ĵ򿪻�ر�״̬
	private int[] check = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0 };
	List<BasicNameValuePair> parms;
	// ȫ�־�̬����
	public static final int CHANGE = 1;
	public static final int ADD_CART = 2;
	public static final int PAY = 3;

	// �����������̴߳��ݵ���Ϣ
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CHANGE:// �������ʱ
				if (!msg.obj.toString().equals("-1")) {// �����ѯ�����Ϊ��
					if (check[POS] == 0)// ����÷���Ϊ�ر�״̬ ֱ�ӷ���
						break;
					// �޸�����Դ
					mExpandableListViewAdapter.gridViewChild[POS] = JsonUtils
							.JsonArray2StringArray(msg.obj.toString());
					// ������ʾ
					mExpandableListViewAdapter.notifyDataSetChanged();
				} else {
					ShowToast("û�п��滻��Ʒ");
				}
				break;
			case ADD_CART:
				// ��ӹ��ﳵ
				if (msg.obj.toString().equals("\"y\"")) {
					// ���� y ����ӳɹ�
					dialog.dismiss();
					ShowToast("��ӹ��ﳵ�ɹ�");
				} else {// ���ɹ�ʱ����ʾ
					ShowToast("δ��ӳɹ���������һ��");
					dialog.dismiss();
				}
				break;
			case PAY:
				// ֧���¼�
				if (msg.obj.toString().equals("\"y\"")) {// ֧���ɹ�ʱ ��ʾ ֧���ɹ�
					dialog.dismiss();
					ShowToast("֧���ɹ�");
				} else {
					ShowToast("δ֧���ɹ���������һ��");
					dialog.dismiss();
				}
				break;
			}
		}
	};

	// �����ʱ���� OnCreat����
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ò���ʾ������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ���ز���
		setContentView(R.layout.change);
		// ��ȡ meal ��Դ
		meal = (Meal) getIntent().getSerializableExtra("meal");
		initLeftBtnAndTitle(meal.getName());
		// ��ʼ������
		init();
	}

	private void init() {
		// ��ʼ�� ��Ϣ��
		dialog = new ProgressDialog(this);
		dialog.setMessage("��ȴ�������");
		dialog.setCanceledOnTouchOutside(false);

		// ȡ�ÿؼ�
		mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
		// ����������
		mExpandableListViewAdapter = new ExpandableListViewAdapter(this, meal);
		mExpandableListView.setAdapter(mExpandableListViewAdapter);
		// ���� Group ����¼�
		mExpandableListView
				.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
					@Override
					public boolean onGroupClick(ExpandableListView parent,
							View v, int groupPosition, long id) {
						// ��¼���λ��
						POS = groupPosition;
						// ��� �򿪹ر�״̬
						check[groupPosition] = ~check[groupPosition];
						// �ر� �� ����
						if (check[groupPosition] == 0)
							return false;
						// ��¼���ε��λ��
						lastposition = groupPosition;
						// ��Web��������
						parms = new ArrayList<BasicNameValuePair>();
						parms.add(new BasicNameValuePair("name",
								mExpandableListViewAdapter.group[groupPosition]));
						new Thread(new MyThread(Config.IP_ADDRESS
								+ "/android/getOneDishes", parms, CHANGE))
								.start();
						mExpandableListViewAdapter.notifyDataSetChanged();
						return false;
					}
				});
		// ����Child����¼�
		mExpandableListView
				.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
					@Override
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						return false;
					}
				});
		// ȡ����� ���ü���
		meal_addcart = (Button) findViewById(R.id.meal_addcart);
		meal_addcart.setOnClickListener(this);
		meal_pay = (Button) findViewById(R.id.meal_pay);
		meal_pay.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// ����¼�����ʱ ���ݴ����Ŀؼ��Ĳ�ͬ ִ�в�ͨ�Ĵ���
		switch (v.getId()) {
		case R.id.meal_addcart:// ��ӹ��ﳵ
			dialog.show();// ��ʾ ����
			// �����߳�����ӵ����ﳵ
			parms = new ArrayList<BasicNameValuePair>();
			parms.add(new BasicNameValuePair("uid", mApplication.getSpUtil()
					.getIS_FRIST_LOGIN() + ""));
			parms.add(new BasicNameValuePair("id", meal.getId() + ""));
			parms.add(new BasicNameValuePair("sum", "1"));
			StringBuilder builder = new StringBuilder();
			for (String str : mExpandableListViewAdapter.group) {
				builder.append(str);
				builder.append(";");
			}
			parms.add(new BasicNameValuePair("remark", builder.toString()));
			new Thread(new MyThread(Config.IP_ADDRESS + "/android/mealtocart",
					parms, ADD_CART)).start();
			break;
		case R.id.meal_pay:// ����֧��
			dialog.show();// ��ʾ����
			// �����߳��� ִ��
			parms = new ArrayList<BasicNameValuePair>();
			parms.add(new BasicNameValuePair("uid", mApplication.getSpUtil()
					.getIS_FRIST_LOGIN() + ""));
			parms.add(new BasicNameValuePair("id", meal.getId() + ""));
			parms.add(new BasicNameValuePair("sum", "1"));
			StringBuilder builder2 = new StringBuilder();
			for (String str : mExpandableListViewAdapter.group) {
				builder2.append(str);
				builder2.append(";");
			}
			parms.add(new BasicNameValuePair("remark", builder2.toString()));
			new Thread(new MyThread(Config.IP_ADDRESS + "/android/mealpay",
					parms, PAY)).start();
			break;
		}
	}

	// ���߳�
	class MyThread implements Runnable {
		private String ip; // ����IP
		private List<BasicNameValuePair> parms;// ����
		private int what; // ��Ϣ

		// ��ʼ��
		public MyThread(String ip, List<BasicNameValuePair> parms, int what) {
			this.ip = ip;
			this.parms = parms;
			this.what = what;
		}

		@Override
		public void run() {
			// post���� ִ�д��ݲ���
			String str = HttpUtils.postRequest(ip, parms);
			Message message = Message.obtain();
			message.obj = str;
			message.what = what;
			// ���ظ����߳�
			handler.sendMessage(message);
		}
	}
}
