package io.cloudboost;


import io.cloudboost.util.CloudSocket;
//import io.cloudboost.util.SqlLite;


public class CloudApp {
	private static String appId;
	private static String appKey;
	private static String serverUrl = "https://api.cloudboost.io";
	private static String serviceUrl = "https://service.cloudboost.io";
//	private static String serviceUrl = "http://localhost:3000";
//	private static String serverUrl = "http://localhost:4730";
	private static String appUrl = serverUrl+"/api";
	private static String apiUrl = serverUrl;
	private static String socketUrl = "https://realtime.cloudboost.io";
	public static String SESSION_ID=null;
	
	
	public static String getAppId() {
		return appId;
	}
	

	public static String getAppKey() {
		return appKey;
	}
	

	public static String getAppUrl() {
		return appUrl;
	}
	

	public static String getApiUrl(){
		return apiUrl;
	}
	

	public static String getServerUrl() {
		return serverUrl;
	}

	public static String getServiceUrl(){
		return serviceUrl;
	}
	

	public static String getSocketUrl(){
		return socketUrl;
	}

	public static void init(String appId, String appKey) {
		CloudApp.appId = appId;
		CloudApp.appKey = appKey;
//		SqlLite.init();
		CloudSocket.init(getSocketUrl());
	}	
}
