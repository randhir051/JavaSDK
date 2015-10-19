package io.cloudboost;

import org.json.JSONObject;

/**
 * 
 * @author cloudboost
 *
 */

public class Column{
	
	protected JSONObject document;
	
	public enum DataType{
		Text, Email, URL, Number, Boolean, DateTime, GeoPoint, File, List, Relation, Object, Id, Password, ACL
	}
	
	public Column(String columnName, DataType dataType, boolean required, boolean unique){
		if(!PrivateMethod._columnNameValidation(columnName)){
			try {
				throw new CloudException("Invalid Column Name");
			} catch (CloudException e) {
				e.printStackTrace();
			}
		}
			
		this.document  = new JSONObject();
		this.document.put("name", columnName);
		this.document.put("dataType", dataType);
		this.document.put("_type", "column");
		this.document.put("required", required);
		this.document.put("unique", unique);
		this.document.put("relatedTo", JSONObject.NULL);
		this.document.put("relationType",JSONObject.NULL);
		this.document.put("isDeletable", true);
		this.document.put("isEditable", true);
		this.document.put("isRenamable", false);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getColumnName(){
		return this.document.getString("name");
	}
	
	/**
	 * 
	 * @return
	 */
	public DataType getDataType(){
		return (DataType) this.document.get("dataType");
	}
	
	/***
	 * 
	 * Set Required
	 * 
	 * @param required
	 */
	public void setRequired(boolean required){
		this.document.put("required", required);
	}
	
	public boolean getRequired(){
		return this.document.getBoolean("required");
	}
	
	/**
	 * 
	 * Set Unique
	 * 
	 * @param unique
	 */
	public void setUnique(boolean unique){
		this.document.put("unique", unique);
	}
	
	/***
	 * 
	 * @return
	 */
	public boolean getUnique(){
		return this.document.getBoolean("unique");
	}
	
	/***
	 *
	 * Set Related To
	 * 
	 * @param table
	 */
	public void setRelatedTo(CloudTable table){
		this.document.put("relatedTo", table.document.toString());
	}
	
	public void setRelatedTo(DataType type){
		this.document.put("relatedTo", type);
	}
	/**
	 * 
	 * @return
	 */
	public CloudTable getRelatedTo(){
		JSONObject table =  (JSONObject) this.document.get("relatedTo");
		CloudTable object = new CloudTable(table.getString("name"));
		object.document = table;
		return (CloudTable) object;
	}
	
	/**
	 * 
	 * Set RelatedToType
	 * 
	 * @param value
	 */
	public void setRelatedToType(String value){
		this.document.put("relatedTotype", value);
	}
	
	public String getRelatedToType(){
		return this.document.getString("relatedToType");
	}
	
	/***
	 * 
	 * Set Relation Type
	 * 
	 * @param value
	 */
	public void setRelationType(String value){
		this.document.put("relationType", value);
	}
	
	/**
	 * 
	 * @return
	 */
	public String setRelationType(){
		return this.document.getString("relationType");
	}
	
	/**
	 * 
	 * Set Deletable 
	 * 
	 * @param value
	 */
	void setIsDeletable(boolean value){
		this.document.put("isDeletable", value);
	}
	
	boolean getIsDeletable(){
		return this.document.getBoolean("isDeletable");
	}
	
	/***
	 * 
	 * Set Editable
	 * 
	 * @param value
	 */
	void setIsEditable(boolean value){
		this.document.put("isEditable", value);
	}
	
	boolean getIsEditable(){
		return this.document.getBoolean("isEditable");
	}
	
	/**
	 * 
	 * Set Renamable
	 * 
	 * @param value
	 */
	void setIsRenamable(boolean value){
		this.document.put("isRenamable", value);
	}
	
	boolean getIsRenamable(){
		return this.document.getBoolean("isRenamable");
	}
		
}