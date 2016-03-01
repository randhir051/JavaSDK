package io.cloudboost;


import javax.xml.ws.soap.Addressing;

import io.cloudboost.util.CloudSocket;
import io.socket.client.Socket;
import io.socket.emitter.Emitter.Listener;
/**
 * An abstract representation of CloudBoost, it manages connection parameters, URLs and authentication 
 * to the CloudBoost server
 * @author new
 *
 */

public class CloudApp {
	protected static String appId;
	protected static String appKey;
	protected static String serverUrl = "https://api.cloudboost.io";
	protected static String serviceUrl = "https://service.cloudboost.io";
	protected static String appUrl = serverUrl+"/api";
	protected static String apiUrl = serverUrl;
	protected static String socketUrl = "https://realtime.cloudboost.io";
	public static String SESSION_ID=null;
	public static String masterKey=null;
	public static String socketIoUrl=null;
	
	/**
	 * gives the App ID to connect to
	 * @return
	 */
	public static String getAppId() {
		return appId;
	}
	
/**
 * returns the authentication key to connect to the App, every App created in cloudboost has an ID
 * and client key
 * @return
 */
	public static String getAppKey() {
		return appKey;
	}
	
/**
 * get the URL for connecting to an App on CloudBoost
 * @return
 */
	public static String getAppUrl() {
		return appUrl;
	}
	
/**
 * URL for connecting to API
 * @return
 */
	public static String getApiUrl(){
		return apiUrl;
	}
	/**
	 * URL for accessing the server, can connect, disconnect the server
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
	

	public static String getSocketUrl(){
		return socketUrl;
	}
	public static void onConnect(){
		CloudSocket.getSocket().on(Socket.EVENT_CONNECT,new Listener() {
			
			@Override
			public void call(Object... args) {
				System.out.println("conneced");
				
			}
		});
	}
	public static void connect(){
		CloudSocket.getSocket().connect();
	}
	public static void disconnect(){
		CloudSocket.getSocket().disconnect();
	}
	public static void onDisconnect(){
		CloudSocket.getSocket().on(Socket.EVENT_DISCONNECT,new Listener() {
			
			@Override
			public void call(Object... args) {
				System.out.println("disconnected");
				
			}
		});
	}

	public static void init(String appId, String appKey) {
		CloudApp.appId = appId;
		CloudApp.appKey = appKey;
		CloudSocket.init(getSocketUrl());
	}	
}
