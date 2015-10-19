package io.cloudboost;

import java.io.IOException;
import java.util.ArrayList;
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
public class CloudSearch{
	
	private static AsyncHttpClient client;
	String collectionName;
	ArrayList<String> collectionArray;
	JSONObject query;
	private JSONObject filtered;
	private JSONObject bool;
	int from;
	int size;
	ArrayList<Object> sort;
	
	/**
	 * 
	 * Constructor
	 * 
	 * @param tableName
	 * @param searchObject
	 * @param searchFilter
	 */
	public CloudSearch(String tableName, SearchQuery searchObject, SearchFilter searchFilter){
			this.collectionName = tableName;
			this.collectionArray = new ArrayList<String>();
			this.query = new JSONObject();
			this.bool = new JSONObject();
			filtered = new JSONObject();
			if(searchObject != null){
				this.bool.put("bool", searchObject.bool);
				filtered.put("query",this.bool );
			}else{
				filtered.put("query", new JSONObject());
			}
			if(searchFilter != null){
				this.bool.put("bool", searchFilter.bool);
				filtered.put("filter",this .bool);
			}else{
				filtered.put("filter", new JSONObject());
			}
			
			System.out.println("bool :: " + filtered.toString());
			this.from = 0;
			this.size = 10;
			this.sort = new ArrayList<Object>();
	}
	
	public CloudSearch(String[] tableName, SearchQuery searchObject, SearchFilter searchFilter){
		for(int i=0; i<tableName.length; i++){
			this.collectionArray.add(tableName[i]);
		}
	
		this.query = new JSONObject();
		filtered = new JSONObject();
		
		filtered.put("query", searchObject.bool.toString());
		filtered.put("filter", searchFilter.bool.toString());
		
		this.from = 0;
		this.size = 10;
		this.sort = new ArrayList<Object>();
}
	
	/**
	 * 
	 * Set Skip
	 * 
	 * @param data
	 * @return
	 */
	public CloudSearch setSkip(int data){
		this.from = data;
		return this;
	}
	
	/**
	 * 
	 * Set Limit
	 * 
	 * @param data
	 * @return
	 */
	public CloudSearch setLimit(int data){
		this.size = data;
		return this;
	}
	
	/**
	 * 
	 * Order By Asc
	 * 
	 * @param columnName
	 * @return
	 */
	public CloudSearch orderByAsc(String columnName){
		if (columnName.equals("id") || columnName.equals("isSearchable") || columnName.equals("expires")){
			columnName = "_"+ columnName;
		}
	      
		JSONObject obj = new JSONObject();
		JSONObject column= new JSONObject();
		column.put("order", "asc");
		obj.put(columnName, column);
		this.sort.add(obj);
		
		return this;
	}
	
	/**
	 * 
	 * Order by Desc
	 * 
	 * @param columnName
	 * @return
	 */
	public CloudSearch orderByDesc(String columnName){
		if (columnName.equals("id") || columnName.equals("isSearchable") || columnName.equals("expires")){
			columnName = "_"+ columnName;
		}
	      
		JSONObject obj = new JSONObject();
		JSONObject column= new JSONObject();
		column.put("order", "desc");
		obj.put(columnName, column);
		this.sort.add(obj);
		
		return this;
	}
	
	/**
	 * 
	 * Search
	 * 
	 * @param callbackObject
	 * @throws CloudException 
	 * @throws IOException 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * @throws JSONException 
	 */
	public void search(CloudObjectArrayCallback callbackObject) throws JSONException, InterruptedException, ExecutionException, IOException, CloudException{
		String collectionString;
		if(this.collectionArray.size() >0){
			collectionString = this.collectionArray.get(0);
			for(int i=1; i<this.collectionArray.size(); i++){
				collectionString.concat(","+this.collectionArray.get(i));
			}
		}else{
			collectionString = this.collectionName;
		}
		this.query.put("filtered", this.filtered);
		JSONObject params = new JSONObject();
		params.put("collectionName", collectionString);
		
		params.put("query", this.query);
		params.put("sort", this.sort);
		params.put("limit", this.size);
		params.put("skip", this.from);
		params.put("key", CloudApp.getAppKey());
		String url = CloudApp.getServerUrl() + "/data/" + CloudApp.getAppId() + "/" +this.collectionName + "/search";
		client = new AsyncHttpClient();
		Future<Response> f = client.preparePost(url).addHeader("sessionId", PrivateMethod._getSessionId()).addHeader("Content-type", "application/json").setBody(params.toString()).execute();
		System.out.println(url);
		System.out.println(params);
		System.out.println(f.get().getResponseBody());
		if(f.get().getHeader("sessionId") != null){
			PrivateMethod._setSessionId(f.get().getHeader("sessionId"));
		}else{
			PrivateMethod._deleteSessionId();
		}
		System.out.println(f.get().getStatusCode());
		if(f.get().getStatusCode() == 200){
			JSONArray body = new JSONArray(f.get().getResponseBody());
			CloudObject[] object = new CloudObject[body.length()];
			
			for(int i=0; i<object.length; i++){
				object[i] = new CloudObject(body.getJSONObject(i).get("_tableName").toString());
				object[i].document = body.getJSONObject(i);
			}
			callbackObject.done(object, null);
		}else{
			CloudException e = new CloudException(f.get().getResponseBody());
			callbackObject.done((CloudObject[])null, e);
		}
	}
}