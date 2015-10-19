package io.cloudboost;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

/**
 * 
 * @author cloudboost
 *
 */
public class CloudTable{
	
	protected JSONObject document;
	private static AsyncHttpClient client;
	
	public CloudTable(String tableName){
		if(!PrivateMethod._tableValidation(tableName)){
			try {
				throw new CloudException("Invalid Table Name");
			} catch (CloudException e) {
				e.printStackTrace();
			}
		}
		
		this.document = new JSONObject();
		this.document.put("name", tableName);
		this.document.put("appId", CloudApp.getAppId());
		this.document.put("_type", "table");
		if(tableName.toLowerCase() == "user"){
			this.document.put("type", "user");
			this.document.put("maxCount", 1);
		}else if(tableName.toLowerCase() == "role"){
			this.document.put("type", "role");
			this.document.put("maxCount", 1);
		}else{
			this.document.put("type", "custom");
			this.document.put("maxCount", 9999);
		}
		
		this.document.put("columns", PrivateMethod._defaultColumns(this.document.getString("type")));
		
	}
	
	
	/***
	 * 
	 * @param tableName
	 */
	public void setTableName(String tableName){
		this.document.put("name", tableName);
	}
	
	public String getTableName(){
		return this.document.getString("name");
	}
	
	String getTableType(){
		return this.document.getString("type");
	}
	
	
	/**
	 * 
	 * CloudTable Methods
	 * @throws CloudException 
	 * 
	 */
	
	/**
	 * 
	 * Add Column
	 * @param column
	 * @param table
	 * @throws CloudException
	 */
	public void addColumn(Column column) throws CloudException{
		if(!PrivateMethod._columnValidation(column, this)){
			throw new CloudException("Invalid Column Found, Do Not Use Reserved Column Names");
		}
		
		JSONArray columnList = new JSONArray( this.document.get("columns").toString());
		columnList.put(column.document);
		this.document.put("columns", columnList);
	}
	
	/**
	 * 
	 * @param column
	 * @param table
	 * @throws CloudException
	 */
	
	public void addColumn(Column[] column) throws CloudException{
		for(int i=0; i<column.length; i++){
			this.addColumn(column[i]);
		}
	}
	
	/**
	 * 
	 * Delete Column
	 * 
	 * @param column
	 * @throws CloudException
	 */
	public void deleteColumn(Column column) throws CloudException{
		if(!PrivateMethod._columnValidation(column, this)){
			throw new CloudException("Can Not Delete a Reserved Column");
		}else{
			JSONArray col = new JSONArray( this.document.get("columns").toString());
			for(int i=0; i<col.length(); i++){
				if(col.getJSONObject(i).get("name") == column.document.get("name")){
					col.remove(i);
					break;
				}
			}
			this.document.put("columns", col);
		}
	}
	
	/**
	 * 
	 * @param column
	 * @throws CloudException
	 */
	public void deleteColumn(Column[] column) throws CloudException{
		for(int i=0; i<column.length; i++){
			this.deleteColumn(column[i]);
		}
	}
	
