package com.sky.wydc.httputils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.sky.wydc.bean.Cart;
import com.sky.wydc.bean.Dishes;
import com.sky.wydc.bean.Meal;

public class JsonUtils {

	/**
	 * 
	 * @param json
	 *            JSON字符串
	 * @param jsonkey
	 *            要返回map中的KEY列表
	 * @return
	 */
	// 将JsonArray字符串解析成ListMap
	public static List<Map<String, Object>> JsonArray2Map(String json,
			List<String> jsonkey) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject obj = jsonArray.getJSONObject(i);// 得到单个JSON对象
				for (String key : jsonkey) {// 遍历KEY,设置MAP
					map.put(key, obj.get(key));
				}
				list.add(map);
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 将JsonArray字符串解析成List<Dishes>
	public static List<Dishes> JsonArray2Dishlist(String json) {

		List<Dishes> list = new ArrayList<Dishes>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				Dishes dishes = new Dishes();
				JSONObject obj = jsonArray.getJSONObject(i);// 得到单个JSON对象
				// 设置对象的属性
				dishes.setId(obj.getInt("Id"));
				dishes.setName(obj.getString("name"));
				dishes.setContext(obj.getString("context"));
				dishes.setImageurl(obj.getString("imageurl"));
				dishes.setPrice((float) obj.getDouble("price"));
				dishes.setFlag(obj.getInt("flag"));
				list.add(dishes);
			}
			return list;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 将JsonArray字符串解析成List<Meal>
	public static List<Meal> JsonArray2Meallist(String json) {
		List<Meal> list = new ArrayList<Meal>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				Meal meal = new Meal();
				JSONObject obj = jsonArray.getJSONObject(i);
				meal.setId(obj.getInt("Id"));
				meal.setName(obj.getString("name"));
				meal.setContext(obj.getString("context"));
				meal.setImageurl(obj.getString("imageurl"));
				meal.setOldprice((float) obj.getDouble("oldprice"));
				meal.setNewprice((float) obj.getDouble("newprice"));
				meal.setDishids(obj.getString("dishids"));
				list.add(meal);
			}
			return list;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 将JsonArray字符串解析成List<Cart>
	public static List<Cart> JsonArray2Cartlist(String json) {

		List<Cart> list = new ArrayList<Cart>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				Cart cart = new Cart();
				JSONObject obj = jsonArray.getJSONObject(i);
				cart.setId(obj.getInt("Id"));
				cart.setName(obj.getString("name"));
				cart.setUid(obj.getInt("userid"));
				cart.setContext(obj.getString("context"));
				cart.setSum(obj.getInt("sum"));
				cart.setPrice((float) obj.getDouble("price"));
				list.add(cart);
			}
			return list;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 将单个json字符串，解析为map
	public static Map<String, Object> JsonObject2Map(String json,
			List<String> jsonkey) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject obj = new JSONObject(json);
			for (String key : jsonkey) {
				map.put(key, obj.get(key));
			}
		} catch (Exception e) {
		}
		return map;
	}

	// 将JsonArray解析成string[]格式
	public static String[] JsonArray2StringArray(String json) {
		List<String> list = new ArrayList<String>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				list.add(array.getString(i));// 获得json数组中单个字符串
			}
			return list.toArray(new String[] {});// 返回值为Sring[]类型
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
