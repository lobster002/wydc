package com.sky.wydc.adapter;

import java.util.List;

import com.sky.wydc.R;
import com.sky.wydc.adapter.base.BaseListAdapter;
import com.sky.wydc.adapter.base.ViewHolder;
import com.sky.wydc.bean.Cart;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CartListAdapter extends BaseListAdapter<Cart> {
	// 四个UI组件
	private TextView cart_name;
	private TextView cart_price;
	private TextView cart_sum;
	public CartListAdapter(Context context, List<Cart> list) {
		super(context, list);
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		// 设置每一个视图，获得UI组件，设置显示格�?
		convertView = mInflater.inflate(R.layout.cart_list_item, parent, false);
		cart_name = ViewHolder.get(convertView, R.id.cart_name);
		cart_price = ViewHolder.get(convertView, R.id.cart_price);
		cart_sum = ViewHolder.get(convertView, R.id.cart_sum);
		cart_name.setText(list.get(position).getName());
		cart_price.setText("¥" + list.get(position).getPrice());
		cart_sum.setText("x" + list.get(position).getSum());
		return convertView;
	}

}
