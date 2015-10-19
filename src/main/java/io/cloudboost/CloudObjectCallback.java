package io.cloudboost;
/**
 * 
 * @author cloudboost
 *
 */
public interface CloudObjectCallback extends CloudCallback<CloudObject, CloudException>{

	void done(CloudObject x, CloudException t) throws CloudException;
	
}
