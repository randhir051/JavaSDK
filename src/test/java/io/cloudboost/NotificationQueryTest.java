package io.cloudboost;

import junit.framework.Assert;

import org.junit.Test;

public class NotificationQueryTest {
	void initialize() {
		CloudApp.init("travis123", "6dzZJ1e6ofDamGsdgwxLlQ==");
		// CloudApp.init("travis123","vfmMIbP4KaqxihajNqLNFGuub8CIOLREP1oH0QC0qy4=");
	}
	@Test(timeout = 100000)
	public void queryOnEqualToOverCO() throws CloudException,
			InterruptedException {
		initialize();
			CloudObject obj = new CloudObject("student1");
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(final CloudObject x, CloudException t)
						throws CloudException {
					CloudObject obj2 = new CloudObject("student1");
					obj2.save(new CloudObjectCallback() {
						
						@Override
						public void done(CloudObject x1, CloudException t) throws CloudException {
							CloudQuery q=new CloudQuery("Custom2");
							q.equalTo("newColumn7", x);
							CloudObject.on("Custom2", "created", q, new CloudObjectCallback() {
								
								@Override
								public void done(CloudObject x, CloudException t) throws CloudException {
//									;;
									
								}
							});
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							CloudObject ob=new CloudObject("Custom2");
							ob.set("newColumn7", x1);
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					});
				
					
					
				}
			});

	}
	@Test(timeout = 100000)
	public void startsWith1() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q2=new CloudQuery("Student");
		q2.startsWith("name", "n");
		CloudObject.on("Student", "created", q2, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
//				
//				;
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "ben");
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
//						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void startsWith() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q2=new CloudQuery("Student");
		q2.startsWith("name", "n");
		CloudObject.on("Student", "created", q2, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
//				Assert.a();
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "nawaz");
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
//						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void or3() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.equalTo("age", 11);
		CloudQuery q2=new CloudQuery("Student");
		q2.equalTo("name", "nawaz");
		CloudQuery qq=CloudQuery.or(q, q2);
		CloudObject.on("Student", "created", qq, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
//				
//				;
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("age", 10);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void or2() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.equalTo("age", 11);
		CloudQuery q2=new CloudQuery("Student");
		q2.equalTo("name", "nawaz");
		CloudQuery qq=CloudQuery.or(q, q2);
		CloudObject.on("Student", "created", qq, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
//				;
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "ben");
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void or1() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.equalTo("age", 11);
		CloudQuery q2=new CloudQuery("Student");
		q2.equalTo("name", "nawaz");
		CloudQuery qq=CloudQuery.or(q, q2);
		CloudObject.on("Student", "created", qq, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
//				;
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("age", 11);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void or() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.equalTo("age", 11);
		CloudQuery q2=new CloudQuery("Student");
		q2.equalTo("name", "nawaz");
		CloudQuery qq=CloudQuery.or(q, q2);
		CloudObject.on("Student", "created", qq, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
//				;
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "nawaz");
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void containsAllTrick() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.containedIn("age",new Integer[]{11});
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
//				;
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.set("age", 12);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void containsAll() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.containedIn("age",new Integer[]{11});
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
//				;
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.set("age", 11);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void notContainedInTrick() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.containedIn("age",new Integer[]{11});
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
//				;
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.set("age", 11);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void notContainedIn() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.containedIn("age",new Integer[]{11});
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
//				;
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.set("age", 12);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void containedInTrick() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.containedIn("age",new Integer[]{12});
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				;
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.set("age", 11);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void containedIn() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.containedIn("age",new Integer[]{11});
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				;
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.set("age", 11);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void existsNotTrick() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.doesNotExists("age");
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				;
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.set("age", 11);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void existsNot() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.doesNotExists("age");
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				Assert.assertTrue(true);
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void existsTrick() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.exists("age");
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				Assert.assertTrue(true);
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void exists() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.exists("age");
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				Assert.assertTrue(true);
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.set("age", 11);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void queryLessThanEqTrick2() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.lessThan("age", 10);
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				if (t != null) {
					Assert.fail(t.getMessage());
				}
				if (data.get("name").equals("sample")) {
					System.out.print(data.get("name").toString());
					CloudObject.off("Student", "created",
							new CloudStringCallback() {
								@Override
								public void done(String x, CloudException e)
										throws CloudException {
									if (e != null) {
										Assert.fail(e.getMessage());
									}
								}
							});
				} else {
					Assert.fail("Wrong data received");
				}
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.set("age", 11);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void queryLessThanEqTrick1() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.lessThan("age", 10);
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				if (t != null) {
					Assert.fail(t.getMessage());
				}
				if (data.get("name").equals("sample")) {
					System.out.print(data.get("name").toString());
					CloudObject.off("Student", "created",
							new CloudStringCallback() {
								@Override
								public void done(String x, CloudException e)
										throws CloudException {
									if (e != null) {
										Assert.fail(e.getMessage());
									}
								}
							});
				} else {
					Assert.fail("Wrong data received");
				}
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.set("age", 9);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void queryLessThanEq() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.lessThanEqualTo("age", 10);
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				if (t != null) {
					Assert.fail(t.getMessage());
				}
				if (data.get("name").equals("sample")) {
					System.out.print(data.get("name").toString());
					CloudObject.off("Student", "created",
							new CloudStringCallback() {
								@Override
								public void done(String x, CloudException e)
										throws CloudException {
									if (e != null) {
										Assert.fail(e.getMessage());
									}
								}
							});
				} else {
					Assert.fail("Wrong data received");
				}
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.set("age", 10);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void queryLessThanTrick2() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.lessThan("age", 10);
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				if (t != null) {
					Assert.fail(t.getMessage());
				}
				if (data.get("name").equals("sample")) {
					System.out.print(data.get("name").toString());
					CloudObject.off("Student", "created",
							new CloudStringCallback() {
								@Override
								public void done(String x, CloudException e)
										throws CloudException {
									if (e != null) {
										Assert.fail(e.getMessage());
									}
								}
							});
				} else {
					Assert.fail("Wrong data received");
				}
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.set("age", 11);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void queryLessThanTrick1() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.lessThan("age", 10);
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				if (t != null) {
					Assert.fail(t.getMessage());
				}
				if (data.get("name").equals("sample")) {
					System.out.print(data.get("name").toString());
					CloudObject.off("Student", "created",
							new CloudStringCallback() {
								@Override
								public void done(String x, CloudException e)
										throws CloudException {
									if (e != null) {
										Assert.fail(e.getMessage());
									}
								}
							});
				} else {
					Assert.fail("Wrong data received");
				}
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.set("age", 10);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void queryLessThan() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.lessThan("age", 10);
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				if (t != null) {
					Assert.fail(t.getMessage());
				}
				if (data.get("name").equals("sample")) {
					System.out.print(data.get("name").toString());
					CloudObject.off("Student", "created",
							new CloudStringCallback() {
								@Override
								public void done(String x, CloudException e)
										throws CloudException {
									if (e != null) {
										Assert.fail(e.getMessage());
									}
								}
							});
				} else {
					Assert.fail("Wrong data received");
				}
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.set("age", 9);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void queryGreaterThanOrEqTrick2() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.greaterThanEqualTo("age", 10);
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				if (t != null) {
					Assert.fail(t.getMessage());
				}
				if (data.get("name").equals("sample")) {
					System.out.print(data.get("name").toString());
					CloudObject.off("Student", "created",
							new CloudStringCallback() {
								@Override
								public void done(String x, CloudException e)
										throws CloudException {
									if (e != null) {
										Assert.fail(e.getMessage());
									}
								}
							});
				} else {
					Assert.fail("Wrong data received");
				}
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.set("age", 9);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void queryGreaterThanOrEqTrick1() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.greaterThanEqualTo("age", 10);
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				if (t != null) {
					Assert.fail(t.getMessage());
				}
				if (data.get("name").equals("sample")) {
					System.out.print(data.get("name").toString());
					CloudObject.off("Student", "created",
							new CloudStringCallback() {
								@Override
								public void done(String x, CloudException e)
										throws CloudException {
									if (e != null) {
										Assert.fail(e.getMessage());
									}
								}
							});
				} else {
					Assert.fail("Wrong data received");
				}
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.set("age", 11);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void queryGreaterThanOrEq() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.greaterThanEqualTo("age", 10);
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				if (t != null) {
					Assert.fail(t.getMessage());
				}
				if (data.get("name").equals("sample")) {
					System.out.print(data.get("name").toString());
					CloudObject.off("Student", "created",
							new CloudStringCallback() {
								@Override
								public void done(String x, CloudException e)
										throws CloudException {
									if (e != null) {
										Assert.fail(e.getMessage());
									}
								}
							});
				} else {
					Assert.fail("Wrong data received");
				}
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.set("age", 10);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void queryGreaterThanTrick() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.greaterThan("age", 10);
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				if (t != null) {
					Assert.fail(t.getMessage());
				}
				if (data.get("name").equals("sample")) {
					System.out.print(data.get("name").toString());
					CloudObject.off("Student", "created",
							new CloudStringCallback() {
								@Override
								public void done(String x, CloudException e)
										throws CloudException {
									if (e != null) {
										Assert.fail(e.getMessage());
									}
								}
							});
				} else {
					Assert.fail("Wrong data received");
				}
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.set("age", 9);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void queryGreaterThan() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.greaterThan("age", 10);
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				if (t != null) {
					Assert.fail(t.getMessage());
				}
				if (data.get("name").equals("sample")) {
					System.out.print(data.get("name").toString());
					CloudObject.off("Student", "created",
							new CloudStringCallback() {
								@Override
								public void done(String x, CloudException e)
										throws CloudException {
									if (e != null) {
										Assert.fail(e.getMessage());
									}
								}
							});
				} else {
					Assert.fail("Wrong data received");
				}
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.set("age", 11);
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void queryOnNotEqualTo() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.equalTo("name", "sample");
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				if (t != null) {
					Assert.fail(t.getMessage());
				}
				if (data.get("name").equals("sample")) {
					System.out.print(data.get("name").toString());
					CloudObject.off("Student", "created",
							new CloudStringCallback() {
								@Override
								public void done(String x, CloudException e)
										throws CloudException {
									if (e != null) {
										Assert.fail(e.getMessage());
									}
								}
							});
				} else {
					Assert.fail("Wrong data received");
				}
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void queryOnNotEqualToTrick() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.notEqualTo("name", "sample");
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				if (t != null) {
					Assert.fail(t.getMessage());
				}
				if (data.get("name").equals("sample")) {
					System.out.print(data.get("name").toString());
					CloudObject.off("Student", "created",
							new CloudStringCallback() {
								@Override
								public void done(String x, CloudException e)
										throws CloudException {
									if (e != null) {
										Assert.fail(e.getMessage());
									}
								}
							});
				} else {
					Assert.fail("Wrong data received");
				}
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample1");
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void querySkipOne() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.setSkip(1);
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				if (t != null) {
					Assert.fail(t.getMessage());
				}
				if (data.get("name").equals("sample")) {
					System.out.print(data.get("name").toString());
					CloudObject.off("Student", "created",
							new CloudStringCallback() {
								@Override
								public void done(String x, CloudException e)
										throws CloudException {
									if (e != null) {
										Assert.fail(e.getMessage());
									}
								}
							});
				} else {
					Assert.fail("Wrong data received");
				}
			}
		});

		Thread.sleep(2000);
		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
		}


	}
	@Test(timeout = 100000)
	public void queryOnEqualToTrick() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.equalTo("name", "sample1");
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				if (t != null) {
					Assert.fail(t.getMessage());
				}
				if (data.get("name").equals("sample")) {
					System.out.print(data.get("name").toString());
					CloudObject.off("Student", "created",
							new CloudStringCallback() {
								@Override
								public void done(String x, CloudException e)
										throws CloudException {
									if (e != null) {
										Assert.fail(e.getMessage());
									}
								}
							});
				} else {
					Assert.fail("Wrong data received");
				}
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void queryOnEqualTo() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.equalTo("name", "sample");
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				if (t != null) {
					Assert.fail(t.getMessage());
				}
				if (data.get("name").equals("sample")) {
					System.out.print(data.get("name").toString());
					CloudObject.off("Student", "created",
							new CloudStringCallback() {
								@Override
								public void done(String x, CloudException e)
										throws CloudException {
									if (e != null) {
										Assert.fail(e.getMessage());
									}
								}
							});
				} else {
					Assert.fail("Wrong data received");
				}
			}
		});

		Thread.sleep(2000);
