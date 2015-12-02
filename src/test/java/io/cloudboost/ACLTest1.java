package io.cloudboost;

import junit.framework.Assert;

import org.junit.Test;

public class ACLTest1 {
	void initialize(){
		CloudApp.init("travis123", "6dzZJ1e6ofDamGsdgwxLlQ==");
	}
	@Test(timeout=30000)
	public void shouldUpdateACL() throws CloudException{
		initialize();
		CloudObject ob=new CloudObject("Employee");
		ob.acl.setRoleWriteAccess("x", true);
		ob.acl.setPublicWriteAccess(true);
		ob.save(new CloudObjectCallback() {
			
			@Override
			public void done(CloudObject x, CloudException t) throws CloudException {
				x.acl.setRoleWriteAccess("y", true);
				x.acl.setPublicWriteAccess(true);
				x.save(new CloudObjectCallback() {
					
					@Override
					public void done(CloudObject x, CloudException t) throws CloudException {
						String id=x.getId();
						CloudQuery q=new CloudQuery("Employee");
						q.findById(id, new CloudObjectCallback() {
							
							@Override
							public void done(CloudObject x, CloudException t) throws CloudException {
								if(t!=null)
									Assert.fail(t.getMessage());
								else{
								System.out.println(x.document.toString());
								}
								
							}
						});
						
					}
				});
				
			}
		});
		
	}
	@Test(timeout=30000)
	public void shouldSetThePublicWriteAccess(){
		
	}
	@Test(timeout=30000)
	public void shouldPersistACLObjectInsideCloudObjectAfterSave(){
		
	}
	@Test(timeout=30000)
	public void shouldSetPublicReadAccess(){
		
	}
	@Test(timeout=30000)
	public void shouldCreateNewUser(){
		
	}
	@Test(timeout=30000)
	public void shouldSetUserReadAccess(){
		
	}
	@Test(timeout=30000)
	public void shouldAllowUsersOfRoleToWrite(){
		
	}
	@Test(timeout=30000)
	public void shouldAllowUsersOfRoleToRead(){
		
	}
}
