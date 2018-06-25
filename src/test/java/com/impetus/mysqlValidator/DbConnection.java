package com.impetus.mysqlValidator;
import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
	String driverClass = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/";
	String user;
	String password;
	String dbName;

	public DbConnection(String user, String password, String dbName) {
		this.user = user;
		this.password = password;
		this.dbName = dbName;

		//System.out.println("Connection object created");
	}

	public Connection connect() {

		Connection conn = null;
		try {
			Class.forName(driverClass);
			if (dbName == null || dbName == "") {
				System.out.println("Database name is not provided");
			} else {
				conn = DriverManager.getConnection(url + dbName + "?useSSL=false", user, password);
				//System.out.println("Connected to database: "+dbName);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage()+e);
		}

		return conn;
	}
}
