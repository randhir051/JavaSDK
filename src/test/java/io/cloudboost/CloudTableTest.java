package io.cloudboost;
import io.cloudboost.Column.DataType;
import junit.framework.Assert;

import org.junit.Test;

/**
 * 
 * @author cloudboost
 *
 */

public class CloudTableTest{
	
		void initialize(){
			//CloudApp.init("sample123", "Qopoy/kXd+6G734HsjQMqGPGOvwEJYmBG84lQawRmWM=");
			CloudApp.init("travis123", "vfmMIbP4KaqxihajNqLNFGuub8CIOLREP1oH0QC0qy4=");
		}
		
//		@Test(timeout=40000)
//		public void duplicateTable() throws CloudException{
//	        initialize();
//			final String tableName = PrivateMethod._makeString();
//			System.out.println(tableName);
//			CloudTable obj = new CloudTable(tableName);
//			obj.save(new CloudTableCallback(){
//				@Override
//				public void done(CloudTable table, CloudException e) throws CloudException {
//					 CloudTable obj1 = new CloudTable(tableName);
//					 obj1.save(new CloudTableCallback(){
//						 @Override
//						 public void done(CloudTable table, CloudException e){
//							   if(e != null){
//								   Assert.assertEquals("Table already exists.", e.getMessage().trim());
//							   }
//							   
//							 	if(table != null){
//							 			Assert.fail("Should not create duplicate table");
//							 	}else{
//							 		Assert.assertEquals(table, null);
//							 	}
//						 }
//					 });
//				}
//			});
//			
//		}
		
	@Test(timeout=50000)
		public void createAndDeleteTable () throws CloudException{
			 initialize();
			final String tableName = PrivateMethod._makeString();
			System.out.println(tableName);
			CloudTable obj = new CloudTable(tableName);
			obj.save(new CloudTableCallback(){
				@Override
				public void done(CloudTable table, CloudException e) throws CloudException {
					 table.delete(new CloudStringCallback(){
						 @Override
						 public void done(String response, CloudException e){
							 
							   if(e != null){
								   Assert.fail(e.getMessage());
							   }
							   
							 	if(response == null){
							 			Assert.fail("Should have delete the  table");
							 	}else{
							 		Assert.assertEquals(response, "Success");
							 	}
							 	
						 }
					 });
				}
			});
		}
		
//		@Test(timeout=50000)
//		public void getTable () throws CloudException{
//			initialize();
//			CloudTable obj = new CloudTable("Address");
//			CloudTable.get(obj, new CloudTableCallback(){
//				@Override
//				public void done(CloudTable table, CloudException e)
//						throws CloudException {
//						if(e != null){
//							Assert.fail(e.getMessage());
//						}
//						
//						if(table != null){
//							Assert.assertEquals(table.getTableName(), "Address");
//						}
//				}
//				
//			});
//		}
		
		@Test(timeout=30000)
		public void GetAllTable () throws CloudException{
			initialize();
			CloudTable.getAll(new CloudTableArrayCallback(){

				@Override
				public void done(CloudTable[] table, CloudException e)	throws CloudException {
					   if(e != null){
						   Assert.fail(e.getMessage());
					   }
					   System.out.println("Number of Tables "+table.length);
						if(table.length > 0){
							Assert.assertTrue(table.length > 0);				
						}
				}
				
			});
		}
		
