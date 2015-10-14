package com.sky.wydc.ui;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.wydc.R;
import com.sky.wydc.fragment.IndexFragment;
import com.sky.wydc.fragment.MealFragment;
import com.sky.wydc.fragment.MoreFragment;
import com.sky.wydc.fragment.MyFragment;

@SuppressLint("ResourceAsColor")
public class MainActivity extends ActivityBase implements OnClickListener {

	// ��¼BACK����ʱ��
	private long exitTime = 0;

	// �ײ��ĸ�ImageButton
	private ImageButton btn_index;
	private ImageButton btn_meal;
	private ImageButton btn_my;
	private ImageButton btn_more;

	// �ײ��ĸ�TextView
	private TextView tv_index;
	private TextView tv_meal;
	private TextView tv_my;
	private TextView tv_more;

	// �ײ��ĸ�LinearLayout
	private LinearLayout layout_index;
	private LinearLayout layout_meal;
	private LinearLayout layout_my;
	private LinearLayout layout_more;

	// �м���������
	private IndexFragment index_content;
	private MealFragment meal_content;
	private MyFragment my_content;
	private MoreFragment more_content;

	// ��һ�� �����ť������ ���������л�Ч��
	private int LastIndex = 0;

	// ҳ���б�
	private ArrayList<Fragment> fragmentList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
		Select(1);
	}

	// ��ʼ����ͼ���ҵ�����UI���
	private void initView() {
		btn_index = (ImageButton) findViewById(R.id.btn_id_index);
		btn_meal = (ImageButton) findViewById(R.id.btn_id_meal);
		btn_my = (ImageButton) findViewById(R.id.btn_id_my);
		btn_more = (ImageButton) findViewById(R.id.btn_id_more);

		tv_index = (TextView) findViewById(R.id.tv_id_index);
		tv_meal = (TextView) findViewById(R.id.tv_id_meal);
		tv_my = (TextView) findViewById(R.id.tv_id_my);
		tv_more = (TextView) findViewById(R.id.tv_id_more);

		layout_index = (LinearLayout) findViewById(R.id.layout_id_index);
		layout_meal = (LinearLayout) findViewById(R.id.layout_id_meal);
		layout_my = (LinearLayout) findViewById(R.id.layout_id_my);
		layout_more = (LinearLayout) findViewById(R.id.layout_id_more);
	}

	// ��ʼ���¼�
	private void initEvent() {
		// ��Activityʵ����OnClickListener�ӿڣ�����this�ͺ�
		layout_index.setOnClickListener(this);
		layout_meal.setOnClickListener(this);
		layout_my.setOnClickListener(this);
		layout_more.setOnClickListener(this);
	}

	// ˫���˳�
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// ������µļ�Ϊ���ؼ�
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			// ����һ�ΰ���ʱ��������룬����Toast
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "�ٴε���˳�",
						Toast.LENGTH_SHORT).show();
				// ��¼���ΰ���ʱ��
				exitTime = System.currentTimeMillis();
			} else {
				// ����һ�ΰ���ʱ��С������
				// ��Ӧ�ó����ں�̨����
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// ����Fragment���л�����
	private FragmentTransaction obtainFragmentTransaction(int index) {
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		// �����ǰ���µĽ���������һ�ΰ��½����ŵ��ұߣ���ǰ���������Ƴ���������ұ��Ƴ�
		if (index > LastIndex) {
			ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
		} else {
			ft.setCustomAnimations(R.anim.slide_right_in,
					R.anim.slide_right_out);
		}
		return ft;
	}

	// �����е�ͼƬ��������Ϊδѡ��״̬
	private void resImgAndTv() {

		btn_index.setImageResource(R.drawable.tab01_nomal);
		btn_meal.setImageResource(R.drawable.tab02_nomal);
		btn_my.setImageResource(R.drawable.tab03_nomal);
		btn_more.setImageResource(R.drawable.tab04_nomal);
		// ����ͼƬ��ɫΪδѡ��״̬����ɫ
		tv_index.setTextColor(this.getResources().getColor(
				R.color.text_color_nomal));
		tv_meal.setTextColor(this.getResources().getColor(
				R.color.text_color_nomal));
		tv_my.setTextColor(this.getResources().getColor(
				R.color.text_color_nomal));
		tv_more.setTextColor(this.getResources().getColor(
				R.color.text_color_nomal));
	}

	private void Select(int i) {
		// �л�ͼƬ��ѡ��״̬
		// �л�������������
		if (i == LastIndex)
			return;
		resImgAndTv();// ����ѡ��״̬
		// ���Fragment��������
		FragmentTransaction transaction = obtainFragmentTransaction(i);
		HideFragment(transaction);// ���ص�ǰ��ʾ Fragement
		// ��fragment�����ڣ��ȴ���������ʾ��
		// ����ͼ�꣬����Ϊѡ��״̬
		switch (i) {
		case 1:
			if (index_content == null) {
				index_content = new IndexFragment();
				transaction.add(R.id.fragment_content, index_content);
			} else {
				transaction.show(index_content);
			}
			btn_index.setImageResource(R.drawable.tab01_pressed);
			tv_index.setTextColor(this.getResources().getColor(
					R.color.theme_color));
			LastIndex = 1;// ��¼���ν���ı��
			break;
		case 2:
			if (meal_content == null) {
				meal_content = new MealFragment();
				transaction.add(R.id.fragment_content, meal_content);
			} else {
				transaction.show(meal_content);
			}
			btn_meal.setImageResource(R.drawable.tab02_pressed);
			tv_meal.setTextColor(this.getResources().getColor(
					R.color.theme_color));
			LastIndex = 2;
			break;
		case 3:
			if (my_content == null) {
				my_content = new MyFragment();
				transaction.add(R.id.fragment_content, my_content);
			} else {
				transaction.show(my_content);
			}
			btn_my.setImageResource(R.drawable.tab03_pressed);
			tv_my.setTextColor(this.getResources()
					.getColor(R.color.theme_color));
			LastIndex = 3;
			break;
		case 4:
			if (more_content == null) {
				more_content = new MoreFragment();
				transaction.add(R.id.fragment_content, more_content);
			} else {
				transaction.show(more_content);
			}
			btn_more.setImageResource(R.drawable.tab04_pressed);
			tv_more.setTextColor(this.getResources().getColor(
					R.color.theme_color));
			LastIndex = 4;
			break;
		}
		// �����ύ
		transaction.commit();

	}

	// ���Fragment��Ϊ�գ������ص�ǰ��ʾ�Ĵ��ڣ��Ա���һ��������ʾ
	private void HideFragment(FragmentTransaction transaction) {
		if (index_content != null) {
			transaction.hide(index_content);
		}
		if (meal_content != null) {
			transaction.hide(meal_content);
		}
		if (my_content != null) {
			transaction.hide(my_content);
		}
		if (more_content != null) {
			transaction.hide(more_content);
		}
	}

	// ��ȡFragmentManager
	public class MyViewPagerAdapter extends FragmentPagerAdapter {
		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentList.get(position);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_id_index:
			Select(1);
			break;
		case R.id.layout_id_meal:
			Select(2);
			break;
		case R.id.layout_id_my:
			Select(3);
			break;
		case R.id.layout_id_more:
			Select(4);
			break;
		}
	}
}
