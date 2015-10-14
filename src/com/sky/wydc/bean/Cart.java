package com.sky.wydc.bean;

import java.io.Serializable;

/**
 * 购物车及订单
 */
public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;// 用于序列化传输
	private int id;
	private int uid;// 用于区分用户
	private int ispay;// 是否付款
	private String name;// 商品的名称
	private String context;// 商品的描述
	private String imageurl;// 图片地址
	private String ramark = "";// 套餐中的商品，不是套餐可以为空
	private int sum;// 数量
	private float price;// 单价

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
