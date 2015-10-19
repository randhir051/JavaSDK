package io.cloudboost;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import io.socket.emitter.Emitter;
import com.ning.http.client.*;
import org.json.*;

import io.cloudboost.util.CloudSocket;

/**
 * 
 * @author cloudboost
 *
 */
public class CloudObject{
	public ACL acl;
	protected AsyncHttpClient client;
	protected JSONObject document;
	private CloudObject thisObj;
	protected ArrayList<String> _modifiedColumns;
	
	/**
	 * 
	 * Constructor
	 * 
	 * @param tableName
	 */
	public CloudObject(String tableName){
		this.acl = new ACL();
		this._modifiedColumns = new ArrayList<String>();
		this._modifiedColumns.add("createdAt");
		this._modifiedColumns.add("updatedAt");
		this._modifiedColumns.add("ACL");
		this._modifiedColumns.add("expires");
		//adding properties of this object is document HashMap, which can letter pass to serialization
		document = new JSONObject();
		document.put("_id", (Object)null);
		document.put("_tableName", tableName);
		document.put("_type", "custom");
		document.put("createdAt", (Object)null);
		document.put("updatedAt", (Object)null);
		document.put("ACL", acl.getACL());
		document.put("expires", JSONObject.NULL);
		document.put("_modifiedColumns", this._modifiedColumns);
		document.put("_isModified", true);
	}
	
	/**
	 * 
	 * Constructor
	 * 
	 * @param tableName
	 * @param id
	 */
	public CloudObject(String tableName, String id){
		this.acl = new ACL();
		this._modifiedColumns = new ArrayList<String>();
		
		//adding properties of this object is document HashMap, which can letter pass to serialization
		document = new JSONObject();
		document.put("_id", id);
		document.put("_tableName", tableName);
		document.put("_type", "custom");
		document.put("ACL", acl.acl.toString());
		document.put("_isSearchable", false);
		document.put("createdAt", (Object)null);
		document.put("updatedAt", (Object)null);
		document.put("expires", (Object)null);
		document.put("_modifiedColumns", this._modifiedColumns);
		document.put("_isModified", true);
	}
	
	/**
	 * 
	 * @return
	 */
	String getId(){
		return (document.get("_id")).toString();
	}
	
	/**
	 * 
	 * @return
	 */
	Date getCreatedAt(){
		return (Date)document.get("createdAt");
	}
	
	/**
	 * 
	 * @param value
	 */
	void setCreatedAt(Date value){
		document.put("createdAt", value);
	}
	
	/**
	 * 
	 * @return
	 */
	Date getUpdatedAt(){
		return (Date)document.get("updatedAt");
	}
	
	/**
	 * 
	 * @param value
	 */
	void setUpdatedAt(Date value){
		document.put("updatedAt", value);
	}
	
	/**
	 * 
	 * @return
	 */
	boolean getIsSearchable(){
		return (boolean)document.get("_isSearchable");
	}
	
	/**
	 * 
	 * @param value
	 */
	void setIsSearchable(boolean value){
		document.put("_isSearchable", value);
	}
	
	/**
	 * 
	 * @return
	 */
	Calendar getExpires(){
		return (Calendar)document.get("expires");
	}
	
	/**
	 * 
	 * @param value
	 */
	void setExpires(Calendar  value){
		document.put("expires", value);
	}
	
	/**
	 * 
	 * Set
	 * 
	 * @param columnName
	 * @param data
	 * @throws CloudException
	 */
	public void set(String columnName, Object data) throws CloudException{		
		String keywords[] = {"_tableName", "_type", "operator" };
		int index = -1;
		if(columnName == "id" || columnName == "isSearchable"){
			columnName = "_" + columnName;
		}
		for (int i=0;i<keywords.length;i++) {
    		if (keywords[i].equals(columnName)) {
        		index = i;
       	 		break;
    		}
		}
		if(index > -1){
			throw new CloudException(columnName + "is a keyword. Please choose a different column name.");
		}
		if(data instanceof CloudObject){
			data = ((CloudObject) data).document;
		}
		
		if(data instanceof CloudGeoPoint){
			data = ((CloudGeoPoint) data).document;
		}
		
		if(data == null){
			document.put(columnName, JSONObject.NULL);
		}else{
			document.put(columnName, data);
		}
		
		this._modifiedColumns.add(columnName);
		document.put("_modifiedColumns", this._modifiedColumns);
		document.put("_isModified", true);
	}
	
