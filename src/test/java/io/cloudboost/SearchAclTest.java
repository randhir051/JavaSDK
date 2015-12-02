package io.cloudboost;

import junit.framework.Assert;

import org.junit.Test;

public class SearchAclTest {
	void initialize() {
		CloudApp.init("travis123", "6dzZJ1e6ofDamGsdgwxLlQ==");
	}
	@Test(timeout=30000)
	public void shouldCreateNewUser() throws CloudException{
		initialize();
		CloudUser user=new CloudUser();
		final String name=PrivateMethod._makeString();
		user.set("username", name);
		user.set("password", "abcd");
		user.set("email", PrivateMethod._makeString()+"@gmail.com");
		user.signUp(new CloudUserCallback() {
			
			@Override
			public void done(CloudUser user, CloudException e) throws CloudException {
				if(e!=null)
					Assert.fail(e.getMessage());
				else{
					final String uname=user.getUserName();
					Assert.assertEquals(name, uname);
				
				}
				
			}
		});
	}
	@Test(timeout=30000)
	public void shouldSetPublicReadAccessToFalse() throws CloudException{
		initialize();
		CloudObject ob=new CloudObject("student4");
		ob.set("age", 150);
		ACL acl=ob.getAcl();
		acl.setPublicReadAccess(false);
		ob.setAcl(acl);
		ob.save(new CloudObjectCallback() {
			
			@Override
			public void done(CloudObject x, CloudException t) throws CloudException {
				ACL acl=x.getAcl();
				if(!acl.getAllowedReadUser().contains("all")){
					SearchQuery query=new SearchQuery();
					query.searchOn("age", 150, null, null, null, null);
					CloudSearch search=new CloudSearch("student4", query, null);
					search.search(new CloudObjectArrayCallback() {
						
						@Override
						public void done(CloudObject[] x, CloudException t) throws CloudException {
							for(int i=0;i<x.length;i++){
								CloudObject obj=x[i];
								if(obj.hasKey("age"))
									if(!obj.getAcl().getAllowedReadUser().contains("all"))
										Assert.fail("should not return any items");
							}
							Assert.assertTrue(true);
							
						}
					});
					
				}else Assert.fail("Failed to save ACL");
				
			}
		});
	}
	@Test(timeout=30000)
	public void shouldRetrieveObjectWithReadPermission() throws CloudException{
		initialize();
		CloudObject ob=new CloudObject("student4");
		ob.set("age", 150);
		ob.setAcl(new ACL());
		ob.save(new CloudObjectCallback() {
			
			@Override
			public void done(CloudObject x, CloudException t) throws CloudException {
					SearchQuery query=new SearchQuery();
					query.searchOn("age", 150, null, null, null, null);
					CloudSearch search=new CloudSearch("student4", query, null);
					search.search(new CloudObjectArrayCallback() {
						
						@Override
						public void done(CloudObject[] x, CloudException t) throws CloudException {
							
							Assert.assertTrue(x.length>0);
							
						}
					});
					

				
			}
		});
	}
}
