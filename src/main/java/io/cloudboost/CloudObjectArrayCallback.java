package io.cloudboost;
/**
 * 
 * @author cloudboost
 *
 */
public interface CloudObjectArrayCallback extends CloudCallback<CloudObject[], CloudException>{

	void done(CloudObject[]  x, CloudException t) throws CloudException;
	
}
