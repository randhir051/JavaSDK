package io.cloudboost;

import java.util.ArrayList;

import org.json.JSONObject;

/**
 * 
 * @author cloudboost
 *
 */
class SearchFilter{
	
	JSONObject bool;
	ArrayList<String> $include;
	ArrayList<Object> must;
	ArrayList<Object> should;
	ArrayList<Object> must_not;
	
	/**
	 * Constructor
	 */
	SearchFilter(){
		bool = new JSONObject();
		$include = new ArrayList<String>();
		must = new ArrayList<Object>();
		should = new ArrayList<Object>();
		must_not = new ArrayList<Object>();
		
		this.bool.put("must", this.must);
		this.bool.put("should", this.should);
		this.bool.put("must_not", this.must_not);
		
	}
	
	/**
	 * 
	 * Not Equal To
	 * 
	 * @param columnName
	 * @param data
	 * @return
	 */
	SearchFilter notEqualTo(String columnName, Object data){
		if (columnName.equals("id") || columnName.equals("isSearchable") || columnName.equals("expires"))
	        columnName = "_" + columnName;
		
		JSONObject term = new JSONObject();
		JSONObject column = new JSONObject();
		column.put(columnName, data);
		term.put("term", column);
		
		this.must_not.add(term);
		this.bool.put("must_not", this.must_not);
		
		return this;
	}
	
	/**
	 * 
	 * Not Equal To Overload
	 * 
	 * @param columnName
	 * @param data
	 * @return
	 */
	SearchFilter notEqualTo(String columnName, Object[] data){
		
		if (columnName.equals("id") || columnName.equals("isSearchable") || columnName.equals("expires"))
	        columnName = "_" + columnName;
		
		JSONObject term = new JSONObject();
		JSONObject column = new JSONObject();
		column.put(columnName, data);
		term.put("terms", column);
		
		this.must_not.add(term);
		this.bool.put("must_not", this.must_not);
		
		return this;
	}
	
	/**
	 * 
	 * Equal To
	 * 
	 * @param columnName
	 * @param data
	 * @return
	 */
	SearchFilter equalTo(String columnName, Object data){
		if (columnName.equals("id") || columnName.equals("isSearchable") || columnName.equals("expires"))
	        columnName = "_" + columnName;
		
		JSONObject term = new JSONObject();
		JSONObject column = new JSONObject();
		column.put(columnName, data);
		term.put("term", column);
		
		this.must.add(term);
		this.bool.put("must", this.must);
		
		return this;
	}
	
	/**
	 * 
	 * Equal To Overload
	 * 
	 * @param columnName
	 * @param data
	 * @return
	 */
	SearchFilter equalTo(String columnName, Object[] data){
		if (columnName.equals("id") || columnName.equals("isSearchable") || columnName.equals("expires"))
	        columnName = "_" + columnName;
		
		JSONObject term = new JSONObject();
		JSONObject column = new JSONObject();
		column.put(columnName, data);
		term.put("terms", column);
		
		this.must.add(term);
		this.bool.put("must", this.must);
		
		return this;
	}
	
	
	/**
	 * 
	 * Exists
	 * 
	 * @param columnName
	 * @return
	 */
	SearchFilter exists(String columnName){
		if (columnName.equals("id") || columnName.equals("isSearchable") || columnName.equals("expires"))
	        columnName = "_" + columnName;
		
		JSONObject obj = new JSONObject();
		JSONObject field = new JSONObject();
		field.put("field", columnName);
		obj.put("exists", field);
		
		this.must.add(obj);
		this.bool.put("must", this.must);
		
		return this;
	}
	
	/**
	 * 
	 * Does Not Exists
	 * 
	 * @param columnName
	 * @return
	 */
	SearchFilter doesNotExists(String columnName){
		
		if (columnName.equals("id") || columnName.equals("isSearchable") || columnName.equals("expires"))
	        columnName = "_" + columnName;
		
		JSONObject obj = new JSONObject();
		JSONObject field = new JSONObject();
		field.put("field", columnName);
		obj.put("missing", field);
		
		this.must.add(obj);
		this.bool.put("must", this.must);
		return this;
	}
	
