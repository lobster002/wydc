package com.sky.wydc.bean;

import java.io.Serializable;

/**
 * ���ﳵ������
 */
public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;// �������л�����
	private int id;
	private int uid;// ���������û�
	private int ispay;// �Ƿ񸶿�
	private String name;// ��Ʒ������
	private String context;// ��Ʒ������
	private String imageurl;// ͼƬ��ַ
	private String ramark = "";// �ײ��е���Ʒ�������ײͿ���Ϊ��
	private int sum;// ����
	private float price;// ����

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getIspay() {
		return ispay;
	}

	public void setIspay(int ispay) {
		this.ispay = ispay;
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

	public String getRamark() {
		return ramark;
	}

	public void setRamark(String ramark) {
		this.ramark = ramark;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
