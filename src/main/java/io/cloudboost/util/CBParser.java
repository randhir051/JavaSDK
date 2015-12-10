package io.cloudboost.util;

import io.cloudboost.CloudApp;
import io.cloudboost.beans.CBResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CBParser {
    static Random random = new Random();

	  static String boundary = "---------------------------" + randomString() + randomString() + randomString();

    public static CBResponse callJson(String myUrl,String httpMethod, JSONObject parameters) {
    	try {
			parameters.put("sdk", "java");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
        String params=parameters.toString(); 
//        System.out.println(params);
        URL url=null;
		try {
			url = new URL(myUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
        HttpURLConnection conn=null;
		try {
			
			conn = (HttpURLConnection) url.openConnection();
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
	        if(respCode!=200){
	        	String error=inputStreamToString(conn.getErrorStream());
//	        	System.out.println(error);
	        	CBResponse response=new CBResponse(respMsg, respMsg, respCode, null);
	        	response.setError(error);
	        	return response; 
	        	}
	        inputString=inputStreamToString(conn.getInputStream());
	       

	        
		} catch (IOException e) {
			CBResponse resp=new CBResponse(respMsg, respMsg, respCode, sid);
			return resp;
		}
        CBResponse rr=new CBResponse(inputString, respMsg, respCode,sid);
//        System.out.println(rr.toString());
       return rr;
    }
    private static void writeName(String name) throws IOException {
        newline();
        write("Content-Disposition: form-data; name=\"");
        write(name);
        write("\"");
      }
    protected static void newline() throws IOException {
        write("\r\n");
      }
    private static void boundary() throws IOException {
        write("--");
        write(boundary);
      }
    protected static void writeln(String s) throws IOException {
        write(s);
        newline();
      }
    private static void write(String s) throws IOException {
		dos.writeBytes(s);
		
	}
	public static void setParameter(String name, String value) throws IOException {
        boundary();
        writeName(name);
        newline(); newline();
        writeln(value);
      }
    public static  DataOutputStream dos=null;
    public static  HttpURLConnection conn=null;
    public static CBResponse postFormData(String myurl,String httpMethod,JSONObject params,InputStream is) throws IOException{
        URL url=null;
  		try {
  			url = new URL(myurl);
  		} catch (MalformedURLException e) {
  			e.printStackTrace();
  		}
  		try {
  			
  			conn = (HttpURLConnection) url.openConnection();
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
          conn.setRequestProperty("Content-Type",
                  "multipart/form-data; boundary=" + boundary);
			dos = new DataOutputStream(conn.getOutputStream());
			setParameter("key", CloudApp.getAppKey());
			setParameter("fileObj", params.toString());
			setParameter("sdk", "java");
			setFile("fileToUpload", "blob", is);
			InputStream stream=post();
			int code=conn.getResponseCode();
			String msg=conn.getResponseMessage();
			String response=inputStreamToString(stream);
			CBResponse resp=new CBResponse(response, msg, code,null);
			return resp;
			
    }
    public static InputStream post() throws IOException {
        boundary();
        writeln("--");
        dos.close();
        return conn.getInputStream();
      }
    private static void pipe(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[500000];
        int nread;
        int navailable;
        int total = 0;
        synchronized (in) {
          while((nread = in.read(buf, 0, buf.length)) >= 0) {
            out.write(buf, 0, nread);
            total += nread;
          }
        }
        out.flush();
        buf = null;
      }
    public static void setFile(String name, String filename, InputStream is) throws IOException {
        boundary();
        writeName(name);
        write("; filename=\"");
        write(filename);
        write("\"");
        newline();
        write("Content-Type: ");
        String type = HttpURLConnection.guessContentTypeFromName(filename);
        if (type == null) type = "application/octet-stream";
        writeln(type);
        newline();
        pipe(is, dos);
        newline();
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


	   protected static String randomString() {
	     return Long.toString(random.nextLong(), 36);
	   }
	   public static ArrayList<String> jsonToList(JSONArray array){
		   ArrayList<String> list=new ArrayList<>();
		   for(int i=0;i<array.length();i++){
			   try {
				list.add(array.getString(i));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		   }
		   return list;
	   }

}
