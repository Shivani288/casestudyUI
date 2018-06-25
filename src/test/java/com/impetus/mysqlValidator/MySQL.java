package com.impetus.mysqlValidator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQL {
	DbConnection db;
	private static final Logger logger = Logger.getLogger(MySQL.class.getName());

	public MySQL(String user, String pass, String dbName) {
		db = new DbConnection(user, pass, dbName);
	}

	public ResultSet getData(String table) {

		ResultSet rs = null;
		logger.fine("Getting data for table: " + table);
		Connection conn = db.connect();
		String sql = "Select * from " + table;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		return rs;
	}

	public ResultSet getData(String table, String[] columns, String conditions) {

		ResultSet rs = null;
		StringBuilder sql = new StringBuilder("SELECT ");
		int col = columns.length;
		for (int i = 0; i < col; i++) {
			if (columns[i] == "*") {
				sql.append("*");
			} else {
				if (i == col - 1 && columns[i] != "*") {
					sql.append("`" + columns[i] + "`");
				} else {
					sql.append("`" + columns[i] + "`, ");
				}
			}
		}
		if (conditions != "") {
			sql.append(" FROM " + table + " WHERE " + conditions + ";");
		} else {
			sql.append(" FROM " + table + ";");
		}
		logger.fine("Getting data for given columns");
		logger.fine(sql.toString());
		Connection conn = db.connect();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql.toString());
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		return rs;
	}

	public ResultSet getSampleData(String table, String[] columns, int limit) {

		ResultSet rs = null;
		StringBuilder sql = new StringBuilder("SELECT ");
		int col = columns.length;
		for (int i = 0; i < col; i++) {
			if (columns[i] == "*") {
				sql.append("*");
			} else {
				if (i == col - 1 && columns[i] != "*") {
					sql.append("`" + columns[i] + "`");
				} else {
					sql.append("`" + columns[i] + "`, ");
				}
			}
		}
		sql.append(" FROM " + table + " LIMIT " + limit + ";");
		logger.info("Select row query: " + sql);
		Connection conn = db.connect();
		Statement stmt = null;
		try {
			if (limit <= 0) {
				logger.info("Row limit should be grater than 0.");
			} else {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql.toString());
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		return rs;
	}

	public ResultSet getData(String table, String sql) {
		ResultSet rs = null;
		Connection conn = db.connect();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		return rs;
	}

	public int getRowCount(String table, String condition) {

		int rowCount = 0;
		ResultSet rs;
		Connection conn = db.connect();
		Statement stmt;
		String sql = "SELECT count(*) as rows FROM "+table+" WHERE "+condition;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				rowCount = rs.getInt("rows");
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		return rowCount;
	}
}
