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

	// Group ����
	public String[] group = { "", "", "", "", "", "", "", "" };
	// ���ڱ��� gridView View�� ��ά����
	public String[][] gridViewChild = new String[10][10];
	// ���ڱ���Group��Chil����
	String[][] child = { { "" }, { "" }, { "" }, { "" }, { "" }, { "" },
			{ "" }, { "" }, { "" }, { "" } };
	LayoutInflater mInflater;
	Context context;

	// ��ʼ��
	public ExpandableListViewAdapter(Context context, Meal meal) {
		String res = meal.getDishids().replace(" ", "");
		group = res.split(";");
		mInflater = LayoutInflater.from(context);
		this.context = context;
	}

	// ����ָ��Child����
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return child[groupPosition][childPosition];
	}

	// ����ָ��Child_ID
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	// ����ָ��ChildView
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {// ���Ϊ��
			// ��������
			mViewChild = new ViewChild();
			// ���ز���
			convertView = mInflater.inflate(
					R.layout.channel_expandablelistview_item, null);
			// ȡ�ÿؼ�
			mViewChild.gridView = (GridView) convertView
					.findViewById(R.id.channel_item_child_gridView);
			convertView.setTag(mViewChild);
		} else {
			mViewChild = (ViewChild) convertView.getTag();
		}

		// ΪGroup��gridView����������
		SimpleAdapter mSimpleAdapter = new SimpleAdapter(context,
				setGridViewData(gridViewChild[groupPosition]),
				R.layout.channel_gridview_item,
				new String[] { "channel_gridview_item" },
				new int[] { R.id.channel_gridview_item });
		mViewChild.gridView.setAdapter(mSimpleAdapter);
		setGridViewListener(groupPosition, mViewChild.gridView);
		// ���õ��Ч��
		mViewChild.gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

		return convertView;
	}

	// ����GridView����¼� ����
	private void setGridViewListener(final int pos, final GridView gridView) {
		gridView.setOnItemClickListener(new GridView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (view instanceof TextView) {// ȡ��TextView

					TextView tv = (TextView) view;
					// ������������
					group[pos] = tv.getText().toString();
					// ������ʾ
					ExpandableListViewAdapter.this.notifyDataSetChanged();
				}
			}
		});
	}

	// ��װ���� ��String[] ��װΪArrayList
	private ArrayList<HashMap<String, Object>> setGridViewData(String[] data) {
		ArrayList<HashMap<String, Object>> gridItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < data.length; i++) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("channel_gridview_item", data[i]);
			gridItem.add(hashMap);
		}
		return gridItem;
	}

	// ����ָ����ChildCount
	@Override
	public int getChildrenCount(int groupPosition) {
		return child[groupPosition].length;
	}

	// ����ָ��Group
	@Override
	public Object getGroup(int groupPosition) {
		return group[groupPosition];
	}

	// ����Group����
	@Override
	public int getGroupCount() {
		return group.length;
	}

	// ����ָ��GroupId
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	// ����ָ��GroupView
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// ���Ϊ��
		if (convertView == null) {
			// ��������
			mViewChild = new ViewChild();
			// ���ز���
			convertView = mInflater.inflate(
					R.layout.channel_expandablelistview, null);
			// ȡ�ÿؼ�
			mViewChild.textView = (TextView) convertView
					.findViewById(R.id.channel_group_name);
			mViewChild.imageView = (ImageView) convertView
					.findViewById(R.id.channel_imageview_orientation);
			convertView.setTag(mViewChild);
		} else {
			mViewChild = (ViewChild) convertView.getTag();
		}

		// ���ݴ򿪻�ر�״̬ ����ͼƬ��Դ
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
