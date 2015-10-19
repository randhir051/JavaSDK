package io.cloudboost;
/**
 * 
 * @author cloudboost
 *
 */
public interface CloudUserCallback extends CloudCallback<CloudUser, CloudException>{

	void done(CloudUser user, CloudException e) throws CloudException;
	
}