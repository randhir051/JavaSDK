package io.cloudboost;
import java.util.ArrayList;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class ACLTest{
	
	private JSONObject aclList;
	private JSONObject write;
	@SuppressWarnings("unused")
	private JSONObject read;
	@SuppressWarnings("unused")
	private JSONObject allow;
	private JSONObject deny;
	@SuppressWarnings("unused")
	private ArrayList<String> allowedUser;
	private ArrayList<String> deniedUser;
	public JSONObject getAclList() {
		return aclList;
	}

	public void setAclList(JSONObject aclList) {
		this.aclList = aclList;
	}
	
	void initialize(){
		CloudApp.init("travis123", "6dzZJ1e6ofDamGsdgwxLlQ==");
	}
	@Test(timeout=20000)
	public void shouldUpdateAcl() throws CloudException{
		initialize();
		CloudObject obj=new CloudObject("Employee");
		ACL acl=new ACL();
		acl.setRoleWriteAccess("x", true);
		acl.setPublicWriteAccess(true);
		obj.acl=acl;
		obj.save(new CloudObjectCallback() {
			
			@Override
			public void done(CloudObject x, CloudException t) throws CloudException {
				x.acl.setRoleWriteAccess("y", true);
				x.acl.setPublicWriteAccess(true);
				x.save(new CloudObjectCallback() {
					
					@Override
					public void done(CloudObject x, CloudException t) throws CloudException {
						String id=x.getId();
						CloudQuery query=new CloudQuery("Employee");
						query.findById(id, new CloudObjectCallback() {
							
							@Override
							public void done(CloudObject x, CloudException t) throws CloudException {
								if(t!=null)
									Assert.fail(t.getMessage());
								else{
									try {
										Assert.assertTrue(((ArrayList<String>)(x.acl.write.getJSONObject("allow").get("role"))).size()==2);
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								
							}
						});
						
					}
				});
				
			}
		});
	}
//	@Test(timeout=20000)
//	public void revokePublicWriteAccess() throws CloudException{
//		initialize();
//		final CloudObject obj = new CloudObject("student4");
//        obj.acl.setPublicWriteAccess(false);
//        obj.save(new CloudObjectCallback(){
//			@Override
//			public void done(CloudObject x, CloudException t) throws CloudException {
//				
//				if(t != null){
//					Assert.fail(t.getMessage());
//				}
//				try{
//				setAclList(new JSONObject(x.get("ACL").toString()));
//				write = aclList.getJSONObject("write");
//				deny = write.getJSONObject("deny");
//				deniedUser =  new ArrayList<String>();
//				JSONArray user = new JSONArray(deny.get("user").toString());
//				for(int i=0; i<user.length(); i++){
//					
//						deniedUser.add(i, user.getString(i));
//					
//				}
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				if(deniedUser.size() == 0){
//					obj.set("age", 15);
//					obj.save(new CloudObjectCallback(){
//						@Override
//						public void done(CloudObject y, CloudException t)	throws CloudException {
//							if(t != null){
//								Assert.fail(t.getMessage());
//							}
//							if(y != null){
//								Assert.fail("Should not save object with no right access");
//							}
//						}
//					});
//				}else{
//					Assert.fail("public write access set error");
//				}
//			}
//        });
//	}
}
