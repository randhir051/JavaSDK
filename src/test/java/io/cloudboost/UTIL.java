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

	public static void main(String[] args) throws CloudException {
		init();
		// CloudUser user=new CloudUser();
		// // String user=PrivateMethod._makeString();
		// user.setUserName("trust");
		// user.setPassword("trust");
		// // user.setEmail("trust@cloudboost.io");
		// user.logIn(new CloudUserCallback() {
		//
		// @Override
		// public void done(CloudUser user, CloudException e) throws
		// CloudException {
		// if(e!=null)
		// System.out.println("error: "+e.getMessage());
		// else
		// user.logOut(new CloudUserCallback() {
		//
		// @Override
		// public void done(CloudUser user, CloudException e) throws
		// CloudException {
		// if(e!=null)
		// System.out.println("error: "+e.getMessage());
		// else{System.out.println("logged out");}
		//
		// }
		// });
		//
		// }
		// });
		int n = 9;
		// System.out.println("binary of n is "+);
		decToBin(n);
		System.out.println("our bin number is " + bin);
		System.out.println("binary gap="
				+ getBinGap("100011101100111110011100"));
	}

	public static int getBinGap(String s) {

		char pres;
		char prev = 'a';
		int bestCount = 0;
		int currentCount = 0;
		for (int i = 0; i < s.length(); i++) {
			pres = s.charAt(i);
			if (i > 0)
				prev = s.charAt(i - 1);
			if (pres == '1' && prev == '0') {
				if (currentCount > bestCount)
					bestCount = currentCount;

				currentCount = 0;
				continue;
			}
			if (pres == '0')
				currentCount += 1;
			else
				continue;
		}
		return bestCount;
	}

	static String bin = "";

	public static void decToBin(int number) {

		int remainder;

		if (number <= 1) {
			remainder = number;
			bin += remainder;
			return; 
		}

		remainder = number % 2;
		decToBin(number >> 1);
		bin += remainder;
	}
}
