package com.sky.wydc.adapter;

import java.util.List;
import com.sky.wydc.R;
import com.sky.wydc.adapter.base.BaseListAdapter;
import com.sky.wydc.adapter.base.ViewHolder;
import com.sky.wydc.bean.Meal;
import com.sky.wydc.config.Config;
import com.sky.wydc.view.RoundRectImageView;
import android.content.Context;
import android.graphics.Paint;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MealListAdapter extends BaseListAdapter<Meal> {
	// 定义meal_list_item中的组件
	private RoundRectImageView meal_img;
	private TextView meal_name;
	private TextView meal_content;
	private TextView meal_old_price;
	private TextView meal_new_price;

	public MealListAdapter(Context context, List<Meal> list) {
		super(context, list);
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		// 创建item视图对象
		convertView = mInflater.inflate(R.layout.meal_list_item, parent, false);

		meal_img = ViewHolder.get(convertView, R.id.meal_img);
		meal_name = ViewHolder.get(convertView, R.id.meal_name);
		meal_content = ViewHolder.get(convertView, R.id.meal_content);
		meal_old_price = ViewHolder.get(convertView, R.id.meal_old_price);
		meal_new_price = ViewHolder.get(convertView, R.id.meal_new_price);

		{
			meal_img.setImageUrl(Config.IP_ADDRESS + "/"
					+ list.get(position).getImageurl());
			meal_name.setText(list.get(position).getName());
			meal_content.setText(list.get(position).getContext());

			String oldprice = "¥" + list.get(position).getOldprice() + "";
			oldprice += "<font color='#a29686'>�?</font>";

			meal_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			meal_old_price.setText(Html.fromHtml(oldprice));

			String newprice = "¥" + list.get(position).getNewprice() + "";
			newprice += "<font color='#a29686'>�?</font>";
			meal_new_price.setText(Html.fromHtml(newprice));

		}
		// 返回每一个Item
		return convertView;
	}
}
