package io.cloudboost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.cloudboost.Column.DataType;
import junit.framework.Assert;

public class UTIL {
	public static void init(){
		CloudApp.init("bengi123",
				"mLiJB380x9fhPRCjCGmGRg==");
	}
	public static void initMaster(){
		CloudApp.init("bengi123",
				"MjFWX9D3JqTa76tcEHt9GL2ITB8Gzsp68S1+3oq7CBE=");
	}
	public static void initKisenyiMaster(){
		CloudApp.init("kisenyi",
				"3cau5aq6i85zzLiIpbZ84Y+NDkS7Ojlx4Mj26+oSsMg=");
	}
	public static void main(String[] args) throws CloudException {
		UTIL.initMaster();
		CloudTable table = new CloudTable("student1");
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
//					table.delete(new CloudStringCallback() {
//
//						@Override
//						public void done(String x, CloudException e)
//								throws CloudException {
//							Assert.assertEquals("Success", x);
//
//						}
//					});
				}

			}
		});
		
	}
}