		@Test(timeout=80000)
		public void updateNewColumnIntoTable () throws CloudException{
			initialize();
			final String tableName1 = PrivateMethod._makeString();
			String tableName2 = PrivateMethod._makeString();
			final CloudTable obj = new CloudTable(tableName1);
			final CloudTable obj1 = new CloudTable(tableName2);
			obj.save(new CloudTableCallback(){
				@Override
				public void done(CloudTable table, CloudException e)	throws CloudException {
					obj1.save(new CloudTableCallback(){
						@Override
						public void done(CloudTable table1, CloudException e)throws CloudException {
							CloudTable.get(obj,new CloudTableCallback(){
								@Override
								public void done(CloudTable getTable,	CloudException e) throws CloudException {
									   System.out.println("dd :: " + getTable.getTableName());
										Column column1 = new Column("Name", DataType.Relation, true, false );
										column1.setRelatedTo(getTable);
										getTable.addColumn(column1);
										getTable.save(new CloudTableCallback(){
											@Override
											public void done(CloudTable newTable, CloudException e)	throws CloudException {
												Column column2 = new Column("Name", DataType.Relation, true, false);
												newTable.deleteColumn(column2);
												newTable.save(new CloudTableCallback(){
													@Override
													public void done(CloudTable finalTable, CloudException e)throws CloudException {
															if(e != null){
																Assert.fail(e.getMessage());
															}
															if(finalTable != null){
																Assert.assertEquals(finalTable.getTableName(), tableName1);
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
			});
		}
		
		@Test(timeout=40000)
		public void addColumnAfterSave () throws CloudException{
			initialize();
			String tableName = PrivateMethod._makeString();
			CloudTable table = new CloudTable(tableName);
			table.save(new CloudTableCallback(){
				@Override
				public void done(CloudTable newTable, CloudException e)throws CloudException {
						if(newTable != null){
							Column column1  = new Column("Name1", DataType.Text, false, false);
							newTable.addColumn(column1);
							newTable.save(new CloudTableCallback(){
								@Override
								public void done(CloudTable anotherTable,CloudException e) throws CloudException {
									anotherTable.delete(new CloudStringCallback(){

										@Override
										public void done(String x,
												CloudException e)
												throws CloudException {
											
										}
									});
								}
							});
						}
				}
			});
		}
		
//		@Test(timeout=40000)
//		public void renameTable () throws CloudException{
//			initialize();
//			CloudTable table = new CloudTable("Address");
//			CloudTable.get(table, new CloudTableCallback(){
//				@Override
//				public void done(CloudTable newTable, CloudException e)	throws CloudException {
//						newTable.setTableName("sdvds");
//						newTable.save(new CloudTableCallback(){
//							@Override
//							public void done(CloudTable table, CloudException e)throws CloudException {
//									if(e != null){
//										System.out.println("Rename Table ::"+ e.getMessage());
//										Assert.assertEquals(e.getMessage(), "Cannot Rename a Table.");
//									}
//									if(table != null){
//										Assert.fail("Should not have rename the table");
//									}
//							}
//						});
//				  }
//			});
//			
//		}
		
//		@Test(timeout=20000)
//		public void  deleteTableAddress() throws CloudException{
//			initialize();
//			CloudTable obj = new CloudTable("Address");
//			CloudTable.get(obj, new  CloudTableCallback(){
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//					table.delete(new CloudStringCallback(){
//						@Override
//						public void done(String response, CloudException e)throws CloudException {
//							if(e != null){
//								   Assert.fail(e.getMessage());
//							   }
//							
//							 	if(response == null){
//							 			Assert.fail("Should have delete the  table");
//							 	}else{
//							 		Assert.assertEquals(response, "Success");
//							 	}
//						}
//					});
//				}
//			});
//		}
		
//		@Test(timeout=50000)
//		public void deleteTableCompany() throws CloudException{
//			initialize();
//			CloudTable obj = new CloudTable("Company");
//			CloudTable.get(obj, new  CloudTableCallback(){
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//					table.delete(new CloudStringCallback(){
//						@Override
//						public void done(String response, CloudException e)throws CloudException {
//							if(e != null){
//								   Assert.fail(e.getMessage());
//							   }
//							
//							 	if(response == null){
//							 			Assert.fail("Should have delete the  table");
//							 	}else{
//							 		Assert.assertEquals(response, "Success");
//							 	}
//						}
//					});
//				}
//			});
//		}
		
//		@Test(timeout=20000)
//		public void  deleteTableEmployee() throws CloudException{
//			initialize();
//			CloudTable obj = new CloudTable("Employee");
//			CloudTable.get(obj, new  CloudTableCallback(){
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//					table.delete(new CloudStringCallback(){
//						@Override
//						public void done(String response, CloudException e)throws CloudException {
//							if(e != null){
//								   Assert.fail(e.getMessage());
//							   }
//							
//							 	if(response == null){
//							 			Assert.fail("Should have delete the  table");
//							 	}else{
//							 		Assert.assertEquals(response, "Success");
//							 	}
//						}
//					});
//				}
//			});
//		}
		
//		@Test(timeout=50000)
//		public void createTableEmployee() throws CloudException{
//			initialize();
//			CloudTable obj = new CloudTable("Employee");
//			Column age = new Column("Age", DataType.Number, false, false);
//			Column name = new Column("Name", DataType.Text, false, false);
//			obj.addColumn(age);
//			obj.addColumn(name);
//			obj.save(new CloudTableCallback(){
//
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//						if(e!=null){
//							Assert.fail(e.getMessage());
//						}
//						
//						if(table !=null){
//							Assert.assertEquals(table.getTableName(), "Employee");
//						}
//				}
//			});
//		}
//		@Test(timeout=50000)
//		public void createTableCompany() throws CloudException{
//			initialize();
//				CloudTable obj = new CloudTable("Company");
//				Column revenue = new Column("Revenue", DataType.Number, false, false);
//				Column name = new Column("Name", DataType.Text, false, false);
//				obj.addColumn(revenue);
//				obj.addColumn(name);
//				obj.save(new CloudTableCallback(){
//	
//					@Override
//					public void done(CloudTable table, CloudException e)	throws CloudException {
//							if(e!=null){
//								Assert.fail(e.getMessage());
//							}
//							
//							if(table !=null){
//								Assert.assertEquals(table.getTableName(), "Company");
//							}
//					}
//				});
//		}
		
//		@Test(timeout=20000)
//		public void  createTableAddress() throws CloudException{
//			initialize();
//			CloudTable obj = new CloudTable("Address");
//			Column city = new Column("City", DataType.Text, false, false);
//			Column pinCode = new Column("PinCode", DataType.Number, false, false);
//			obj.addColumn(city);
//			obj.addColumn(pinCode);
//			obj.save(new CloudTableCallback(){
//
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//						if(e!=null){
//							Assert.fail(e.getMessage());
//						}
//						
//						if(table !=null){
//							Assert.assertEquals(table.getTableName(), "Address");
//						}
//				}
//			});
//			
//		}
		
//		@Test(timeout=10000)
//		public void updateTableSchemaEmployee() throws CloudException{
//				initialize();
//				CloudTable table = new CloudTable("Employee");
//				CloudTable.get(table,new CloudTableCallback(){
//					@Override
//					public void done(CloudTable newTable, CloudException e)throws CloudException {
//							Column company = new Column("Company", DataType.Relation, false, false);
//							CloudTable companyObj = new CloudTable("Company");
//							company.setRelatedTo(companyObj);
//							newTable.addColumn(company);
//							
//							Column address = new Column("Address", DataType.Relation, false, false);
//							CloudTable addressObj = new CloudTable("Address");
//							address.setRelatedTo(addressObj);
//							newTable.addColumn(address);
//							newTable.save(new CloudTableCallback(){
//								@Override
//								public void done(CloudTable anotherTable,CloudException e) throws CloudException {
//										if(e!=null){
//											Assert.fail(e.getMessage());
//										}
//										
//										if(anotherTable != null){
//											Assert.assertEquals(anotherTable.getTableName(), "Employee");
//										}
//								}
//							});
//					}
//				});
//		}
//		
//		@Test(timeout=50000)
//		public void updateTableSchemaCompany() throws CloudException{
//			initialize();
//			CloudTable table = new CloudTable("Company");
//			CloudTable.get(table,new CloudTableCallback(){
//				@Override
//				public void done(CloudTable newTable, CloudException e)throws CloudException {
//						Column company = new Column("Employee", DataType.Relation, false, false);
//						CloudTable companyObj = new CloudTable("Employee");
//						company.setRelatedTo(companyObj);
//						newTable.addColumn(company);
//						
//						Column address = new Column("Address", DataType.Relation, false, false);
//						CloudTable addressObj = new CloudTable("Address");
//						address.setRelatedTo(addressObj);
//						newTable.addColumn(address);
//						newTable.save(new CloudTableCallback(){
//							@Override
//							public void done(CloudTable anotherTable,CloudException e) throws CloudException {
//									if(e!=null){
//										Assert.fail(e.getMessage());
//									}
//									
//									if(anotherTable != null){
//										Assert.assertEquals(anotherTable.getTableName(), "Company");
//									}
//							}
//						});
//				}
//			});
//		}
		
//		@Test(timeout=50000)
//		public void createTableStudent4() throws CloudException{
//			initialize();
//			CloudTable obj = new CloudTable("student4");
//			Column subject = new Column("subject", DataType.List, false, false);
//			subject.setRelatedTo(DataType.Text);
//			Column age = new Column("age", DataType.Number, false, false);
//			obj.addColumn(subject);
//			obj.addColumn(age);
//			obj.save(new CloudTableCallback(){
//
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//						if(e!=null){
//							Assert.fail(e.getMessage());
//						}
//						
//						if(table !=null){
//							Assert.assertEquals(table.getTableName(), "student4");
//						}
//				}
//			});
//			
//			CloudTable obj1 = new CloudTable("student4");
//			obj1.delete(new CloudStringCallback(){
//
//				@Override
//				public void done(String response, CloudException e)throws CloudException {
//					
//				}
//				
//			});
//		}
		
//		@Test(timeout=50000)
//		public void createTableRole() throws CloudException{
//			initialize();
//			CloudTable user= new CloudTable("Role");
//			user.save(new CloudTableCallback(){
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//						if(e!=null){
//							Assert.fail(e.getMessage());
//						}
//						
//						if(table !=null){
//							Assert.assertEquals(table.getTableName(), "Role");
//						}
//				}
//			});
//			
//			CloudTable obj1 = new CloudTable("Role");
//			obj1.delete(new CloudStringCallback(){
//				@Override
//				public void done(String response, CloudException e)throws CloudException {
//					
//				}
//				
//			});
//		}
		
//		@Test(timeout=50000)
//		public void createTableUser() throws CloudException{
//			initialize();
//			CloudTable user= new CloudTable("User");
//			user.save(new CloudTableCallback(){
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//						if(e!=null){
//							Assert.fail(e.getMessage());
//						}
//						
//						if(table !=null){
//							Assert.assertEquals(table.getTableName(), "User");
//						}
//				}
//			});
//			
//			CloudTable obj1 = new CloudTable("User");
//			obj1.delete(new CloudStringCallback(){
//				@Override
//				public void done(String response, CloudException e)throws CloudException {
//					
//				}
//				
//			});
//		}
		
//		@Test(timeout=50000)
//		public void createTableCustom() throws CloudException{
//			initialize();
//				CloudTable custom = new CloudTable("Custom");
//				Column newColumn = new Column("newColumn", DataType.Email, false, false);
//				custom.addColumn(newColumn);
//				Column newColumn1 = new Column("newColumn1", DataType.Text, false, false);
//	            custom.addColumn(newColumn1);
//	            Column newColumn2 = new Column("newColumn2", DataType.URL, false, false);
//	            custom.addColumn(newColumn2);
//	            Column newColumn3 = new Column("newColumn3", DataType.Number, false, false);
//	            custom.addColumn(newColumn3);
//	            Column newColumn4 = new Column("newColumn4", DataType.Boolean, false, false);
//	            custom.addColumn(newColumn4);
//	            Column newColumn5 = new Column("newColumn5", DataType.DateTime, false, false);
//	            custom.addColumn(newColumn5);
//	            Column newColumn6 = new Column("newColumn6", DataType.Object, false, false);
//	            custom.addColumn(newColumn6);
//	   
//				custom.save(new CloudTableCallback(){
//					@Override
//					public void done(CloudTable table, CloudException e)	throws CloudException {
//							if(e!=null){
//								Assert.fail(e.getMessage());
//							}
//							
//							if(table !=null){
//								Assert.assertEquals(table.getTableName(), "Custom");
//							}
//					}
//				});
//				
//				CloudTable obj1 = new CloudTable("Custom");
//				obj1.delete(new CloudStringCallback(){
//					@Override
//					public void done(String response, CloudException e)throws CloudException {
//						
//					}
//					
//				});
//		}
//		
		@Test(timeout=10000)
		public void updateTableCustom(){
			
		}
		
//		@Test(timeout=50000)
//		public void createTableCustom5() throws CloudException{
//			initialize();
//			CloudTable custom = new CloudTable("Custom5");
//			Column newColumn = new Column("location", DataType.GeoPoint, false, false);
//			custom.addColumn(newColumn);
//			custom.save(new CloudTableCallback(){
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//						if(e!=null){
//							Assert.fail(e.getMessage());
//						}
//						
//						if(table !=null){
//							Assert.assertEquals(table.getTableName(), "Custom5");
//						}
//				}
//			});
//			
//			CloudTable obj1 = new CloudTable("Custom5");
//			obj1.delete(new CloudStringCallback(){
//				@Override
//				public void done(String response, CloudException e)throws CloudException {
//				
//				}
//				
//			});
//		}
		
//		@Test(timeout=50000)
//		public void createTableSample() throws CloudException{
//			initialize();
//			CloudTable custom = new CloudTable("Sample");
//            Column newColumn = new Column("name", DataType.Text, true, false);
//            custom.addColumn(newColumn);
//            Column newColumn1 = new Column("unique", DataType.Text, false, true);
//            custom.addColumn(newColumn1);
//            Column newColumn2 = new Column("stringArray", DataType.List, false, false);
//            newColumn2.setRelatedTo(DataType.Text);
//            custom.addColumn(newColumn2);
//            Column newColumn3 = new Column("objectArray", DataType.List,  false, false);
//            newColumn3.setRelatedTo(DataType.Object);
//            custom.addColumn(newColumn3);
//            Column newColumn6 = new Column("file", DataType.File, false, false);
//            custom.addColumn(newColumn6);
//            Column newColumn7 = new Column("fileList", DataType.List, false, false);
//            newColumn7.setRelatedTo(DataType.File);
//            custom.addColumn(newColumn7);
//            custom.save(new CloudTableCallback(){
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//						if(e!=null){
//							Assert.fail(e.getMessage());
//						}
//						
//						if(table !=null){
//							Assert.assertEquals(table.getTableName(), "Sample");
//						}
//				}
//			});
//			
//			CloudTable obj1 = new CloudTable("Sample");
//			obj1.delete(new CloudStringCallback(){
//				@Override
//				public void done(String response, CloudException e)throws CloudException {
//						
//				}
//			});
//            
//		}
		
//		@Test(timeout=50000)
//		public void createTableHostel() throws CloudException{
//			initialize();
//			CloudTable custom = new CloudTable("hostel");
//		   	Column newColumn = new Column("room", DataType.Number, false, false);
//		   	custom.addColumn(newColumn);
//		   	Column newColumn1 = new Column("name", DataType.Text, false, false);
//		   	custom.addColumn(newColumn1);
//			custom.save(new CloudTableCallback(){
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//						if(e!=null){
//							Assert.fail(e.getMessage());
//						}
//						
//						if(table !=null){
//							Assert.assertEquals(table.getTableName(), "hostel");
//						}
//				}
//			});
//			
//			CloudTable obj1 = new CloudTable("hostel");
//			obj1.delete(new CloudStringCallback(){
//				@Override
//				public void done(String response, CloudException e)throws CloudException {
//				
//				}
//				
//			});
//		}
		
//		@Test(timeout=50000)
//		public void createTableStudent1() throws CloudException{
//			initialize();
//			CloudTable custom = new CloudTable("student1");
//		   	Column newColumn = new Column("age", DataType.Number, false, false);
//		   	custom.addColumn(newColumn);
//		   	Column newColumn1 = new Column("newColumn", DataType.Relation, false, false);
//		   	CloudTable hostel = new CloudTable("hostel");
//		   	newColumn1.setRelatedTo(hostel);
//		   	custom.addColumn(newColumn1);
//		   	Column newColumn2 = new Column("name", DataType.Text, false, false);
//		   	custom.addColumn(newColumn2);
//			custom.save(new CloudTableCallback(){
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//						if(e!=null){
//							Assert.fail(e.getMessage());
//						}
//						
//						if(table !=null){
//							Assert.assertEquals(table.getTableName(), "student1");
//						}
//				}
//			});
//			
//			CloudTable obj1 = new CloudTable("student1");
//			obj1.delete(new CloudStringCallback(){
//				@Override
//				public void done(String response, CloudException e)throws CloudException {
//				
//				}
//				
//			});
//		}
		
//		@Test(timeout=50000)
//		public void createTableStudent() throws CloudException{
//			initialize();
//			CloudTable custom = new CloudTable("Student");
//		   	Column newColumn = new Column("age", DataType.Number, false, false);
//		   	custom.addColumn(newColumn);
//		   	Column newColumn1 = new Column("class", DataType.Text, false, false);
//		   	custom.addColumn(newColumn1);
//		   	Column newColumn2 = new Column("name", DataType.Text, false, false);
//		   	custom.addColumn(newColumn2);
//			Column newColumn3 = new Column("description", DataType.Text, false, false);
//		   	custom.addColumn(newColumn3);
//			custom.save(new CloudTableCallback(){
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//						if(e!=null){
//							Assert.fail(e.getMessage());
//						}
//						
//						if(table !=null){
//							Assert.assertEquals(table.getTableName(), "Student");
//						}
//				}
//			});
//			
//			CloudTable obj1 = new CloudTable("Student");
//			obj1.delete(new CloudStringCallback(){
//				@Override
//				public void done(String response, CloudException e)throws CloudException {
//				
//				}
//				
//			});
//		}
		
//		@Test(timeout=50000)
//		public void createTableCustom18() throws CloudException{
//			initialize();
//			CloudTable custom = new CloudTable("Custom18");
//			Column newColumn3 = new Column("number", DataType.Number, false, false);
//		   	custom.addColumn(newColumn3);
//			custom.save(new CloudTableCallback(){
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//						if(e!=null){
//							Assert.fail(e.getMessage());
//						}
//						
//						if(table !=null){
//							Assert.assertEquals(table.getTableName(), "Custom18");
//						}
//				}
//			});
//			
//			CloudTable obj1 = new CloudTable("Custom18");
//			obj1.delete(new CloudStringCallback(){
//				@Override
//				public void done(String response, CloudException e)throws CloudException {
//				
//				}
//				
//			});
//		}
		
//		@Test(timeout=50000)
//		public void createTableCustom3() throws CloudException{
//			initialize();
//			CloudTable custom = new CloudTable("Custom3");
//			Column newColumn3 = new Column("address", DataType.Text, false, false);
//		   	custom.addColumn(newColumn3);
//			custom.save(new CloudTableCallback(){
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//						if(e!=null){
//							Assert.fail(e.getMessage());
//						}
//						
//						if(table !=null){
//							Assert.assertEquals(table.getTableName(), "Custom3");
//						}
//				}
//			});
//			
//			CloudTable obj1 = new CloudTable("Custom3");
//			obj1.delete(new CloudStringCallback(){
//				@Override
//				public void done(String response, CloudException e)throws CloudException {
//				
//				}
//				
//			});
//			
//		}
		
//		@Test(timeout=50000)
//		public void createTableCustom7() throws CloudException{
//			initialize();
//			CloudTable custom = new CloudTable("Custom7");
//			Column newColumn3 = new Column("requiredNumber", DataType.Number, false, false);
//		   	custom.addColumn(newColumn3);
//			custom.save(new CloudTableCallback(){
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//						if(e!=null){
//							Assert.fail(e.getMessage());
//						}
//						
//						if(table !=null){
//							Assert.assertEquals(table.getTableName(), "Custom7");
//						}
//				}
//			});
//			
//			CloudTable obj1 = new CloudTable("Custom7");
//			obj1.delete(new CloudStringCallback(){
//				@Override
//				public void done(String response, CloudException e)throws CloudException {
//				
//				}
//			});
//		}
		
//		@Test(timeout=50000)
//		public void createTableCustom2() throws CloudException{
//			initialize();
//			CloudTable custom = new CloudTable("Custom2");
//			Column newColumn = new Column("newColumn1", DataType.Text, false, false);
//		   	custom.addColumn(newColumn);
//		   	Column newColumn1 = new Column("newColumn7", DataType.Relation, false, false);
//		   	CloudTable student1 = new CloudTable("student1");
//		   	newColumn1.setRelatedTo(student1);
//		   	custom.addColumn(newColumn1);
//		   	Column newColumn2 = new Column("newColumn2", DataType.Relation, false, false);
//		   	CloudTable custom3 = new CloudTable("Custom3");
//		   	newColumn2.setRelatedTo(custom3);
//		   	custom.addColumn(newColumn2);
//			custom.save(new CloudTableCallback(){
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//						if(e!=null){
//							Assert.fail(e.getMessage());
//						}
//						
//						if(table !=null){
//							Assert.assertEquals(table.getTableName(), "Custom2");
//						}
//				}
//			});
//			
//			CloudTable obj1 = new CloudTable("Custom2");
//			obj1.delete(new CloudStringCallback(){
//				@Override
//				public void done(String response, CloudException e)throws CloudException {
//				
//				}
//			});
//		}
		
//		@Test(timeout=50000)
//		public void createTableCustom4() throws CloudException{
//			initialize();
//			CloudTable custom = new CloudTable("Custom4");
//			Column newColumn = new Column("newColumn1", DataType.Text, false, false);
//		   	custom.addColumn(newColumn);
//		   	Column newColumn1 = new Column("newColumn7", DataType.List, false, false);
//		   	CloudTable student1 = new CloudTable("student1");
//		   	newColumn1.setRelatedTo(student1);
//		   	custom.addColumn(newColumn1);
//			custom.save(new CloudTableCallback(){
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//						if(e!=null){
//							Assert.fail(e.getMessage());
//						}
//						
//						if(table !=null){
//							Assert.assertEquals(table.getTableName(), "Custom4");
//						}
//				}
//			});
//			
//			CloudTable obj1 = new CloudTable("Custom4");
//			obj1.delete(new CloudStringCallback(){
//				@Override
//				public void done(String response, CloudException e)throws CloudException {
//				
//				}
//			});
//		}
		
//		@Test(timeout=50000)
//		public void createTableCustom14() throws CloudException{
//			initialize();
//			CloudTable custom = new CloudTable("Custom14");
//			Column newColumn = new Column("List_Number", DataType.List, false, false);
//			newColumn.setRelatedTo(DataType.Number);
//		   	custom.addColumn(newColumn);
//		   	Column newColumn1 = new Column("List_GeoPoint", DataType.List, false, false);
//		   	newColumn1.setRelatedTo(DataType.GeoPoint);
//		   	custom.addColumn(newColumn1);
//			custom.save(new CloudTableCallback(){
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//						if(e!=null){
//							Assert.fail(e.getMessage());
//						}
//						
//						if(table !=null){
//							Assert.assertEquals(table.getTableName(), "Custom14");
//						}
//				}
//			});
//			
//			CloudTable obj1 = new CloudTable("Custom14");
//			obj1.delete(new CloudStringCallback(){
//				@Override
//				public void done(String response, CloudException e)throws CloudException {
//				
//				}
//			});
//		}
		
//		@Test(timeout=50000)
//		public void createTableCustom1() throws CloudException{
//			initialize();
//			CloudTable custom = new CloudTable("Custom1");
//		   	Column newColumn1 = new Column("newColumn", DataType.Text, false, false);
//		   	custom.addColumn(newColumn1);
//		   	Column newColumn2 = new Column("newColumn1", DataType.Boolean, false, false);
//		   	custom.addColumn(newColumn2);
//			Column newColumn3 = new Column("description", DataType.Text, false, false);
//		   	custom.addColumn(newColumn3);
//			custom.save(new CloudTableCallback(){
//				@Override
//				public void done(CloudTable table, CloudException e)	throws CloudException {
//						if(e!=null){
//							Assert.fail(e.getMessage());
//						}
//						
//						if(table !=null){
//							Assert.assertEquals(table.getTableName(), "Custom1");
//						}
//				}
//			});
//			
//			CloudTable obj1 = new CloudTable("Custom1");
//			obj1.delete(new CloudStringCallback(){
//				@Override
//				public void done(String response, CloudException e)throws CloudException {
//				
//				}
//				
//			});
//		}
		
}