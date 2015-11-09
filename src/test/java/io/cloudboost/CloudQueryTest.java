package io.cloudboost;
import junit.framework.Assert;

import org.junit.Test;
/**
 * 
 * @author cloudboost
 *
 */
public class CloudQueryTest{
		void initialize(){
			CloudApp.init("travis123", "6dzZJ1e6ofDamGsdgwxLlQ==");
		}
//		
		@Test(timeout=20000)
		public void selectColumn() throws CloudException{
			initialize();
			CloudObject obj1 = new CloudObject("Custom1");
			obj1.set("newColumn", "sample");
			obj1.set("description", "sample2");
			obj1.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)	throws CloudException {
						CloudQuery cbQuery = new CloudQuery("Custom1");
						cbQuery.equalTo("id", x.get("id"));
						String[] column = {"newColumn"};
						cbQuery.selectColumn(column);
						cbQuery.find(new CloudObjectArrayCallback(){
							@Override
							public void done(CloudObject[] list, CloudException t)throws CloudException {
									if(t != null){
										Assert.fail(t.getMessage());
									}
									
									if(list.length > 0){
										
									}else{
										Assert.fail("object could not queried properly");
									}
							}
						});
				}
			});
		}
//	
		@Test(timeout=20000)
		public void selectColumnDistinct() throws CloudException{
			initialize();
			CloudObject obj1 = new CloudObject("Custom1");
			obj1.set("newColumn", "sample");
			obj1.set("description", "sample2");
			obj1.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)	throws CloudException {
						CloudQuery cbQuery = new CloudQuery("Custom1");
						cbQuery.equalTo("id", x.get("id"));
						String[] column = {"newColumn"};
						String[] keys = {"id"};
						cbQuery.selectColumn(column);
						cbQuery.distinct(keys, new CloudObjectArrayCallback(){
							@Override
							public void done(CloudObject[] list, CloudException t)throws CloudException {
									if(t != null){
										Assert.fail(t.getMessage());
									}
									
									if(list.length > 0){
									
									}else{
										Assert.fail("object could not queried properly");
									}
							}
						});
				}
			});
		}
//		
		@Test(timeout=20000)
		public void equalToWithNull() throws CloudException{
			initialize();
			CloudObject obj = new CloudObject("student1");
			obj.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)	throws CloudException {
					CloudQuery cbQuery = new CloudQuery("student1");
					cbQuery.equalTo("name", null);
					cbQuery.find(new CloudObjectArrayCallback(){
						@Override
						public void done(CloudObject[] list, CloudException t)throws CloudException {
								if(t != null){
									Assert.fail(t.getMessage());
								}
								
								if(list.length > 0){
									for(int i=0 ; i<list.length; i++){
									
									}
								}else{
									Assert.fail("object could not queried properly");
								}
						}
					});
				}
			});
		}
//		
		@Test(timeout=20000)
		public void notEqualToWithNull() throws CloudException{
			initialize();
			CloudObject obj = new CloudObject("student1");
			obj.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)	throws CloudException {
					CloudQuery cbQuery = new CloudQuery("student1");
					cbQuery.notEqualTo("name", null);
					cbQuery.find(new CloudObjectArrayCallback(){
						@Override
						public void done(CloudObject[] list, CloudException t)throws CloudException {
								if(t != null){
									Assert.fail(t.getMessage());
								}
								
								if(list.length > 0){
									for(int i=0 ; i<list.length; i++){
										
									}
								}else{
									Assert.fail("object could not queried properly");
								}
						}
					});
				}
			});
		}
//		
//		
		@Test(timeout=20000)
		public void findOne() throws CloudException{
			initialize();
			CloudQuery cbQuery = new CloudQuery("student1");
			cbQuery.equalTo("name", "vipul");
			cbQuery.findOne(new CloudObjectCallback(){
				@Override
				public void done(CloudObject object, CloudException t)throws CloudException {
						if(t != null){
							Assert.fail(t.getMessage());
						}
						
						if(object != null){
							Assert.assertEquals(object.get("name"), "vipul");
						}else{
							Assert.fail("object could not queried properly");
						}
				}
			});
		}
