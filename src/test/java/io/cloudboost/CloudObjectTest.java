package io.cloudboost;
import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

public class CloudObjectTest{
	private SecureRandom random = new SecureRandom();
	
	public String randomString(){
		return new BigInteger(130, random).toString(32);
	}
	void initialize(){
		CloudApp.init("sample123","9SPxp6D3OPWvxj0asw5ryA==");
//		CloudApp.init("egimabengitest", "yiBh75txY35CB+LSb/1XLQ==");

	}
	@Test(timeout=10000)
	public void SaveData() throws CloudException{
		 initialize();
//		CloudObject obj = new CloudObject("sample");
		CloudObject obj = new CloudObject("Sample");

		obj.set("name", "samplexyz");
		obj.save(new CloudObjectCallback(){
			@Override
			public void done(CloudObject x, CloudException t) {	
				if(x != null){
					Assert.assertEquals("samplexyz", x.get("name").toString());
				}
				if(t != null){
					Assert.fail("Failed to save data");
				}
			}
		});
	}
	
	@Test(timeout = 10000)
	public void ShouldNotSaveStringIntoDate() throws CloudException{
		initialize();
		CloudObject obj = new CloudObject("Sample");
//		CloudObject obj = new CloudObject("sample");

		obj.set("createdAt", "abcd");
		obj.save(new CloudObjectCallback(){

			@Override
			public void done(CloudObject x, CloudException t) {
				if(x != null){
					Assert.fail("should not have saved string in date column");
				}
				
				if(t != null)
					Assert.assertNull(x);
			}			
		});
	}
	
	@Test(timeout = 10000)
	public void ShouldNotSaveWithoutRequiredColumn() throws CloudException{
		initialize();
		CloudObject obj = new CloudObject("Sample");
//		CloudObject obj = new CloudObject("sample");

		obj.save(new CloudObjectCallback(){

			@Override
			public void done(CloudObject x, CloudException t) {
				if(t != null)
					Assert.assertNull(x);
				
				if(x!=null){
					Assert.fail("should not have saved object without require field");
				}
				
			}
			
		});
	}
	
	@Test(timeout = 10000)
	public void ShouldNotSaveWithWrongDataType() throws CloudException{
		initialize();
		CloudObject obj = new CloudObject("Sample");
//		CloudObject obj = new CloudObject("sample");

		obj.set("name", 10);
		obj.save(new CloudObjectCallback(){

			@Override
			public void done(CloudObject x, CloudException t) {
				if(t != null)
					Assert.assertNull(x);
				
				if(x!=null){
					Assert.fail("Should not have saved data with wrong data type");
				}				
			}

		});
	}
	
	@Test(timeout=30000)
	public void ShouldNotSaveDuplicateValue() throws CloudException{
		initialize();
		CloudObject obj = new CloudObject("Sample");
//		CloudObject obj = new CloudObject("sample");

		final String text = PrivateMethod._makeString();
		obj.set("name", "sample");
//		obj.set("unique", text);
		obj.save(new CloudObjectCallback(){
			@Override
			public void done(CloudObject x, CloudException t) throws CloudException {
				if(x != null){
					CloudObject obj = new CloudObject("Sample");
//					CloudObject obj = new CloudObject("sample");

					obj.set("name", "sample1");
					obj.set("unique", text);
					obj.save(new CloudObjectCallback(){
						@Override
						public void done(CloudObject x, CloudException t) {
							if(x != null){
								Assert.fail("Should not have save duplicate data in unique column");
							}							
						}
					});
				}
				
				if(t != null)
					Assert.fail("Should save the data");
			}

		});
	}
	
	@Test(timeout=30000)
	public void updateAfterSave() throws CloudException{
		initialize();
		CloudObject obj = new CloudObject("Sample");
//		CloudObject obj = new CloudObject("sample");

     	obj.set("name", "sample");
     	obj.save(new CloudObjectCallback(){
			@Override
			public void done(CloudObject x, CloudException t)	throws CloudException {
				if(t != null){
					Assert.fail(t.getMessage());
				}
				x.set("name", "sample1");
				x.save(new CloudObjectCallback(){
					@Override
					public void done(CloudObject object, CloudException err)throws CloudException {
						if(err != null){
							Assert.fail(err.getMessage());
						}
					}
				});
			}
     	});
	}
	