	/**
	 * 
	 * Set
	 * 
	 * @param columnName
	 * @param data
	 * @throws CloudException
	 */
	public void set(String columnName, Object[] data) throws CloudException{
		String keywords[] = {"_tableName", "_type", "operator" };
		int index = -1;
		
		if(columnName == "id" || columnName == "isSearchable"){
			columnName = "_" + columnName;
		}
		
		for (int i=0;i<keywords.length;i++) {
	   		if (keywords[i].equals(columnName)) {
	       		index = i;
	   	 		break;
	   		}
		}
		if(index > -1){
			throw new CloudException(columnName + "is a keyword. Please choose a different column name.");
		}
		if(data instanceof CloudObject[]){
			CloudObject[] arrayList = (CloudObject[])data;
			ArrayList<Object> objectArray = new ArrayList<Object>(); 
			for(int i=0; i<arrayList.length; i++){
				objectArray.add(arrayList[i].document);
			}
			document.put(columnName, objectArray);
			this._modifiedColumns.add(columnName);
			document.put("_modifiedColumns", this._modifiedColumns);
		}
		else{
			document.put(columnName, data);
			this._modifiedColumns.add(columnName);
			document.put("_modifiedColumns", this._modifiedColumns);
		}
	}
	
	public void set(String columnName, CloudObject[] data) throws CloudException{
		String keywords[] = {"_tableName", "_type", "operator" };
		int index = -1;
		
		if(columnName == "id" || columnName == "isSearchable"){
			columnName = "_" + columnName;
		}
		
		for (int i=0;i<keywords.length;i++) {
	   		if (keywords[i].equals(columnName)) {
	       		index = i;
	   	 		break;
	   		}
		}
		if(index > -1){
			throw new CloudException(columnName + "is a keyword. Please choose a different column name.");
		}
		ArrayList<Object> objectArray = new ArrayList<Object>(); 
		for(int i=0; i<data.length; i++){
			objectArray.add(data[i].document);
		}
		document.put(columnName, objectArray);
		this._modifiedColumns.add(columnName);
		document.put("_modifiedColumns", this._modifiedColumns);
	}
	/**
	 * 
	 * Get
	 * 
	 * @param columnName
	 * @return
	 */
	public Object get(String columnName){
		if(columnName == "id" || columnName == "isSearchable"){
			columnName = "_" + columnName;
		}
		
		return  document.get(columnName);
	}
	
	public Integer getInteger(String columnName){
		if(columnName == "id" || columnName == "isSearchable"){
			columnName = "_" + columnName;
		}
		
		return  (Integer) document.get(columnName);
	}
	
	public Boolean getBoolean(String columnName){
		if(columnName == "id" || columnName == "isSearchable"){
			columnName = "_" + columnName;
		}
		
		return  (Boolean) document.get(columnName);
	}
	
	public Double getDouble(String columnName){
		if(columnName == "id" || columnName == "isSearchable"){
			columnName = "_" + columnName;
		}
		
		return  (Double) document.get(columnName);
	}
	
	public String getString(String columnName){
		if(columnName == "id" || columnName == "isSearchable"){
			columnName = "_" + columnName;
		}
		
		return  (String) document.get(columnName);
	}
	
	public CloudObject getCloudObject(String columnName){
		if(columnName == "id" || columnName == "isSearchable"){
			columnName = "_" + columnName;
		}
		
		JSONObject obj = new JSONObject(this.document.get(columnName).toString());
		CloudObject object = new CloudObject(obj.getString("_tableName"));
		object.document = obj;
		return  object;
	}
	
	public CloudObject[] getCloudObjectArray(String columnName){
		if(columnName == "id" || columnName == "isSearchable"){
			columnName = "_" + columnName;
		}
		JSONArray obj = new JSONArray();
		obj = this.document.getJSONArray(columnName);
		CloudObject[] object = new CloudObject[obj.length()];
		for(int i=0; i<obj.length(); i++){
			object[i] = new CloudObject(obj.getJSONObject(i).getString("_tableName"));
			object[i].document = obj.getJSONObject(i);
		}
		return  object;
	}
	
	public Object[] getArray(String columnName){
		if(columnName == "id" || columnName == "isSearchable"){
			columnName = "_" + columnName;
		}
		
		JSONArray data = document.getJSONArray(columnName);
		Object[] object = new Object[data.length()];
		for(int i=0 ;i < data.length(); i++){
			object[i] = data.get(i);
		}
		return object;
	}
	
	/**
	 * 
	 * UnSet
	 * 
	 * @param columnName
	 */
	void unset(String columnName){
		document.put(columnName, (Object)null);
	}
	
	/**
	 * 
	 * Relate
	 * 
	 * @param columnName
	 * @param tableName
	 * @param objectId
	 * @throws CloudException 
	 */
	public void relate(String columnName, String tableName, String objectId) throws CloudException{
	
		if(columnName == "id" || columnName == "_id"){
			throw new CloudException("You cannot set the id of a CloudObject");
		}
		
		if (columnName == "id" ||  columnName == "expires"){
			throw new CloudException("You cannot link an object to this column");
		}
		
		CloudObject object = new CloudObject(tableName, objectId);
		this.document.put(columnName, object.document);
		PrivateMethod._isModified(this, columnName);		
	}
	
