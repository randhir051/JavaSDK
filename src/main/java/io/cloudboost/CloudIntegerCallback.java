package io.cloudboost;

public interface CloudIntegerCallback extends CloudCallback<Integer, CloudException>{

	void done(Integer x, CloudException e) throws CloudException;
	
}