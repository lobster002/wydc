package com.sky.wydc.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sky.wydc.R;
import com.sky.wydc.adapter.base.BaseListAdapter;
import com.sky.wydc.adapter.base.ViewHolder;
import com.sky.wydc.bean.Dishes;
import com.sky.wydc.config.Config;
import com.sky.wydc.ui.ShowActivity;
import com.sky.wydc.utils.ImageLoader;
import com.sky.wydc.view.RoundRectImageView;

public class DishesListAdapter extends BaseListAdapter<Dishes> {

	// 定义dishes_list_item中的组件
	private RoundRectImageView dishes_img;
	private TextView dishes_name;
	private TextView dishes_content;
	private TextView dishes_price;
	private TextView dishes_sum;
	private List<Dishes> AllList = null;
	private List<Dishes> selectList = new ArrayList<Dishes>();

	public void setFlag(int flag) {

		if (flag == 0) {
			this.list = AllList;
		} else {
			selectList.clear();
			for (int i = 0; i < AllList.size(); i++) {
				if (flag == AllList.get(i).getFlag()) {
					selectList.add(AllList.get(i));
				}
			}
			this.list = selectList;
		}
	}

	public void setAllsource(List<Dishes> list) {
		AllList = list;
	}

	public DishesListAdapter(Context context, List<Dishes> list) {
		super(context, list);
		this.AllList = list;
	}

	@Override
	public View bindView(final int position, View convertView, ViewGroup parent) {
		// 创建item视图对象
		convertView = mInflater.inflate(R.layout.dishes_list_item, parent,
				false);
		// 获取组件对象，�?�过自定义ViewHolder优化获得
		dishes_img = ViewHolder.get(convertView, R.id.dish_img);
		dishes_name = ViewHolder.get(convertView, R.id.dish_name);
		dishes_content = ViewHolder.get(convertView, R.id.dish_content);
		dishes_price = ViewHolder.get(convertView, R.id.dish_price);
		dishes_sum = ViewHolder.get(convertView, R.id.dish_sum);

		// dishes_img.setImageUrl(Config.IP_ADDRESS + "/"
		// + list.get(position).getImageurl());
		String url = Config.IP_ADDRESS + "/" + list.get(position).getImageurl();
		dishes_img.setTag(url);
		ImageLoader.getInstance().showImageByAsynctask(dishes_img, url);
		dishes_name.setText(list.get(position).getName());
		dishes_content.setText(list.get(position).getContext());
		String html = "¥" + list.get(position).getPrice() + "";
		html += "<font color='#a29686'>�?</font>";
		dishes_price.setText(Html.fromHtml(html));// Html.fromHtml()将普通文字�?�转换为带HTML标记语言的�??
		dishes_sum.setText(Html.fromHtml("<font color='#a29686'>总销�?</font>"
				+ (list.get(position).getSum() + new Random().nextInt(200))));

		// 定义图片的点击事�?
		dishes_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("url",
						Config.IP_ADDRESS + "/"
								+ list.get(position).getImageurl());
				intent.setClass(mContext, ShowActivity.class);
				mContext.startActivity(intent);
			}
		});
		// 返回每一个Item的视�?
		return convertView;
	}
}
