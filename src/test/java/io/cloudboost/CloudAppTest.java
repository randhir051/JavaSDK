package io.cloudboost;
import io.cloudboost.Column.DataType;
import junit.framework.Assert;

import org.junit.Test;

public class CloudAppTest{
	
	//CloudApp Initialization Test
	@Test(timeout=2000)
	public void cloudAppTest(){
		CloudApp.init("sample123", "9SPxp6D3OPWvxj0asw5ryA==");
		Assert.assertEquals("sample123", CloudApp.getAppId());
		Assert.assertEquals("9SPxp6D3OPWvxj0asw5ryA==", CloudApp.getAppKey());
	}
	@Test(timeout=50000)
	public void initialize() throws CloudException {
//master=MjFWX9D3JqTa76tcEHt9GL2ITB8Gzsp68S1+3oq7CBE=
		//client=mLiJB380x9fhPRCjCGmGRg==
//		CloudApp.init("bengi",
//				"ailFnQf+q102UpB86ZZBKg==");
//		CloudObject ob=new CloudObject("visitors");
//		System.out.println("gonna save");
//		ob.save(new CloudObjectCallback() {
//			
//			@Override
//			public void done(CloudObject x, CloudException t) throws CloudException {
//				if(t!=null)
//					System.out.println("error="+t.getMessage());
//				else if(x!=null)
//					System.out.println("clear");
//			}
//		});
		Assert.assertTrue(true);
	}

	
}
