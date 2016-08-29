package com.example.coolwheather.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpUtil {
	public static void sendHttpRequest(final String address, final HttpCallbackListener listener){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpsURLConnection connection = null;
				
				try {
					URL url = new URL(address);
					connection = (HttpsURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder respones = new StringBuilder();
					String line;
					while((line = reader.readLine()) != null){
						
						respones.append(line);
					}
					if(listener != null){
						listener.onFinish(respones.toString());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					if(listener != null){
						
						listener.onError(e);
					}
					e.printStackTrace();
				}finally{
					if(connection != null){
						connection.disconnect();
					}
					
				}
			}
		}).start();
	}

}
