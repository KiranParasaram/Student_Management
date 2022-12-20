package com.factory;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
	
	private static Connection con;
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static Connection getConnection() {
		return con;
	}

}
