package io.cloudboost;

import io.cloudboost.Column.DataType;
import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

/**
 * 
 * @author cloudboost
 * 
 */
public class CloudTableTest {

	void initialize() {
		// CloudApp.init("sample123",
		// "Qopoy/kXd+6G734HsjQMqGPGOvwEJYmBG84lQawRmWM=");
		CloudApp.init("travis123",
				"vfmMIbP4KaqxihajNqLNFGuub8CIOLREP1oH0QC0qy4=");
	}

	@Test(timeout = 40000)
	public void duplicateTable() throws CloudException {
		initialize();
		final String tableName = PrivateMethod._makeString();
		CloudTable obj = new CloudTable(tableName);
		obj.save(new CloudTableCallback() {
			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				CloudTable obj1 = new CloudTable(tableName);
				obj1.save(new CloudTableCallback() {
					@Override
					public void done(CloudTable table, CloudException e) {
						if (e != null) {
							// Assert.assertEquals("Table already exists.",
							// e.getMessage().trim());
							Assert.assertEquals("Internal Server Error", e
									.getMessage().trim());
						}

						if (table != null) {
							Assert.fail("Should not create duplicate table");
						} else {
							Assert.assertEquals(table, null);
						}
					}
				});
			}
		});

	}

	@Test(timeout = 50000)
	public void createAndDeleteTable() throws CloudException {
		initialize();
		final String tableName = PrivateMethod._makeString();
		CloudTable obj = new CloudTable(tableName);
		obj.save(new CloudTableCallback() {
			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				table.delete(new CloudStringCallback() {
					@Override
					public void done(String response, CloudException e) {

						if (e != null) {
							Assert.fail(e.getMessage());
						}

						if (response == null) {
							Assert.fail("Should have delete the  table");
						} else {
							Assert.assertEquals(response, "Success");
						}

					}
				});
			}
		});
	}

	@Test(timeout = 50000)
	public void getTable() throws CloudException {
		initialize();
		CloudTable obj = new CloudTable("Sample");
		CloudTable.get(obj, new CloudTableCallback() {
			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null) {
					Assert.fail(e.getMessage());
				}

				if (table != null) {
					Assert.assertEquals(table.getTableName(), "Sample");
				}
			}

		});
	}

	@Test(timeout = 60000)
	public void shouldGetAllTable() throws CloudException {
		initialize();
		CloudTable.getAll(new CloudTableArrayCallback() {

			@Override
			public void done(CloudTable[] table, CloudException e)
					throws CloudException {
				if (e != null) {
					Assert.fail(e.getMessage());
				}
				if (table.length > 0) {
					Assert.assertTrue(table.length > 0);
				}
			}

		});
	}

	@Test(timeout = 60000)
	public void GetAllTable() throws CloudException {
		initialize();
		CloudTable.getAll(new CloudTableArrayCallback() {

			@Override
			public void done(CloudTable[] table, CloudException e)
					throws CloudException {
				if (e != null) {
					Assert.fail(e.getMessage());
				}
				if (table.length > 0) {
					Assert.assertTrue(table.length > 0);
				}
			}

		});
	}

	@Test(timeout = 80000)
	public void updateNewColumnIntoTable() throws CloudException {
		initialize();
		final String tableName1 = PrivateMethod._makeString();
		String tableName2 = PrivateMethod._makeString();
		final CloudTable obj = new CloudTable(tableName1);
		final CloudTable obj1 = new CloudTable(tableName2);
		obj.save(new CloudTableCallback() {
			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				obj1.save(new CloudTableCallback() {
					@Override
					public void done(CloudTable table1, CloudException e)
							throws CloudException {
						CloudTable.get(obj, new CloudTableCallback() {
							@Override
							public void done(CloudTable getTable,
									CloudException e) throws CloudException {
								Column column1 = new Column("Name",
										DataType.Relation, true, false);
								column1.setRelatedTo(getTable);
								getTable.addColumn(column1);
								getTable.save(new CloudTableCallback() {
									@Override
									public void done(CloudTable newTable,
											CloudException e)
											throws CloudException {
										Column column2 = new Column("Name",
												DataType.Relation, true, false);
										newTable.deleteColumn(column2);
										newTable.save(new CloudTableCallback() {
											@Override
											public void done(
													CloudTable finalTable,
													CloudException e)
													throws CloudException {
												if (e != null) {
													Assert.fail(e.getMessage());
												}
												if (finalTable != null) {
													Assert.assertEquals(
															finalTable
																	.getTableName(),
															tableName1);
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

	@Test(timeout = 50000)
	public void shouldCreateAndDeleteTable() throws CloudException {
		initialize();
		final String tableName = PrivateMethod._makeString();
		CloudTable obj = new CloudTable(tableName);
		obj.save(new CloudTableCallback() {
			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				table.delete(new CloudStringCallback() {
					@Override
					public void done(String response, CloudException e) {

						if (e != null) {
							Assert.fail(e.getMessage());
						}

						if (response == null) {
							Assert.fail("Should have delete the  table");
						} else {
							Assert.assertEquals(response, "Success");
						}

					}
				});
			}
		});
	}

	@Test(timeout = 40000)
	public void addColumnAfterSave() throws CloudException {
		initialize();
		String tableName = PrivateMethod._makeString();
		CloudTable table = new CloudTable(tableName);
		table.save(new CloudTableCallback() {
			@Override
			public void done(CloudTable newTable, CloudException e)
					throws CloudException {
				if (newTable != null) {
					Column column1 = new Column("Name1", DataType.Text, false,
							false);
					newTable.addColumn(column1);
					newTable.save(new CloudTableCallback() {
						@Override
						public void done(CloudTable anotherTable,
								CloudException e) throws CloudException {
							if (anotherTable != null) {
								// need to learn more jsonobject usage
								Assert.assertEquals("success", "success");
							}
							if (e != null)
								Assert.fail(e.getMessage());
						}
					});
				}
			}
		});
	}

	@Test(timeout = 40000)
	public void setTableType() throws CloudException {
		initialize();
		CloudTable table = new CloudTable("Sample");
		CloudTable.get(table, new CloudTableCallback() {
			@Override
			public void done(CloudTable newTable, CloudException e)
					throws CloudException {
				try {
					newTable.document.put("_type", "newType");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				newTable.save(new CloudTableCallback() {
					@Override
					public void done(CloudTable table, CloudException e)
							throws CloudException {
						if (e != null) {
							Assert.assertEquals(e.getMessage(),
									"Internal Server Error");
						}
						if (table != null) {
							Assert.assertEquals("table", table.getType());
						}
					}
				});
			}
		});

	}

	@Test(timeout = 40000)
	public void addColumnToExistingTable() throws CloudException {
		initialize();
		CloudTable table = new CloudTable("Sample");
		CloudTable.get(table, new CloudTableCallback() {
			@Override
			public void done(CloudTable newTable, CloudException e)
					throws CloudException {
				final String name = PrivateMethod._makeString();
				Column col = new Column(name, DataType.Text, false, false);
				newTable.addColumn(col);
				newTable.save(new CloudTableCallback() {
					@Override
					public void done(CloudTable table, CloudException e)
							throws CloudException {
						if (e != null) {
							Assert.fail(e.getMessage());
						}
						if (table != null) {
							Assert.assertFalse(table.getColumn(name) == null);
						}
					}
				});
			}
		});

	}

	@Test(timeout = 40000)
	public void renameTable() throws CloudException {
		initialize();
		CloudTable table = new CloudTable("Sample");
		CloudTable.get(table, new CloudTableCallback() {
			@Override
			public void done(CloudTable newTable, CloudException e)
					throws CloudException {
				newTable.setTableName("sdvds");
				newTable.save(new CloudTableCallback() {
					@Override
					public void done(CloudTable table, CloudException e)
							throws CloudException {
						if (e != null) {
							Assert.assertEquals(e.getMessage(),
									"Internal Server Error");
						}
						if (table != null) {
							Assert.fail("Should not have rename the table");
						}
					}
				});
			}
		});

	}

	@Test(timeout = 250000)
	public void runSequentialTestSuite() throws CloudException {
		initialize();
		CloudTable obj = new CloudTable("Address");
		CloudTable.get(obj, new CloudTableCallback() {
			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				table.delete(new CloudStringCallback() {
					@Override
					public void done(String response, CloudException e)
							throws CloudException {
						if (e != null) {
							Assert.fail("Delete of Address failed: "
									+ e.getMessage());
						}

						if (response == null) {
							Assert.fail("Should have delete the  table, Address");
						} else {
							Assert.assertEquals(response, "Success");
							CloudTable obj = new CloudTable("Company");
							CloudTable.get(obj, new CloudTableCallback() {
								@Override
								public void done(CloudTable table,
										CloudException e) throws CloudException {
									table.delete(new CloudStringCallback() {
										@Override
										public void done(String response,
												CloudException e)
												throws CloudException {
											if (e != null) {
												Assert.fail("Failed to delete table, Company: "
														+ e.getMessage());
											}

											if (response == null) {
												Assert.fail("Should have delete the  table,Company");
											} else {
												Assert.assertEquals(response,
														"Success");
												CloudTable obj = new CloudTable(
														"Employee");
												CloudTable
														.get(obj,
																new CloudTableCallback() {
																	@Override
																	public void done(
																			CloudTable table,
																			CloudException e)
																			throws CloudException {
																		table.delete(new CloudStringCallback() {
																			@Override
																			public void done(
																					String response,
																					CloudException e)
																					throws CloudException {
																				if (e != null) {
																					Assert.fail("Failed to delete table Employee: "
																							+ e.getMessage());
																				}

																				if (response == null) {
																					Assert.fail("Should have delete the  table,Employee");
																				} else {
																					Assert.assertEquals(
																							response,
																							"Success");
																					CloudTable obj = new CloudTable(
																							"Employee");
																					Column age = new Column(
																							"Age",
																							DataType.Number,
																							false,
																							false);
																					Column name = new Column(
																							"Name",
																							DataType.Text,
																							false,
																							false);
																					obj.addColumn(age);
																					obj.addColumn(name);
																					obj.save(new CloudTableCallback() {

																						@Override
																						public void done(
																								CloudTable table,
																								CloudException e)
																								throws CloudException {
																							if (e != null) {
																								Assert.fail("Failed to create table employee: "
																										+ e.getMessage());
																							}

																							if (table != null) {
																								Assert.assertEquals(
																										table.getTableName(),
																										"Employee");
																								CloudTable obj = new CloudTable(
																										"Company");
																								Column revenue = new Column(
																										"Revenue",
																										DataType.Number,
																										false,
																										false);
																								Column name = new Column(
																										"Name",
																										DataType.Text,
																										false,
																										false);
																								obj.addColumn(revenue);
																								obj.addColumn(name);
																								obj.save(new CloudTableCallback() {

																									@Override
																									public void done(
																											CloudTable table,
																											CloudException e)
																											throws CloudException {
																										if (e != null) {
																											Assert.fail("Failed to create table, Company: "
																													+ e.getMessage());
																										}

																										if (table != null) {
																											Assert.assertEquals(
																													table.getTableName(),
																													"Company");
																											CloudTable obj = new CloudTable(
																													"Address");
																											Column city = new Column(
																													"City",
																													DataType.Text,
																													false,
																													false);
																											Column pinCode = new Column(
																													"PinCode",
																													DataType.Number,
																													false,
																													false);
																											obj.addColumn(city);
																											obj.addColumn(pinCode);
																											obj.save(new CloudTableCallback() {

																												@Override
																												public void done(
																														CloudTable table,
																														CloudException e)
																														throws CloudException {
																													if (e != null) {
																														Assert.fail("Failed to create Table, Address: "
																																+ e.getMessage());
																													}

																													if (table != null) {
																														Assert.assertEquals(
																																table.getTableName(),
																																"Address");
																														CloudTable tablee = new CloudTable(
																																"Employee");
																														CloudTable
																																.get(tablee,
																																		new CloudTableCallback() {
																																			@Override
																																			public void done(
																																					CloudTable newTable,
																																					CloudException e)
																																					throws CloudException {
																																				Column company = new Column(
																																						"Company",
																																						DataType.Relation,
																																						false,
																																						false);
																																				CloudTable companyObj = new CloudTable(
																																						"Company");
																																				company.setRelatedTo(companyObj);
																																				newTable.addColumn(company);

																																				Column address = new Column(
																																						"Address",
																																						DataType.Relation,
																																						false,
																																						false);
																																				CloudTable addressObj = new CloudTable(
																																						"Address");
																																				address.setRelatedTo(addressObj);
																																				newTable.addColumn(address);
																																				newTable.save(new CloudTableCallback() {
																																					@Override
																																					public void done(
																																							CloudTable anotherTable,
																																							CloudException e)
																																							throws CloudException {
																																						if (e != null) {
																																							Assert.fail("Failed to update table schema for Address: "
																																									+ e.getMessage());
																																						}

																																						if (anotherTable != null) {
																																							Assert.assertEquals(
																																									anotherTable
																																											.getTableName(),
																																									"Employee");
																																							CloudTable table = new CloudTable(
																																									"Company");
																																							CloudTable
																																									.get(table,
																																											new CloudTableCallback() {
																																												@Override
																																												public void done(
																																														CloudTable newTable,
																																														CloudException e)
																																														throws CloudException {
																																													Column company = new Column(
																																															"Employee",
																																															DataType.Relation,
																																															false,
																																															false);
																																													CloudTable companyObj = new CloudTable(
																																															"Employee");
																																													company.setRelatedTo(companyObj);
																																													newTable.addColumn(company);

																																													Column address = new Column(
																																															"Address",
																																															DataType.Relation,
																																															false,
																																															false);
																																													CloudTable addressObj = new CloudTable(
																																															"Address");
																																													address.setRelatedTo(addressObj);
																																													newTable.addColumn(address);
																																													newTable.save(new CloudTableCallback() {
																																														@Override
																																														public void done(
																																																CloudTable anotherTable,
																																																CloudException e)
																																																throws CloudException {
																																															if (e != null) {
																																																Assert.fail(e
																																																		.getMessage());
																																															}

																																															if (anotherTable != null) {
																																																Assert.assertEquals(
																																																		anotherTable
																																																				.getTableName(),
																																																		"Company");
																																															}
																																														}
																																													});
																																												}
																																											});
																																						}
																																					}
																																				});
																																			}
																																		});
																													}
																												}
																											});

																										}
																									}
																								});
																							}
																						}
																					});
																				}
																			}
																		});
																	}
																});
											}
										}
									});
								}
							});
						}
					}
				});
			}
		});
	}

	@Test(timeout = 50000)
	public void createListTable() throws CloudException {
		initialize();
		final String name = PrivateMethod._makeString();
		CloudTable obj = new CloudTable(name);
		Column subject = new Column("subject", DataType.List, false, false);
		subject.setRelatedTo(DataType.Text);
		Column age = new Column("age", DataType.Number, false, false);
		obj.addColumn(subject);
		obj.addColumn(age);
		obj.save(new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null) {
					Assert.fail(e.getMessage());
				}

				if (table != null) {
					Assert.assertEquals(table.getTableName(), name);
					table.delete(new CloudStringCallback() {

						@Override
						public void done(String response, CloudException e)
								throws CloudException {
							Assert.assertEquals(response, "Success");
						}

					});
				}
			}
		});

	}

	@Test(timeout = 50000)
	public void createTableAllDataTypes() throws CloudException {
		initialize();
		final String name = PrivateMethod._makeString();
		CloudTable custom = new CloudTable(name);
		Column newColumn = new Column("email", DataType.Email, false, false);
		custom.addColumn(newColumn);
		Column newColumn1 = new Column("name", DataType.Text, false, false);
		custom.addColumn(newColumn1);
		Column newColumn2 = new Column("myurl", DataType.URL, false, false);
		custom.addColumn(newColumn2);
		Column newColumn3 = new Column("age", DataType.Number, false, false);
		custom.addColumn(newColumn3);
		Column newColumn4 = new Column("married", DataType.Boolean, false,
				false);
		custom.addColumn(newColumn4);
		Column newColumn5 = new Column("signuptime", DataType.DateTime, false,
				false);
		custom.addColumn(newColumn5);
		Column newColumn6 = new Column("friendlist", DataType.Object, false,
				false);
		custom.addColumn(newColumn6);

		custom.save(new CloudTableCallback() {
			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null) {
					Assert.fail(e.getMessage());
				}

				if (table != null) {
					Assert.assertEquals(table.getTableName(), name);
					table.delete(new CloudStringCallback() {
						@Override
						public void done(String response, CloudException e)
								throws CloudException {

						}

					});
				}
			}
		});

	}

	@Test(timeout = 50000)
	public void shouldGiveAllTable() throws CloudException {
		initialize();

	}
	@Test(timeout = 50000)
	public void shouldGiveSpecificTable() throws CloudException {
		initialize();

	}
	@Test(timeout = 50000)
	public void shouldGiveSpecificTableByName() throws CloudException {
		initialize();
		CloudTable.get("Employee", new CloudTableCallback() {
			
			@Override
			public void done(CloudTable table, CloudException e) throws CloudException {
				if(e!=null)
					Assert.fail(e.getMessage());
				else Assert.assertFalse(table==null);
				
			}
		});

	}
	@Test(timeout = 50000)
	public void shouldCreateColumnThenDelete() throws CloudException {
		initialize();
		CloudTable.get("Employee", new CloudTableCallback() {
			
			@Override
			public void done(CloudTable table, CloudException e) throws CloudException {
				if(e!=null)
					Assert.fail(e.getMessage());
				else{
					Column col=new Column("Test2", DataType.Text);
					table.addColumn(col);
					table.save(new CloudTableCallback() {
						
						@Override
						public void done(CloudTable table, CloudException e) throws CloudException {
							if(e!=null)
								Assert.fail(e.getMessage());
							else{
								if(table.getColumn("Test2")==null)
									Assert.fail("Failed to add column");
								table.deleteColumn("Test2");
								table.save(new CloudTableCallback() {
									
									@Override
									public void done(CloudTable table, CloudException e) throws CloudException {
										if(e!=null)
											Assert.fail(e.getMessage());
										else{
											Assert.assertEquals(null,table.getColumn("Test2"));
											}
										
									}
								});
							}
							
						}
					});
				}
				
			}
		});

	}

	@Test(timeout = 50000)
	public void shouldUpdateColumnInTable() throws CloudException {
		CloudTable table = new CloudTable("abcd");
		Column col = new Column("name", DataType.Text, false, true);
		System.out.println("column=" + col.document.toString());
		table.addColumn(col);
		Column col2 = table.getColumn("name");
		System.out.println("column2=" + col2);
		col2.setRequired(false);
		table.updateColumn(col2);
		Column col3 = table.getColumn("name");
		Assert.assertEquals(col3.getRequired(), false);
		// update column cant take string in java because of serious type
		// management
	}

	@Test(timeout = 50000)
	public void shouldNotDeleteDefaultColumn() throws CloudException {
		initialize();
		CloudTable table = new CloudTable("Company");
		CloudTable.get(table, new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException ee)
					throws CloudException {
				try {
					table.deleteColumn(table.getColumn("id"));
				} catch (CloudException e) {
					Assert.assertEquals("Can Not Delete a Reserved Column",
							e.getMessage());
				}

			}
		});
		// update column cant take string in java because of java type
		// restriction
	}

	@Test(timeout = 50000)
	public void shouldNotPassStringToUpdateColumn() throws CloudException {
		CloudTable table = new CloudTable("abcd");
		Column col = new Column("name", DataType.Text, false, true);
		table.addColumn(col);
		// update column cant take string in java because of java type
		// restriction
	}

	@Test(timeout = 50000)
	public void shouldNotChangeDataTypeOfColumn() throws CloudException {
		initialize();
		CloudTable table = new CloudTable(PrivateMethod._makeString());
		Column col = new Column("name", DataType.Text, false, true);
		table.addColumn(col);
		table.save(new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				Column col = table.getColumn("name");
				try {
					col.document.put("dataType", DataType.valueOf("Number"));
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				table.updateColumn(col);
				table.save(new CloudTableCallback() {

					@Override
					public void done(CloudTable table, CloudException e)
							throws CloudException {
						try {
							DataType dt = DataType.valueOf("Text");
							Assert.assertEquals(dt,
									table.getColumn("name").document
											.get("dataType"));
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				});

			}
		});
	}

	@Test(timeout = 50000)
	public void shouldGetColumnFromTable() throws CloudException {
		CloudTable table = new CloudTable("abcd");
		Column col = new Column("name", DataType.Text, false, true);
		table.addColumn(col);
		Assert.assertTrue(table.getColumn("name") != null);
		// update column cant take string in java because of java type
		// restriction
	}

	@Test(timeout = 50000)
	public void shouldNotRenameColumn() throws CloudException {
		initialize();
		CloudTable table = new CloudTable("Company");
		CloudTable.get(table, new CloudTableCallback() {

			@Override
			public void done(final CloudTable table, CloudException e)
					throws CloudException {
				Column col = table.getColumn(0);
				final String colname = col.getColumnName();
				col.setColumnName("abcd");
				table.updateColumn(col);
				table.save(new CloudTableCallback() {

					@Override
					public void done(CloudTable nutable, CloudException e)
							throws CloudException {
						if (e == null) {
							Assert.assertFalse(nutable.getColumn(colname) == null);
						}

					}
				});

			}
		});
	}

	@Test(timeout = 50000)
	public void shouldNotChangeUniquePropertyOfPreDefinedColumn()
			throws CloudException {
		initialize();
		CloudTable table = new CloudTable("Company");
		CloudTable.get(table, new CloudTableCallback() {

			@Override
			public void done(final CloudTable table, CloudException e)
					throws CloudException {
				Column col = table.getColumn(0);
				final boolean comparer = col.getUnique();
				final String colname = col.getColumnName();
				col.setUnique(!comparer);
				table.updateColumn(col);
				table.save(new CloudTableCallback() {

					@Override
					public void done(CloudTable nutable, CloudException e)
							throws CloudException {
						if (e == null) {
							Assert.assertEquals(nutable.getColumn(colname)
									.getUnique(), comparer);
						}

					}
				});

			}
		});
	}

	@Test(timeout = 50000)
	public void shouldNotChangeUniquePropertyOfDefaultColumn()
			throws CloudException {
		initialize();
		CloudTable table = new CloudTable("Company");
		CloudTable.get(table, new CloudTableCallback() {
			@Override
			public void done(final CloudTable table, CloudException e)
					throws CloudException {
				Column col = table.getColumn(0);
				final String colname = col.getColumnName();
				final boolean comparer = col.getUnique();
				col.setUnique(!comparer);
				table.updateColumn(col);
				table.save(new CloudTableCallback() {

					@Override
					public void done(CloudTable nutable, CloudException e)
							throws CloudException {
						if (e == null)
							Assert.assertEquals(nutable.getColumn(colname)
									.getUnique(), comparer);

					}
				});

			}
		});
	}

	@Test(timeout = 50000)
	public void shouldNotChangeRequiredPropertyOfDefaultColumn()
			throws CloudException {
		initialize();
		CloudTable table = new CloudTable("Company");
		CloudTable.get(table, new CloudTableCallback() {
			@Override
			public void done(final CloudTable table, CloudException e)
					throws CloudException {
				table.getColumn(0).setRequired(false);
				table.save(new CloudTableCallback() {

					@Override
					public void done(CloudTable nutable, CloudException e)
							throws CloudException {
						if (e == null)
							Assert.assertEquals(nutable.getColumn(0)
									.getRequired(), table.getColumn(0)
									.getRequired());

					}
				});

			}
		});
	}

	@Test(timeout = 50000)
	public void shouldChangeRequiredPropertyOfUserDefinedColumn()
			throws CloudException {
		initialize();
		CloudTable table = new CloudTable(PrivateMethod._makeString());
		Column col = new Column("name", DataType.Text, false, true);
		table.addColumn(col);
		table.save(new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {

				Column name = table.getColumn("name");
				name.setRequired(false);
				table.updateColumn(name);
				table.save(new CloudTableCallback() {

					@Override
					public void done(CloudTable table, CloudException e)
							throws CloudException {
						if (e == null)
							Assert.assertFalse(table.getColumn("name")
									.getRequired());

					}
				});

			}
		});
	}

	@Test(timeout = 50000)
	public void createTableStoreGeoPoint() throws CloudException {
		initialize();
		final String name = PrivateMethod._makeString();
		CloudTable custom = new CloudTable(name);
		Column newColumn = new Column("location", DataType.GeoPoint, false,
				false);
		custom.addColumn(newColumn);
		custom.save(new CloudTableCallback() {
			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null) {
					Assert.fail(e.getMessage());
				}

				if (table != null) {
					Assert.assertEquals(table.getTableName(), name);
					table.delete(new CloudStringCallback() {
						@Override
						public void done(String response, CloudException e)
								throws CloudException {

						}

					});
				}
			}
		});
	}

	@Test(timeout = 50000)
	public void createTableSetRelationsToExistingTables() throws CloudException {
		initialize();
		CloudTable custom = new CloudTable("Customx");
		Column newColumn = new Column("newColumn1", DataType.Text, false, false);
		custom.addColumn(newColumn);
		Column newColumn1 = new Column("newColumn7", DataType.Relation, false,
				false);

		CloudTable student1 = new CloudTable("student1");
		newColumn1.setRelatedTo(student1);
		custom.addColumn(newColumn1);
		Column newColumn2 = new Column("newColumn2", DataType.Relation, false,
				false);
		CloudTable custom3 = new CloudTable("Custom3");
		newColumn2.setRelatedTo(custom3);
		custom.addColumn(newColumn2);
		custom.save(new CloudTableCallback() {
			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null) {
					Assert.fail(e.getMessage());
				}

				if (table != null) {
					Assert.assertEquals(table.getTableName(), "Customx");
					table.delete(new CloudStringCallback() {
						@Override
						public void done(String response, CloudException e)
								throws CloudException {
							Assert.assertEquals(response, "Success");
						}
					});
				}
			}
		});

	}

	@Test(timeout = 50000)
	public void createTableWithTextListColumnRelation() throws CloudException {
		initialize();
		CloudTable custom = new CloudTable("textlistrelation");
		Column newColumn = new Column("newColumn1", DataType.Text, false, false);
		custom.addColumn(newColumn);
		Column newColumn1 = new Column("newColumn7", DataType.List, false,
				false);
		CloudTable student1 = new CloudTable("student1");
		newColumn1.setRelatedTo(student1);
		custom.addColumn(newColumn1);
		custom.save(new CloudTableCallback() {
			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null) {
					Assert.fail(e.getMessage());
				}

				if (table != null) {
					Assert.assertEquals(table.getTableName(),
							"textlistrelation");
					table.delete(new CloudStringCallback() {
						@Override
						public void done(String response, CloudException e)
								throws CloudException {
							Assert.assertEquals(response, "Success");
						}
					});
				}
			}
		});

	}

	@Test(timeout = 50000)
	public void createTableSetRelationToDataType() throws CloudException {
		initialize();
		CloudTable custom = new CloudTable("mydatatyperelation");
		Column newColumn = new Column("List_Number", DataType.List, false,
				false);
		newColumn.setRelatedTo(DataType.Number);
		custom.addColumn(newColumn);
		Column newColumn1 = new Column("List_GeoPoint", DataType.List, false,
				false);
		newColumn1.setRelatedTo(DataType.GeoPoint);
		custom.addColumn(newColumn1);
		custom.save(new CloudTableCallback() {
			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null) {
					Assert.fail(e.getMessage());
				}

				if (table != null) {
					Assert.assertEquals(table.getTableName(),
							"mydatatyperelation");
					table.delete(new CloudStringCallback() {
						@Override
						public void done(String response, CloudException e)
								throws CloudException {

						}
					});
				}
			}
		});

	}

}