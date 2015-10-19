package io.cloudboost.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 
 * @author cloudboost
 *
 */

public class SqlLite{
	static String dbURL;
	static Connection conn;
	
	/**
	 * init
	 */
	public static void init(){
		dbURL = "jdbc:sqlite:session.db";
		try {
			conn = DriverManager.getConnection(dbURL);
			 if (conn != null) {
				System.out.println("SQL Lite is Up"); 
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
	            System.out.println("Driver name: " + dm.getDriverName());
	            System.out.println("Driver version: " + dm.getDriverVersion());
	            System.out.println("Product name: " + dm.getDatabaseProductName());
	            System.out.println("Product version: " + dm.getDatabaseProductVersion());
	            Statement stmt = conn.createStatement();
	            String sql = "CREATE TABLE IF NOT EXISTS session(sessionID VARCHAR(100) PRIMARY KEY)";
	            stmt.executeUpdate(sql);
	            stmt.close();
	            System.out.println("Table Created");
	            conn.close();
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Set Session Id
	 * 
	 * @param sessionId
	 */
	public static void setSessionId(String sessionId){
		try {
			conn = DriverManager.getConnection(dbURL);
			if(conn != null){
				Statement stmt = conn.createStatement();
				String sql = "DELETE FROM session;";
		        stmt.executeUpdate(sql);
		        
		        sql = "INSERT INTO session(sessionID) VALUES('"+sessionId+"');";
		        stmt.executeUpdate(sql);
		        stmt.close();
		        conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Delete SessionId
	 * 
	 */
	public static void deleteSessionId(){
		try {
			conn = DriverManager.getConnection(dbURL);
			if(conn != null){
				Statement stmt = conn.createStatement();
		        String sql = "DELETE FROM session;";
		        stmt.executeUpdate(sql);
		        stmt.close();
		        conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * Fetch Session Id
	 * 
	 * @return
	 */
	public static String getSessionId(){
		try {
			conn = DriverManager.getConnection(dbURL);
			if(conn != null){
				Statement stmt = conn.createStatement();
		        String sql = "SELECT sessionId FROM session;";
		        ResultSet rs = stmt.executeQuery(sql);
		        stmt.close();
		        conn.close();
		        while( rs.next() ){
		        	return rs.getString("sessionID");
		        }
		     
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}