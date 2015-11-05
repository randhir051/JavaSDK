package io.cloudboost;


import io.cloudboost.util.CloudSocket;
//import io.cloudboost.util.SqlLite;
/**
 * @author cloudboost
 *
 */


public class CloudApp {
	private static String appId;
	private static String appKey;
	private static String serverUrl = "https://api.cloudboost.io";
	private static String serviceUrl = "https://service.cloudboost.io";
//	private static String serviceUrl = "http://localhost:3000";
//	private static String serverUrl = "http://localhost:4730";
	private static String appUrl = serverUrl+"/api";
	private static String apiUrl = serverUrl;
	private static String socketUrl = serverUrl;
	public static String SESSION_ID=null;
	
	/**
	 * 
	 * @return
	 */
	public static String getAppId() {
		return appId;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getAppKey() {
		return appKey;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getAppUrl() {
		return appUrl;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getApiUrl(){
		return apiUrl;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getServerUrl() {
		return serverUrl;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getServiceUrl(){
		return serviceUrl;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getSocketUrl(){
		return socketUrl;
	}
	
	/**
	 * 
	 * App Initialization
	 * 
	 * @param appId
	 * @param appKey
	 */
	public static void init(String appId, String appKey) {
		CloudApp.appId = appId;
		CloudApp.appKey = appKey;
//		SqlLite.init();
		CloudSocket.init(getSocketUrl());
	}	
}
