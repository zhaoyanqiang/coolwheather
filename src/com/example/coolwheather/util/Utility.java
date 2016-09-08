package com.example.coolwheather.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

import com.example.coolwheather.model.City;
import com.example.coolwheather.model.CoolWheatherDB;
import com.example.coolwheather.model.County;
import com.example.coolwheather.model.Province;

public class Utility {
	/**
	* �����ʹ�����������ص�ʡ������
	*/
	public synchronized static boolean handleProvincesResponse(CoolWheatherDB coolWeatherDB, String response) {
		if (!TextUtils.isEmpty(response)) {
			
			
			parseJSONWithJSONObject(coolWeatherDB,response);
			return true;
		}
		
		return false;
	}
	
	private static void parseJSONWithJSONObject(CoolWheatherDB coolWeatherDB,String jsonData){
		
		try {
			Log.d("Utility", "json is " + jsonData);

				//JSONArray jsonArray = new JSONArray(jsonData);
				
			    JSONObject jsonObject2 =new JSONObject(jsonData);
			    
			    JSONArray jsonArray = jsonObject2.getJSONArray("list");
			    for (int i = 0; i < jsonArray.length(); i++) {
			    	JSONObject jsonObject = jsonArray.getJSONObject(i);
			    	String id = jsonObject.getString("city_id");
			    	String name = jsonObject.getString("name");
			    	Log.d("Utility", "id is " + id);
			    	Log.d("Utility", "name is " + name);

			    	Province province = new Province();
			    	province.setProvinceCode(id);
			    	province.setProvinceName(name);
			    	// ���������������ݴ洢��Province��
			    	coolWeatherDB.saveProvince(province);

			    }

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/**
	* �����ʹ�����������ص��м�����
	*/
	public static boolean handleCitiesResponse(CoolWheatherDB coolWeatherDB,String response, int provinceId) {
		if(!TextUtils.isEmpty(response)){
			String[] allCities = response.split(",");
			if(allCities != null && allCities.length>0){
				for(String c : allCities){
					String[] array = c.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					
					//д�����ݿ�
					coolWeatherDB.saveCity(city);
				}
				
				return true;
			}
		}
		
		return false;
	
	}
	
	/**
	* �����ʹ�����������ص��ؼ�����
	*/
	public static boolean handleCountiesResponse(CoolWheatherDB coolWeatherDB,String response, int cityId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCounties = response.split(",");
			if (allCounties != null && allCounties.length > 0) {
				for (String c : allCounties) {
					String[] array = c.split("\\|");
					County county = new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityId(cityId);
					// ���������������ݴ洢��County��
					coolWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}
	
	
}
