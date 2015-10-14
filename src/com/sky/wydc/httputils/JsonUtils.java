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
	 *            JSON�ַ���
	 * @param jsonkey
	 *            Ҫ����map�е�KEY�б�
	 * @return
	 */
	// ��JsonArray�ַ���������ListMap
	public static List<Map<String, Object>> JsonArray2Map(String json,
			List<String> jsonkey) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject obj = jsonArray.getJSONObject(i);// �õ�����JSON����
				for (String key : jsonkey) {// ����KEY,����MAP
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

	// ��JsonArray�ַ���������List<Dishes>
	public static List<Dishes> JsonArray2Dishlist(String json) {

		List<Dishes> list = new ArrayList<Dishes>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				Dishes dishes = new Dishes();
				JSONObject obj = jsonArray.getJSONObject(i);// �õ�����JSON����
				// ���ö��������
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

	// ��JsonArray�ַ���������List<Meal>
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

	// ��JsonArray�ַ���������List<Cart>
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

	// ������json�ַ���������Ϊmap
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

	// ��JsonArray������string[]��ʽ
	public static String[] JsonArray2StringArray(String json) {
		List<String> list = new ArrayList<String>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				list.add(array.getString(i));// ���json�����е����ַ���
			}
			return list.toArray(new String[] {});// ����ֵΪSring[]����
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
