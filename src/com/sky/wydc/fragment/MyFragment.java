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

	private LinearLayout gerenzhongxin = null;// ��������
	private LinearLayout gouwuche = null; // ���ﳵ
	private LinearLayout yizhifu = null; // ��֧��
	private LinearLayout qianbao = null; // Ǯ��
	private RoundImageView headPicture = null; // ͷ��
	private TextView nickname; // �û���
	private User user; // ��ǰ��¼���û�

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*
		 * ����ʽ Fragment��Ҫ�ķ���
		 */
		return inflater.inflate(R.layout.fragment_my, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		// ���Զ���Ĳ��ֽ��г�ʼ�� ���ּ��d��ɺ����
		init();
	}

	private void init() {
		// ͨ��findViewById�ķ��� ȡ��xml�����ļ��е�ȫ���ؼ�
		user = CurrentUserUitl.getCurUser(mApplication);
		gerenzhongxin = (LinearLayout) findViewById(R.id.gerenzhongxin);
		gouwuche = (LinearLayout) findViewById(R.id.gouwuche);
		yizhifu = (LinearLayout) findViewById(R.id.yizhifu);
		qianbao = (LinearLayout) findViewById(R.id.qianbao);
		nickname = (TextView) findViewById(R.id.nickname);

		/*
		 * �Ը��ؼ���� �����click������ ��onclickListener�� Listener������ߵĴ��� Ϊ�����ִ�еĲ��� ��Ϊ
		 * MyFragment �̳�OnClickListener �ӿ� ���� ������Ҳ��һ��OnClickListener �������� ֱ��д
		 * this
		 */
		gouwuche.setOnClickListener(this);
		yizhifu.setOnClickListener(this);
		qianbao.setOnClickListener(this);
		gerenzhongxin.setOnClickListener(this);
		headPicture = (RoundImageView) findViewById(R.id.headPicture);

		/*
		 * headPicture����̳� SmartImageView ��һ���������Ŀ�Դ�ؼ� ����ͨ��urlֱ�Ӵ���������ͼƬ��ʾ�ڱ���
		 */
		headPicture.setImageUrl(Config.IP_ADDRESS + "/" + user.getAvatar());
		nickname.setText(user.getNickname());
	}

	@Override
	public void onClick(View v) {
		/*
		 * ͨ��getId�������� ��ô������¼���View��Id ͨ��ID���ֿؼ� ִ�в�ͬ�Ĵ���
		 */
		switch (v.getId()) {
		case R.id.yizhifu:
			// FragmentBase�����з�װ�ķ���
			// ��ת��HistoryCartActivity����֧��������
			startAnimActivity(HistoryCartActivity.class);
			break;
		case R.id.gouwuche:
			// ��ת�����ﳵ����
			startAnimActivity(CartActivity.class);
			break;
		case R.id.gerenzhongxin:
			// ��ת����������
			startAnimActivity(UserInfoActivity.class);
			break;
		case R.id.qianbao:
			// ��FragmentBase�����з�װ�ķ��� ����ֱ��ʹ��
			// ����Ļ����ʾ�����ڵ�����
			ShowToast("��ع������ȴ���ˣ���δ���ţ������ĵȴ�");
			break;
		}
	}
}