	/**
	 * 
	 * CloudObject On
	 * 
	 * @param tableName
	 * @param eventType
	 * @param queryObject
	 * @param callbackObj
	 * @throws CloudException 
	 */
	
	public static void on(String tableName, String eventType, final CloudObjectCallback callbackObject) throws CloudException{
		
		tableName = tableName.toLowerCase();
		eventType = eventType.toLowerCase();
		if(eventType.equals("created") || eventType.equals("updated") || eventType.equals("deleted")){
			String str = (CloudApp.getAppId() + "table" + tableName + eventType).toLowerCase();
			JSONObject payload = new JSONObject();
			payload.put("room", str);
			payload.put("sessionId", PrivateMethod._getSessionId());
			System.out.println(str);
			CloudSocket.getSocket().connect();
			CloudSocket.getSocket().emit("join-object-channel", payload);
			CloudSocket.getSocket().on((str).toLowerCase(), new Emitter.Listener() {
				@Override
				public void call(final Object... args) {
					JSONObject body = new JSONObject(args[0].toString());
					System.out.println("data recived");
					System.out.println(body.toString());
					CloudObject object = new CloudObject(body.getString("_tableName"));
					object.document = body;
					try {
						callbackObject.done(object, null);
					} catch (CloudException e) {
						try {
							callbackObject.done(null, e);
						} catch (CloudException e1) {
							e1.printStackTrace();
						}
						e.printStackTrace();
					}
				}
			});
		}else{
			
			throw new CloudException("created, updated, deleted are supported notification types");
			
		}
	}
	
	/**
	 * 
	 * @param tableName
	 * @param eventType
	 * @param callbackObject
	 * @throws CloudException
	 */
	public static void on(String tableName, String[] eventType, final CloudObjectCallback callbackObject) throws CloudException{
		for(int i=0; i<eventType.length; i++){
			CloudObject.on(tableName, eventType[i], callbackObject);
		}
	}
	
	/**
	 * 
	 * CloudObject Off
	 * 
	 * @param tableName
	 * @param eventType
	 * @param callbackObj
	 * @throws CloudException 
	 */
	public static void off(String tableName, String eventType, final CloudStringCallback callbackObj) throws CloudException{
		tableName = tableName.toLowerCase();
		eventType = eventType.toLowerCase();
		
		if(eventType == "created" || eventType == "updated" || eventType == "deleted"){
			CloudSocket.getSocket().disconnect();
            CloudSocket.getSocket().emit("leave-object-channel", (CloudApp.getAppId()+"table"+tableName+eventType).toLowerCase());
            CloudSocket.getSocket().disconnect();
            CloudSocket.getSocket().off((CloudApp.getAppId() + "table" + tableName + eventType).toLowerCase(), new Emitter.Listener() {
				@Override
				public void call(final Object... args) {
					try {
						callbackObj.done("success", null);
					} catch (CloudException e) {
						
						try {
							callbackObj.done(null, e);
						} catch (CloudException e1) {
							e1.printStackTrace();
						}
						e.printStackTrace();
					}
				}
			});
		}else{
			throw new CloudException("created, updated, deleted are supported notification types");
		}
		
	}
	/**
	 * 
	 * @param tableName
	 * @param eventType
	 * @param callbackObj
	 * @throws CloudException
	 */
	public static void off(String tableName, String[] eventType, final CloudStringCallback callbackObj) throws CloudException{
		
		for(int i=0; i<eventType.length; i++){
			CloudObject.off(tableName, eventType[i], callbackObj);
		}
	}
	