//		
		@Test(timeout=20000)
		public void findData() throws CloudException{
			initialize();
			CloudQuery cbQuery = new CloudQuery("student1");
			cbQuery.equalTo("name", "vipul");
			cbQuery.find(new CloudObjectArrayCallback(){
				@Override
				public void done(CloudObject[] list, CloudException t)throws CloudException {
						if(t != null){
							Assert.fail(t.getMessage());
						}
						if(list.length > 0){
							for(int i=0 ; i<list.length; i++){
								Assert.assertEquals(list[i].get("name"), "vipul");
							}
						}else{
							Assert.fail("object could not queried properly");
						}
				}
			});
		}
//		
//		@Test(timeout=20000)
//		public void containsAll() throws CloudException{
//			initialize();
//				CloudObject obj = new CloudObject("student4");
//				final String[] column = {"java", "python"};
//				obj.set("subject", column);
//				obj.save(new CloudObjectCallback(){
//					@Override
//					public void done(CloudObject x, CloudException t)	throws CloudException {
//							CloudQuery query = new CloudQuery("student4");
//							query.containsAll("subject", column);
//							query.find(new CloudObjectArrayCallback(){
//								@Override
//								public void done(CloudObject[] list,	CloudException t) throws CloudException {
//									if(list.length > 0){
//										for(int i=0 ; i<list.length; i++){
//											String[] col = (String[]) list[i].getArray("subject");
//											for(int j=0; j<col.length; i++){
//												if(!col[j].equals("java") && !col[j].equals("python") ){
//														Assert.fail("should retrieve saved data with particular value");
//												}
//											}
//											Assert.assertEquals(list[i].get("name"), "vipul");
//										}
//									}else{
//										Assert.fail("object could not queried properly");
//									}
//								}
//							});
//					}
//				});
//		}
//		
//		@Test(timeout=20000)
//		public void startsWith() throws CloudException{
//			initialize();
//			CloudQuery query = new CloudQuery("student1");
//			query.equalTo("name", "vipul");
//			query.startsWith("name", "v");
//			
//			query.find(new CloudObjectArrayCallback(){
//				@Override
//				public void done(CloudObject[] list,	CloudException t) throws CloudException {
//					if(list.length > 0){
//						for(int i=0 ; i<list.length; i++){
//								if(!(list[i].get("name").toString().charAt(0) != 'v') && !(list[i].get("name").toString().charAt(0) != 'V')) {
//										Assert.fail("should retrieve saved data with particular value");
//								}
//							Assert.assertEquals(list[i].get("name"), "vipul");
//						}
//					}else{
//						Assert.fail("object could not queried properly");
//					}
//				}
//			});
//		}
//		
//		@Test(timeout=20000)
//		public void notContianedIn() throws CloudException{
//			initialize();
//			CloudObject obj = new CloudObject("student4");
//			final String[] column = {"java", "python"};
//			obj.set("subject", column);
//			obj.save(new CloudObjectCallback(){
//				@Override
//				public void done(CloudObject x, CloudException t)	throws CloudException {
//						CloudQuery query = new CloudQuery("student4");
//						 String[] column1 = {"java", "python"};
//						query.notContainedIn("subject", column1);
//						query.find(new CloudObjectArrayCallback(){
//							@Override
//							public void done(CloudObject[] list,	CloudException t) throws CloudException {
//								if(list.length > 0){
//									for(int i=0 ; i<list.length; i++){
//										String[] col = (String[]) list[i].getArray("subject");
//										for(int j=0; j<col.length; i++){
//											if(col[j].equals("java") || col[j].equals("python") ){
//													Assert.fail("should retrieve saved data with particular value");
//											}
//										}
//										Assert.assertEquals(list[i].get("name"), "vipul");
//									}
//								}else{
//									Assert.fail("object could not queried properly");
//								}
//							}
//						});
//				}
//			});
//		}
//		
		@Test(timeout=20000)
		public void greaterThan() throws CloudException{
			initialize();
			CloudObject obj = new CloudObject("student4");
			final String[] column = {"C#", "C"};
			obj.set("subject", column);
			obj.set("age", 15);
			obj.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)	throws CloudException {
						CloudQuery query = new CloudQuery("student4");
						query.greaterThan("age", 10);
						query.find(new CloudObjectArrayCallback(){
							@Override
							public void done(CloudObject[] list,	CloudException t) throws CloudException {
								if(list.length > 0){
										for(int i=0 ; i<list.length; i++){
												if(list[i].getInteger("age") <= 10){
														Assert.fail("received value less than the required value");
												}
										}
								}else{
									Assert.fail("object could not queried properly");
								}
							}
						});
				}
			});
		}
