package com.sky.wydc.bean;

import java.io.Serializable;

/**
 * ��Ʒ
 */
public class Dishes implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;// ��Ʒ����
	private String context;// ����������������
	private String imageurl;// ͼƬ��ַ
	private float price;// �۸�
	private int sum;// �ܹ�������
	private int flag;// ������

	public Dishes() {
	}

	public Dishes(int id, String name, String context, String imageurl,
			float price, int sum, int flag) {
		super();
		this.id = id;
		this.name = name;
		this.context = context;
		this.imageurl = imageurl;
		this.price = price;
		this.sum = sum;
		this.flag = flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}
