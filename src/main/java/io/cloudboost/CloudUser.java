package io.cloudboost;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.json.JSONException;
import org.json.JSONObject;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

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
		this.document.put("_type", "user");
	}
	
	private static CloudUser current ;
	private AsyncHttpClient client;  
	
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
		this.document.put("username", username);
		PrivateMethod._isModified(this, "username");
	}
	
	/**
	 * 
	 * @return
	 */
	public String getUserName(){
		return this.document.getString("username");
	}
	
	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password){
		this.document.put("password", password);
		PrivateMethod._isModified(this, "password");
	}
	
	/**
	 * 
	 * @return
	 */
	public String getPassword(){
		return this.document.getString("password");
	}
	
	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email){
		this.document.put("email", email);
		PrivateMethod._isModified(this, "email");
	}
	
	/**
	 * 
	 * @return
	 */
	public String getEmail(){
		return this.document.getString("email");
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
		
		if(this.document.get("username") == null){
			throw new CloudException("Username is not set");
		}
		if(this.document.get("password") == null){
			throw new CloudException("Password is not set");
		}
		if(this.document.get("email") == null){
			throw new CloudException("Email is not set");
		}
		
		CloudUser thisObj;
		JSONObject data  = new JSONObject();
		thisObj = this;
		data.put("document", document);		
		data.put("key", CloudApp.getAppKey());
		
		client = new AsyncHttpClient();
		String url = CloudApp.getApiUrl()+"/user/"+CloudApp.getAppId()+"/signup";
	
		Future<Response> f = client.preparePost(url).addHeader("sessionId", PrivateMethod._getSessionId()).addHeader("Content-type", "application/json").setBody(data.toString()).execute();
		try {
			if(f.get().getHeader("sessionId") != null){
				PrivateMethod._setSessionId(f.get().getHeader("sessionId"));
			}else{
				PrivateMethod._deleteSessionId();
			}
			if(f.get().getStatusCode() == 200){
				
				JSONObject body = new JSONObject(f.get().getResponseBody());
				thisObj.document = body;
				current = thisObj;
				callbackObject.done(thisObj, null);
			}else{
				CloudException e = new CloudException(f.get().getResponseBody());
				callbackObject.done(null, e);
			}
		} catch (JSONException | InterruptedException | ExecutionException | IOException e) {
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
		
		client = new AsyncHttpClient();
		String url = CloudApp.getApiUrl()+"/user/"+CloudApp.getAppId()+"/login";
		
		Future<Response> f = client.preparePost(url).addHeader("sessionId", PrivateMethod._getSessionId()).addHeader("Content-type", "application/json").setBody(data.toString()).execute();
		
		try {
			if(f.get().getHeader("sessionId") != null){
				PrivateMethod._setSessionId(f.get().getHeader("sessionId"));
			}else{
				PrivateMethod._deleteSessionId();
			}
			if(f.get().getStatusCode() == 200){
				
				JSONObject body = new JSONObject(f.get().getResponseBody());
				thisObj.document = body;
				current = thisObj;
				callbackObject.done(thisObj, null);
			}else{
				CloudException e = new CloudException(f.get().getResponseBody());
				callbackObject.done(null, e);
			}
		} catch (JSONException | InterruptedException | ExecutionException | IOException e) {
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
		
		client = new AsyncHttpClient();
		String url = CloudApp.getApiUrl()+"/user/"+CloudApp.getAppId()+"/logout";
		
		Future<Response> f = client.preparePost(url).addHeader("sessionId", PrivateMethod._getSessionId()).addHeader("Content-type", "application/json").setBody(data.toString()).execute();
		
		try {
			if(f.get().getHeader("sessionId") != null){
				PrivateMethod._setSessionId(f.get().getHeader("sessionId"));
			}else{
				PrivateMethod._deleteSessionId();
			}
			if(f.get().getStatusCode() == 200){
				System.out.println("Logout :: "+ f.get().getResponseBody());
				JSONObject body = new JSONObject(f.get().getResponseBody());
				thisObj.document = body;
				
				current = null;
				callbackObject.done(thisObj, null);
			}else{
				CloudException e = new CloudException(f.get().getResponseBody());
				callbackObject.done(null, e);
			}
		} catch (JSONException | InterruptedException | ExecutionException | IOException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done(null, e1);
			e.printStackTrace();
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
		
		data.put("document", document);		
		data.put("user", thisObj.document);
		data.put("role", role.document);
		data.put("key", CloudApp.getAppKey());
		
		client = new AsyncHttpClient();
		String url = CloudApp.getApiUrl()+"/user/"+CloudApp.getAppId()+"/addToRole";
		
		Future<Response> f = client.preparePut(url).addHeader("sessionId", PrivateMethod._getSessionId()).addHeader("Content-type", "application/json").setBody(data.toString()).execute();
		
		try {
			if(f.get().getHeader("sessionId") != null){
				PrivateMethod._setSessionId(f.get().getHeader("sessionId"));
			}else{
				PrivateMethod._deleteSessionId();
			}
			if(f.get().getStatusCode() == 200){
				
				JSONObject body = new JSONObject(f.get().getResponseBody());
				thisObj.document = body;
				
				current = null;
				callbackObject.done(thisObj, null);
			}else{
				CloudException e = new CloudException(f.get().getResponseBody());
				callbackObject.done(null, e);
			}
		} catch (JSONException | InterruptedException | ExecutionException | IOException e){
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
		
		ArrayList<String> roles = (ArrayList<String>) this.document.get("roles");
		
		if(roles.contains(role.document.get("_id"))){
			return true;
		}else{
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
		
		data.put("document", document);		
		data.put("user", thisObj.document);
		data.put("role", role.document);
		data.put("key", CloudApp.getAppKey());
		
		client = new AsyncHttpClient();
		String url = CloudApp.getApiUrl()+"/user/"+CloudApp.getAppId()+"/removeFromRole";
		
		Future<Response> f = client.preparePut(url).addHeader("sessionId", PrivateMethod._getSessionId()).addHeader("Content-type", "application/json").setBody(data.toString()).execute();
		
		try {
			if(f.get().getHeader("sessionId") != null){
				PrivateMethod._setSessionId(f.get().getHeader("sessionId"));
			}else{
				PrivateMethod._deleteSessionId();
			}
			
			if(f.get().getStatusCode() == 200){
				
				JSONObject body = new JSONObject(f.get().getResponseBody());
				thisObj.document = body;
				
				current = null;
				callbackObject.done(thisObj, null);
			}else{
				CloudException e = new CloudException(f.get().getResponseBody());
				callbackObject.done(null, e);
			}
		} catch (JSONException | InterruptedException | ExecutionException | IOException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done(null, e1);
			e.printStackTrace();
			e.printStackTrace();
		}
		
	}
}