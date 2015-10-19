package io.cloudboost;
import junit.framework.Assert;
import org.junit.Test;

/**
 * 
 * @author cloudboost
 *
 */

public class CloudUserTest{
	
	    String username = PrivateMethod._makeString();
	    String passwd =  "abcd";
	    void initialize(){
			CloudApp.init("sample123","9SPxp6D3OPWvxj0asw5ryA==");
		}
		@Test(timeout=20000)
		public void createNewUser() throws CloudException{
				initialize();
				CloudUser obj = new CloudUser();
				obj.setUserName(username);
				obj.setPassword(passwd);
				obj.setEmail(PrivateMethod._makeString()+"@abc.com");
				obj.signUp(new CloudUserCallback(){
					@Override
					public void done(CloudUser object, CloudException e)	throws CloudException {
						if(e != null){
							Assert.fail(e.getMessage());
						}
						if(object != null){
							Assert.assertEquals(object.get("username"), username);
						}
					}
				});
		}
		
		@Test(timeout=20000)
		public void logoutUser() throws CloudException{
				initialize();
				CloudUser.getcurrentUser().logOut(new CloudUserCallback(){
					@Override
					public void done(CloudUser x, CloudException t)	throws CloudException {
						
					}

		
				});
		}
		
		@Test(timeout=20000)
		public void createUserGetVersion() throws CloudException{
			initialize();
			CloudUser user = new CloudUser();
			final String newUser = PrivateMethod._makeString();
			user.setUserName(newUser);
			user.setPassword(passwd);
			user.setEmail(PrivateMethod._makeString()+"@abc.com");
			user.signUp(new CloudUserCallback(){
				@Override
				public void done(CloudUser object, CloudException e)	throws CloudException {
					if( e != null ){
						Assert.fail(e.getMessage());
					}
					
					if(object != null){
						if(object.getInteger("_version") >= 0){
							Assert.assertEquals(object.get("username"), newUser);
						}else{
							Assert.fail("Create User Error");
						}
						
					}
				}
			});
		}
		
		@Test(timeout=20000)
		public void queryOnUser(){
				
		}
		
		@Test(timeout=20000)
		public void loginUser() throws CloudException{
			initialize();
			CloudUser user = new CloudUser();
			user.setUserName(username);
			user.setPassword(passwd);
			user.logIn(new CloudUserCallback(){

				@Override
				public void done(CloudUser object, CloudException e)throws CloudException {
					if(e != null){
							Assert.fail(e.getMessage());
					}
					
					if(object != null){
						Assert.assertEquals(object.get("username"), username);
					}else{
						Assert.fail();
					}
				}
			});
		}
		
		String roleName = PrivateMethod._makeString();
		
		
		@Test(timeout=20000)
		public void createRole() throws CloudException{
			initialize();
			CloudRole role = new CloudRole(roleName);
			role.save(new CloudRoleCallback(){

				@Override
				public void done(CloudRole  x, CloudException t)throws CloudException {
					if(t != null){
						Assert.fail(t.getMessage());
					}
					
					if(x != null){
						Assert.assertEquals(x.get("name"), roleName);
					}else{
						Assert.fail("Role Create Error");
					}
				}
				
			});
		}
		
		@Test(timeout=20000)
		public void assignRoleToUser() throws CloudException{
			initialize();
			CloudUser user = new CloudUser();
			user.setUserName(username);
			user.setPassword(passwd);
			user.logIn(new CloudUserCallback(){

				@Override
				public void done(final CloudUser newUser, CloudException t)	throws CloudException {
						CloudRole role = new CloudRole(roleName);
						role.save(new CloudRoleCallback(){
							@Override
							public void done(CloudRole newRole, CloudException t)	throws CloudException {
									newUser.addToRole(newRole, new CloudUserCallback(){

										@Override
										public void done(CloudUser anotherUser, CloudException e)throws CloudException {
											if(e != null){
												Assert.fail(e.getMessage());
											}
											
											if(anotherUser != null){
												Assert.assertEquals(anotherUser.getUserName(), username);
											}else{
												Assert.fail("Add Role Error");
											}
										}
										
									});
							}
							
						});
				}
				
			});
		}
		
		@Test(timeout=20000)
		public void removeRoleFromUser() throws CloudException{
			initialize();
			CloudUser user = new CloudUser();
			user.setUserName(username);
			user.setPassword(passwd);
			String roleName = PrivateMethod._makeString();
			final CloudRole role = new CloudRole(roleName);
			user.logIn(new CloudUserCallback(){
				@Override
				public void done(final CloudUser newUser, CloudException t)	throws CloudException {
						role.save(new CloudRoleCallback(){
							@Override
							public void done(final CloudRole newRole, CloudException t)	throws CloudException {
									newUser.addToRole(newRole, new CloudUserCallback(){
										@Override
										public void done(CloudUser anotherUser, CloudException e)throws CloudException {
												CloudUser.getcurrentUser().removeFromRole(newRole, new CloudUserCallback(){
													@Override
													public void done(CloudUser object, CloudException e)throws CloudException {
														if(e != null){
															Assert.fail(e.getMessage());
														}	
														if(object != null){
															Assert.assertEquals(object.getUserName(), username);
														}else{
															Assert.fail("Add Role Error");
														}
													}
												});
										}
									});
							}
							
						});
				}
				
			});
		}
		
		@Test(timeout=20000)
		public void encryptUserPassword() throws CloudException{
			initialize();
			CloudUser user = new CloudUser();
			user.setUserName(username);
			user.setPassword(passwd);
			user.setEmail(PrivateMethod._makeString()+"@abc.com");
			user.save(new CloudObjectCallback(){

				@Override
				public void done(CloudObject newUser, CloudException e)	throws CloudException {
					if(e != null ){
						Assert.fail(e.getMessage());
					}
					
					if(newUser != null){
						if(newUser.get("password").equals(passwd)){
							Assert.fail("Password is not encrypted");
						}
					}
				}
			});
		}
		
}