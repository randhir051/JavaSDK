package io.cloudboost;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

/**
 * 
 * @author cloudboost
 *
 */
public class CloudFile{
	private JSONObject document;
	
	/**
	 * 
	 * @param file
	 * @throws CloudException
	 */
	protected AsyncHttpClient client;
	private File file = null;
	private Blob blobFile = null;
	private Object data = new Object();
	public CloudFile(File fileObj) throws CloudException{
		
		if(fileObj == null){
			throw new CloudException("File is null");
		}
		
		this.file = fileObj;
		this.document = new JSONObject();
		this.document.put("_type", "file");
		this.document.put("name", file.getName());
		this.document.put("size", file.length());
		this.document.put("url", (Object)null);
		this.document.put("contentType", this.getFileExtension(file));
	}
	
	/**
	 * 
	 * @param file
	 * @throws CloudException
	 */
	public CloudFile(Blob fileObj) throws CloudException{
		
		if(fileObj == null){
			throw new CloudException("File is null");
		}
		
		this.blobFile = fileObj;
		this.document = new JSONObject();
		this.document.put("_type", "file");
		this.document.put("name", ((File) this.blobFile).getName());
		
		try {
			this.document.put("size", file.length());
		} catch (JSONException e) {
			this.document.put("size", (Object)null);
			e.printStackTrace();
		}
		
		this.document.put("url", (Object)null);
		this.document.put("contentType", (Object)null);
	}
	
	/**
	 * 
	 * @param file
	 * @throws CloudException
	 */
	public CloudFile(String url) throws CloudException{
		
		if(url.isEmpty()){
			throw new CloudException("Enter valid URL");
		}
		this.document = new JSONObject();
		Pattern pattern = Pattern.compile("https?:\\/\\/(?:www\\.|(?!www))[^\\s\\.]+\\.[^\\s]{2,}|www\\.[^\\s]+\\.[^\\s]{2,}");
		if(pattern.matcher(url).find()){
			this.document.put("_type", "file");
			this.document.put("expires", JSONObject.NULL);
			this.document.put("ACL", new ACL());
			this.document.put("name", JSONObject.NULL);
			this.document.put("size", JSONObject.NULL);
			this.document.put("url", url);
			this.document.put("contentType", JSONObject.NULL);
		}else{
			throw new CloudException("Invalid URL");
		}
		
	}
	
	public CloudFile(String file, Object data, String type) throws CloudException{
		this.document = new JSONObject();
		if(file.isEmpty()){
			throw new CloudException("file name is required");
		}
		if(type.isEmpty()){
			throw new CloudException("file type is required");
		}
		
		this.data = data;
		this.document.put("_id", JSONObject.NULL);
		this.document.put("_type", "file");
		this.document.put("expires", JSONObject.NULL);
		this.document.put("ACL", new ACL());
		this.document.put("name", JSONObject.NULL);
		this.document.put("size", JSONObject.NULL);
		this.document.put("url", JSONObject.NULL);
		this.document.put("contentType", "type");
	}
	/**
	 * 
	 * @param type
	 */
	public void setFileType(String type){
		this.document.put("_type", type);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFileType(){
		return this.document.getString("_type");
	}
	
	/**
	 * 
	 * @param url
	 */
	public void setFileUrl(String url){
		this.document.put("url", url);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFileUrl(){
		return this.document.getString("url");
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setFileName(String name){
		this.document.put("name", name);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFileName(){
		return this.document.getString("name");
	}
	
	/**
	 * 
	 * @param size
	 */
	public void setFileSize(long size){
		this.document.put("size", size);
	}
	
	/**
	 * 
	 * @return
	 */
	public int getFileSize(){
		return this.document.getInt("size");
	}
	
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public String getFileExtension(File file){
		 String name = file.getName();
		 int lastIndexOf = name.lastIndexOf(".");
		 if (lastIndexOf == -1) {
			 return null;
		 }
		 return name.substring(lastIndexOf);
	}
	
	
	public void save(CloudStringCallback callbackObject) throws CloudException, FileNotFoundException{
		if(CloudApp.getAppId() == null){
			throw new CloudException("App Id is null");
		}
			
		client = new AsyncHttpClient();
		String url = CloudApp.getApiUrl()+"/file/"+CloudApp.getAppId();
		System.out.println(url);
		Future<Response> f ;
		if(this.data.equals(null)){
			//Future<Response> f = client.preparePost(url).addHeader("Content-Type", "multipart/form-data; boundary=" + BOUNDARY).setBodyEncoding("UTF-8").addBodyPart(part).addBodyPart(new StringPart("from", "1")).addFormParam("key",CloudApp.getAppKey()).setBody(this.file).execute();
			//Future<Response> f = client.preparePost(url).addHeader("Content-Type", "multipart/form-data; boundary=" + BOUNDARY).addBodyPart(part).addFormParam("key",CloudApp.getAppKey()).execute();
			f = client.preparePost(url).setBody(this.file).addFormParam("key",CloudApp.getAppKey()).addFormParam("fileObj", this.document.toString()).setBody(new FileInputStream(this.file)).execute();
		}else{
			JSONObject params = new JSONObject();
			params.put("data", this.data);
			params.put("fileObj", this.document);
			params.put("key", CloudApp.getAppKey());
			f = client.preparePost(url).addHeader("sessionId", PrivateMethod._getSessionId()).addHeader("Content-type", "application/json").setBody(params.toString()).execute();
		}
	
		try {
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
	/**
	 * 
	 * @param callbackObject
	 * @throws CloudException
	 */
	public void delete(CloudStringCallback callbackObject) throws CloudException{
		if(CloudApp.getAppId() == null){
			throw new CloudException("App Id is null");
		}
		
		if(this.document.getString("url") == null){
			throw new CloudException("You cannot delete a file which does not have an URL");
		}
		
		JSONObject params  = new JSONObject();
		params.put("url", this.document.getString("url"));		
		params.put("key", CloudApp.getAppKey());
		
		client = new AsyncHttpClient();
		String url = CloudApp.getApiUrl()+"/file/"+CloudApp.getAppId()+"/delete";
		Future<Response> f = client.preparePost(url).addHeader("Content-type", "application/json").setBody(params.toString()).execute();
		
		try {
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