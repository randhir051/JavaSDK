package io.cloudboost;
/**
 * 
 * @author cloudboost
 *
 */
public interface CloudObjectCallback extends CloudCallback<CloudPush, CloudException>{

	@Override
	void done(CloudPush x, CloudException t) throws CloudException;
	
}