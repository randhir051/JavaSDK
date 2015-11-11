package io.cloudboost;

import io.cloudboost.beans.CBResponse;
import io.cloudboost.util.CBParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * 
 * @author cloudboost
 *
 */
public class CloudTable{
	
	protected JSONObject document;
	
	public CloudTable(String tableName){
		if(!PrivateMethod._tableValidation(tableName)){
			try {
				throw new CloudException("Invalid Table Name");
			} catch (CloudException e) {
				e.printStackTrace();
			}
		}
		try{
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
	} catch (JSONException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}	
	}
	
	
	/***
	 * 
	 * @param tableName
	 */
	public void setTableName(String tableName){
		try {
			this.document.put("name", tableName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getTableName(){
		try {
			return this.document.getString("name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	String getTableType(){
		try {
			return this.document.getString("type");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
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
		try{
		JSONArray columnList = new JSONArray( this.document.get("columns").toString());
		columnList.put(column.document);
		this.document.put("columns", columnList);
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}	
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
		}else{try{
			JSONArray col = new JSONArray( this.document.get("columns").toString());
			for(int i=0; i<col.length(); i++){
				if(col.getJSONObject(i).get("name") == column.document.get("name")){
					col.remove(i);
					break;
				}
			}
			this.document.put("columns", col);
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}	
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
		try {
		params.put("key", CloudApp.getAppKey());
		String url = CloudApp.getServiceUrl()+"/"+CloudApp.getAppId()+"/table";
//		System.out.println("url: "+url);
//		System.out.println(params);
		CBResponse response=CBParser.callJson(url, "POST", params);
			if(response.getStatusCode() == 200){
//				System.out.println(response.getResponseBody());
				JSONArray body = new JSONArray(response.getResponseBody());
				CloudTable[] object = new CloudTable[body.length()];
				
				for(int i=0; i<object.length; i++){
					object[i] = new CloudTable(body.getJSONObject(i).get("name").toString());
					object[i].document = body.getJSONObject(i);
				}
				callbackObject.done(object, null);
				
			}else{
				
				CloudException e = new CloudException(response.getResponseBody());
				callbackObject.done((CloudTable[])null, e);
			}
			
		} catch (JSONException e) {
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
		try {
		params.put("key", CloudApp.getAppKey());
		params.put("appId", CloudApp.getAppId());
		String url = CloudApp.getServiceUrl()+"/"+CloudApp.getAppId()+"/table/"+table.getTableName();
		CBResponse response=CBParser.callJson(url, "POST", params);

			if(response.getStatusCode() == 200){
				JSONObject body = new JSONObject(response.getResponseBody());
				CloudTable object = new CloudTable(body.getString("name"));
				object.document = body;
				callbackObject.done(object, null);
			}else{
				
				CloudException e = new CloudException(response.getResponseBody());
				callbackObject.done((CloudTable)null, e);
			}
			
		} catch (JSONException e) {
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
		try {
		params.put("data", document);		
		params.put("key", CloudApp.getAppKey());
		System.out.println(params.toString());
		String url = CloudApp.getServiceUrl()+"/"+CloudApp.getAppId()+"/table/"+this.document.get("name");
		CBResponse response=CBParser.callJson(url, "PUT", params);

			if(response.getStatusCode() == 200){
				JSONObject body = new JSONObject(response.getResponseBody());
				thisObj.document = body;
				callbackObject.done(thisObj, null);
			}else{
				System.out.println("we got an exception: "+response.getResponseBody());
				CloudException e = new CloudException(response.getResponseBody());
				callbackObject.done((CloudTable)null, e);
				
			}
		} catch (JSONException e) {
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
		try {
		params.put("data", document);		
		params.put("key", CloudApp.getAppKey());
		String url = CloudApp.getServiceUrl()+"/"+CloudApp.getAppId()+"/table/"+this.document.get("name");
		CBResponse response=CBParser.callJson(url, "DELETE", params);
			if(response.getStatusCode() == 200){
				callbackObject.done(response.getResponseBody(), null);
			}else{
				CloudException e = new CloudException(response.getResponseBody());
				callbackObject.done((String)null, e);
			}
		} catch (JSONException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done((String)null, e1);
			e.printStackTrace();
		}
	}
}