//		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
//		}


	}
	@Test(timeout = 100000)
	public void queryLimitTwo() throws CloudException,
			InterruptedException {
		initialize();
		CloudQuery q = new CloudQuery("Student");
		q.setLimit(2);
		CloudObject.on("Student", "created", q, new CloudObjectCallback() {
			@Override
			public void done(CloudObject data, CloudException t)
					throws CloudException {
				
				if (t != null) {
					Assert.fail(t.getMessage());
				}
				if (data.get("name").equals("sample")) {
					System.out.print(data.get("name").toString());
					CloudObject.off("Student", "created",
							new CloudStringCallback() {
								@Override
								public void done(String x, CloudException e)
										throws CloudException {
									if (e != null) {
										Assert.fail(e.getMessage());
									}
								}
							});
				} else {
					Assert.fail("Wrong data received");
				}
			}
		});

		Thread.sleep(2000);
		for(int i=0;i<3;i++){
			CloudObject obj = new CloudObject("Student");
			obj.set("name", "sample");
			obj.save(new CloudObjectCallback() {
				@Override
				public void done(CloudObject x, CloudException t)
						throws CloudException {
					System.out.println("created");
					if (t != null) {
						Assert.fail(t.getMessage());
					}
				}
			});
			Thread.sleep(2000);
		}


	}
}
