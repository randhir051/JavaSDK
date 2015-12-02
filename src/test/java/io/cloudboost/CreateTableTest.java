package io.cloudboost;

import io.cloudboost.Column.DataType;
import junit.framework.Assert;

import org.junit.Test;

public class CreateTableTest {
	void initialize() {
		// CloudApp.init("sample123",
		// "Qopoy/kXd+6G734HsjQMqGPGOvwEJYmBG84lQawRmWM=");
		CloudApp.init("travis123",
				"vfmMIbP4KaqxihajNqLNFGuub8CIOLREP1oH0QC0qy4=");
	}

	@Test(timeout = 50000)
	public void sequentialTests_0() throws CloudException {
		initialize();
		CloudTable table = new CloudTable("Address");
		CloudTable.get(table, new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null)
					Assert.fail(e.getMessage());
				else
					table.delete(new CloudStringCallback() {

						@Override
						public void done(String x, CloudException e)
								throws CloudException {
							if (e != null)
								Assert.fail(e.getMessage());
							else if ("Success".equals(x)) {
								CloudTable company = new CloudTable("Company");
								CloudTable.get(company,
										new CloudTableCallback() {

											@Override
											public void done(CloudTable table,
													CloudException e)
													throws CloudException {
												table.delete(new CloudStringCallback() {

													@Override
													public void done(String x,
															CloudException e)
															throws CloudException {
														if (e != null)
															Assert.fail(e
																	.getMessage());
														else if ("Success"
																.equals(x)) {
															CloudTable employee = new CloudTable(
																	"Employee");
															CloudTable
																	.get(employee,
																			new CloudTableCallback() {

																				@Override
																				public void done(
																						CloudTable table,
																						CloudException e)
																						throws CloudException {
																					table.delete(new CloudStringCallback() {

																						@Override
																						public void done(
																								String x,
																								CloudException e)
																								throws CloudException {
																							if (e != null)
																								Assert.fail(e
																										.getMessage());
																							else if ("Success"
																									.equals(x)) {
																								// create
																								// employee
																								// table
																								CloudTable employee = createEmployee();
																								employee.save(new CloudTableCallback() {

																									@Override
																									public void done(
																											CloudTable table,
																											CloudException e)
																											throws CloudException {
																										if (e != null)
																											Assert.fail(e
																													.getMessage());
																										CloudTable company = createCompany();
																										company.save(new CloudTableCallback() {

																											@Override
																											public void done(
																													CloudTable table,
																													CloudException e)
																													throws CloudException {
																												if (e != null)
																													Assert.fail(e
																															.getMessage());
																												CloudTable address = createAddress();
																												address.save(new CloudTableCallback() {

																													@Override
																													public void done(
																															CloudTable table,
																															CloudException e)
																															throws CloudException {
																														if (e != null)
																															Assert.fail(e
																																	.getMessage());
																														updateEmployeeSchema(new CloudTableCallback() {

																															@Override
																															public void done(
																																	CloudTable table,
																																	CloudException e)
																																	throws CloudException {
																																if (e != null)
																																	Assert.fail(e
																																			.getMessage());
																																updateCompanySchema(new CloudTableCallback() {

																																	@Override
																																	public void done(
																																			CloudTable table,
																																			CloudException e)
																																			throws CloudException {
																																		if (e != null)
																																			Assert.fail(e
																																					.getMessage());
																																		Assert.assertTrue(table != null);

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

	public CloudTable createEmployee() {
		Column age = new Column("age", DataType.Number, false, false);
		Column name = new Column("name", DataType.Text, false, false);
		Column dob = new Column("dob", DataType.DateTime, false, false);
		Column password = new Column("password", DataType.EncryptedText, false,
				false);
		CloudTable table = new CloudTable("Employee");
		try {
			table.addColumn(new Column[] { age, name, dob, password });
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return table;

	}

	public CloudTable createCompany() {
		Column revenue = new Column("Revenue", DataType.Number, false, false);
		Column name = new Column("Name", DataType.Text, false, false);
		Column file = new Column("File", DataType.File, false, false);
		CloudTable table = new CloudTable("Company");
		try {
			table.addColumn(new Column[] { revenue, name, file });
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return table;

	}

	public CloudTable createAddress() {
		Column city = new Column("City", DataType.Text, false, false);
		Column pincode = new Column("PinCode", DataType.Number, false, false);
		CloudTable table = new CloudTable("Address");
		try {
			table.addColumn(new Column[] { city, pincode });
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return table;

	}

	public void updateEmployeeSchema(final CloudTableCallback call) {
		CloudTable table = new CloudTable("Employee");
		try {
			CloudTable.get(table, new CloudTableCallback() {

				@Override
				public void done(CloudTable table, CloudException e)
						throws CloudException {
					Column company = new Column("Company", DataType.Relation,
							false, false);
					company.setRelatedTo(new CloudTable("Company"));
					Column address = new Column("Address", DataType.Relation,
							false, false);
					address.setRelatedTo(new CloudTable("Address"));
					table.addColumn(new Column[] { company, address });
					table.save(call);

				}
			});
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateCompanySchema(final CloudTableCallback call) {
		CloudTable table = new CloudTable("Company");
		try {
			CloudTable.get(table, new CloudTableCallback() {

				@Override
				public void done(CloudTable table, CloudException e)
						throws CloudException {
					Column employee = new Column("Company", DataType.List,
							false, false);
					employee.setRelatedTo(new CloudTable("Employee"));
					Column address = new Column("Address", DataType.Relation,
							false, false);
					address.setRelatedTo(new CloudTable("Address"));
					table.addColumn(new Column[] { employee, address });
					table.save(call);

				}
			});
		} catch (CloudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(timeout = 50000)
	public void deleteCreateEmpty() throws CloudException {
		initialize();
		CloudTable empty = new CloudTable("Empty");
		CloudTable.get(empty, new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null)
					Assert.fail(e.getMessage());
				table.delete(new CloudStringCallback() {

					@Override
					public void done(String x, CloudException e)
							throws CloudException {
						if ("Success".equals(x)) {
							CloudTable empty = new CloudTable("Empty");
							empty.save(new CloudTableCallback() {

								@Override
								public void done(CloudTable table,
										CloudException e) throws CloudException {
									Assert.assertTrue(table != null);

								}
							});
						}

					}
				});

			}
		});
	}

	@Test(timeout = 50000)
	public void deleteCreateUnderscore() throws CloudException {
		initialize();
		CloudTable empty = new CloudTable("UnderScoreTable_a");
		CloudTable.get(empty, new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null)
					Assert.fail(e.getMessage());
				else
					table.delete(new CloudStringCallback() {

						@Override
						public void done(String x, CloudException e)
								throws CloudException {
							if ("Success".equals(x)) {
								CloudTable underscore = new CloudTable(
										"UnderScoreTable_a");
								Column age_a = new Column("Age_a",
										DataType.Text, false, false);
								Column age_b = new Column("Age_b",
										DataType.Text, false, false);

								underscore.addColumn(new Column[] { age_a,
										age_b });
								underscore.save(new CloudTableCallback() {

									@Override
									public void done(CloudTable table,
											CloudException e)
											throws CloudException {
										Assert.assertTrue(table != null);

									}
								});
							}

						}
					});

			}
		});
	}

	@Test(timeout = 50000)
	public void deleteCreatestudent_4() throws CloudException {

		CloudTable student_4 = new CloudTable("student444");
		Column subject = new Column("subject", DataType.List, false, false);
		subject.setRelatedToType("Text");
		Column age = new Column("age", DataType.Number, false, false);

		student_4.addColumn(new Column[] { subject, age });
		student_4.save(new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (table != null)
					table.delete(new CloudStringCallback() {

						@Override
						public void done(String x, CloudException e)
								throws CloudException {
							Assert.assertEquals("Success", x);

						}
					});

			}
		});

	}

	@Test(timeout = 50000)
	public void deleteAddressTable() throws CloudException {
		initialize();

	}

	@Test(timeout = 50000)
	public void deleteUnderscoreTable() throws CloudException {
		initialize();

	}

	@Test(timeout = 50000)
	public void deleteCompanyTable() throws CloudException {
		initialize();

	}

	@Test(timeout = 50000)
	public void deleteEmptyTable() throws CloudException {
		initialize();

	}

	@Test(timeout = 50000)
	public void deleteEmployeeTable() throws CloudException {
		initialize();

	}

	@Test(timeout = 50000)
	public void createEmployeeTable() throws CloudException {
		initialize();

	}

	@Test(timeout = 50000)
	public void createEmptyTable() throws CloudException {
		initialize();

	}

	@Test(timeout = 50000)
	public void createTableWith2UnderscoreColumns() throws CloudException {
		initialize();

	}

	@Test(timeout = 50000)
	public void createCompanyTable() throws CloudException {
		initialize();

	}

	@Test(timeout = 50000)
	public void createAddressTable() throws CloudException {
		initialize();

	}

	@Test(timeout = 50000)
	public void updateEmployeeSchema() throws CloudException {
		initialize();

	}

	@Test(timeout = 50000)
	public void updateCompanySchema() throws CloudException {
		initialize();

	}

	@Test(timeout = 50000)
	public void createTableStudent4() throws CloudException {
		initialize();

	}

	@Test(timeout = 50000)
	public void createTableRole() throws CloudException {
		initialize();
		CloudTable role = new CloudTable("Rolex");
		role.save(new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (table != null)
					table.delete(new CloudStringCallback() {

						@Override
						public void done(String x, CloudException e)
								throws CloudException {
							Assert.assertEquals("Success", x);

						}
					});

			}
		});

	}

	@Test(timeout = 50000)
	public void createTableUser() throws CloudException {
		initialize();
		CloudTable user = new CloudTable("Userx");
		Column col = new Column("newColumn", DataType.Text, false, false);
		user.addColumn(col);
		user.save(new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (table != null) {
					Column col = new Column("newColumn1", DataType.Text, false,
							false);
					table.addColumn(col);
					table.save(new CloudTableCallback() {

						@Override
						public void done(CloudTable table, CloudException e)
								throws CloudException {
							if (e != null)
								Assert.fail(e.getMessage());
							else {
								table.delete(new CloudStringCallback() {

									@Override
									public void done(String x, CloudException e)
											throws CloudException {
										Assert.assertEquals("Success", x);

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
	public void createTableCustom() throws CloudException {
		initialize();
		CloudTable custom = new CloudTable("CustomAll");
		Column column1 = new Column("newColumn1", DataType.Email, false, false);
		Column column2 = new Column("newColumn2", DataType.Text, false, false);
		Column column3 = new Column("newColumn3", DataType.URL, false, false);
		Column column4 = new Column("newColumn4", DataType.Number, false, false);
		Column column5 = new Column("newColumn5", DataType.Boolean, false,
				false);
		Column column6 = new Column("newColumn6", DataType.DateTime, false,
				false);
		Column column7 = new Column("newColumn7", DataType.Object, false, false);
		Column column8 = new Column("newColumn8", DataType.GeoPoint, false,
				false);
		custom.addColumn(new Column[] { column1, column2, column3, column4,
				column5, column6, column7, column8 });
		custom.save(new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null)
					Assert.fail(e.getMessage());
				else
					table.delete(new CloudStringCallback() {

						@Override
						public void done(String x, CloudException e)
								throws CloudException {
							Assert.assertEquals("Success", x);

						}
					});

			}
		});

	}

	@Test(timeout = 50000)
	public void updateCustomTable() throws CloudException {
		initialize();
		CloudTable table = new CloudTable("Custom");
		CloudTable.get(table, new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null)
					Assert.fail(e.getMessage());
				else {
					table.delete(new CloudStringCallback() {

						@Override
						public void done(String x, CloudException e)
								throws CloudException {
							if (e != null)
								Assert.fail(e.getMessage());
							else if ("Success".equals(x)) {
								CloudTable custom = new CloudTable("Custom");
								Column column1 = new Column("newColumn1",
										DataType.Email, false, false);
								Column column2 = new Column("newColumn2",
										DataType.Text, false, false);
								Column column3 = new Column("newColumn3",
										DataType.URL, false, false);
								Column column4 = new Column("newColumn4",
										DataType.Number, false, false);
								Column column5 = new Column("newColumn5",
										DataType.Boolean, false, false);
								Column column6 = new Column("newColumn6",
										DataType.DateTime, false, false);
								Column column7 = new Column("newColumn7",
										DataType.Object, false, false);
								Column column8 = new Column("newColumn8",
										DataType.GeoPoint, false, false);
								custom.addColumn(new Column[] { column1,
										column2, column3, column4, column5,
										column6, column7, column8 });
								custom.save(new CloudTableCallback() {

									@Override
									public void done(CloudTable table,
											CloudException e)
											throws CloudException {
										if (e != null)
											Assert.fail(e.getMessage());
										else {
											Column col = table
													.getColumn("newColumn7");
											col.setDataType(DataType.List);
											col.setRelatedTo(new CloudTable(
													"Custom"));
											table.setColumn(col);
											table.save(new CloudTableCallback() {

												@Override
												public void done(
														CloudTable table,
														CloudException e)
														throws CloudException {
													if (e != null)
														Assert.fail(e
																.getMessage());
													else
														Assert.assertTrue(table != null);

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

	@Test(timeout = 50000)
	public void createTableCustom5() throws CloudException {
		initialize();
		CloudTable table = new CloudTable(PrivateMethod._makeString());
		Column col = new Column("location", DataType.GeoPoint, false, false);
		table.addColumn(col);
		table.save(new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null)
					Assert.fail(e.getMessage());
				else
					table.delete(new CloudStringCallback() {

						@Override
						public void done(String x, CloudException e)
								throws CloudException {
							Assert.assertEquals("Success", x);

						}
					});

			}
		});

	}

	@Test(timeout = 50000)
	public void createTableSample() throws CloudException {
		initialize();
		CloudTable table = new CloudTable("Sample");
		CloudTable.get(table, new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null)
					Assert.fail(e.getMessage());
				else
					table.delete(new CloudStringCallback() {

						@Override
						public void done(String x, CloudException e)
								throws CloudException {
							if (e != null)
								Assert.fail(e.getMessage());
							else {
								if ("Success".equals(x)) {
									CloudTable table = new CloudTable("Sample");
									Column col1 = new Column("name",
											DataType.Text, true, false);
									Column col2 = new Column("unique",
											DataType.Text, false, true);
									Column col3 = new Column("stringArray",
											DataType.List, false, false);
									col3.setRelatedTo(DataType.Text);
									Column col4 = new Column("objectArray",
											DataType.List, false, false);
									col4.setRelatedTo(DataType.Text);
									Column col5 = new Column("file",
											DataType.File, false, false);
									Column col6 = new Column("fileList",
											DataType.List, false, false);
									col6.setRelatedTo(DataType.File);
									table.addColumn(new Column[] { col1, col2,
											col3, col4, col5, col6 });
									table.save(new CloudTableCallback() {

										@Override
										public void done(CloudTable table,
												CloudException e)
												throws CloudException {
											Assert.assertFalse(table == null);
										}

									});
								}
							}

						}
					});

			}
		});

	}

	@Test(timeout = 50000)
	public void updateTableSample() throws CloudException {
		initialize();

	}

	@Test(timeout = 50000)
	public void createTableHostel() throws CloudException {
		initialize();
		CloudTable table = new CloudTable("hostalite");
		Column col = new Column("room", DataType.Number);
		Column col2 = new Column("name", DataType.Text);
		table.addColumn(new Column[] { col, col2 });
		table.save(new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null)
					Assert.fail(e.getMessage());
				else {
					table.delete(new CloudStringCallback() {

						@Override
						public void done(String x, CloudException e)
								throws CloudException {
							Assert.assertEquals("Success", x);

						}
					});
				}

			}
		});

	}

	@Test(timeout = 50000)
	public void createTablestudent1() throws CloudException {
		initialize();
		CloudTable table = new CloudTable("studentsss1");
		Column col = new Column("age", DataType.Number);
		Column col2 = new Column("name", DataType.Text);
		Column col3 = new Column("newColumn", DataType.Relation);
		col3.setRelatedTo(new CloudTable("hostel"));

		table.addColumn(new Column[] { col, col2, col3 });
		table.save(new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null)
					Assert.fail(e.getMessage());
				else {
					table.delete(new CloudStringCallback() {

						@Override
						public void done(String x, CloudException e)
								throws CloudException {
							Assert.assertEquals("Success", x);

						}
					});
				}

			}
		});

	}

	@Test(timeout = 50000)
	public void createTableStudent() throws CloudException {
		initialize();
		CloudTable table = new CloudTable("Etudiant");
		Column col = new Column("age", DataType.Number);
		Column col2 = new Column("name", DataType.Text);
		Column col3 = new Column("class", DataType.Text);
		Column col4 = new Column("description", DataType.Text);

		table.addColumn(new Column[] { col, col2, col3, col4 });
		table.save(new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null)
					Assert.fail(e.getMessage());
				else {
					table.delete(new CloudStringCallback() {

						@Override
						public void done(String x, CloudException e)
								throws CloudException {
							Assert.assertEquals("Success", x);

						}
					});
				}

			}
		});

	}

	@Test(timeout = 50000)
	public void createTableCustom18() throws CloudException {
		initialize();
		CloudTable table = new CloudTable("Douane18");
		Column col = new Column("number", DataType.Number);
		table.addColumn(col);
		table.save(new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null)
					Assert.fail(e.getMessage());
				else {
					table.delete(new CloudStringCallback() {

						@Override
						public void done(String x, CloudException e)
								throws CloudException {
							Assert.assertEquals("Success", x);

						}
					});
				}

			}
		});

	}

	@Test(timeout = 50000)
	public void createTableCustom3() throws CloudException {
		initialize();
		CloudTable table = new CloudTable("Douane3");
		Column col = new Column("address", DataType.Text);
		table.addColumn(col);
		table.save(new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null)
					Assert.fail(e.getMessage());
				else {
					table.delete(new CloudStringCallback() {

						@Override
						public void done(String x, CloudException e)
								throws CloudException {
							Assert.assertEquals("Success", x);

						}
					});
				}

			}
		});

	}

	@Test(timeout = 50000)
	public void createTableCustom7() throws CloudException {
		initialize();
		CloudTable table = new CloudTable("Douane7");
		Column col = new Column("requiredNumber", DataType.Number);
		table.addColumn(col);
		table.save(new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null)
					Assert.fail(e.getMessage());
				else {
					table.delete(new CloudStringCallback() {

						@Override
						public void done(String x, CloudException e)
								throws CloudException {
							Assert.assertEquals("Success", x);

						}
					});
				}

			}
		});
	}

	@Test(timeout = 50000)
	public void createTableCustom2() throws CloudException {
		initialize();
		CloudTable table = new CloudTable("Douane2");
		Column col = new Column("newColumn1", DataType.Text);
		Column col2 = new Column("newColumn7", DataType.Relation);
		col2.setRelatedTo(new CloudTable("student1"));
		Column col3 = new Column("newColumn2", DataType.Relation);
		col3.setRelatedTo(new CloudTable("Custom3"));

		table.addColumn(new Column[] { col, col2, col3 });
		table.save(new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null)
					Assert.fail(e.getMessage());
				else {
					table.delete(new CloudStringCallback() {

						@Override
						public void done(String x, CloudException e)
								throws CloudException {
							Assert.assertEquals("Success", x);

						}
					});
				}

			}
		});

	}

	@Test(timeout = 50000)
	public void createTableCustom4() throws CloudException {
		initialize();
		CloudTable table = new CloudTable("Douane4");
		Column col = new Column("newColumn1", DataType.Text);
		Column col2 = new Column("newColumn7", DataType.List);
		col2.setRelatedTo(new CloudTable("student1"));

		table.addColumn(new Column[] { col, col2 });
		table.save(new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null)
					Assert.fail(e.getMessage());
				else {
					table.delete(new CloudStringCallback() {

						@Override
						public void done(String x, CloudException e)
								throws CloudException {
							Assert.assertEquals("Success", x);

						}
					});
				}

			}
		});

	}

	@Test(timeout = 50000)
	public void createTableCustom14() throws CloudException {
		initialize();
		CloudTable table = new CloudTable("Douane14");
		Column col = new Column("ListNumber", DataType.List);
		col.setRelatedTo(DataType.Number);
		Column col2 = new Column("ListGeoPoint", DataType.List);
		col2.setRelatedTo(DataType.GeoPoint);

		table.addColumn(new Column[] { col, col2 });
		table.save(new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null)
					Assert.fail(e.getMessage());
				else {
					table.delete(new CloudStringCallback() {

						@Override
						public void done(String x, CloudException e)
								throws CloudException {
							Assert.assertEquals("Success", x);

						}
					});
				}

			}
		});

	}

	@Test(timeout = 50000)
	public void createTableCustom1() throws CloudException {
		initialize();
		CloudTable table = new CloudTable("Douane1");
		Column col = new Column("newColumn1", DataType.Boolean);
		Column col2 = new Column("description", DataType.Text);
		Column col3 = new Column("newColumn", DataType.Text);

		table.addColumn(new Column[] { col, col2, col3 });
		table.save(new CloudTableCallback() {

			@Override
			public void done(CloudTable table, CloudException e)
					throws CloudException {
				if (e != null)
					Assert.fail(e.getMessage());
				else {
					table.delete(new CloudStringCallback() {

						@Override
						public void done(String x, CloudException e)
								throws CloudException {
							Assert.assertEquals("Success", x);

						}
					});
				}

			}
		});

	}

}
