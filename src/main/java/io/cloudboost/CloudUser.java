package io.cloudboost;

import io.cloudboost.beans.CBResponse;
import io.cloudboost.json.JSONArray;
import io.cloudboost.json.JSONException;
import io.cloudboost.json.JSONObject;
import io.cloudboost.util.CBParser;

import java.util.ArrayList;

/**
 * 
 * @author cloudboost
 *
 */
public class CloudUser extends CloudObject{
	
	/**
	 * Constructor
	 */
	public CloudUser(){
		super("User");
		try {
			this.document.put("_type", "user");
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}
	
	private static CloudUser current ;
	
	/**
	 * 
	 * @param user
	 */
	public static void setCurrentUser(CloudUser user){
		current = user;
	}
	
	/**
	 * 
	 * @return
	 */
	public static CloudUser getcurrentUser(){
		return current;
	}
	/**
	 * 
	 * @param username
	 */
	public void setUserName(String username){
		try {
			this.document.put("username", username);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		PrivateMethod._isModified(this, "username");
	}
	
	/**
	 * 
	 * @return
	 */
	public String getUserName(){
		try {
			return this.document.getString("username");
		} catch (JSONException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password){
		try {
			this.document.put("password", password);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		PrivateMethod._isModified(this, "password");
	}
	
	/**
	 * 
	 * @return
	 */
	public String getPassword(){
		try {
			return this.document.getString("password");
		} catch (JSONException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email){
		try {
			this.document.put("email", email);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		PrivateMethod._isModified(this, "email");
	}
	
	/**
	 * 
	 * @return
	 */
	public String getEmail(){
		try {
			return this.document.getString("email");
		} catch (JSONException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * Sign Up
	 * 
	 * @param callbackObject
	 * @throws CloudException
	 */
	public void signUp(CloudUserCallback callbackObject) throws CloudException{
		
		if(CloudApp.getAppId() == null){
			throw new CloudException("App Id is null");
		}
		CloudUser thisObj=null;
		JSONObject data=null ;
		try{
		if(this.document.get("username") == null){
			throw new CloudException("Username is not set");
		}
		if(this.document.get("password") == null){
			throw new CloudException("Password is not set");
		}
		if(this.document.get("email") == null){
			throw new CloudException("Email is not set");
		}
		
		
		 data  = new JSONObject();
		thisObj = this;
		data.put("document", document);		
		data.put("key", CloudApp.getAppKey());
		String url = CloudApp.getApiUrl()+"/user/"+CloudApp.getAppId()+"/signup";
		CBResponse response=CBParser.callJson(url, "POST", data);
		if(response.getStatusCode() == 200){
				
				JSONObject body = new JSONObject(response.getResponseBody());
				thisObj.document = body;
				current = thisObj;
				callbackObject.done(thisObj, null);
			}else{
				CloudException e = new CloudException(response.getResponseBody());
				callbackObject.done(null, e);
			}
		} catch (JSONException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done(null, e1);
			e.printStackTrace();
		}		
	}
	
	/**
	 * 
	 * Log In
	 * 
	 * @param callbackObject
	 * @throws CloudException
	 */
	public void logIn(CloudUserCallback callbackObject) throws CloudException{
		if(CloudApp.getAppId() == null){
			throw new CloudException("App Id is null");
		}
		try{
		if(this.document.get("username") == null){
			throw new CloudException("Username is not set");
		}
		
		if(this.document.get("password") == null){
			throw new CloudException("Password is not set");
		}
		
		CloudUser thisObj;
		JSONObject data  = new JSONObject();
		thisObj = this;
		data.put("document", document);		
		data.put("key", CloudApp.getAppKey());

		String url = CloudApp.getApiUrl()+"/user/"+CloudApp.getAppId()+"/login";

		CBResponse response=CBParser.callJson(url, "POST", data);
		if(response.getStatusCode() == 200){
				
				JSONObject body = new JSONObject(response.getResponseBody());
				thisObj.document = body;
				current = thisObj;
				callbackObject.done(thisObj, null);
			}else{
				CloudException e = new CloudException(response.getResponseBody());
				callbackObject.done(null, e);
			}
		} catch (JSONException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done(null, e1);
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Log Out
	 * 
	 * @param callbackObject
	 * @throws CloudException
	 */
	public void logOut(CloudUserCallback callbackObject) throws CloudException{
		try {
		if(CloudApp.getAppId() == null){
			throw new CloudException("App Id is null");
		}
		
		if(this.document.get("username") == null){
			throw new CloudException("Username is not set");
		}
		
		if(this.document.get("password") == null){
			throw new CloudException("Password is not set");
		}
		
		CloudUser thisObj;
		JSONObject data  = new JSONObject();
		thisObj = this;
		data.put("document", document);		
		data.put("key", CloudApp.getAppKey());
		
		String url = CloudApp.getApiUrl()+"/user/"+CloudApp.getAppId()+"/logout";
		CBResponse response=CBParser.callJson(url, "POST", data);
		response.toString();
		if(response.getStatusCode() == 200){
				JSONObject body = new JSONObject(response.getResponseBody());
				thisObj.document = body;
				
				current = null;
				callbackObject.done(thisObj, null);
			}else{
				CloudException e = new CloudException(response.getResponseBody());
				callbackObject.done(null, e);
			}
		} catch (JSONException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done(null, e1);
		}
	}
	
	/**
	 * 
	 * Add To Role
	 * 
	 * @param role
	 * @param callbackObject
	 * @throws CloudException
	 */
	public void addToRole(CloudRole role, CloudUserCallback callbackObject) throws CloudException{
		if(role == null ){
			throw new CloudException("role is null");
		}
		
		CloudUser thisObj;
		JSONObject data  = new JSONObject();
		thisObj = this;
		try {
		data.put("document", document);		
		data.put("user", thisObj.document);
		data.put("role", role.document);
		
			data.put("key", CloudApp.getAppKey());
		
		String url = CloudApp.getApiUrl()+"/user/"+CloudApp.getAppId()+"/addToRole";
		
		CBResponse response=CBParser.callJson(url, "PUT", data);
		if(response.getStatusCode() == 200){
				
				JSONObject body = new JSONObject(response.getResponseBody());
				thisObj.document = body;
				
				current = null;
				callbackObject.done(thisObj, null);
			}else{
				CloudException e = new CloudException(response.getResponseBody());
				callbackObject.done(null, e);
			}
		} catch (JSONException e){
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done(null, e1);
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean isInRole(CloudRole role) throws CloudException{
		if(role == null){
			throw new CloudException("role is null");
		}
		try{
		ArrayList<String> roles = (ArrayList<String>) this.document.get("roles");
		
		if(roles.contains(role.document.get("_id"))){
			return true;
		}else{
			return false;
		}} catch (JSONException e2) {
			
			e2.printStackTrace();
			return false;
		}
		
	}
	
	public void removeFromRole(CloudRole role, CloudUserCallback callbackObject) throws CloudException{
		if(role == null){
			throw new CloudException("role is null");
		}
		
		CloudUser thisObj;
		JSONObject data  = new JSONObject();
		thisObj = this;
		
		try {
			data.put("document", document);
			
		data.put("user", thisObj.document);
		data.put("role", role.document);
		data.put("key", CloudApp.getAppKey());
		String url = CloudApp.getApiUrl()+"/user/"+CloudApp.getAppId()+"/removeFromRole";
		
		CBResponse response=CBParser.callJson(url, "PUT", data);
		if(response.getStatusCode() == 200){
			String resp=response.getResponseBody();
			JSONArray arr=null;
			if(resp.charAt(0)=='[')
			arr=new JSONArray(resp);
			else arr=new JSONArray("["+resp+"]");
				JSONObject body = arr.getJSONObject(0);
				thisObj.document = body;
				
				current = null;
				callbackObject.done(thisObj, null);
			}else{
				CloudException e = new CloudException(response.getResponseBody());
				callbackObject.done(null, e);
			}
		} catch (JSONException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done(null, e1);
			e.printStackTrace();
			e.printStackTrace();
		}
		
	}
}