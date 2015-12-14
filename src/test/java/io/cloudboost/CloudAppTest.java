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
	void initialize() {
		CloudApp.init("bengi123",
				"MjFWX9D3JqTa76tcEHt9GL2ITB8Gzsp68S1+3oq7CBE=");
	}

	
}
