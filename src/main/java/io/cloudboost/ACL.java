package io.cloudboost;

/*
 * @author cloudboost
 */
import io.cloudboost.util.CBParser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ACL{
	
	private ArrayList<String> allowedReadUser; 
	private ArrayList<String> allowedReadRole; 
	private ArrayList<String> deniedReadUser; 
	private ArrayList<String> deniedReadRole;
	private ArrayList<String> allowedWriteUser; 
	private ArrayList<String> allowedWriteRole; 
	private ArrayList<String> deniedWriteUser; 
	private ArrayList<String> deniedWriteRole;
	JSONObject allowRead;
	JSONObject denyRead;
	JSONObject allowWrite;
	JSONObject denyWrite;
	JSONObject read;
	JSONObject write;
	JSONObject acl;
	
	public ACL()
	{
		allowedReadUser = new ArrayList<String>();
		allowedReadRole = new ArrayList<String>();
		deniedReadUser = new ArrayList<String>();
		deniedReadRole = new ArrayList<String>();
		allowedWriteUser = new ArrayList<String>();
		allowedWriteRole = new ArrayList<String>();
		deniedWriteUser = new ArrayList<String>();
		deniedWriteRole = new ArrayList<String>();
		allowedReadUser.add("all");
		allowedWriteUser.add("all");
		allowRead= new JSONObject();
		denyRead = new JSONObject();
		allowWrite= new JSONObject();
		denyWrite = new JSONObject();
		
		try {
			allowRead.put("user", allowedReadUser);
		
		allowRead.put("role", allowedReadRole);
		allowWrite.put("user", allowedWriteUser);
		allowWrite.put("role", allowedWriteRole);
		
		denyRead.put("user", deniedReadUser);
		denyRead.put("role", deniedReadRole);
		denyWrite.put("user", deniedWriteUser);
		denyWrite.put("role", deniedWriteRole);
		
		
		read = new JSONObject();
		read.put("allow", allowRead);
		read.put("deny", denyRead);
		
		write = new JSONObject();
		write.put("allow", allowWrite);
		write.put("deny", denyWrite);
		
		acl = new JSONObject();
		acl.put("read", read);
		acl.put("write", write);} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public JSONObject getACL(){
		return acl;
	}
	@SuppressWarnings({ })
	private ArrayList<String> getWriteList(JSONObject acl){
		JSONArray user;
		try {
			write = (JSONObject) acl.get("write");
		
		allowWrite = (JSONObject) write.get("allow");
		user = new JSONArray(allowWrite.get("user").toString());
		allowedWriteUser.clear();
		for(int i=0; i<user.length(); i++){
			allowedWriteUser.add(i, user.getString(i));
		}
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return allowedWriteUser;
	}
	
	private ArrayList<String> getReadList(JSONObject acl){
		try {
			read = (JSONObject) acl.get("read");
		
		allowRead = (JSONObject) read.get("allow");
		allowedReadUser.clear();
		JSONArray user = new JSONArray(allowRead.get("user").toString());
		for(int i=0; i<user.length(); i++){
			allowedReadUser.add(i, user.getString(i));
		}
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return allowedReadUser;
	}
	
	private ArrayList<String> getDeniedWriteList(JSONObject acl){
		try {
			write = (JSONObject) acl.get("write");
		
		denyWrite = (JSONObject) write.get("deny");
		JSONArray user = new JSONArray(denyWrite.get("user").toString());
		deniedWriteUser.clear();
		for(int i=0; i<user.length(); i++){
			deniedWriteUser.add(i, user.getString(i));
		}
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return deniedWriteUser;
	}
	
	private ArrayList<String> getDeniedReadList(JSONObject acl){
		try {
			read = (JSONObject) acl.get("read");
		
		denyRead= (JSONObject) read.get("deny");
		JSONArray user = new JSONArray(denyRead.get("user").toString());
		deniedReadUser.clear();
		for(int i=0; i<user.length(); i++){
			deniedReadUser.add(i, user.getString(i));
		}
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return deniedReadUser;
	}
	
	public void setPublicWriteAccess(boolean value){  //allow write permission to all user
		allowedWriteUser = getWriteList(acl);
		if(value == true){	//if value is true then clear the existing list and add "all" and push it to jsonObject
			allowedWriteUser.clear();
			allowedWriteUser.add("all");
		}
		else{ // remove "all" from list
			int index = allowedWriteUser.indexOf("all");
			if (index >=0) {
				allowedWriteUser.remove("all");
			}
		}
		try {
		allowWrite.put("user", allowedWriteUser);
		write.put("allow", allowWrite);
		
			acl.put("write", write);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		
	}

	public void setPublicReadAccess(boolean value){  //allow read permission to all user
		allowedReadUser = getReadList(acl);
		if(value){	//if value is true then clear the existing list and add "all" and push it to jsonObject
			allowedReadUser.clear();
			allowedReadUser.add("all");
		}
		else{ // remove "all" from list
			int index = allowedReadUser.indexOf("all");
			if (index > -1) {
				allowedReadUser.remove("all");
			}
		}
		try {
			allowRead.put("user", allowedReadUser);
		
		read.put("allow", allowRead);
		acl.put("read", read);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}

	public void setUserWriteAccess(String userId, boolean value){ //for setting the user write access
		int index;
		allowedWriteUser = getWriteList(acl);
		deniedWriteUser = getDeniedWriteList(acl);
		if(value){
			index = allowedWriteUser.indexOf("all");
			if(index > -1){
				allowedWriteUser.remove(index);
			}			
			index = allowedWriteUser.indexOf(userId);
			if(index <= -1){
				allowedWriteUser.add(userId);
			}
		}else{ 
			index = allowedWriteUser.indexOf(userId);
			if(index > -1){
				allowedWriteUser.remove(index);
			}
			deniedWriteUser.add(userId);
			
		}
		try {
			allowWrite.put("user", allowedWriteUser);
		
		denyWrite.put("user", deniedWriteUser);
		write.put("deny", denyWrite);
		write.put("allow", allowWrite);
		acl.put("write", write);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}	

	public void setUserReadAccess(String userId, boolean value){ //for setting the user read access
		int index;
		
		allowedReadUser = getReadList(acl);
		deniedReadUser = getDeniedReadList(acl);
		if(value){
			index = allowedReadUser.indexOf("all");
			if(index > -1){
				allowedReadUser.remove(index);
			}			
			index = allowedReadUser.indexOf(userId);
			if(index <= -1){
				allowedReadUser.add(userId);
			}
		}else{ 
			index = allowedReadUser.indexOf(userId);
			if(index > -1){
				allowedReadUser.remove(index);
			}
			deniedReadUser.add(userId);
			
		}
		try {
			allowRead.put("user", allowedReadUser);
		
		denyRead.put("user", deniedReadUser);
		read.put("deny", denyRead);
		read.put("allow", allowRead);
		acl.put("read", read);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}	
	
	@SuppressWarnings("unchecked")
	public void setRoleWriteAccess(String roleId, boolean value){
		int index;
		allowedWriteUser = getWriteList(acl);
		deniedWriteUser = getDeniedWriteList(acl);
		
		try {
			write = (JSONObject) acl.get("write");
		
		//allowedRole
		allowWrite = (JSONObject) write.get("allow");
		allowedWriteRole =CBParser.jsonToList((JSONArray) allowWrite.get("role"));
		System.out.println("allowedwriterole="+allowedWriteRole);
		//deniedRole
		denyWrite = (JSONObject) write.get("deny");
		deniedWriteRole = CBParser.jsonToList((JSONArray) denyWrite.get("role"));
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		
		if(value){
			index = allowedWriteUser.indexOf("all");
			if(index > -1){
				allowedWriteUser.remove(index);
			}			
			index = allowedWriteRole.indexOf(roleId);
			System.out.println("index="+index);
			System.out.println("allowedwriterole="+allowedWriteRole);
			if(index <= -1){
				System.out.println("adding roleid");
				allowedWriteRole.add(roleId);
			}
			System.out.println("allowwriterole now="+allowedWriteRole);
		}else{
			index = allowedWriteRole.indexOf(roleId);
			if(index > -1){
				allowedWriteRole.remove(index);
			}
			
			index = allowedWriteUser.indexOf("all");
			if(index > -1){
				allowedWriteUser.remove(index);
			}
			deniedWriteRole.add(roleId);
		}
		try {
			allowWrite.put("user", allowedWriteUser);
		
		allowWrite.put("role", allowedWriteRole);
		denyWrite.put("role", deniedWriteRole);
		write.put("deny", denyWrite);
		write.put("allow", allowWrite);
		acl.put("write", write);
		System.out.println("acl="+acl.toString());
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void setRoleReadAccess(String roleId, boolean value){
		int index;
		allowedReadUser = getReadList(acl);
		deniedWriteUser = getDeniedReadList(acl);
		
		try {
			write = (JSONObject) acl.get("write");
		
		//allowedReadRole
		allowRead = (JSONObject) read.get("allow");
		allowedReadRole = CBParser.jsonToList((JSONArray)  allowRead.get("role"));
		
		//deniedReadRole
		denyRead = (JSONObject) read.get("deny");
		deniedReadRole = CBParser.jsonToList((JSONArray)  denyRead.get("role"));
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		if(value){
			index = allowedReadUser.indexOf("all");
			if(index > -1){
				allowedReadUser.remove(index);
			}			
			index = allowedReadRole.indexOf(roleId);
			if(index <= -1){
				allowedReadRole.add(roleId);
			}
		}else{
			index = allowedReadRole.indexOf(roleId);
			if(index > -1){
				allowedReadRole.remove(index);
			}
			
			index = allowedReadUser.indexOf("all");
			if(index > -1){
				allowedReadUser.remove(index);
			}
			deniedReadRole.add(roleId);
		}
		try {
			allowRead.put("user", allowedReadUser);
		
		allowRead.put("role", allowedReadRole);
		denyRead.put("role", deniedReadRole);
		read.put("deny", denyRead);
		read.put("allow", allowRead);
		acl.put("read", read);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}
}	