	/**
	 * 
	 * Greater Than Equal To
	 * 
	 * @param columnName
	 * @param data
	 * @return
	 */
	SearchFilter greaterThanEqualTo(String columnName, Object data){
		if (columnName.equals("id") || columnName.equals("isSearchable") || columnName.equals("expires"))
	        columnName = "_" + columnName;
		
		JSONObject obj = new JSONObject();
		JSONObject range = new JSONObject();
		JSONObject column = new JSONObject();
		
		column.put("gte", data);
		range.put(columnName, column);
		obj.put("range", range);
		
		this.must.add(obj);
		this.bool.put("must", this.must);
		
		return this;
	}
	
	/**
	 * 
	 * Greater Than
	 * 
	 * @param columnName
	 * @param data
	 * @return
	 */
	SearchFilter greaterThan(String columnName, Object data){
		if (columnName.equals("id") || columnName.equals("isSearchable") || columnName.equals("expires"))
	        columnName = "_" + columnName;
		
		JSONObject obj = new JSONObject();
		JSONObject range = new JSONObject();
		JSONObject column = new JSONObject();
		
		column.put("gt", data);
		range.put(columnName, column);
		obj.put("range", range);
		
		this.must.add(obj);
		this.bool.put("must", this.must);
		
		return this;
	}
	
	/**
	 * 
	 * Less Than
	 * 
	 * @param columnName
	 * @param data
	 * @return
	 */
	SearchFilter lessThan(String columnName, Object data){
		
		if (columnName.equals("id") || columnName.equals("isSearchable") || columnName.equals("expires"))
	        columnName = "_" + columnName;
		
		JSONObject obj = new JSONObject();
		JSONObject range = new JSONObject();
		JSONObject column = new JSONObject();
		
		column.put("lt", data);
		range.put(columnName, column);
		obj.put("range", range);
		
		this.must.add(obj);
		this.bool.put("must", this.must);
		
		return this;
	}
	
	/**
	 * 
	 * Less Than Or Equal To
	 * 
	 * @param columnName
	 * @param data
	 * @return
	 */
	SearchFilter lessThanOrEqualTo(String columnName, Object data){
		if (columnName.equals("id") || columnName.equals("isSearchable") || columnName.equals("expires"))
	        columnName = "_" + columnName;
		
		JSONObject obj = new JSONObject();
		JSONObject range = new JSONObject();
		JSONObject column = new JSONObject();
		
		column.put("lte", data);
		range.put(columnName, column);
		obj.put("range", range);
		
		this.must.add(obj);
		this.bool.put("must", this.must);
		
		return this;
	}
	
	/**
	 * 
	 * AND
	 * 
	 * @param object
	 * @return
	 * @throws CloudException 
	 */
	SearchFilter and(SearchFilter object) throws CloudException{
		if(object.$include.size() > 0){
			throw new CloudException("You cannot have an include over AND. Have an CloudSearch Include over parent SearchFilter instead");
		}
		
		object.$include.clear();
		
		this.must.add(object);
		this.bool.put("must", this.must);
		
		return this;
	}
	
	/**
	 * 
	 * OR
	 * 
	 * @param object
	 * @return
	 * @throws CloudException 
	 */
	SearchFilter or(SearchFilter object) throws CloudException{
		if(object.$include.size() > 0){
			throw new CloudException("You cannot have an include over OR. Have an CloudSearch Include over parent SearchFilter instead");
		}
		
		object.$include.clear();
		
		this.should.add(object);
		this.bool.put("should", this.should);
		
		return this;
	}
	
	/**
	 * 
	 * NOT
	 * 
	 * @param object
	 * @return
	 * @throws CloudException 
	 */
	SearchFilter not(SearchFilter object) throws CloudException{
		if(object.$include.size() > 0){
			throw new CloudException("You cannot have an include over NOT. Have an CloudSearch Include over parent SearchFilter instead");
		}
		
		object.$include.clear();
		
		this.must_not.add(object);
		this.bool.put("must_not", this.must_not);
		
		return this;
	}
	
	/**
	 * 
	 * Include
	 * 
	 * @param columnName
	 * @return
	 */
	SearchFilter include(String columnName){
		if (columnName.equals("id") || columnName.equals("expires"))
	        columnName = "_" + columnName;
		
		this.$include.add(columnName);
		
		return this;
	}
}