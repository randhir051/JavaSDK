package io.cloudboost;
import junit.framework.Assert;

import org.junit.Test;
/**
 * 
 * @author cloudboost
 *
 */
public class CloudRoleTest{
	
		void initialize(){
			CloudApp.init("travis123", "6dzZJ1e6ofDamGsdgwxLlQ==");
		}
		
		@Test(timeout=20000)
		public void createRole() throws CloudException{
				initialize();
				String roleName = PrivateMethod._makeString();
				CloudRole role = new CloudRole(roleName);
				role.save(new CloudRoleCallback(){

					@Override
					public void done(CloudRole roleObj, CloudException e)throws CloudException {
							if(e != null){
									Assert.fail(e.getMessage());
							}
							if(roleObj == null){
									Assert.fail("Should have create the cloud role");
							}else{
									CloudQuery query = new CloudQuery("Role");
									query.equalTo("id", roleObj.get("id"));
									query.find(new CloudObjectArrayCallback(){

										@Override
										public void done(CloudObject[] x,CloudException t)throws CloudException {
											
												if(t != null){
													Assert.fail(t.getMessage());
												}
												
												if(x.length < 0){
													Assert.fail("Should retrieve the cloud role");
												}
										}
									});
							}
					}
				});
		}
	
		
}