package io.cloudboost;

import org.junit.Test;

public class SocketTest {
	void initialize(){
		CloudApp.init("travis123", "6dzZJ1e6ofDamGsdgwxLlQ==");
	}
	@Test(timeout=30000)
	public void shouldFireEventOnConnect() throws InterruptedException{
		initialize();
		CloudApp.onConnect();
		Thread.sleep(1000);
		CloudApp.connect();		
		Thread.sleep(1000);

	}
	@Test(timeout=30000)
	public void shouldFireEventOnDisconnect() throws InterruptedException{
		initialize();
		CloudApp.onDisconnect();
		Thread.sleep(1000);
		CloudApp.disconnect();		
		Thread.sleep(1000);
	}
}
