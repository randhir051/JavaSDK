package io.cloudboost;

import io.cloudboost.beans.CBResponse;
import io.cloudboost.json.JSONArray;
import io.cloudboost.json.JSONException;
import io.cloudboost.json.JSONObject;
import io.cloudboost.util.CBParser;
import io.cloudboost.util.CloudSocket;
import io.socket.client.Ack;
import io.socket.emitter.Emitter;
import android.app.Activity;  
import android.content.Context;  
import android.content.SharedPreferences;  
import android.content.pm.PackageInfo;  
import android.content.pm.PackageManager.NameNotFoundException;  
import android.os.AsyncTask;  
import android.util.Log;  
import com.google.android.gms.common.ConnectionResult;  
import com.google.android.gms.common.GooglePlayServicesUtil;  
import com.google.android.gms.gcm.GoogleCloudMessaging;  
import com.google.android.gms.iid.InstanceID;  
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CloudPush{
    
    protected JSONObject document;
    public static final String TAG = "CBPush";
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration";
    private static String PROPERTY_APP_VERSION = "version";
    private final static int PLAY_SERVICE = 9000;
    private GoogleCloudMessaging gcm;
    private static String registrationId;
    private String senderId;
    private Activity activity;
    private CloudPush thisObj;
    
    public CloudPush(Activity activity){
        
        document = new JSONObject();
        try{
            
            
        } catch (JSONException e){
            e.printStackTrace();
        }
        this.activity = activity;
        this.gcm = GoogleCloudMessaging.getInstance(activity);
        
        registerDevice(new CloudPush.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                Log.d("Registration id", registrationId);
                this.registrationId = registarationId;
                //send this registrationId to your node server
                //timezone
                //type = android
                
            }
            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
            }
        });
    }
    
    
    public CloudPush(Activity activity, String senderId, String APIKey){
        
        this.senderId = senderId;
        this.APIKey = APIKey;
        this.activity = activity;
        this(activity);
    }
       
    public static void subscribe(String[] list, final CloudStringCallback callbackObject) throws CloudException {
        
        if (CloudApp.getAppId() == null) {
			throw new CloudException("App Id is null");
		}
        
        JSONObject params = new JSONObject();
		try {
            params.put("key", CloudApp.getAppKey());
            params.put("channelList", list);
            String url = CloudApp.getPushUrl()+"/subscribe/"+CloudApp.getAppId()+"/"+ CloudPush.registarationId;
		    CBResponse response=CBParser.callJson(url, "POST", params);
			if(response.getStatusCode() == 200){
				String res = response.getResponseBody();
				callbackObject.done(res, null);
				
			}else{
				
				CloudException e = new CloudException(response.getResponseBody());
				callbackObject.done((String)null, e);
			}
			
		} catch (JSONException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done((String)null, e1);
			e.printStackTrace();
		}        
    }
    
    public static string[] subscribedChannelList(final CloudStringArrayCallback callbackObject) throws CloudException {
        
        if (CloudApp.getAppId() == null) {
			throw new CloudException("App Id is null");
		}
        
        JSONObject params = new JSONObject();
		try {
            params.put("key", CloudApp.getAppKey());
            String url = CloudApp.getPushUrl()+"/subscribe/list/"+CloudApp.getAppId()+"/"+ CloudPush.registarationId;
		    CBResponse response=CBParser.callJson(url, "POST", params);
			if(response.getStatusCode() == 200){
				String res = response.getResponseBody();
				callbackObject.done(res, null);
				
			}else{
				
				CloudException e = new CloudException(response.getResponseBody());
				callbackObject.done((String[])null, e);
			}
			
		} catch (JSONException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done((String[])null, e1);
			e.printStackTrace();
		}        
    }
    
    public static void unsubscribe(String[] list){
        if (CloudApp.getAppId() == null) {
			throw new CloudException("App Id is null");
		}
        
        JSONObject params = new JSONObject();
		try {
            params.put("key", CloudApp.getAppKey());
            params.put("channelList", list);
            String url = CloudApp.getPushUrl()+"/unsubscribe/"+CloudApp.getAppId()+"/"+ CloudPush.registarationId;
		    CBResponse response=CBParser.callJson(url, "POST", params);
			if(response.getStatusCode() == 200){
				String res = response.getResponseBody();
				callbackObject.done(res, null);
				
			}else{
				
				CloudException e = new CloudException(response.getResponseBody());
				callbackObject.done((String)null, e);
			}
			
		} catch (JSONException e) {
			CloudException e1 = new CloudException(e.toString());
			callbackObject.done((String)null, e1);
			e.printStackTrace();
		}       
    }
    
    public void setChannel(String[] list){
        
    }
    
    public void setChannel(String channel){
        
    }
    
    public void setMessage(String message){
        
    }
    
    public void setData(JSONObject object){
        
    }
    
    public void send(final CloudObjectCallback callbackObject)throws CloudException {
        if (CloudApp.getAppId() == null) {
			throw new CloudException("App Id is null");
		}
		JSONObject data = new JSONObject();
		String url = null;
		CBResponse response = null;
        try {
            data.put("document", document);
			data.put("key", CloudApp.getAppKey());
			url = CloudApp.getPushUrl() + "/send/"+CloudApp.getAppId();
			response = CBParser.callJson(url, "PUT", data);
            if (response.getStatusCode() == 200) {
				String responseBody = response.getResponseBody();
				JSONObject body = new JSONObject(responseBody);
				thisObj = new CloudPush().toString());
				thisObj.document = body;
				callbackObject.done(thisObj, null);
			} else {
				CloudException e = new CloudException(
				response.getStatusMessage());
				callbackObject.done(null, e);
			}
        } catch (Exception e1) {
            e1.printStackTrace();
			CloudException e = new CloudException(e1.getMessage());
			callbackObject.done(null, e);
        }
    }
    
    public static int getAppVersion(Context context){
        try{
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);  
            return packageInfo.versionCode;    
        }catch(NameNotFoundException e){
            throw new RuntimeException("Could not get package name: "+e);
        }
    }
    
    public void registerDevice(final RegistrationCompletedHandler handler){
        if(checkPlayServices()){
            registrationId = getRegistrationId(getContext());
            if(registrationId.isEmpty()){
                register(handler);
            }else{
                Log.i(TAG, registrationId);
                handler.onSuccess(registrationId, false);
            }
        }else{
            Log.i(TAG, "No valid google play service");
        }
    }
    
	private void register(final RegistrationCompletedHandler handler) {  
           new AsyncTask<Void, Void, String>() {  
                @Override  
                protected String doInBackground(Void... params) {  
                     try {  
                          if (gcm == null) {  
                               gcm = GoogleCloudMessaging.getInstance(getContext());  
                          }  
                          InstanceID instanceID = InstanceID.getInstance(getContext());  
                          registrationId = instanceID.getToken(projectNumber, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);  
                          Log.i(TAG, registrationId);  
                          // Persist the regID - no need to register again.  
                          storeRegistrationId(getContext(), registarationId);  
                     } catch (IOException ex) {  
                          // If there is an error, don't just keep trying to register.  
                          // Require the user to click a button again, or perform  
                          // exponential back-off.  
                          handler.onFailure("Error :" + ex.getMessage());  
                     }  
                     return registrationId;  
                }  
                @Override  
                protected void onPostExecute(String regId) {  
                  if (regId != null) {  
                    handler.onSuccess(regId, true);  
                  }  
                }  
           }.execute(null, null, null);  
      }  
      /**  
       * Gets the current registration ID for application on GCM service.  
       * <p>  
       * If result is empty, the app needs to register.  
       *  
       * @return registration ID, or empty string if there is no existing  
       *     registration ID.  
       */  
      private String getRegistrationId(Context context) {  
            final SharedPreferences prefs = getGCMPreferences(context);  
            String registrationId = prefs.getString(PROPERTY_REG_ID, "");  
            if (registrationId.isEmpty()) {  
                Log.i(TAG, "Registration not found.");  
                return "";  
            }  
            // Check if app was updated; if so, it must clear the registration ID  
            // since the existing regID is not guaranteed to work with the new  
            // app version.  
            int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);  
            int currentVersion = getAppVersion(context);  
            if (registeredVersion != currentVersion) {  
                Log.i(TAG, "App version changed.");  
                return "";  
            }  
            return registrationId;  
      }  
      /**  
       * Stores the registration ID and app versionCode in the application's  
       * {@code SharedPreferences}.  
       *  
       * @param context application's context.  
       * @param regId registration ID  
       */  
      private void storeRegistrationId(Context context, String regId) {  
            final SharedPreferences prefs = getGCMPreferences(context);  
            int appVersion = getAppVersion(context);  
            Log.i(TAG, "Saving regId on app version " + appVersion);  
            SharedPreferences.Editor editor = prefs.edit();  
            editor.putString(PROPERTY_REG_ID, regId);  
            editor.putInt(PROPERTY_APP_VERSION, appVersion);  
            editor.commit();   
      }  
      private SharedPreferences getGCMPreferences(Context context) {  
        // This sample app persists the registration ID in shared preferences, but  
        // how you store the regID in your app is up to you.  
        return getContext().getSharedPreferences(context.getPackageName(),  
            Context.MODE_PRIVATE);  
      }  
      /**  
       * Check the device to make sure it has the Google Play Services APK. If  
       * it doesn't, display a dialog that allows users to download the APK from  
       * the Google Play Store or enable it in the device's system settings.  
       */  
      private boolean checkPlayServices() {  
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());  
        if (resultCode != ConnectionResult.SUCCESS) {  
          if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {  
            GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),  
                PLAY_SERVICES_RESOLUTION_REQUEST).show();  
          } else {  
            Log.i(TAG, "This device is not supported.");  
          }  
          return false;  
        }  
        return true;  
      }  
      
      private Context getContext() {  
           return activity;  
      }  
      
      private Activity getActivity() {  
           return activity;  
      }  
      
      public static abstract class RegistrationCompletedHandler {  
           public abstract void onSuccess(String registrationId, boolean isNewRegistration);  
           public void onFailure(String ex) {  
             // If there is an error, don't just keep trying to register.  
             // Require the user to click a button again, or perform  
             // exponential back-off.  
             Log.e(TAG, ex);  
           }  
      } 
      
            
}