	/**
	 * 
	 * Get All Table of an App
	 * 
	 * @param callbackObject
	 * @throws CloudException
	 */
	public static void getAll(CloudTableArrayCallback callbackObject) throws CloudException{
		if(CloudApp.getAppId() == null){
			throw new CloudException("App Id is missing");
		}
		
		JSONObject params = new JSONObject();
		params.put("key", CloudApp.getAppKey());
		String url = CloudApp.getServiceUrl()+"/"+CloudApp.getAppId()+"/table";
		System.out.println("url: "+url);
		System.out.println(params);
		client = new AsyncHttpClient();
		Future<Response> f = client.preparePost(url).addHeader("sessionId", PrivateMethod._getSessionId()).addHeader("Content-type", "application/json").setBody(params.toString()).execute();
		try {
			if(f.get().getHeader("sessionId") != null){
				PrivateMethod._setSessionId(f.get().getHeader("sessionId"));
			}else{
				PrivateMethod._deleteSessionId();
			}
			if(f.get().getStatusCode() == 200){
				System.out.println(f.get().getResponseBody());
				JSONArray body = new JSONArray(f.get().getResponseBody());
				CloudTable[] object = new CloudTable[body.length()];
				
				for(int i=0; i<object.length; i++){
					object[i] = new CloudTable(body.getJSONObject(i).get("name").toString());
					object[i].document = body.getJSONObject(i);
				}
				callbackObject.done(object, null);
				
			}else{
				
				CloudException e = new CloudException(f.get().getResponseBody());
				callbackObject.done((CloudTable[])null, e);
			}
			
		} catch (InterruptedException | ExecutionException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done((CloudTable[])null, e1);
			e.printStackTrace();
		} catch (JSONException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done((CloudTable[])null, e1);
			e.printStackTrace();
		} catch (IOException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done((CloudTable[])null, e1);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * Get a Table form an App
	 * 
	 * @param callbackObject
	 * @throws CloudException
	 */
	public static void get(CloudTable table, CloudTableCallback callbackObject) throws CloudException{
		if(CloudApp.getAppId() == null){
			throw new CloudException("App Id is missing");
		}
		
		JSONObject params = new JSONObject();
		params.put("key", CloudApp.getAppKey());
		params.put("appId", CloudApp.getAppId());
		String url = CloudApp.getServiceUrl()+"/"+CloudApp.getAppId()+"/table/"+table.getTableName();
		client = new AsyncHttpClient();
		Future<Response> f = client.preparePost(url).addHeader("sessionId", PrivateMethod._getSessionId()).addHeader("Content-type", "application/json").setBody(params.toString()).execute();
		try {
			if(f.get().getHeader("sessionId") != null){
				PrivateMethod._setSessionId(f.get().getHeader("sessionId"));
			}else{
				PrivateMethod._deleteSessionId();
			}
			if(f.get().getStatusCode() == 200){
				System.out.println("Get Response :: "+ f.get().getResponseBody());
				JSONObject body = new JSONObject(f.get().getResponseBody());
				CloudTable object = new CloudTable(body.getString("name"));
				object.document = body;
				callbackObject.done(object, null);
			}else{
				
				CloudException e = new CloudException(f.get().getResponseBody());
				callbackObject.done((CloudTable)null, e);
			}
			
		} catch (InterruptedException | ExecutionException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done((CloudTable)null, e1);
			e.printStackTrace();
		} catch (JSONException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done((CloudTable)null, e1);
			e.printStackTrace();
		} catch (IOException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done((CloudTable)null, e1);
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Save a Table
	 * 
	 * @param callbackObject
	 * @throws CloudException 
	 */
	public void save(CloudTableCallback callbackObject) throws CloudException{
			
		if(CloudApp.getAppId() == null){
			throw new CloudException("App Id is null");
		}
		
		JSONObject params  = new JSONObject();
		CloudTable thisObj = this;
		params.put("data", document);		
		params.put("key", CloudApp.getAppKey());
		System.out.println(params.toString());
		client = new AsyncHttpClient();
		String url = CloudApp.getServiceUrl()+"/"+CloudApp.getAppId()+"/table/"+this.document.get("name");
		Future<Response> f = client.preparePut(url).addHeader("sessionId", PrivateMethod._getSessionId()).addHeader("Content-type", "application/json").setBody(params.toString()).execute();
		try {
			if(f.get().getHeader("sessionId") != null){
				PrivateMethod._setSessionId(f.get().getHeader("sessionId"));
			}else{
				PrivateMethod._deleteSessionId();
			}
			if(f.get().getStatusCode() == 200){
				JSONObject body = new JSONObject(f.get().getResponseBody());
				thisObj.document = body;
				callbackObject.done(thisObj, null);
				System.out.println(url);
			}else{
				CloudException e = new CloudException(f.get().getResponseBody());
				callbackObject.done((CloudTable)null, e);
				
			}
		} catch (InterruptedException | ExecutionException | JSONException | IOException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done((CloudTable)null, e1);
		}
	}
	
	/**
	 * 
	 * Delete a Table
	 * 
	 * @param callbackObject
	 * @throws CloudException 
	 */
	public void delete(CloudStringCallback callbackObject) throws CloudException{
		if(CloudApp.getAppId() == null){
			throw new CloudException("App id is null");
		}
		
		if(this.document.has("_id") == false){
			throw new CloudException("Cannot delete a table which is not saved.");
		}
		
		JSONObject params  = new JSONObject();
		params.put("data", document);		
		params.put("key", CloudApp.getAppKey());
		
		client = new AsyncHttpClient();
		String url = CloudApp.getServiceUrl()+"/"+CloudApp.getAppId()+"/table/"+this.document.get("name");
		Future<Response> f = client.prepareDelete(url).addHeader("sessionId", PrivateMethod._getSessionId()).addHeader("Content-type", "application/json").setBody(params.toString()).execute();
		try {
			if(f.get().getHeader("sessionId") != null){
				PrivateMethod._setSessionId(f.get().getHeader("sessionId"));
			}else{
				PrivateMethod._deleteSessionId();
			}
			if(f.get().getStatusCode() == 200){
				callbackObject.done(f.get().getResponseBody(), null);
			}else{
				CloudException e = new CloudException(f.get().getResponseBody());
				callbackObject.done((String)null, e);
			}
		} catch (InterruptedException | ExecutionException | IOException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done((String)null, e1);
			e.printStackTrace();
		}
	}
}