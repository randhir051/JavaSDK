package io.cloudboost;

public interface CloudStringCallback extends CloudCallback<String, CloudException>{

	void done(String x, CloudException e) throws CloudException;
	
}