//		
		@Test(timeout=20000)
		public void greaterThanEqualTo() throws CloudException{
			initialize();
			CloudObject obj = new CloudObject("student4");
			obj.set("age", 10);
			obj.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)	throws CloudException {
						CloudQuery query = new CloudQuery("student4");
						query.greaterThanEqualTo("age", 10);
						query.find(new CloudObjectArrayCallback(){
							@Override
							public void done(CloudObject[] list,	CloudException t) throws CloudException {
								if(list.length > 0){
										for(int i=0 ; i<list.length; i++){
												if(list[i].getInteger("age") < 10){
														Assert.fail("received value less than the required value");
												}
										}
								}else{
									Assert.fail("object could not queried properly");
								}
							}
						});
				}
			});
		}
//		
		@Test(timeout=20000)
		public void lessThan() throws CloudException{
			initialize();
			CloudObject obj = new CloudObject("student4");
			obj.set("age", 20);
			obj.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)	throws CloudException {
						CloudQuery query = new CloudQuery("student4");
						query.lessThan("age", 20);
						query.find(new CloudObjectArrayCallback(){
							@Override
							public void done(CloudObject[] list,	CloudException t) throws CloudException {
								if(list.length > 0){
										for(int i=0 ; i<list.length; i++){
												if(list[i].getInteger("age") >= 20){
														Assert.fail("received value greater than the required value");
												}
										}
								}else{
									Assert.fail("object could not queried properly");
								}
							}
						});
				}
			});
		}
//		
		@Test(timeout=20000)
		public void lessThanEqualTo() throws CloudException{
			initialize();
			CloudObject obj = new CloudObject("student4");
			obj.set("age", 15);
			obj.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)	throws CloudException {
						CloudQuery query = new CloudQuery("student4");
						query.lessThanEqualTo("age", 15);
						query.find(new CloudObjectArrayCallback(){
							@Override
							public void done(CloudObject[] list,	CloudException t) throws CloudException {
								if(list.length > 0){
										for(int i=0 ; i<list.length; i++){
												if(list[i].getInteger("age") > 15){
														Assert.fail("received value greater than the required value");
												}
										}
								}else{
									Assert.fail("object could not queried properly");
								}
							}
						});
				}
			});
		}
//	
//		
//		@Test(timeout=50000)
//		public void or() throws CloudException{
//			initialize();
//			CloudQuery query1 = new CloudQuery("student4");
//			String[] col = {"java", "python"};
//			query1.equalTo("subject", col);
//			CloudQuery query2 = new CloudQuery("student4");
//			query1.equalTo("age", 12);
//			
//			CloudQuery query = CloudQuery.or(query1, query2);
//			query.find(new CloudObjectArrayCallback(){
//				@Override
//				public void done(CloudObject[] list,	CloudException t) throws CloudException {
//					if(list.length > 0){
//							for(int i=0 ; i<list.length; i++){
//									if( list[i].getInteger("age") == 12){
//											continue;
//									}else{
//											String[] subject = (String[]) list[i].getArray("subject");
//											for(int j=0; j<subject.length; j++){
//													if(subject[j].equals("java") || subject[i].equals("python")){
//															continue;
//													}else{
//														Assert.fail("should retrieve saved data with particular value");
//													}
//													continue;
//											}
//									}
//							}
//					}else{
//						Assert.fail("object could not queried properly");
//					}
//				}
//			});
//		}
//		
		@Test(timeout=20000)
		public void ascendingOrder() throws CloudException{
			initialize();
			CloudObject obj = new CloudObject("student4");
			obj.set("age", 21);
			obj.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)	throws CloudException {
						CloudQuery query = new CloudQuery("student4");
						query.orderByAsc("age");
						query.find(new CloudObjectArrayCallback(){
							@Override
							public void done(CloudObject[] list,	CloudException t) throws CloudException {
								if(list.length > 0){
									  	int age = list[0].getInteger("age");
										for(int i=0 ; i<list.length; i++){
												if(age > list[i].getInteger("age")){
														Assert.fail("received value greater than the required value");
												}
												age = list[i].getInteger("age");
										}
								}else{
									Assert.fail("object could not queried properly");
								}
							}
						});
				}
			});
		}
