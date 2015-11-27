package io.cloudboost.util;

import io.cloudboost.beans.CBResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

public class CBParser {
    public static CBResponse callJson(String myUrl,String httpMethod, JSONObject parameters) {
        String params=parameters.toString(); 
//        System.out.println("parameters: "+params);
//        System.out.println("URL="+myUrl);

        URL url=null;
		try {
			url = new URL(myUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
        HttpsURLConnection conn=null;
		try {
			
			conn = (HttpsURLConnection) url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
        conn.setDoOutput(true);
        conn.setDoInput(true);
        
        conn.setReadTimeout(10000);
        try {
			conn.setRequestMethod(httpMethod);
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:26.0) Gecko/20100101 Firefox/26.0");
        conn.setRequestProperty("Content-Type", "application/json");
        DataOutputStream dos=null;
        String respMsg = null;
        int respCode = 0;
        String inputString = null;
        String sid=null;
		try {
			dos = new DataOutputStream(conn.getOutputStream());
		       
	        dos.writeBytes(params);
	        dos.flush();
	        dos.close();
	        respCode=conn.getResponseCode();
	        respMsg=conn.getResponseMessage();
//	        System.out.println("code="+respCode+",msg="+respMsg);
	        inputString=inputStreamToString(conn.getInputStream());
	        
		} catch (IOException e) {
			CBResponse resp=new CBResponse(respMsg, respMsg, respCode, sid);
			return resp;
		}
        CBResponse rr=new CBResponse(inputString, respMsg, respCode,sid);
//        System.out.println(rr.toString());
       return rr;
//        return null;
    }
   
    	
    
	   public static String inputStreamToString(InputStream is) {
	        StringBuffer responseInBuffer = new StringBuffer();
	        byte[] b = new byte[4028];
	        while (true) {
	            try {
	                int n = is.read(b);
	                if (n == -1) {
	                    break;
	                }
	                responseInBuffer.append(new String(b, 0, n));
	            } catch (IOException e) {
	                e.printStackTrace();
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	        }
	        return new String(responseInBuffer.toString());
	    }
}
