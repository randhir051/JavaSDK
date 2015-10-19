package io.cloudboost;
/**
 * 
 * @author cloudboost
 *
 */
public interface CloudRoleCallback extends CloudCallback<CloudRole, CloudException>{

	void done(CloudRole  role, CloudException e) throws CloudException;
	
}