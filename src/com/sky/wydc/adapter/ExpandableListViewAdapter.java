package com.sky.wydc.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.sky.wydc.R;
import com.sky.wydc.bean.Meal;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

	// Group 内容
	public String[] group = { "", "", "", "", "", "", "", "" };
	// 用于保存 gridView View的 二维数组
	public String[][] gridViewChild = new String[10][10];
	// 用于保存Group的Chil内容
	String[][] child = { { "" }, { "" }, { "" }, { "" }, { "" }, { "" },
			{ "" }, { "" }, { "" }, { "" } };
	LayoutInflater mInflater;
	Context context;

	// 初始化
	public ExpandableListViewAdapter(Context context, Meal meal) {
		String res = meal.getDishids().replace(" ", "");
		group = res.split(";");
		mInflater = LayoutInflater.from(context);
		this.context = context;
	}

	// 返回指定Child内容
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return child[groupPosition][childPosition];
	}

	// 返回指定Child_ID
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	// 返回指定ChildView
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {// 如果为空
			// 创建对象
			mViewChild = new ViewChild();
			// 加载布局
			convertView = mInflater.inflate(
					R.layout.channel_expandablelistview_item, null);
			// 取得控件
			mViewChild.gridView = (GridView) convertView
					.findViewById(R.id.channel_item_child_gridView);
			convertView.setTag(mViewChild);
		} else {
			mViewChild = (ViewChild) convertView.getTag();
		}

		// 为Group的gridView设置适配器
		SimpleAdapter mSimpleAdapter = new SimpleAdapter(context,
				setGridViewData(gridViewChild[groupPosition]),
				R.layout.channel_gridview_item,
				new String[] { "channel_gridview_item" },
				new int[] { R.id.channel_gridview_item });
		mViewChild.gridView.setAdapter(mSimpleAdapter);
		setGridViewListener(groupPosition, mViewChild.gridView);
		// 设置点击效果
		mViewChild.gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

		return convertView;
	}

	// 设置GridView点击事件 监听
	private void setGridViewListener(final int pos, final GridView gridView) {
		gridView.setOnItemClickListener(new GridView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (view instanceof TextView) {// 取得TextView

					TextView tv = (TextView) view;
					// 保存点击的内容
					group[pos] = tv.getText().toString();
					// 更改显示
					ExpandableListViewAdapter.this.notifyDataSetChanged();
				}
			}
		});
	}

	// 封装数据 将String[] 封装为ArrayList
	private ArrayList<HashMap<String, Object>> setGridViewData(String[] data) {
		ArrayList<HashMap<String, Object>> gridItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < data.length; i++) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("channel_gridview_item", data[i]);
			gridItem.add(hashMap);
		}
		return gridItem;
	}

	// 返回指定的ChildCount
	@Override
	public int getChildrenCount(int groupPosition) {
		return child[groupPosition].length;
	}

	// 返回指定Group
	@Override
	public Object getGroup(int groupPosition) {
		return group[groupPosition];
	}

	// 返回Group数量
	@Override
	public int getGroupCount() {
		return group.length;
	}

	// 返回指定GroupId
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	// 返回指定GroupView
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// 如果为空
		if (convertView == null) {
			// 创建对象
			mViewChild = new ViewChild();
			// 加载布局
			convertView = mInflater.inflate(
					R.layout.channel_expandablelistview, null);
			// 取得控件
			mViewChild.textView = (TextView) convertView
					.findViewById(R.id.channel_group_name);
			mViewChild.imageView = (ImageView) convertView
					.findViewById(R.id.channel_imageview_orientation);
			convertView.setTag(mViewChild);
		} else {
			mViewChild = (ViewChild) convertView.getTag();
		}

		// 根据打开或关闭状态 设置图片资源
		if (isExpanded) {
			mViewChild.imageView
					.setImageResource(R.drawable.channel_expandablelistview_top_icon);
		} else {
			mViewChild.imageView
					.setImageResource(R.drawable.channel_expandablelistview_bottom_icon);
		}
		mViewChild.textView.setText(getGroup(groupPosition).toString());
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	ViewChild mViewChild;

	static class ViewChild {
		ImageView imageView;
		TextView textView;
		GridView gridView;
	}
}
