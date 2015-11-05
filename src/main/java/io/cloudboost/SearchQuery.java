package io.cloudboost;

import java.util.ArrayList;

import io.cloudboost.json.*;

/**
 * 
 * @author cloudboost
 *
 */
public class SearchQuery{
	
	JSONObject bool;
	JSONObject multi_match ;
	JSONObject match;
	ArrayList<String> $include;
	ArrayList<Object> must;
	ArrayList<Object> should;
	ArrayList<Object> must_not;
	
	public SearchQuery(){
		bool = new JSONObject();
		$include = new ArrayList<String>();
		must = new ArrayList<Object>();
		should = new ArrayList<Object>();
		must_not = new ArrayList<Object>();
		
		try {
			this.bool.put("must", this.must);
		
		this.bool.put("should", this.should);
		this.bool.put("must_not", this.must_not);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * _Build Search Pharse
	 * 
	 * @param columnName
	 * @param query
	 * @param slop
	 * @param boost
	 * @return
	 */
	public JSONObject _buildSearchPhrase(String columnName, String query, String slop, String boost){
		
		JSONObject obj = this._buildSearchOn(columnName, query, null, null,null,boost);	
		JSONObject column = new JSONObject();
		match = new JSONObject();
		try{
		column.put("query", query);
	    column.put("type", "phrase");
	    if(slop != null){
	    	column.put("slop", slop);
	    }else{
	    	column.put("slop", JSONObject.NULL);
	    }
	    match.put(columnName, column);
	    obj.put("match", match);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return obj;
	}
	
	public JSONObject _buildSearchPhrase(String[] columnName, String query, String slop, String boost){
		
		JSONObject obj = this._buildSearchOn(columnName, query, null, null,null,boost);	
		multi_match = new JSONObject();
		try{
		multi_match.put("query", query);
		multi_match.put("type", "phrase");
	    if(slop != null){
	    	multi_match.put("slop", slop);
	    }else{
	    	multi_match.put("slop", JSONObject.NULL);
	    }
	    obj.put("multi_match", multi_match);
} catch (JSONException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		return obj;
	}
	
	/**
	 * 
	 * _Build Best Columns
	 * 
	 * @param columns
	 * @param query
	 * @param fuzziness
	 * @param operator
	 * @param match_percent
	 * @param boost
	 * @return
	 */
	public JSONObject _buildBestColumns(String columnName, String query, String fuzziness, String operator, String match_percent, String boost){
		
		JSONObject obj = this._buildSearchOn(columnName, query, fuzziness, operator, match_percent, boost);
		
		JSONObject column = new JSONObject();
		try{
	    column.put("type", "best_fields");
	    match.put(columnName, column);
	    obj.put("match", match);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	    return obj;
	}
	
	public JSONObject _buildBestColumns(String[] columnName, String query, String fuzziness, String operator, String match_percent, String boost){
		
		JSONObject obj = this._buildSearchOn(columnName, query, fuzziness, operator, match_percent, boost);
		try{
		multi_match.put("type", "best_fields");
	    obj.put("multi_match",multi_match);
} catch (JSONException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	    return obj;
	}
	
	/**
	 * 
	 * _Build Most Columns
	 * 
	 * @param columns
	 * @param query
	 * @param fuzziness
	 * @param operator
	 * @param match_percent
	 * @param boost
	 * @return
	 */
	public JSONObject _buildMostColumns(String columnName, String query, String fuzziness,  String operator, String match_percent, String boost){
		
		JSONObject obj = this._buildSearchOn(columnName, query, fuzziness, operator, match_percent, boost);

		JSONObject column = new JSONObject();
		 match = new JSONObject();
		 try{
	    column.put("type", "most_fields");
	    match.put(columnName, column);
	    obj.put("match", match);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	    return obj;
	}
	
	public JSONObject _buildMostColumns(String[] columnName, String query, String fuzziness,  String operator, String match_percent, String boost){
		
		JSONObject obj = this._buildSearchOn(columnName, query, fuzziness, operator, match_percent, boost);
		try{
		multi_match.put("type", "most_fields");
	    obj.put("multi_match", multi_match);
} catch (JSONException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	    return obj;
	}
	
	/**
	 * 
	 * _Build Search On
	 * 	
	 * @param columns
	 * @param query
	 * @param fuzziness
	 * @param operator
	 * @param match_percent
	 * @param boost
	 * @return
	 */
	public JSONObject _buildSearchOn(String columnName, String query, String fuzziness, String operator, String match_percent, String boost){
		
		JSONObject obj = new JSONObject();
		match = new JSONObject();
		JSONObject column = new JSONObject();
		try{
		column.put("query", query);
		
		if(operator != null){
			column.put("operator", operator);
		}else{
			column.put("operator", JSONObject.NULL);
		}
       

        if(match_percent != null){
        	column.put("minimum_should_match", match_percent);
        }else{
        	column.put("minimum_should_match", JSONObject.NULL);
        }

        if(boost != null){
        	column.put("boost", boost);
        }else{
        	column.put("boost", JSONObject.NULL);
        }

        if(fuzziness != null){
        	column.put("fuzziness", fuzziness);
        }else{
        	column.put("fuzziness", JSONObject.NULL);
        }
        
        match.put(columnName, column);
        obj.put("match", match);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return obj;
	}
	
	public JSONObject _buildSearchOn(String[] columnName, String query, String fuzziness, String operator, String match_percent, String boost){
		
		JSONObject obj = new JSONObject();
		multi_match = new JSONObject();
		try{
		multi_match.put("query", query);
		multi_match.put("fields", columnName);
		if(operator != null){
			multi_match.put("operator", operator);
		}else{
			multi_match.put("operator", JSONObject.NULL);
		}
       
        if(match_percent != null){
        	multi_match.put("minimum_should_match", match_percent);
        }else{
        	multi_match.put("minimum_should_match", JSONObject.NULL);
        }

        if(boost != null){
        	multi_match.put("boost", boost);
        }else{
        	multi_match.put("boost", JSONObject.NULL);
        }

        if(fuzziness != null){
        	multi_match.put("fuzziness", fuzziness);
        }else{
        	multi_match.put("fuzziness", JSONObject.NULL);
        }
     
        obj.put("multi_match", multi_match);
} catch (JSONException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		return obj;
	}

	/**
	 * 
	 * Search On
	 * 
	 * @param columns
	 * @param query
	 * @param fuzziness
	 * @param all_words
	 * @param match_percent
	 * @param priority
	 * @return
	 */
	public SearchQuery searchOn(String columns, Object  query, String fuzziness, String all_words, String match_percent, String priority){
		
		if(all_words != null){
	        all_words = "and";
	    }
	        
	    JSONObject obj = this._buildSearchOn(columns,query.toString(), fuzziness,all_words,match_percent,priority);
	    
	    
	    this.should.add(obj);
		try {
			this.bool.put("should", this.should);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}
	
	/**
	 * 
	 * pharse
	 * 
	 * @param columns
	 * @param query
	 * @param fuzziness
	 * @param priority
	 * @return
	 */
	public SearchQuery phrase(String columns, Object  query, String fuzziness, String priority){
		
		JSONObject obj = this._buildSearchPhrase(columns, query.toString(),fuzziness, priority);
		
		this.should.add(obj);
		try {
			this.bool.put("should", this.should);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
		return this;
	}
	
	/**
	 * 
	 * Best Columns
	 * 
	 * @param columns
	 * @param query
	 * @param fuzziness
	 * @param all_words
	 * @param match_percent
	 * @param priority
	 * @return
	 * @throws CloudException 
	 */
	public SearchQuery bestColumns(String[] columns, Object query, String fuzziness, String all_words, String match_percent, String priority) throws CloudException{
		
		if(columns.length < 2) {
			throw new CloudException("There should be more than one columns in-order to use this function");
		}
		
		if(all_words != null){
	        all_words = "and";
	    }
	    
		JSONObject obj = this._buildBestColumns(columns, query.toString(), fuzziness, all_words, match_percent, priority);
		
		this.should.add(obj);
		try {
			this.bool.put("should", this.should);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this;
	}
	
	/**
	 * 
	 * Most Columns
	 * 
	 * @param columns
	 * @param query
	 * @param fuzziness
	 * @param all_words
	 * @param match_percent
	 * @param priority
	 * @return
	 * @throws CloudException 
	 */
	public SearchQuery mostColumns(String[] columns, Object query, String fuzziness, String all_words, String match_percent, String priority) throws CloudException{
		if(columns.length < 2) {
			throw new CloudException("There should be more than one columns in-order to use this function");
		}
		
		if(all_words != null){
	        all_words = "and";
	    }
	    
		JSONObject obj = this._buildMostColumns(columns, query.toString(), fuzziness, all_words, match_percent, priority);
		
		this.should.add(obj);
		try {
			this.bool.put("should", this.should);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this;
	}
	
	/**
	 * 
	 * Starts With
	 * 
	 * @param column
	 * @param value
	 * @param priority
	 * @return
	 */
	public SearchQuery startsWith(String columnName, String value, String priority){
		
		JSONObject obj = new JSONObject();
		JSONObject prefix = new JSONObject();
		JSONObject column = new JSONObject();
		try{
		column.put("value", value);	    
	    if(priority != null){
	    	column.put("boost", priority);
	    }
	    prefix.put(columnName, column);
		obj.put("prefix", prefix);

		this.must.add(obj);
		this.bool.put("must", this.must);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return this;
	}
	
	/**
	 * 
	 * Wild Cards
	 * 
	 * @param column
	 * @param value
	 * @param priority
	 * @return
	 */
	public SearchQuery wildcard(String columnName, String value, String priority){
		
		JSONObject obj = new JSONObject();
		JSONObject wildcard = new JSONObject();
		JSONObject column = new JSONObject();
		try{
		column.put("value", value);	    
	    if(priority != null){
	    	column.put("boost", priority);
	    }
	    wildcard.put(columnName, column);
		obj.put("wildcard", wildcard);

		this.should.add(obj);
		this.bool.put("should", this.should);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return this;
	}
	
	/**
	 * 
	 * Reg Exp
	 * 
	 * @param column
	 * @param value
	 * @param priority
	 * @return
	 */
	public SearchQuery regexp(String columnName, String value, String priority){
		
		JSONObject obj = new JSONObject();
		JSONObject regexp = new JSONObject();
		JSONObject column = new JSONObject();
		try{
		column.put("value", value);	    
	    if(priority != null){
	    	column.put("boost", priority);
	    }
	    regexp.put(columnName, column);
		obj.put("regexp", regexp);

		this.must.add(obj);
		this.bool.put("must", this.must);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
		return this;
	}
	
	/**
	 * 
	 * AND
	 * 
	 * @param object
	 * @return
	 */
	public SearchQuery and(SearchQuery object){
		
		this.must.add(object);
		try {
			this.bool.put("must", must);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		return this;
	}
	
	/**
	 * OR
	 * 
	 * @param object
	 * @return
	 */
	public SearchQuery or(SearchQuery object){
		
		this.should.add(object);
		try {
			this.bool.put("should", this.should);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this;
	}
	
	/**
	 * 
	 * NOT
	 * 
	 * @param object
	 * @return
	 */
	public SearchQuery not(SearchQuery object){
		
		this.must_not.add(object);
		try {
			this.bool.put("must_not", this.must_not);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this;
	}
}