package com.impetus.mysqlValidator;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MetadataValidation {
	MySQL mysql;
	GenerateReport gr = new GenerateReport();
	private static final Logger logger = Logger.getLogger(Implementation.class.getName());
	private static final String ERROR = " Error: ";  
	public MetadataValidation(String user, String pass, String dbName) {
		mysql = new MySQL(user, pass, dbName);
	}

	public MetadataValidation() {
	}

	public int getColumnCount(String table) {
		
		int colCount = 0;
		ResultSet data = null;
		ResultSetMetaData metaData = null;
		
		data = mysql.getData(table);
		try {
			metaData = data.getMetaData();
			colCount = metaData.getColumnCount();
		} catch (SQLException e) {
			gr.writeToFile("Error while getting no. of columns from table: " + table + ERROR + e.getMessage());
			logger.log(Level.FINE,"Error while getting no. of columns from table: " + table + ERROR + e.getMessage());
		}
		return colCount;
	}

	public String[] getColumnDataType(String table) {

		String[] dataType = {};
		int colCount = 0;
		ResultSet data = null;
		ResultSetMetaData metaData = null;
		
		data = mysql.getData(table);
		try {
			metaData = data.getMetaData();
			colCount = metaData.getColumnCount();
			dataType = new String[colCount];
			for (int i = 0; i < colCount; i++) {
				dataType[i] = metaData.getColumnTypeName(i + 1);
			}
		} catch (SQLException e) {
			gr.writeToFile("Error while getting column datatypes from table: " + table + ERROR + e.getMessage());
			logger.log(Level.FINE,"Error while getting column datatypes from table: " + table + ERROR + e.getMessage());
		}
		return dataType;
	}

	public String[] getColumnNames(String table) {

		String[] columnList = {};
		int columnCount = 0;
		ResultSet data;
		ResultSetMetaData metaData = null;
		
		data = mysql.getData(table);
		try {
			metaData = data.getMetaData();
			columnCount = metaData.getColumnCount();
			columnList = new String[columnCount];

			for (int i = 0; i < columnCount; i++) {
				columnList[i] = metaData.getColumnName(i + 1);
			}
		} catch (Exception e) {
			gr.writeToFile("Error while getting column names from table: " + table + ERROR + e.getMessage());
			logger.log(Level.SEVERE, "Error while getting column names from table: " + table + ERROR + e.getMessage());
		}
		return columnList;
	}

	public void getColumnDetails(String table) {

		String[] columnDataTypeList = this.getColumnDataType(table);
		String[] columnNameList = this.getColumnNames(table);
		int colCount = this.getColumnCount(table);

		for (int i = 0; i < colCount; i++) {
			String content = columnNameList[i] + "\t\t" + columnDataTypeList[i];
			gr.writeToFile(content);
			System.out.println(content);
		}
	}
	
	public int compareColumnCount(String sourceTable, String targetTable) {
		int result = 0;
		int sourceColCount = this.getColumnCount(sourceTable);
		int targetColCount = this.getColumnCount(targetTable);
		
		if(sourceColCount == targetColCount) {
			result = 1; // same number of columns
		}else if(sourceColCount > targetColCount) {
			result = 2; //source table has more columns
		}else if(sourceColCount < targetColCount) {
			result = 3; //target table has more columns
		}else {
			gr.writeToFile("Error Occured while comparing column count for source table '"+sourceTable+"'and target table '"+targetTable+"'");
			logger.log(Level.SEVERE,"Error Occured while comparing column count for source table '"+sourceTable+"'and target table '"+targetTable+"'");
		}
		return result;
	}
}