	@Test(timeout=30000)
	public void deleteAfterSave() throws CloudException{
		initialize();
		CloudObject obj = new CloudObject("Sample");
//		CloudObject obj = new CloudObject("sample");

     	obj.set("name", "sample");
     	obj.save(new CloudObjectCallback(){
			@Override
			public void done(CloudObject x, CloudException t)	throws CloudException {
				if(t != null){
					Assert.fail(t.getMessage());
				}
				x.delete(new CloudObjectCallback(){
					@Override
					public void done(CloudObject x, CloudException t)	throws CloudException {
						if(t != null){
							Assert.fail(t.getMessage());
						}
					}
				});
			}
		});
	}
	
	@Test(timeout=30000)
	public void saveArray() throws CloudException{
		initialize();
		CloudObject obj = new CloudObject("Sample");
		obj.set("name", "sample");
		String text = PrivateMethod._makeString();
		String[] string = {text , text};
		obj.set("stringArray", string);
		obj.save(new CloudObjectCallback(){
			@Override
			public void done(CloudObject x, CloudException t)throws CloudException {
				if(t != null){
					Assert.fail(t.getMessage());
				}
			}
		});
	}
	
	@Test(timeout=30000)
	public void saveArrayWithWrongDataType() throws CloudException{
		initialize();
		CloudObject obj = new CloudObject("Custom");
		Integer[] string = {23, 45};
		obj.set("newColumn7", string);
		obj.save(new CloudObjectCallback(){
			@Override
			public void done(CloudObject x, CloudException t)throws CloudException {
				if(t == null){
					Assert.fail("Should Not save");
				}
			}
		});
	}
	