	/**
	 * 
	 * Save
	 * 
	 * @param callbackObject
	 * @throws CloudException
	 */
	public void save(final CloudObjectCallback callbackObject) throws CloudException{
		if(CloudApp.getAppId() == null){
			throw new CloudException("App Id is null");
		}
		JSONObject data  = new JSONObject();
		data.put("document", document);		
		data.put("key", CloudApp.getAppKey());
		client = new AsyncHttpClient();
		String url = CloudApp.getApiUrl()+"/data/"+CloudApp.getAppId()+"/"+this.document.get("_tableName");
		System.out.println(data.toString());
		
		Future<Response> f = client.preparePut(url).addHeader("sessionId", PrivateMethod._getSessionId()).addHeader("Content-type", "application/json").setBody(data.toString()).execute();
		try {
			if(f.get().getHeader("sessionId") != null){
				PrivateMethod._setSessionId(f.get().getHeader("sessionId"));
			}else{
				PrivateMethod._deleteSessionId();
			}
			int statusCode = f.get().getStatusCode();
			System.out.println(f.get().getStatusCode());
			if(statusCode == 200){
				JSONObject body = new JSONObject(f.get().getResponseBody().toString());
				thisObj = new CloudObject(body.get("_tableName").toString());
				//thisObj = new CloudObject("Custom2");
				thisObj.document = body;
				System.out.println("success");
				System.out.println(body);
				//thisObj.document.remove("createdAt");
				//thisObj.document.remove("updatedAt");
				//thisObj.document.remove("_version");
				//thisObj.document.remove("expires");
				callbackObject.done(thisObj, null);
			}else{
				CloudException e = new CloudException(f.get().getResponseBody());
				callbackObject.done(null, e);
			}
		} catch (InterruptedException | ExecutionException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done(null, e1);
		} catch (JSONException e) {
			CloudException e1 =  new CloudException(e.toString());
			callbackObject.done(null, e1);
		} catch (IOException e) {
			CloudException e1 = new CloudException("Invalid I/O Operation");
			callbackObject.done(null, e1);
		}		
	}
	
	/**
	 * 
	 * Fetch
	 * 
	 * @param callbackObject
	 * @throws CloudException
	 */
	
	public void fetch(final CloudObjectCallback callbackObject) throws CloudException{
		if(CloudApp.getAppId() == null){
			throw new CloudException("App Id is null");
		}
		
		if(this.getId() == null){
			throw new CloudException("Can't fetch an object which is not saved");
		}
		this.document.put("ACL", this.acl.getACL());
		JSONObject data  = new JSONObject();
		thisObj = this;
		data.put("document", document);		
		data.put("key", CloudApp.getAppKey());
		String url = CloudApp.getApiUrl()+"/"+CloudApp.getAppId()+"/"+this.document.get("_tableName")+"/get/"+this.getId();
		client = new AsyncHttpClient();
		Future<Response> f = client.preparePost(url).addHeader("sessionId", PrivateMethod._getSessionId()).addHeader("Content-type", "application/json").setBody(data.toString()).execute();
		try {
			if(f.get().getHeader("sessionId") != null){
				PrivateMethod._setSessionId(f.get().getHeader("sessionId"));
			}else{
				PrivateMethod._deleteSessionId();
			}
			int statusCode = f.get().getStatusCode();
			System.out.println(f.get().getStatusCode());
			System.out.println(f.get().getResponseBody());
			if(statusCode == 200){
				System.out.println("success");
				JSONObject body = new JSONObject(f.get().getResponseBody());
				thisObj.document = body;
				System.out.println(thisObj.get("name"));
				callbackObject.done(thisObj, null);
			}else{
				CloudException e = new CloudException(f.get().getResponseBody());
				callbackObject.done(null, e);
			}
		} catch (InterruptedException | ExecutionException e) {
			CloudException e1 = new CloudException("HTTP Request Error");
			callbackObject.done(null, e1);
		} catch (IOException e) {
			CloudException e1 = new CloudException("Request Body Read Error");
			callbackObject.done(null, e1);
			e1.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Delete
	 * 
	 * @param callbackObject
	 * @throws CloudException
	 */
	public void delete(final CloudObjectCallback callbackObject) throws CloudException{
		if(CloudApp.getAppId() == null){
			throw new CloudException("App Id is null");
		}
		
		if(this.getId() == null){
			throw new CloudException("You cannot delete an object which is not saved.");
		}
		
		this.document.put("ACL", this.acl.getACL());
		JSONObject data  = new JSONObject();
		data.put("document", document);		
		data.put("key", CloudApp.getAppKey());
		String url = CloudApp.getApiUrl()+"/data/"+CloudApp.getAppId()+"/"+this.document.getString("_tableName");
		client = new AsyncHttpClient();
		
		Future<Response> f = client.prepareDelete(url).addHeader("sessionId", PrivateMethod._getSessionId()).addHeader("Content-type", "application/json").setBody(data.toString()).execute();
		try {
			if(f.get().getHeader("sessionId") != null){
				PrivateMethod._setSessionId(f.get().getHeader("sessionId"));
			}else{
				PrivateMethod._deleteSessionId();
			}
			if(f.get().getStatusCode() == 200){
				callbackObject.done(null, null);
			}else{
				CloudException e = new CloudException(f.get().getResponseBody());
				callbackObject.done(null, e);
			}
			
		} catch (InterruptedException | ExecutionException e) {
			CloudException e1 = new CloudException("HTTP Request Error");
			callbackObject.done(null,e1);
			e.printStackTrace();
		} catch (IOException e) {
			CloudException e1 = new CloudException("Request Body Read Error");
			callbackObject.done(null,e1);
			e1.printStackTrace();
		}
	}
}