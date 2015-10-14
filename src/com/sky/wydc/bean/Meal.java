package com.sky.wydc.bean;

import java.io.Serializable;

/**
 * �ײ�
 */
public class Meal implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;// ��Ʒ������
	private String context;// ��Ʒ����ϸ����
	private String imageurl;// ��ƷͼƬ��ַ
	private float oldprice;// ԭ��
	private float newprice;// �ּ�
	private String remark;// ��עѡ����Щ��Ʒ
	private String dishids;

	public Meal() {
	}

	public Meal(int id, String name, String context, String imageurl,
			float oldprice, float newprice, String remark, String dishids) {
		super();
		this.id = id;
		this.name = name;
		this.context = context;
		this.imageurl = imageurl;
		this.oldprice = oldprice;
		this.newprice = newprice;
		this.remark = remark;
		this.dishids = dishids;
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

	public float getOldprice() {
		return oldprice;
	}

	public void setOldprice(float oldprice) {
		this.oldprice = oldprice;
	}

	public float getNewprice() {
		return newprice;
	}

	public void setNewprice(float newprice) {
		this.newprice = newprice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDishids() {
		return dishids;
	}

	public void setDishids(String dishids) {
		this.dishids = dishids;
	}

}
