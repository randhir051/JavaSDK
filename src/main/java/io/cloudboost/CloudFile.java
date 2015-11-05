package io.cloudboost;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

import io.cloudboost.json.*;

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
	private File file = null;
	private Blob blobFile = null;
	private Object data = new Object();
	public CloudFile(File fileObj) throws CloudException{
		
		if(fileObj == null){
			throw new CloudException("File is null");
		}
		
		this.file = fileObj;
		this.document = new JSONObject();
		try {
			this.document.put("_type", "file");
		
		this.document.put("name", file.getName());
		this.document.put("size", file.length());
		this.document.put("url", (Object)null);
		this.document.put("contentType", this.getFileExtension(file));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		try {
			this.document.put("_type", "file");
		
		this.document.put("name", ((File) this.blobFile).getName());
		
		try {
			this.document.put("size", file.length());
		} catch (JSONException e) {
			this.document.put("size", (Object)null);
			e.printStackTrace();
		}
		
		this.document.put("url", (Object)null);
		this.document.put("contentType", (Object)null);} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
			try {
				this.document.put("_type", "file");
			
			this.document.put("expires", JSONObject.NULL);
			this.document.put("ACL", new ACL());
			this.document.put("name", JSONObject.NULL);
			this.document.put("size", JSONObject.NULL);
			this.document.put("url", url);
			this.document.put("contentType", JSONObject.NULL);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		try {
			this.document.put("_id", JSONObject.NULL);
		
		this.document.put("_type", "file");
		this.document.put("expires", JSONObject.NULL);
		this.document.put("ACL", new ACL());
		this.document.put("name", JSONObject.NULL);
		this.document.put("size", JSONObject.NULL);
		this.document.put("url", JSONObject.NULL);
		this.document.put("contentType", "type");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param type
	 */
	public void setFileType(String type){
		try {
			this.document.put("_type", type);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFileType(){
		try {
			return this.document.getString("_type");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param url
	 */
	public void setFileUrl(String url){
		try {
			this.document.put("url", url);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFileUrl(){
		try {
			return this.document.getString("url");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setFileName(String name){
		try {
			this.document.put("name", name);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getFileName(){
		try {
			return this.document.getString("name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param size
	 */
	public void setFileSize(long size){
		try {
			this.document.put("size", size);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public int getFileSize(){
		try {
			return this.document.getInt("size");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
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
			
		String url = CloudApp.getApiUrl()+"/file/"+CloudApp.getAppId();
		System.out.println(url);
		if(this.data.equals(null)){
			//Future<Response> f = client.preparePost(url).addHeader("Content-Type", "multipart/form-data; boundary=" + BOUNDARY).setBodyEncoding("UTF-8").addBodyPart(part).addBodyPart(new StringPart("from", "1")).addFormParam("key",CloudApp.getAppKey()).setBody(this.file).execute();
			//Future<Response> f = client.preparePost(url).addHeader("Content-Type", "multipart/form-data; boundary=" + BOUNDARY).addBodyPart(part).addFormParam("key",CloudApp.getAppKey()).execute();
//			f = client.preparePost(url).setBody(this.file).addFormParam("key",CloudApp.getAppKey()).addFormParam("fileObj", this.document.toString()).setBody(new FileInputStream(this.file)).execute();
		}else{
			JSONObject params = new JSONObject();
			try {
				params.put("data", this.data);
			
			params.put("fileObj", this.document);
			params.put("key", CloudApp.getAppKey());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			f = client.preparePost(url).addHeader("sessionId", PrivateMethod._getSessionId()).addHeader("Content-type", "application/json").setBody(params.toString()).execute();
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
		
		try {
			if(this.document.getString("url") == null){
				throw new CloudException("You cannot delete a file which does not have an URL");
			}
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		JSONObject params  = new JSONObject();
		try {
			params.put("url", this.document.getString("url"));
				
		params.put("key", CloudApp.getAppKey());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
//		client = new AsyncHttpClient();
		String url = CloudApp.getApiUrl()+"/file/"+CloudApp.getAppId()+"/delete";
//		Future<Response> f = client.preparePost(url).addHeader("Content-type", "application/json").setBody(params.toString()).execute();
	}
}