package io.cloudboost;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author cloudboost
 *
 */
public class CloudGeoPoint{
	JSONObject document;
	private ArrayList<Double> coordinates;
	
	/**
	 * 
	 * Constructor
	 * 
	 * @param latitude
	 * @param longitude
	 * @throws CloudException
	 */
	public CloudGeoPoint(Double latitude, Double longitude) throws CloudException{
		document = new JSONObject();
		this.coordinates = new ArrayList<Double>();
		document.put("_type", "point");
		document.put("_isModified", true);
		if((latitude >= -90.0 && latitude <= 90.0)&&( longitude >= -180.0 && longitude<=180.0)) {
			this.coordinates.add(latitude);
			this.coordinates.add(longitude);
			this.document.put("coordinates", this.coordinates);
			this.document.put("longitude", longitude);
			this.document.put("latitude", latitude);
	    }else{
	    	throw new CloudException("latitude and longitudes are not in range");
	    }
	}
	
	/**
	 * 
	 * Set Longitude
	 * 
	 * @param longitude
	 * @throws CloudException
	 */
	public void setLongitude(Double longitude) throws CloudException{
		if(longitude >= -180 && longitude <= 180) {
            this.document.put("latitude",longitude);
            JSONArray lat = new JSONArray(this.document.get("coordinates").toString());
            for(int i=0; i<lat.length(); i++){
            	this.coordinates.add((double) lat.getInt(i));
            }

            this.coordinates.set(0, longitude);
            this.document.put("coordinates", this.coordinates);
            this.document.put("_isModified", true);
        }else{
        	throw new CloudException("Longitude is not in Range");
        }
	}
	
	/**
	 * 
	 * Get Longitude
	 * 
	 * @return
	 */
	public Double getLongitude(){
		return this.document.getDouble("longitude");
	}
	
	/**
	 * 
	 * Set Latitute
	 * 
	 * @param latitute
	 * @throws CloudException
	 */
	public void setLatitute(Double latitute) throws CloudException{
		if(latitute >= -90 && latitute <= 90) {
            this.document.put("longitude",latitute);
            JSONArray lat = new JSONArray(this.document.get("coordinates").toString());
            for(int i=0; i<lat.length(); i++){
            	this.coordinates.add((double) lat.getInt(i));
            }
            this.coordinates.set(1, latitute);
            this.document.put("coordinates", this.coordinates);
            this.document.put("_isModified", true);
        }else{
        	throw new CloudException("Latitute is not in Range");
        }
	}
	
	/**
	 * 
	 * Get Latitute
	 * 
	 * @return
	 */
	public Double getLatitute(){
		return this.document.getDouble("latitute");
	}
	
	/**
	 * 
	 * @param point
	 * @return
	 */
	public Double distanceInKMs(CloudGeoPoint point) {

	    int earthRedius = 6371; //in Kilometer
	    return earthRedius * this.greatCircleFormula(point);
	}
	
	/**
	 * 
	 * @param point
	 * @return
	 */
	public Double distanceInMiles(CloudGeoPoint point){

	    int earthRedius = 3959; // in Miles
	    return earthRedius * this.greatCircleFormula(point);

	}

	/**
	 * 
	 * @param point
	 * @return
	 */
	public Double distanceInRadians(CloudGeoPoint point){

	    return this.greatCircleFormula(point);
	}
	/**
	 * 
	 * @param point
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Double greatCircleFormula(CloudGeoPoint point){
		this.coordinates = (ArrayList<Double>) this.document.get("coordinates");
		point.coordinates = (ArrayList<Double>) point.document.get("coordinates");
		
	    Double dLat =toRad(this.coordinates.get(1) - point.coordinates.get(1));
	    Double dLon = toRad(this.coordinates.get(0) - point.coordinates.get(0));
	    
	    Double lat1 = toRad(point.coordinates.get(1));
	    Double lat2 = toRad(this.coordinates.get(1));
	    
	    Double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    
	    return c;
	}
	
	/**
	 * 
	 * @param number
	 * @return
	 */
	private Double toRad(Double number){
		return number * Math.PI / 180;
	}
	
	public static CloudGeoPoint toGeoPoint(Object object) throws JSONException, CloudException{
		JSONObject doc = new JSONObject(object.toString());
		System.out.print(doc.toString());
		CloudGeoPoint loc = new CloudGeoPoint(doc.getDouble("latitude"), doc.getDouble("longitude"));
		loc.document = doc;
		return loc;
	}
	
}