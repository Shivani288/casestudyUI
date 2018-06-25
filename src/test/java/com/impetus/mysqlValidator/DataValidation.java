package com.impetus.mysqlValidator;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;

import javax.xml.bind.DatatypeConverter;

@SuppressWarnings("restriction")
public class DataValidation {
	MySQL mysql;
	GenerateReport gr = new GenerateReport();
	MetadataValidation metaData = new MetadataValidation();

	public DataValidation(String user, String pass, String dbName) {
		mysql = new MySQL(user, pass, dbName);
		metaData = new MetadataValidation();
	}

	public String printTab(int num) {
		StringBuilder tab = new StringBuilder();
		for (int i = 0; i < num; i++) {
			tab.append("\t");
		}
		return tab.toString();
	}

	public ResultSet getSampleData(String table, int limit) {
		ResultSet rs = null;
		String[] column = { "*" };
		rs = mysql.getSampleData(table, column, limit);
		return rs;
	}

	public String getHash(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte[] hash = digest.digest(data.getBytes("UTF-8"));
		return DatatypeConverter.printHexBinary(hash);
	}

	public int getRowCount(String table, String condition) {

		return mysql.getRowCount(table, condition);
	}
/*
	public void rowByRow(String sourceTable, String destTable) {

		ResultSet sourceData = mysql.getData(sourceTable);
		ResultSet rs2 = get_resultSet(destTable);

		// ResultSetMetaData source_metadata = rs1.getMetaData();
		// ResultSetMetaData dest_metadata = rs2.getMetaData();

		// int source_columnCount = source_metadata.getColumnCount();
		// int dest_columnCount = dest_metadata.getColumnCount();

		while (rs1.next() || rs2.next()) {
			rs2.next();
			String sourceRow = "", destRow = "";
			int source_rowCount = get_rowCount(sourceTable);
			int dest_rowCount = get_rowCount(destTable);

			for (int i = 1; i <= source_rowCount; i++) {
				sourceRow = sourceRow + " " + rs1.getString(i);
			}
			for (int i = 1; i <= dest_rowCount; i++) {
				destRow = destRow + " " + rs2.getString(i);
			}
			// System.out.println(sourceRow);
			// System.out.println(destRow);

			String sourceHash = getHash(sourceRow);
			String destHash = getHash(destRow);

			if (sourceHash.equals(destHash)) {
				System.out.println("MAtched");
			} else {

				System.out.println("Source Row not matched: " + sourceRow);
				System.out.println("Destination Row not matched: " + destRow);
				System.out.println("-----------------------------------------");
			}

			/*
			 * String a=rs1.getString("MD5_checksum1"); String
			 * b=rs2.getString("MD5_checksum2"); if(a==b) { result= "Matched"; } else {
			 * String [] NotMatchedcolumnNames = rs1.getArray(columnLabel)
			 * result="Not Matched"; }
			 */
/*
		}
		rs1.next();
	}

	public String CustomQuery_SourceDest(String sourceTable, String destTable, String sourceQuery, String destQuery)
			throws SQLException {
		String result = "";
		ResultSet rs1 = get_queryResult(sourceQuery);
		ResultSet rs2 = get_queryResult(destQuery);
		if (rs1.equals(rs2)) {
			result = "Matched";
			System.out.println(result);
		} else {
			result = "Not Matched";
			System.out.println(result);
			System.out.println("Source : " + this.ReturnResult(rs1));
			System.out.println("Destination : " + this.ReturnResult(rs2));
		}
		return result;
	}

	public String CustomQuery(String sourceTable, String destTable, String sourceQuery, String destQuery)
			throws SQLException {
		String result = "";
		String res1 = "";
		String res2 = "";
		ResultSet rs1 = get_resultSet(sourceTable);
		ResultSet rs2 = get_resultSet(destTable);
		int source_resultCount = get_rowCount(sourceQuery);
		int dest_resultCount = get_rowCount(destQuery);

		if (source_resultCount == dest_resultCount) {
			result = "resultCount is equal : " + source_resultCount;
			System.out.println(result);
			return result;
		} else {
			result = "resultCount is not equal : " + "Source:" + source_resultCount + "Destination:" + dest_resultCount;

			res1 = ReturnResult(rs1);
			res2 = ReturnResult(rs2);
			System.out.println(result);
			System.out.println("Source : " + res1);
			System.out.println("Destination : " + res2);
			return result;
		}
	}

	public String CustomQuery(String destTable, String query) throws SQLException {
		String result = "";
		Statement stmt = conn.createStatement();
		ResultSet rs5 = stmt.executeQuery(query);
		result = ReturnResult(rs5);
		System.out.println(result);
		return result;
	}

	public String ReturnResult(ResultSet rs) throws SQLException {
		String res = "", colName = "";
		ResultSetMetaData result = rs.getMetaData();
		int col = result.getColumnCount();

		for (int i = 1; i <= col; i++) {
			colName = colName + result.getColumnName(i) + "\t\t";
		}
		System.out.println(colName);
		while (rs.next()) {
			for (int i = 1; i <= col; i++) {
				res = res + rs.getString(i) + "\t\t";
			}
			System.out.println(res);
			res = "";
		}
		return res;
	}
*/
}