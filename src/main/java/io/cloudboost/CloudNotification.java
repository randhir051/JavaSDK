package io.cloudboost;

import io.socket.emitter.Emitter;
import io.cloudboost.util.CloudSocket;
/**
 * 
 * @author cloudboost
 *
 */
public class CloudNotification{
	
	/**
	 * 
	 * CloudNotification On
	 * 
	 * 
	 * @param channelName
	 * @param callbackObject
	 * @throws CloudException
	 */
	public static void on(String channelName, final CloudNotificationCallback callbackObject) throws CloudException{
		if(CloudApp.getAppId() == null){
			throw new CloudException("CloudApp id is null");
		}
		if(CloudApp.getAppKey() == null){
			throw new CloudException("CloudApp key is null");
		}
		CloudSocket.getSocket().connect();
		CloudSocket.getSocket().emit("join-custom-channel",CloudApp.getAppId()+channelName );
		CloudSocket.getSocket().on(CloudApp.getAppId()+channelName, new Emitter.Listener() {
			@Override
			public void call(final Object... args) {
				try {
					callbackObject.done(args[0], null);
				} catch (CloudException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * 
	 * Publish
	 * 
	 * 
	 * @param channelName
	 * @param data
	 * @throws CloudException
	 */
	public static void publish(String channelName, Object data ) throws CloudException{
		if(CloudApp.getAppId() == null){
			throw new CloudException("CloudApp id is null");
		}
		if(CloudApp.getAppKey() == null){
			throw new CloudException("CloudApp key is null");
		}
		CloudSocket.getSocket().emit("publish-custom-channel", "{\"channel\":\""+CloudApp.getAppId()+channelName+"\",\"data\":\""+data+"\"" );
	}
	
	public static void off(String channelName, final CloudStringCallback callbackObject) throws CloudException{
		if(CloudApp.getAppId() == null){
			throw new CloudException("CloudApp id is null");
		}
		if(CloudApp.getAppKey() == null){
			throw new CloudException("CloudApp key is null");
		}
		CloudSocket.getSocket().disconnect();
		CloudSocket.getSocket().emit("leave-custom-channel",CloudApp.getAppId()+channelName );
		CloudSocket.getSocket().disconnect();
		CloudSocket.getSocket().off(CloudApp.getAppId()+channelName, new Emitter.Listener() {
			@Override
			public void call(final Object... args) {
				try {
					callbackObject.done(null, null);
				} catch (CloudException e) {
					e.printStackTrace();
				}
			}
		});
	}
}