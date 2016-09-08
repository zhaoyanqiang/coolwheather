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
	* 解析和处理服务器返回的省级数据
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

				//JSONArray jsonArray = new JSONArray(jsonData);
				
			    JSONObject jsonObject =new JSONObject(jsonData);
			    
			    JSONArray jsonArrayProvince = jsonObject.getJSONArray("list");
			    for (int i = 0; i < jsonArrayProvince.length(); i++) {
			    	JSONObject jsonObjectProvince = jsonArrayProvince.getJSONObject(i);
			    	String provinceid = jsonObjectProvince.getString("city_id");
			    	String provincename = jsonObjectProvince.getString("name");
			    	JSONArray jsonArrayCity = jsonObjectProvince.getJSONArray("list");
			    	for(int j = 0; j<jsonArrayCity.length(); j++){
				    	JSONObject jsonObjectCity = jsonArrayCity.getJSONObject(j);
				    	String cityid = jsonObjectCity.getString("city_id");
				    	String cityname = jsonObjectCity.getString("name");
    	
				    	if(jsonObjectCity.has("list"))
				    	{

					    	JSONArray jsonArrayCounty = jsonObjectCity.getJSONArray("list");
					    	
					    	for(int k = 0; k<jsonArrayCounty.length(); k++){
						    	JSONObject jsonObjectCounty = jsonArrayCounty.getJSONObject(k);
						    	String countyid = jsonObjectCounty.getString("city_id");
						    	String countyname = jsonObjectCounty.getString("name");
						    	County county = new County();
						    	county.setCityId(i*100+j+1);
						    	county.setCountyCode(countyid);
						    	county.setCountyName(countyname);
						    	county.setId(k+1);
						    	
						    	Log.e("Utility", "cityid :"+(j+1));
						    	Log.e("Utility", "countyname :"+countyname);


						    	coolWeatherDB.saveCounty(county);
					    	}

				    	}


				    	
				    	

				    	
				    	Log.v("Utility", "cityid is " + cityid);
				    	Log.v("Utility", "cityname is " + cityname);
				    	City city = new City();
						city.setCityCode(cityid);
						city.setCityName(cityname);
						city.setProvinceId(i+1);
						city.setId(i*100+j+1);
						//写入数据库
						coolWeatherDB.saveCity(city);
			    		
			    	}
			    	Log.v("Utility", "id is " + provinceid);
			    	Log.v("Utility", "name is " + provincename);

			    	Province province = new Province();
			    	province.setProvinceCode(provinceid);
			    	province.setProvinceName(provincename);
			    	province.setId(i+1);
			    	// 将解析出来的数据存储到Province表
			    	coolWeatherDB.saveProvince(province);
			    	Log.e("Utility", "saveProvince");

			    }

		} catch (JSONException e) {
			// TODO Auto-generated catch block
	    	Log.e("Utility", "JSONException");

			e.printStackTrace();
		}
		
		
	}
	/**
	* 解析和处理服务器返回的市级数据
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
					
					//写入数据库
					coolWeatherDB.saveCity(city);
				}
				
				return true;
			}
		}
		
		return false;
	
	}
	
	/**
	* 解析和处理服务器返回的县级数据
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
					// 将解析出来的数据存储到County表
					coolWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}
	
	
}