	@Test(timeout=30000)
	public void saveArrayWithJSONObject() throws CloudException{
		initialize();
		CloudObject obj = new CloudObject("Sample");
		obj.set("name", "sample");
		JSONObject obj1 = new JSONObject();
		try {
			obj1.put("sample", "sample");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray string = new JSONArray();
		string.put(obj1);
		string.put(obj1);
		obj.set("objectArray", string);
		obj.save(new CloudObjectCallback(){
			@Override
			public void done(CloudObject x, CloudException t)throws CloudException {
				if(t != null){
					Assert.fail(t.getMessage());
				}
			}
		});
	}
	
	@Test(timeout=30000)
	public void saveRelation() throws CloudException{
		initialize();
		CloudObject obj = new CloudObject("Sample");
		obj.set("name", "Sample");
		CloudObject obj1 = new CloudObject("Sample");
		obj1.set("name", "samplet");
		obj.set("sameRelation", obj1);
		obj.save(new CloudObjectCallback(){
			@Override
			public void done(CloudObject x, CloudException t)throws CloudException {
				if(t != null){
					Assert.fail(t.getMessage());
				}
			}
		});
	}
	
	@Test(timeout=30000)
	public void saveRelationWithRelate() throws CloudException{
		initialize();
		final CloudObject obj = new CloudObject("Sample");
		obj.set("name", "sample");
		CloudObject obj1 = new CloudObject("Sample");
		obj1.set("name", "sample");
		obj1.save(new CloudObjectCallback(){
			@Override
			public void done(CloudObject x, CloudException t)throws CloudException {
				if(t != null){
					Assert.fail(t.getMessage());
				}
				obj.relate("sameRelation", "Sample", x.getId());
				obj.save(new CloudObjectCallback(){
					@Override
					public void done(CloudObject x, CloudException t)throws CloudException {
						if(t != null){
							Assert.fail(t.getMessage());
						}
					}
				});
			}
		});
	}
	
	@Test(timeout=30000)
	public void keepRelationIntact() throws CloudException{
		initialize();
		 CloudObject obj = new CloudObject("Custom2");
		 CloudObject obj1 = new CloudObject("Custom3");
		 CloudObject obj2 = new CloudObject("student1");
		 obj.set("newColumn2", obj1);
		 obj.set("newColumn7", obj2);
		 obj.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)throws CloudException {
					if(t != null){
						Assert.fail(t.getMessage());
					}
					//System.out.println(x.getCloudObject("newColumn7").get("_tableName"));
					if(x.getCloudObject("newColumn2").get("_tableName").equals("Custom3") && x.getCloudObject("newColumn7").get("_tableName").equals("student1")){
						
					}
				else{
						Assert.fail("Wrong Relationship retrieved.");
					}
				}
			});
	}
	
	@Test(timeout=30000)
	public void saveWrongRelation() throws CloudException{
		initialize();
		CloudObject obj = new CloudObject("Sample");
		obj.set("name", "Sample");
		CloudObject obj1 = new CloudObject("Student");
		obj1.set("name", "sample");
		obj.set("sameRelation", obj1);
		obj.save(new CloudObjectCallback(){
			@Override
			public void done(CloudObject x, CloudException t)throws CloudException {
				if(t != null){
					
				}else{
					Assert.fail("Saved an object with a wrong relation.");
				}
			}
		});
	}
	
	@Test(timeout=30000)
	public void saveEmptyArrayOfCloudObject() throws CloudException{
		initialize();
		CloudObject obj = new CloudObject("Sample");
		final CloudObject obj2 = new CloudObject("Sample");
		obj.set("name", "sample");
		Integer[] string = {};
		obj2.set("name", "sample");
		obj2.set("relationArray", string);
		obj.save(new CloudObjectCallback(){
			@Override
			public void done(CloudObject x, CloudException t)throws CloudException {
				if(t != null){
					Assert.fail(t.getMessage());
				}
				obj2.save(new CloudObjectCallback(){
					@Override
					public void done(CloudObject x, CloudException t)	throws CloudException {
						if(t != null){
							Assert.fail(t.getMessage());
						}
					}
				});
			}
		});
	}
	
	@Test(timeout=30000)
	public void saveArrayOfCloudObject() throws CloudException{
		initialize();
		CloudObject obj = new CloudObject("Sample");
		obj.set("name", "sample");
		CloudObject obj1 = new CloudObject("Sample");
		obj1.set("name", "sample");
		final CloudObject obj2 = new CloudObject("Sample");
		obj2.set("name", "sample");
		CloudObject[] string = {obj1,obj};
		obj2.set("relationArray", string);
		obj.save(new CloudObjectCallback(){
			@Override
			public void done(CloudObject x, CloudException t)throws CloudException {
				if(t != null){
					Assert.fail(t.getMessage());
				}
				
				obj2.save(new CloudObjectCallback(){
					@Override
					public void done(CloudObject x, CloudException t)	throws CloudException {
						if(t != null){
							Assert.fail(t.getMessage());
						}
					}
				});
			}
		});
	}
	
	@Test(timeout=30000)
	public void updateListAfterSave() throws CloudException{
		initialize();
		CloudObject obj = new CloudObject("Sample");
		obj.set("name", "sample");
		CloudObject obj1 = new CloudObject("Sample");
		obj1.set("name", "sample");
		final CloudObject obj2 = new CloudObject("Sample");
		obj2.set("name", "sample");
		CloudObject[] string = {obj1,obj};
		obj2.set("relationArray", string);
		obj.save(new CloudObjectCallback(){
			@Override
			public void done(CloudObject x, CloudException t)throws CloudException {
				if(t != null){
					Assert.fail(t.getMessage());
				}
				
				obj2.save(new CloudObjectCallback(){
					@Override
					public void done(CloudObject obj3, CloudException err)	throws CloudException {
						if(err != null){
							Assert.fail(err.getMessage());
						}
						CloudObject[] relationArray = obj3.getCloudObjectArray("relationArray");
						if(relationArray.length != 2){
							Assert.fail("unable to save relation properly");
						}
						
						CloudObject[] relationArray1 = {relationArray[0]};
						obj3.set("relationArray", relationArray1);
						obj3.save(new CloudObjectCallback(){
							@Override
							public void done(CloudObject x, CloudException t)throws CloudException {
								if(t != null){
									Assert.fail(t.getMessage());
								}
							}
						});
					}
				});
			}
		});
	}
	
	@Test(timeout=30000)
	public void saveJSON(){
		
	}
	
	@Test(timeout=30000)
	public void saveArrayOfGeoPoint(){
		
	}
	
	@Test(timeout=30000)
	public void createObjectNotice() throws CloudException{
		initialize();
		CloudObject obj = new CloudObject("Student");
	    CloudObject.on("Student", "created", new CloudObjectCallback(){
			@Override
			public void done(CloudObject data, CloudException t)	throws CloudException {
				if(t != null){
					Assert.fail(t.getMessage());
				}
				if(data.get("name").equals("sample")) {
						System.out.print(data.get("name").toString());
			           CloudObject.off("Student","created", new CloudStringCallback(){
						@Override
						public void done(String x, CloudException e)throws CloudException {
							if(e != null){
								Assert.fail(e.getMessage());
							}
						}
			           });
			       }else{
			    	   Assert.fail("Wrong data received");
			       }
			}
	    });
	    
	    obj.set("name", "sample");
	    obj.save(new CloudObjectCallback(){
			@Override
			public void done(CloudObject x, CloudException t)throws CloudException {
				if(t != null){
					Assert.fail(t.getMessage());
				}
			}
	    });
	}
	
	@Test(timeout=30000)
	public void updateObjectNotice() throws CloudException{
		initialize();
		CloudObject obj = new CloudObject("Student");
	    CloudObject.on("Student", "updated", new CloudObjectCallback(){
			@Override
			public void done(CloudObject data, CloudException t)	throws CloudException {
				if(t != null){
					Assert.fail(t.getMessage());
				}
				if(data.get("name").equals("sample1")) {
						System.out.print(data.get("name").toString());
			           CloudObject.off("Student","created", new CloudStringCallback(){
						@Override
						public void done(String x, CloudException e)throws CloudException {
							if(e != null){
								Assert.fail(e.getMessage());
							}
						}
			           });
			       }else{
			    	   Assert.fail("Wrong data received");
			       }
			}
	    });
	    
	    obj.set("name", "sample");
	    obj.save(new CloudObjectCallback(){
			@Override
			public void done(CloudObject x, CloudException t)throws CloudException {
				if(t != null){
					Assert.fail(t.getMessage());
				}
				x.set("name", "sample1");
				x.save(new CloudObjectCallback(){
					@Override
					public void done(CloudObject x, CloudException t)	throws CloudException {
						
					}
				});
			}
	    });
	}
	
	@Test(timeout=30000)
	public void deleteObjectNotice() throws CloudException{
		initialize();
		CloudObject obj = new CloudObject("Student");
	    CloudObject.on("Student", "deleted", new CloudObjectCallback(){
			@Override
			public void done(CloudObject data, CloudException t)	throws CloudException {
				if(t != null){
					Assert.fail(t.getMessage());
				}
				if(data.get("name").equals("sample")) {
						System.out.print(data.get("name").toString());
			           CloudObject.off("Student","created", new CloudStringCallback(){
						@Override
						public void done(String x, CloudException e)throws CloudException {
							if(e != null){
								Assert.fail(e.getMessage());
							}
						}
			           });
			       }else{
			    	   Assert.fail("Wrong data received");
			       }
			}
	    });
	    obj.set("name", "sample");
	    obj.save(new CloudObjectCallback(){
			@Override
			public void done(CloudObject x, CloudException t)throws CloudException {
				if(t != null){
					Assert.fail(t.getMessage());
				}
				x.delete(new CloudObjectCallback(){
					@Override
					public void done(CloudObject x, CloudException t)	throws CloudException {
						
					}
				});
			}
	    });
	}
}
