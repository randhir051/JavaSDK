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
public class CloudRole extends CloudObject{
	
	private CloudRole thisObj;
	public CloudRole(String roleName){
		super("Role");
		this.document.put("_type", "role");
		this.document.put("name", roleName);
		JSONArray col = new JSONArray(this.document.get("_modifiedColumns").toString());
		ArrayList<String> modified = new ArrayList<String>();
		for(int i=0; i<col.length(); i++){
			modified.add(col.getString(i));
		}
		modified.add("name");
		this.document.put("_modifiedColumns", modified);
	}
	
	/**
	 * 
	 * Get Role Name
	 * 
	 * @param roleName
	 */
	public void setRoleName(String roleName){
		this.document.put("name", roleName);
	}
	
	/**
	 * 
	 * Set Role Name
	 * 
	 * @return
	 */
	public String getRoleName(){
		return this.document.getString("name");
	}
	
	public void save(final CloudRoleCallback callbackObject) throws CloudException{
		if(CloudApp.getAppId() == null){
			throw new CloudException("App Id is null");
		}
		JSONObject data  = new JSONObject();
		thisObj = this;
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
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done(null, e1);
		} catch (JSONException e) {
			CloudException e1 =  new CloudException(e.toString());
			callbackObject.done(null, e1);
		} catch (IOException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done(null, e1);
		}		
	}
	
}