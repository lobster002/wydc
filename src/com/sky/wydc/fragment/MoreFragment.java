package com.sky.wydc.fragment;

import com.sky.wydc.R;
import com.sky.wydc.ui.DescripeActivity;
import com.sky.wydc.ui.FeedBackActivity;
import com.sky.wydc.ui.LoginActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class MoreFragment extends FragmentBase implements OnClickListener {

	// ���ڼ�¼ ����չListView�� �ϴε����λ�� ��ʼΪ -1
	private int LastExpandIndex = -1;
	// TextView ������ʾ�ı� �±�ȫ����������ť��
	private TextView feedback = null;// �������
	private TextView aboutus = null;// ��������
	private TextView cleanlcear = null;// �������
	private TextView exit = null;// �˳�
	// ������� ���ڱ��� ��չListView�и���Group��״̬ �ر�Ϊ0 -1λ��
	private int group_check[] = { 0, 0, 0, 0, 0, 0, 0, 0 };
	// ��չ ListView ����Group���Դ� �ر� Android�ṩ�����
	private ExpandableListView expandableListView = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// CreatView���� ����fragment_more����
		return inflater.inflate(R.layout.fragment_more, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// ActivityCreated �������Activity�Ĵ���
		super.onActivityCreated(savedInstanceState);
		// FragmentBase�� �Զ���ķ��� ������������ʾ�ڱ�����
		initOnlyTitle("��������");
		// ���Զ��岼�ֵĳ�ʼ��
		init();
	}

	private void init() {
		// ȡ�ò����еĸ������
		expandableListView = (ExpandableListView) findViewById(R.id.list);
		feedback = (TextView) findViewById(R.id.feedback);
		aboutus = (TextView) findViewById(R.id.aboutus);
		cleanlcear = (TextView) findViewById(R.id.cleanclear);
		exit = (TextView) findViewById(R.id.exit);

		// ������趨ÿ��Group֮ǰ���Ǹ�ͼ��
		expandableListView.setGroupIndicator(null);
		// Ϊ��չListView���������� adapter���±߶���
		expandableListView.setAdapter(adapter);
		// ���� ����¼�
		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
			// �����ڲ���
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Ĭ�Ϸ��� ����� �ڴ˵���ر� ����ʲô������д
				return false;
			}
		});

		/*
		 * MoreFragment�̳�OnClickListener�ӿ� ���Ա���Ҳ��OnClickListener ��������ֱ��дthis
		 * �����¼� ��onClick ���
		 */
		cleanlcear.setOnClickListener(this);
		exit.setOnClickListener(this);
		feedback.setOnClickListener(this);
		aboutus.setOnClickListener(this);

		// ��չListViewչ������Ӳ��ֿؼ��ĵ���¼�
		expandableListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				// ����Intent ������ת
				Intent mIntent = new Intent();
				// Bundle ���ڴ��ݲ���
				Bundle mBundle = new Bundle();

				/*
				 * ͨ����õ� groupPosition ��childPosition�жϵ����λ�� ���ݲ�ͬ�Ĳ���
				 */
				switch (groupPosition) {
				case 0: {
					if (childPosition == 0) {
						mBundle.putInt("index", 1);
					} else if (childPosition == 1) {
						mBundle.putInt("index", 2);
					} else {
						mBundle.putInt("index", 3);
					}
					break;
				}
				case 1: {
					if (childPosition == 0) {
						mBundle.putInt("index", 4);
					} else {
						mBundle.putInt("index", 5);
					}
					break;
				}
				case 2: {
					mBundle.putInt("index", 6);
					break;
				}
				}
				// �����Ž�INtent
				mIntent.putExtras(mBundle);
				mIntent.setClass(getActivity(), DescripeActivity.class);
				// �ӵ�ǰ������ת��DescripeActivity
				startActivity(mIntent);
				return false;
			}
		});
	}

	final ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
		@Override
		// �ر� �� groupPosition�� ϵͳ�ṩ�ķ���
		public void onGroupCollapsed(int groupPosition) {
			// ��¼ ��Group��״̬Ϊiguanbi
			group_check[groupPosition] = 0;
			super.onGroupCollapsed(groupPosition);
		}

		// չ�� �� groupPosition�� ϵͳ�ṩ�ķ���
		@Override
		public void onGroupExpanded(int groupPosition) {
			// ��¼����״̬Ϊ��
			group_check[groupPosition] = 1;
			super.onGroupExpanded(groupPosition);
			// ÿ��ֻ�����һ�� ��LastExpandIndex!=-1 �����ǵ�һ�ε��ʱ �ұ������ϴε����ͬ��ʱ �ر��ϴδ򿪵���
			if (LastExpandIndex != -1 && LastExpandIndex != groupPosition)
				expandableListView.collapseGroup(LastExpandIndex);
			// ��¼���λ��
			LastExpandIndex = groupPosition;
		}

		// ��ʼ������ ÿһ��Ϊһ����
		private String[] group_title_arry = new String[] { "��������", "�˿�����",
				"��������" };
		// ÿһ��Ϊ �ϱ�ÿһ������� ������
		private String[][] child_text_array = new String[][] {
				{ "֧���ɹ�������ʾ����", "����Ԥ���ֻ��Ŵ���", "����˵��" },
				{ "����˿�", "�˿�ʲôʱ���ˣ��˵�����" }, { "�޸ĸ�������" } };

		// ÿ�����ر�ʱ ��ʾͼ�����Դ�ļ�
		int[] group_state_array = new int[] { R.drawable.group_down,
				R.drawable.group_up };

		// ����Group������
		@Override
		public int getGroupCount() {
			return group_title_arry.length;
		}

		// ����ָ��Group
		@Override
		public Object getGroup(int groupPosition) {
			return group_title_arry[groupPosition];
		}

		// ����ָ��GroupId
		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		// �����Զ�Group������Դ������
		@Override
		public int getChildrenCount(int groupPosition) {
			return child_text_array[groupPosition].length;
		}

		// �����Զ��Ķ�����Դ
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return child_text_array[groupPosition][childPosition];
		}

		// ����ָ���Ķ�����ԴId
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		// û��
		@Override
		public boolean hasStableIds() {
			return true;
		}

		// ����ָ��Group�Ĳ��ֳ�ʼ��
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// ����group_layout�����ļ�
			convertView = (LinearLayout) LinearLayout.inflate(getActivity()
					.getBaseContext(), R.layout.group_layout, null);

			// ȡ��ȫ�������ؼ�
			TextView group_title = (TextView) convertView
					.findViewById(R.id.group_title);
			ImageView group_state = (ImageView) convertView
					.findViewById(R.id.group_state);

			// ͨ���ж�Group�Ĵ���ر�״̬ ����ͼ��
			if (group_check[groupPosition] == 0) {
				group_state.setImageResource(group_state_array[1]);
			} else {
				group_state.setImageResource(group_state_array[0]);
			}
			group_title.setText(group_title_arry[groupPosition]);
			return convertView;
		}

		// ����ָ���Ķ������ֳ�ʼ��
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// ����child_layout�����ļ�
			convertView = (LinearLayout) LinearLayout.inflate(getActivity()
					.getBaseContext(), R.layout.child_layout, null);
			// ȡ�ÿؼ�
			TextView child_text = (TextView) convertView
					.findViewById(R.id.child_text);
			// ������ʾָ������
			child_text.setText(child_text_array[groupPosition][childPosition]);
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	};

	@Override
	public void onClick(View v) {

		// ������ת�ʹ��ݲ���
		Intent mIntent = new Intent();
		Bundle mBundle = new Bundle();

		// ͨ����ô����¼��Ŀؼ���Id�� ���ݲ�ͬ�Ĳ��� ��ת����ͬ�Ľ���
		switch (v.getId()) {
		case R.id.aboutus:
			mBundle.putInt("index", -1);
			mIntent.putExtras(mBundle);
			mIntent.setClass(getActivity(), DescripeActivity.class);
			startActivity(mIntent);
			break;
		case R.id.feedback:
			mIntent.setClass(getActivity(), FeedBackActivity.class);
			startActivity(mIntent);
			break;
		case R.id.exit:
			mApplication.getSpUtil().setIS_FRIST_LOGIN(0);
			mApplication.getSpUtil().setSTRINGUSERINFO("");
			mIntent.setClass(getActivity(), LoginActivity.class);
			startActivity(mIntent);
			getActivity().finish();
			break;
		case R.id.cleanclear:
			showDailog();
			break;
		}
	}

	// �������ʱ ��������ʾ��
	private void showDailog() {
		AlertDialog.Builder builder = new Builder(getActivity());
		// ���ò���
		builder.setMessage("ȷ�����������(�ù�������������app���ſ���Ч)");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			// ���ȷ��ʱִ��
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// �������
				mApplication.getSpUtil().setIS_FRIST_LOGIN(0);
				mApplication.getSpUtil().setisFristIn(true);
				mApplication.getSpUtil().setSTRINGUSERINFO("");
				System.exit(-1);
			}
		});
		builder.setNegativeButton("ȡ��", null);
		// ��ʾ����
		builder.create().show();
	}
}
