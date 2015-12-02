package io.cloudboost;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

public class CloudExpireTest {
	void initialize(){
		CloudApp.init("travis123", "6dzZJ1e6ofDamGsdgwxLlQ==");
	}
	@Test(timeout=50000)
	public void setExpireInCloudObject() throws CloudException{
		initialize();
		CloudObject ob=new CloudObject("Custom");
		ob.set("newColumn1", "abcd");
	
		ob.save(new CloudObjectCallback() {
			
			@Override
			public void done(CloudObject x, CloudException t) throws CloudException {
				Assert.assertTrue(x!=null);
				
			}
		});
	}
	@Test(timeout=50000)
	public void checkIfExpiredShowsUp() throws CloudException{
		initialize();
		final long now=new Date().getTime();
		CloudQuery ob=new CloudQuery("Custom");
		ob.find(new CloudObjectArrayCallback() {
			
			@Override
			public void done(CloudObject[] x, CloudException t) throws CloudException {
				if(t!=null)
					Assert.fail(t.getMessage());
				if(x.length>0){
					Assert.assertTrue(true);
					
				}else Assert.fail("Nothing retrieved");
				
			}
		});
	}
}