//		
		@Test(timeout=20000)
		public void descendingOrder() throws CloudException{
			initialize();
				CloudObject obj = new CloudObject("student4");
				obj.set("age", 19);
				obj.save(new CloudObjectCallback(){
					@Override
					public void done(CloudObject x, CloudException t)	throws CloudException {
							CloudQuery query = new CloudQuery("student4");
							query.orderByDesc("age");
							query.find(new CloudObjectArrayCallback(){
								@Override
								public void done(CloudObject[] list,	CloudException t) throws CloudException {
									if(list.length > 0){
										  	int age = list[0].getInteger("age");
											for(int i=0 ; i<list.length; i++){
													if(age < list[i].getInteger("age")){
															Assert.fail("received value less than the required value");
													}
													age = list[i].getInteger("age");
											}
									}else{
										Assert.fail("object could not queried properly");
									}
								}
							});
					}
				});
		}
//		
		@Test(timeout=20000)
		public void findById() throws CloudException{
			initialize();
			CloudObject obj = new CloudObject("student1");
			obj.set("name", "abcd");
			obj.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)	throws CloudException {
						CloudQuery query = new CloudQuery("student4");
						query.orderByAsc("age");
						query.findById("kv5vGLCX",  new CloudObjectCallback(){
							@Override
							public void done(CloudObject object,	CloudException t) throws CloudException {
								if(t != null){
									Assert.fail(t.getMessage());
								}
								if(object != null){
										if(object.getInteger("age") != 15){
											Assert.fail("incorrect object");
										}
								}else{
									Assert.fail("object could not queried properly");
								}
							}
						});
				}
			});
		}
//		
		@Test(timeout=40000)
		public void exists() throws CloudException{
			initialize();
			CloudObject obj = new CloudObject("student4");
			obj.set("age", 18);
			obj.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)	throws CloudException {
						CloudQuery query = new CloudQuery("student4");
						query.exists("age");
						query.find(new CloudObjectArrayCallback(){
							@Override
							public void done(CloudObject[] list,	CloudException t) throws CloudException {
								if(list.length > 0){
										for(int i=0 ; i<list.length; i++){
												if(list[i].getInteger("age") == null){
														Assert.fail("received wrong data");
												}
										}
								}else{
									Assert.fail("object could not queried properly");
								}
							}
						});
				}
			});
		}
//		
		@Test(timeout=20000)
		public void doesNotExists() throws CloudException{
			initialize();
			CloudObject obj = new CloudObject("student4");
			obj.set("age", 17);
			obj.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)	throws CloudException {
						CloudQuery query = new CloudQuery("student4");
						query.doesNotExists("age");
						query.find(new CloudObjectArrayCallback(){
							@Override
							public void done(CloudObject[] list,	CloudException t) throws CloudException {
								if(list.length > 0){
									for(int i=0 ; i<list.length; i++){
									
								}
								}else{
									Assert.fail("object could not queried properly");
								}
							}
						});
				}
			});
		}
////		
//		@Test(timeout=20000)
//		public void getElementWithRelation() throws CloudException{
//			initialize();
//			final CloudObject obj1 = new CloudObject("hostel");
//			obj1.set("room", 123);
//			obj1.save(new CloudObjectCallback(){
//				@Override
//				public void done(CloudObject newObj, CloudException t)	throws CloudException {
//					CloudObject obj = new CloudObject("student1");
//					obj.set("newColumn", obj1);
//					obj.save(new CloudObjectCallback(){
//						@Override
//						public void done(CloudObject object, CloudException t)throws CloudException {
//							CloudQuery query = new CloudQuery("student1");
//							query.notEqualTo("newColumn", obj1);
//							query.find(new CloudObjectArrayCallback(){
//								@Override
//								public void done(CloudObject[] list,	CloudException t) throws CloudException {
//									if(list.length > 0){
//											for(int i=0 ; i<list.length; i++){
//													if(list[i].getCloudObject("newColumn").get("id").equals(obj1.get("id"))){
//															Assert.fail("Should not get the id in not equal to");
//													}
//											}
//									}else{
//										Assert.fail("object could not queried properly");
//									}
//								}
//							});
//						}
//					});
//				}
//			});
//		}
//		
		@Test(timeout=30000)
		public void saveRelation() throws CloudException{
			initialize();
			CloudObject obj = new CloudObject("Custom4");
			obj.set("newColumn1", "Course");
			CloudObject obj1 = new CloudObject("student1");
			obj1.set("name", "Ranjeet");
			
			CloudObject obj2 = new CloudObject("student1");
			obj2.set("name", "Ravi");
			CloudObject[] obje = {obj1, obj2};
			obj.set("newColumn7", obje);
			obj.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)throws CloudException {
					if(t != null){
						Assert.fail(t.getMessage());
					}
				}
			});
		}
