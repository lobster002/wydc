package com.sky.wydc.fragment;

import com.sky.wydc.R;
import com.sky.wydc.bean.User;
import com.sky.wydc.config.Config;
import com.sky.wydc.ui.CartActivity;
import com.sky.wydc.ui.HistoryCartActivity;
import com.sky.wydc.ui.UserInfoActivity;
import com.sky.wydc.utils.CurrentUserUitl;
import com.sky.wydc.view.RoundImageView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyFragment extends FragmentBase implements OnClickListener {

	private LinearLayout gerenzhongxin = null;// 个人中心
	private LinearLayout gouwuche = null; // 购物车
	private LinearLayout yizhifu = null; // 已支付
	private LinearLayout qianbao = null; // 钱包
	private RoundImageView headPicture = null; // 头像
	private TextView nickname; // 用户名
	private User user; // 当前登录的用户

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*
		 * 死格式 Fragment必要的方法
		 */
		return inflater.inflate(R.layout.fragment_my, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		// 对自定义的布局进行初始化 布局加載完成后執行
		init();
	}

	private void init() {
		// 通过findViewById的方法 取得xml布局文件中的全部控件
		user = CurrentUserUitl.getCurUser(mApplication);
		gerenzhongxin = (LinearLayout) findViewById(R.id.gerenzhongxin);
		gouwuche = (LinearLayout) findViewById(R.id.gouwuche);
		yizhifu = (LinearLayout) findViewById(R.id.yizhifu);
		qianbao = (LinearLayout) findViewById(R.id.qianbao);
		nickname = (TextView) findViewById(R.id.nickname);

		/*
		 * 对各控件设计 点击（click）监听 （onclickListener） Listener（）里边的代码 为点击是执行的操作 因为
		 * MyFragment 继承OnClickListener 接口 所以 他本身也是一个OnClickListener 所以这里 直接写
		 * this
		 */
		gouwuche.setOnClickListener(this);
		yizhifu.setOnClickListener(this);
		qianbao.setOnClickListener(this);
		gerenzhongxin.setOnClickListener(this);
		headPicture = (RoundImageView) findViewById(R.id.headPicture);

		/*
		 * headPicture本身继承 SmartImageView 是一个第三方的开源控件 可以通过url直接从网上下载图片显示在本地
		 */
		headPicture.setImageUrl(Config.IP_ADDRESS + "/" + user.getAvatar());
		nickname.setText(user.getNickname());
	}

	@Override
	public void onClick(View v) {
		/*
		 * 通过getId（）方法 获得触发该事件的View的Id 通过ID区分控件 执行不同的代码
		 */
		switch (v.getId()) {
		case R.id.yizhifu:
			// FragmentBase中自行封装的方法
			// 跳转到HistoryCartActivity（已支付）界面
			startAnimActivity(HistoryCartActivity.class);
			break;
		case R.id.gouwuche:
			// 跳转到购物车界面
			startAnimActivity(CartActivity.class);
			break;
		case R.id.gerenzhongxin:
			// 跳转到个人中心
			startAnimActivity(UserInfoActivity.class);
			break;
		case R.id.qianbao:
			// 在FragmentBase内自行封装的方法 这里直接使用
			// 在屏幕上显示括号内的内容
			ShowToast("相关功能正等待审核，暂未开放，请耐心等待");
			break;
		}
	}
}
