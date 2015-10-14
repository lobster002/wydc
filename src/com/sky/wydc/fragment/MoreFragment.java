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

	// 用于记录 在扩展ListView中 上次点击的位置 初始为 -1
	private int LastExpandIndex = -1;
	// TextView 用于显示文本 下边全部拿来当按钮用
	private TextView feedback = null;// 意见反馈
	private TextView aboutus = null;// 关于我们
	private TextView cleanlcear = null;// 清除数据
	private TextView exit = null;// 退出
	// 标记数组 用于保存 扩展ListView中各个Group的状态 关闭为0 -1位打开
	private int group_check[] = { 0, 0, 0, 0, 0, 0, 0, 0 };
	// 扩展 ListView 各个Group可以打开 关闭 Android提供的组件
	private ExpandableListView expandableListView = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// CreatView方法 加载fragment_more布局
		return inflater.inflate(R.layout.fragment_more, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// ActivityCreated 完成整个Activity的创建
		super.onActivityCreated(savedInstanceState);
		// FragmentBase中 自定义的方法 将帮助中心显示在标题栏
		initOnlyTitle("帮助中心");
		// 对自定义布局的初始化
		init();
	}

	private void init() {
		// 取得布局中的各个组件
		expandableListView = (ExpandableListView) findViewById(R.id.list);
		feedback = (TextView) findViewById(R.id.feedback);
		aboutus = (TextView) findViewById(R.id.aboutus);
		cleanlcear = (TextView) findViewById(R.id.cleanclear);
		exit = (TextView) findViewById(R.id.exit);

		// 这个是设定每个Group之前的那个图标
		expandableListView.setGroupIndicator(null);
		// 为扩展ListView设置适配器 adapter在下边定义
		expandableListView.setAdapter(adapter);
		// 设置 点击事件
		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
			// 匿名内部类
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// 默认方法 点击打开 在此点击关闭 这里什么都不用写
				return false;
			}
		});

		/*
		 * MoreFragment继承OnClickListener接口 所以本身也是OnClickListener 所以这里直接写this
		 * 具体事件 在onClick 里边
		 */
		cleanlcear.setOnClickListener(this);
		exit.setOnClickListener(this);
		feedback.setOnClickListener(this);
		aboutus.setOnClickListener(this);

		// 扩展ListView展开后的子布局控件的点击事件
		expandableListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				// 定义Intent 用于跳转
				Intent mIntent = new Intent();
				// Bundle 用于传递参数
				Bundle mBundle = new Bundle();

				/*
				 * 通过获得的 groupPosition 与childPosition判断点击的位置 传递不同的参数
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
				// 参数放进INtent
				mIntent.putExtras(mBundle);
				mIntent.setClass(getActivity(), DescripeActivity.class);
				// 从当前界面跳转到DescripeActivity
				startActivity(mIntent);
				return false;
			}
		});
	}

	final ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
		@Override
		// 关闭 第 groupPosition组 系统提供的犯法
		public void onGroupCollapsed(int groupPosition) {
			// 记录 该Group的状态为iguanbi
			group_check[groupPosition] = 0;
			super.onGroupCollapsed(groupPosition);
		}

		// 展开 第 groupPosition组 系统提供的犯法
		@Override
		public void onGroupExpanded(int groupPosition) {
			// 记录该组状态为打开
			group_check[groupPosition] = 1;
			super.onGroupExpanded(groupPosition);
			// 每次只允许打开一个 当LastExpandIndex!=-1 即不是第一次点击时 且本次与上次点击不同组时 关闭上次打开的组
			if (LastExpandIndex != -1 && LastExpandIndex != groupPosition)
				expandableListView.collapseGroup(LastExpandIndex);
			// 记录点击位置
			LastExpandIndex = groupPosition;
		}

		// 初始化数据 每一个为一个组
		private String[] group_title_arry = new String[] { "购买问题", "退款问题",
				"其他问题" };
		// 每一个为 上边每一个分组的 子内容
		private String[][] child_text_array = new String[][] {
				{ "支付成功还是提示付款", "银行预留手机号错误", "其他说明" },
				{ "如何退款", "退款什么时候到账，退到哪里" }, { "修改个人资料" } };

		// 每组打开与关闭时 显示图标的资源文件
		int[] group_state_array = new int[] { R.drawable.group_down,
				R.drawable.group_up };

		// 返回Group的数量
		@Override
		public int getGroupCount() {
			return group_title_arry.length;
		}

		// 返回指定Group
		@Override
		public Object getGroup(int groupPosition) {
			return group_title_arry[groupPosition];
		}

		// 返回指定GroupId
		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		// 返回自顶Group内子资源的数量
		@Override
		public int getChildrenCount(int groupPosition) {
			return child_text_array[groupPosition].length;
		}

		// 返回自顶的二级资源
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return child_text_array[groupPosition][childPosition];
		}

		// 返回指定的二级资源Id
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		// 没用
		@Override
		public boolean hasStableIds() {
			return true;
		}

		// 返回指定Group的布局初始化
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// 加载group_layout布局文件
			convertView = (LinearLayout) LinearLayout.inflate(getActivity()
					.getBaseContext(), R.layout.group_layout, null);

			// 取得全部各个控件
			TextView group_title = (TextView) convertView
					.findViewById(R.id.group_title);
			ImageView group_state = (ImageView) convertView
					.findViewById(R.id.group_state);

			// 通过判断Group的打开与关闭状态 设置图标
			if (group_check[groupPosition] == 0) {
				group_state.setImageResource(group_state_array[1]);
			} else {
				group_state.setImageResource(group_state_array[0]);
			}
			group_title.setText(group_title_arry[groupPosition]);
			return convertView;
		}

		// 返回指定的二级布局初始化
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// 加载child_layout布局文件
			convertView = (LinearLayout) LinearLayout.inflate(getActivity()
					.getBaseContext(), R.layout.child_layout, null);
			// 取得控件
			TextView child_text = (TextView) convertView
					.findViewById(R.id.child_text);
			// 设置显示指定内容
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

		// 用于跳转和传递参数
		Intent mIntent = new Intent();
		Bundle mBundle = new Bundle();

		// 通过获得触发事件的控件的Id来 传递不同的参数 跳转到不同的界面
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

	// 清楚数据时 弹出的提示框
	private void showDailog() {
		AlertDialog.Builder builder = new Builder(getActivity());
		// 设置参数
		builder.setMessage("确认清除缓存吗？(该功能需重新启动app，才可生效)");
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			// 点击确定时执行
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 清楚数据
				mApplication.getSpUtil().setIS_FRIST_LOGIN(0);
				mApplication.getSpUtil().setisFristIn(true);
				mApplication.getSpUtil().setSTRINGUSERINFO("");
				System.exit(-1);
			}
		});
		builder.setNegativeButton("取消", null);
		// 显示窗口
		builder.create().show();
	}
}
