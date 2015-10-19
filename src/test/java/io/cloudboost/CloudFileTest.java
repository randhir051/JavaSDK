package io.cloudboost;
import java.io.IOException;
import junit.framework.Assert;

import org.junit.Test;
/**
 * 
 * @author cloudboost
 *
 */
public class CloudFileTest{
	void initialize(){
		CloudApp.init("sample123","9SPxp6D3OPWvxj0asw5ryA==");
	}
	
	@Test(timeout=50000)
	public void saveFileFromData() throws IOException, CloudException {
		initialize();
		CloudFile file = new CloudFile("abc.txt", "Hello World", "txt");
		file.save(new CloudStringCallback(){
			@Override
			public void done(String x, CloudException e) throws CloudException {
				if(e != null){
					Assert.fail(e.getMessage());
				}
				System.out.println(x);
			}
		});
	}
	
	@Test(timeout=50000)
	public void saveFileFromURL() throws IOException, CloudException {
		initialize();
		CloudFile file = new CloudFile("http://static.giantbomb.com/uploads/scale_small/0/316/520157-apple_logo_dec07.jpg");
		file.save(new CloudStringCallback(){
			@Override
			public void done(String x, CloudException e) throws CloudException {
				if(e != null){
					Assert.fail(e.getMessage());
				}
				System.out.println(x);
			}
		});
	}
}