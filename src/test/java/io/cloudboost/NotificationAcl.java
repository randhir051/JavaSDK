package io.cloudboost;

import junit.framework.Assert;

import org.junit.Test;

public class NotificationAcl {
	void initialize(){
		CloudApp.init("travis123", "6dzZJ1e6ofDamGsdgwxLlQ==");
	}
	void initMaster() {
		CloudApp.init("travis123",
				"vfmMIbP4KaqxihajNqLNFGuub8CIOLREP1oH0QC0qy4=");
	}
	@Test(timeout=30000)
	public void shouldCreateNewUserAndListen() throws CloudException{
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
					CloudObject.on("User", "created", new CloudObjectCallback() {
						
						@Override
						public void done(CloudObject x, CloudException t) throws CloudException {
							System.out.println("realtime");
							Assert.assertTrue(x!=null);
							
						}
					});
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					CloudUser user2=new CloudUser();
					final String name=PrivateMethod._makeString();
					user2.set("username", name);
					user2.set("password", "abcd");
					user2.set("email", PrivateMethod._makeString()+"@gmail.com");
					user2.signUp(new CloudUserCallback() {
						
						@Override
						public void done(CloudUser user, CloudException e) throws CloudException {
							System.out.println("created");
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					});
				
				}
				
			}
		});
	}
	@Test(timeout=30000)
	public void shouldNotReceiveNotificationWhenPublicReadAccessIsFalse() throws CloudException{
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
					CloudObject.on("User", "created", new CloudObjectCallback() {
						
						@Override
						public void done(CloudObject x, CloudException t) throws CloudException {
							Assert.fail("Received notification despite false public read access");
							
						}
					});
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					
					CloudUser user2=new CloudUser();
					final String name=PrivateMethod._makeString();
					user2.set("username", name);
					user2.set("password", "abcd");
					user2.set("email", PrivateMethod._makeString()+"@gmail.com");
					ACL acl=user2.getAcl();
					acl.setPublicReadAccess(false);
					user2.setAcl(acl);
					user2.signUp(new CloudUserCallback() {
						
						@Override
						public void done(CloudUser user, CloudException e) throws CloudException {
							System.out.println("created");
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					});
				
				}
				
			}
		});
	}
	@Test(timeout=30000)
	public void shouldNotReceiveWhenUserReadAccessIsFalse() throws CloudException{
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
					CloudObject.on("User", "created", new CloudObjectCallback() {
						
						@Override
						public void done(CloudObject x, CloudException t) throws CloudException {
							CloudObject.off("User", "created", new CloudStringCallback() {
								
								@Override
								public void done(String x, CloudException e) throws CloudException {
									// TODO Auto-generated method stub
									
								}
							});
							Assert.assertTrue(x!=null);
							
						}
					});
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					CloudUser user2=new CloudUser();
					final String name=PrivateMethod._makeString();
					user2.set("username", name);
					user2.set("password", "abcd");
					user2.set("email", PrivateMethod._makeString()+"@gmail.com");
					user2.signUp(new CloudUserCallback() {
						
						@Override
						public void done(CloudUser user, CloudException e) throws CloudException {
							System.out.println("created");
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					});
				
				}
				
			}
		});
	}
	@Test(timeout=30000)
	public void shouldNotReceiveWhenPublicReadAccessAndNoUserRead() throws CloudException{
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
					CloudObject.on("User", "created", new CloudObjectCallback() {
						
						@Override
						public void done(CloudObject x, CloudException t) throws CloudException {
							System.out.println("realtime");
							Assert.assertTrue(x!=null);
							
						}
					});
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					CloudUser user2=new CloudUser();
					final String name=PrivateMethod._makeString();
					user2.set("username", name);
					user2.set("password", "abcd");
					user2.set("email", PrivateMethod._makeString()+"@gmail.com");
					user2.signUp(new CloudUserCallback() {
						
						@Override
						public void done(CloudUser user, CloudException e) throws CloudException {
							System.out.println("created");
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					});
				
				}
				
			}
		});
	}
	@Test(timeout=30000)
	public void shouldReceiveWhenUserReadAndNoPublic() throws CloudException{
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
					CloudObject.on("User", "created", new CloudObjectCallback() {
						
						@Override
						public void done(CloudObject x, CloudException t) throws CloudException {
							Assert.fail("Received notification despite false user read access ");
							
						}
					});
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					CloudUser user2=new CloudUser();
					final String name=PrivateMethod._makeString();
					user2.set("username", name);
					user2.set("password", "abcd");
					user2.set("email", PrivateMethod._makeString()+"@gmail.com");
					ACL acl=user2.getAcl();
					acl.setUserReadAccess(user.getId(), false);
					user2.setAcl(acl);
					user2.signUp(new CloudUserCallback() {
						
						@Override
						public void done(CloudUser user, CloudException e) throws CloudException {
							System.out.println("created");
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					});
				
				}
				
			}
		});
	}
	//--------------------------------------------------------------------------------------------
	@Test(timeout=30000)
	public void shouldNotReceiveUserLoggedOff() throws CloudException{
		initMaster();
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
					CloudObject.on("Custom1", "created", new CloudObjectCallback() {
						
						@Override
						public void done(CloudObject x, CloudException t) throws CloudException {
							System.out.println("realtime");
							Assert.fail("should not receive an even when user is logged off");
							
						}
					});
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					user.logOut(new CloudUserCallback() {
						
						@Override
						public void done(CloudUser user, CloudException e) throws CloudException {
							if(e!=null)
								Assert.fail(e.getMessage());
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
							CloudObject user2=new CloudObject("Custom1");
							user2.set("newColumn", "sample");
							ACL acl=user2.getAcl();
							acl.setPublicReadAccess(false);
							acl.setPublicWriteAccess(true);
							acl.setUserReadAccess(user.getId(), true);
							user2.setAcl(acl);
							user2.save(new CloudObjectCallback() {
								
								@Override
								public void done(CloudObject x, CloudException t) throws CloudException {
									if(t!=null)
										Assert.fail(t.getMessage());
									else
										try {
											Thread.sleep(5000);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									
								}
							});
							
						}
					});

				
				}
				
			}
		});
	}
	@Test(timeout=30000)
	public void shouldReceiveWhenUserLogsBackIn() throws CloudException{
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
					CloudObject.on("User", "created", new CloudObjectCallback() {
						
						@Override
						public void done(CloudObject x, CloudException t) throws CloudException {
							System.out.println("realtime");
							Assert.assertTrue(x!=null);
							
						}
					});
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					CloudUser user2=new CloudUser();
					final String name=PrivateMethod._makeString();
					user2.set("username", name);
					user2.set("password", "abcd");
					user2.set("email", PrivateMethod._makeString()+"@gmail.com");
					user2.signUp(new CloudUserCallback() {
						
						@Override
						public void done(CloudUser user, CloudException e) throws CloudException {
							System.out.println("created");
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					});
				
				}
				
			}
		});
	}
}
