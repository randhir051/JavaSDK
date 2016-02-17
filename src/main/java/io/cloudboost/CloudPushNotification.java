package io.cloudboost;
import android.os.Bundle;
import com.google.android.gms.gcm.GcmListenerService;
/**
 * 
 * @author cloudboost
 *
 */
  
public interface CloudPushNotification extends GcmListenerService {

	@Override
    public void onMessageReceived(String from, Bundle data);
    
}