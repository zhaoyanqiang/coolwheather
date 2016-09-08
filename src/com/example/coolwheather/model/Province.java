package com.example.coolwheather.model;

import android.util.Log;

public class Province {
	private int id;
	private String provinceName;
	private String provinceCode;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProvinceName() {
		Log.d("Province", "get provinceName:"+provinceName);

		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
		Log.d("Province", "set provinceName:"+provinceName);
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

}
