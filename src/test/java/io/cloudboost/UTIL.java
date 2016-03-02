package io.cloudboost;

import io.cloudboost.json.JSONObject;

public class UTIL {

	public static void init() {
		CloudApp.init("bengi123", "mLiJB380x9fhPRCjCGmGRg==");
	}

	public static void initMaster() {
		CloudApp.init("bengi123",
				"MjFWX9D3JqTa76tcEHt9GL2ITB8Gzsp68S1+3oq7CBE=");
	}

	public static void initKisenyiMaster() {
		CloudApp.init("kisenyi", "3cau5aq6i85zzLiIpbZ84Y+NDkS7Ojlx4Mj26+oSsMg=");
	}

	public static void initKisenyi() {
		CloudApp.init("kisenyi", "yW0nFG/XF1GCfgaRdbj4KA==");
	}
	public static void main(String[] args) {
		String s="{ben:2}";
		JSONObject o=new JSONObject(s);
		System.out.println(o.toString());
	}
}
