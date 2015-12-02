package io.cloudboost;

import junit.framework.Assert;

import org.junit.Test;

public class MasterAcl {
	void initialize() {
		CloudApp.init("travis123",
				"vfmMIbP4KaqxihajNqLNFGuub8CIOLREP1oH0QC0qy4=");
	}
	@Test(timeout=30000)
	public void shouldSaveWithMasterWithNoPermission() throws CloudException{
		initialize();
		CloudObject ob=new CloudObject("student4");
		ob.set("age", 10);
		ACL acl=ob.getAcl();
		acl.setPublicReadAccess(false);
		acl.setPublicWriteAccess(false);
		ob.setAcl(acl);
		ob.save(new CloudObjectCallback() {
			
			@Override
			public void done(CloudObject x, CloudException t) throws CloudException {
				x.set("age", 19);
				x.save(new CloudObjectCallback() {
					
					@Override
					public void done(CloudObject x, CloudException t) throws CloudException {
						Assert.assertTrue(x!=null);
						
					}
				});
				
			}
		});
	}
}
