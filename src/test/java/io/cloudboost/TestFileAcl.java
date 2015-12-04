package io.cloudboost;

import java.io.IOException;

import junit.framework.Assert;

import org.json.JSONException;
import org.junit.Test;

public class TestFileAcl {
	void initialize(){
		CloudApp.init("travis123", "6dzZJ1e6ofDamGsdgwxLlQ==");
	}
	@Test(timeout=30000)
	public void shouldNotGetFileWithNoAccess() throws CloudException, IOException, JSONException{
		initialize();
		CloudFile file=new CloudFile("noread", "you aint reading me", "txt");
		ACL acl=file.getAcl();
		acl.setPublicReadAccess(false);
		file.setAcl(acl);
		file.save(new CloudFileCallback() {
			
			@Override
			public void done(CloudFile x, CloudException t) throws CloudException {
				x.fetch(new CloudFileArrayCallback() {
					
					@Override
					public void done(CloudFile[] x, CloudException t) throws CloudException {
						if(t!=null)
							Assert.fail(t.getMessage());
						Assert.assertTrue(x.length==0);
					}
				});
				
			}
		});
		
	}
	@Test(timeout=30000)
	public void shouldNotGetFileContentWithNoAccess() throws CloudException, IOException, JSONException{
		initialize();
		CloudFile file=new CloudFile("nocontent", "you aint reading me", "txt");
		ACL acl=file.getAcl();
		acl.setPublicReadAccess(false);
		file.setAcl(acl);
		file.save(new CloudFileCallback() {
			
			@Override
			public void done(CloudFile x, CloudException t) throws CloudException {
				x.getFileContent(new ObjectCallback() {
					
					@Override
					public void done(Object x, CloudException t) throws CloudException {
						Assert.assertTrue(t!=null);
							
						
					}
				});
				
			}
		});
		
	}
	@Test(timeout=30000)
	public void shouldNotDeleteFileWithNoAccess() throws CloudException, IOException, JSONException{
		initialize();
		CloudFile file=new CloudFile("nocontent", "you aint reading me", "txt");
		ACL acl=file.getAcl();
		acl.setPublicWriteAccess(false);
		file.setAcl(acl);
		file.save(new CloudFileCallback() {
			
			@Override
			public void done(CloudFile x, CloudException t) throws CloudException {
				x.delete(new CloudStringCallback() {
					
					@Override
					public void done(String x, CloudException t) throws CloudException {
						
						Assert.assertTrue(t!=null);
							
						
					}
				});
				
			}
		});
		
	}
}
