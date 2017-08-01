package com.wintv.framework.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public final class DbUtils {

	public static Connection getConn(){
		String driver = "oracle.jdbc.driver.OracleDriver"; 
		//String url = "jdbc:oracle:thin:@192.168.1.91:1521:caipiao"; 
		String url = "jdbc:oracle:thin:@192.168.1.220:1521:orcl"; 
        Connection conn = null; 
		String u="lottery";
		String p="lottery";
		try 
		{ 
		   Class.forName(driver); 
		   conn = DriverManager.getConnection(url,u,p); 
		}catch(Exception e){
		    e.printStackTrace();
		}
      return conn;
     }
}
