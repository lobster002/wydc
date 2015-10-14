package com.sky.wydc.bean;

import java.io.Serializable;

/**
 * 单品
 */
public class Dishes implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;// 菜品名称
	private String context;// 菜名的描述，作用
	private String imageurl;// 图片地址
	private float price;// 价格
	private int sum;// 总共购买量
	private int flag;// 分类标记

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