//		
		@Test(timeout=30000)
		public void saveMultiJoin() throws CloudException{
			initialize();
			CloudObject obj = new CloudObject("Custom2");
			obj.set("newColumn1", "Course");
			CloudObject obj1 = new CloudObject("student1");
			CloudObject obj2 = new CloudObject("hostel");
			CloudObject obj3 = new CloudObject("Custom3");
			obj3.set("address", "progress");
			obj.set("newColumn2", obj3);
			obj2.set("room", 509);
			obj1.set("name", "Ranjeet");
			obj.set("newColumn7", obj1);
			obj1.set("newColumn", obj2);
			obj.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)throws CloudException {
					if(t != null){
						Assert.fail(t.getMessage());
					}
				}
			});
		}
//		
//		@Test(timeout=30000)
//		public void includeRelationlObjectInQuery() throws CloudException{
//			initialize();
//			CloudObject obj = new CloudObject("Custom2");
//			obj.set("newColumn1", "Course");
//			CloudObject obj1 = new CloudObject("student1");
//			CloudObject obj2 = new CloudObject("hostel");
//			CloudObject obj3 = new CloudObject("Custom3");
//			obj3.set("address", "progress");
//			obj.set("newColumn2", obj3);
//			obj2.set("room", 509);
//			obj1.set("name", "Ranjeet");
//			obj.set("newColumn7", obj1);
//			obj1.set("newColumn", obj2);
//			obj.save(new CloudObjectCallback(){
//				@Override
//				public void done(CloudObject x, CloudException t)throws CloudException {
//					if(t != null){
//						Assert.fail(t.getMessage());
//					}
//					CloudQuery query= new CloudQuery("Custom2");
//					query.include("newColumn7");
//					query.include("newColumn7.newColumn");
//					query.include("newColumn2");
//					query.equalTo("id", x.getId());
//					query.find(new CloudObjectArrayCallback(){
//						@Override
//						public void done(CloudObject[] list, CloudException err)	throws CloudException {
//							if(err != null){
//								Assert.fail(err.getMessage());
//							}
//							if(list.length > 0){
//								for(int i=0; i<list.length; i++){
//									CloudObject student = list[i].getCloudObject("newColumn7");
//									CloudObject room = list[i].getCloudObject("newColumn");
//									if(student.getDouble("name") == null || room.get("room") == null){
//										Assert.fail("Unsuccessful Join");
//									}
//								}
//							}else{
//								Assert.fail("Cannot retrieve a saved relation.");
//							}
//						}
//					});
//				}
//			});
//		}
//		
		@Test(timeout=30000)
		public void includeRelationObjectOnDistinct() throws CloudException{
			initialize();
			CloudObject obj = new CloudObject("Custom2");
			obj.set("newColumn1", "text");
			CloudObject obj1 = new CloudObject("student1");
			obj1.set("name", "Ranjeet");
			obj.set("newColumn7", obj1);
			obj.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)throws CloudException {
					if(t != null){
						Assert.fail(t.getMessage());
					}
					CloudQuery query= new CloudQuery("Custom2");
					query.include("newColumn7");
					String[] column = {"newcolumn1"};
					query.distinct(column, new CloudObjectArrayCallback(){
						@Override
						public void done(CloudObject[] list, CloudException err)	throws CloudException {
							if(err != null){
								Assert.fail(err.getMessage());
							}
							if(list.length > 0){
								for(int i=0; i<list.length; i++){
									CloudObject student = list[i].getCloudObject("newColumn7");
									if(student.get("name") == null ){
										Assert.fail("Unsuccessful Join");
									}
								}
							}else{
								Assert.fail("Cannot retrieve a saved relation.");
							}
						}
					});
				}
			});
		}
//		
		@Test(timeout=30000)
		public void queryOverLinkedColumnWithEqualTo() throws CloudException{
			initialize();
			CloudObject student = new CloudObject("student1");
			CloudObject hostel = new CloudObject("hostel");
			hostel.set("room", 789);
			student.set("newColumn", hostel);
			student.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)throws CloudException {
					if(t != null){
						Assert.fail(t.getMessage());
					}
					CloudQuery query= new CloudQuery("student1");
					CloudObject temp = x.getCloudObject("newColumn");
					query.equalTo("newColumn", temp);
					query.find(new CloudObjectArrayCallback(){
						@Override
						public void done(CloudObject[] list, CloudException err)	throws CloudException {
							if(err != null){
								Assert.fail(err.getMessage());
							}
							if(list.length > 0){
							
							}else{
								Assert.fail("Cannot retrieve a saved relation.");
							}
						}
					});
				}
			});
		}
//		
//		@Test(timeout=30000)
//		public void containedInOverListOfCloudObject() throws CloudException{
//			initialize();
//			CloudObject obj = new CloudObject("Custom");
//			CloudObject obj1 = new CloudObject("Custom");
//			CloudObject obj2 = new CloudObject("Custom");
//			CloudObject[] object = {obj2, obj1};
//			obj.set("newColumn7", object);
//			obj.save(new CloudObjectCallback(){
//				@Override
//				public void done(CloudObject x, CloudException t)throws CloudException {
//					if(t != null){
//						Assert.fail(t.getMessage());
//					}
//					CloudQuery query= new CloudQuery("Custom");
//					CloudObject[] objectList = {x.getCloudObject("newColumn7")};
//					query.containedIn("newColumn7", objectList);
//					query.find(new CloudObjectArrayCallback(){
//						@Override
//						public void done(CloudObject[] list, CloudException err)	throws CloudException {
//							if(err != null){
//								Assert.fail(err.getMessage());
//							}
//							if(list.length > 0){
//							
//							}else{
//								Assert.fail("Cannot retrieve a saved relation.");
//							}
//						}
//					});
//				}
//			});
//		}
//		
		@Test(timeout=30000)
		public void includeFindById() throws CloudException{
			initialize();
			CloudObject obj = new CloudObject("Custom");
			CloudObject obj1 = new CloudObject("Custom");
			CloudObject obj2 = new CloudObject("Custom");
			obj2.set("newColumn1", "sample");
			CloudObject[] object = {obj2, obj1};
			obj.set("newColumn7", object);
			obj.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)throws CloudException {
					if(t != null){
						Assert.fail(t.getMessage());
					}
					CloudQuery query= new CloudQuery("Custom");
					query.include("newColumn7");
					query.findById(x.getId(), new CloudObjectCallback(){
						@Override
						public void done(CloudObject list, CloudException err)	throws CloudException {
							if(err != null){
								Assert.fail(err.getMessage());
							}
							if(list != null){
							
							}else{
								Assert.fail("Cannot retrieve a saved relation.");
							}
						}
					});
				}
			});
		}
//		
		@Test(timeout=30000)
		public void getEncryptedPassword() throws CloudException{
			initialize();
			final String username = PrivateMethod._makeString();
			CloudObject obj = new CloudObject("User");
			obj.set("username", username);
			obj.set("password", "password");
			obj.set("email", username+"@gam.com");
			obj.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)	throws CloudException {
					CloudQuery query = new CloudQuery("User");
					query.equalTo("password", "password");
					query.equalTo("username", username);
					query.find(new CloudObjectArrayCallback(){
						@Override
						public void done(CloudObject[] list, CloudException err)	throws CloudException {
							if(err != null){
								Assert.fail(err.getMessage());
							}
							if(list.length > 0){
								
							}else{
								Assert.fail("cannot get items");
							}
						}
					});
				}
			});
		}
//		
		@Test(timeout=30000)
		public void getEncryptedPasswordOverQuery() throws CloudException{
			initialize();
			final String username = PrivateMethod._makeString();
			CloudObject obj = new CloudObject("User");
			obj.set("username", username);
			obj.set("password", "password");
			obj.set("email", username+"@gam.com");
			obj.save(new CloudObjectCallback(){
				@Override
				public void done(CloudObject x, CloudException t)	throws CloudException {
					if(x.get("password").toString() != "password");{
						CloudQuery query1 = new CloudQuery("User");
						CloudQuery query2 = new CloudQuery("User");
						query1.equalTo("password", "password");
						query1.equalTo("password", "password1");
						CloudQuery query = CloudQuery.or(query1, query2);
						query.equalTo("username",username);
						query.find(new CloudObjectArrayCallback(){
							@Override
							public void done(CloudObject[] list, CloudException err)	throws CloudException {
								if(err != null){
									Assert.fail(err.getMessage());
								}
								if(list.length > 0){
									
								}else{
									Assert.fail("cannot get items");
								}
							}
						});
					}
				}
			});
		}
//		
}