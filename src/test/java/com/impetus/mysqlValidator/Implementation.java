package com.impetus.mysqlValidator;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Implementation {

	MetadataValidation sourceMetadata;
	MetadataValidation targetMetadata;
	DataValidation sourceData;
	DataValidation targetData;
	public static final String SOURCE = "Source Table:";
	public static final String TARGET = "Target Table:";
	private static final Logger logger = Logger.getLogger(Implementation.class.getName());

	public Implementation(String sourceUser, String sourcePassword, String sourceDb, String targetUser,
			String targetPassword, String targetDb) {

		sourceMetadata = new MetadataValidation(sourceUser, sourcePassword, sourceDb);
		targetMetadata = new MetadataValidation(targetUser, targetPassword, targetDb);
		sourceData = new DataValidation(sourceUser, sourcePassword, sourceDb);
		targetData = new DataValidation(targetUser, targetPassword, targetDb);
	}

	GenerateReport gr = new GenerateReport();

	public void printConnectionReport(String sourceType, String targetType, String sourceDb, String targetDb,
			String sourceUser, String targetUser, String sourceTable, String targetTable) throws IOException {
		gr.writeToFile("....................................................................................");
		gr.writeToFile("Source Datasource: " + sourceType + "\t\t" + "Destinaiton Datasource: " + targetType);
		System.out.println("Source Database: " + sourceDb + "\t\t" + "Destinaiton Database: " + targetDb);
		gr.writeToFile("Source Database: " + sourceDb + "\t\t" + "Destinaiton Database: " + targetDb);
		System.out.println("Source Database: " + sourceDb + "\t\t" + "Destinaiton Database: " + targetDb);

		gr.writeToFile("Source User: " + sourceUser + "\t\t" + "Destinaiton User: " + targetUser);
		System.out.println("Source User: " + sourceUser + "\t\t" + "Destinaiton User: " + targetUser);

		gr.writeToFile("Source Table: " + sourceTable + "\t\t" + "Destinaiton Table: " + targetTable);
		System.out.println("Source Table: " + sourceTable + "\t\t" + "Destinaiton Table: " + targetTable);
		gr.writeToFile("\n\n");
	}

	public void compareColumnCount(String sourceTable, String destinationTable) {

		int sourceColCount = sourceMetadata.getColumnCount(sourceTable);
		int destinationColCount = targetMetadata.getColumnCount(destinationTable);
		gr.writeToFile("\n\n");
		gr.writeToFile("Metadata Validation...............................................");
		gr.writeToFile("Validating Column Counts for source table and destination table...");
		if (sourceColCount > destinationColCount) {
			gr.writeToFile("Source table has more columns. Details are given below");
			System.out.println("\n" + "Source table has more columns. Details are given below");
			gr.writeToFile("\n\n");
			gr.writeToFile(SOURCE);
			gr.writeToFile("\n\n");
			System.out.println("\n" + SOURCE);
			sourceMetadata.getColumnDetails(sourceTable);
			gr.writeToFile("\n\n");
			gr.writeToFile(TARGET);
			gr.writeToFile("\n\n");
			System.out.println("\n" + TARGET);
			targetMetadata.getColumnDetails(destinationTable);

		} else if (sourceColCount < destinationColCount) {
			gr.writeToFile("\n\n" );
			gr.writeToFile("Destination table has more columns. Details are given below");
			System.out.println("\n" + "Destination table has more columns. Details are given below");

			gr.writeToFile("\n\n" );
			gr.writeToFile(SOURCE);
			gr.writeToFile("\n\n");
			System.out.println("\n" + SOURCE);
			sourceMetadata.getColumnDetails(sourceTable);

			gr.writeToFile("\n\n" );
			gr.writeToFile(TARGET);
			gr.writeToFile("\n\n");
			System.out.println("\n" + TARGET);
			targetMetadata.getColumnDetails(destinationTable);

		} else if (sourceColCount == destinationColCount) {
			gr.writeToFile("\n\n" );
			gr.writeToFile("Column count matches in both table");
			System.out.println("\n" + "Column count matches in both table");
		} else {
			gr.writeToFile("\n\n" );
			gr.writeToFile("Error while comparing columns");
			logger.log(Level.SEVERE, "Error while comparing columns");
		}
	}

	public void CompareDataType(String sourceTable, String targetTable) {

		String[] sourceTableColumnType = sourceMetadata.getColumnDataType(sourceTable);
		String[] destinationTableColumnType = targetMetadata.getColumnDataType(targetTable);
		String[] sourceTableColumnName = sourceMetadata.getColumnNames(sourceTable);
		String[] destinationTableColumnName = targetMetadata.getColumnNames(targetTable);

		int count = 0;
		int j = 0;
		int sourceColCount = sourceMetadata.getColumnCount(sourceTable);
		int targetColCount = targetMetadata.getColumnCount(targetTable);
		int colDiff = Math.abs(sourceColCount - targetColCount);

		if (sourceColCount < targetColCount) {
			sourceColCount = sourceColCount + colDiff;
			sourceTableColumnType = Arrays.copyOf(sourceTableColumnType, sourceTableColumnType.length + colDiff);
			sourceTableColumnName = Arrays.copyOf(sourceTableColumnName, sourceTableColumnName.length + colDiff);
		} else if (sourceColCount > targetColCount) {
			targetColCount = targetColCount + colDiff;
			destinationTableColumnType = Arrays.copyOf(destinationTableColumnType,
					destinationTableColumnType.length + colDiff);
			destinationTableColumnName = Arrays.copyOf(destinationTableColumnName,
					destinationTableColumnName.length + colDiff);
		}
		gr.writeToFile("\n\n" );
		gr.writeToFile("Validating Column Datatypes for source table and destination table...");
		gr.writeToFile("Detail of the columns which do not match:");
		System.out.println("Details of the column which do not match:");
		gr.writeToFile("\n\n" );
		gr.writeToFile(SOURCE + "\t\t\t\t\t" + TARGET);
		System.out.println(SOURCE + "\t\t\t\t\t" + TARGET);
		for (int i = 0; i < sourceColCount; i++) {
			if (sourceTableColumnType[i] == null) {
				sourceTableColumnType[i] = "-";
				sourceTableColumnName[i] = "-";
			}
			if (destinationTableColumnType[i] == null) {
				destinationTableColumnType[i] = "-";
				destinationTableColumnName[i] = "-";
			}

			if (sourceTableColumnType[i] == destinationTableColumnType[i]
					&& (sourceTableColumnType[i] != "-" || destinationTableColumnType[i] != "-")) {
				count++;
			} else {
				gr.writeToFile(sourceTableColumnName[i] + "\t\t" + sourceTableColumnType[i] + "\t\t\t"
						+ destinationTableColumnName[i] + "\t\t" + destinationTableColumnType[i]);
				System.out.println(sourceTableColumnName[i] + "\t\t" + sourceTableColumnType[i] + "\t\t\t"
						+ destinationTableColumnName[i] + "\t\t" + destinationTableColumnType[i]);
				j++;
			}
		}
		if (count == targetColCount) {
			gr.writeToFile("\n\n" );
			gr.writeToFile("All column data type matches in source table and destination tables");
			System.out.println("All column data type matches in source table and destination tables");
		} else {
			gr.writeToFile("\n\n" );
			gr.writeToFile("No of columns matches: " + count);
			gr.writeToFile("No of columns do not matches: " + j);
			gr.writeToFile("\n\n" );
			System.out.println("No of columns matches: " + count);
			System.out.println("No of columns do not matches: " + j);
		}
	}

	public void validateSamples(String sourceTable, String targetTable, int limit) throws SQLException {

		String[] sourceColName = sourceMetadata.getColumnNames(sourceTable);
		String[] targetColName = targetMetadata.getColumnNames(targetTable);

		ResultSet sourceSampleData = sourceData.getSampleData(sourceTable, limit);
		ResultSet targetSampleData = targetData.getSampleData(targetTable, limit);

		int sourceColumnCount = sourceMetadata.getColumnCount(sourceTable);
		int destinationColumnCount = targetMetadata.getColumnCount(targetTable);
		int count = limit;

		gr.writeToFile(SOURCE + targetData.printTab(sourceColumnCount + 2) + TARGET);
		System.out.println(SOURCE + targetData.printTab(sourceColumnCount + 2) + TARGET);

		StringBuilder colNames = new StringBuilder();
		StringBuilder sourceRows = new StringBuilder();
		StringBuilder targetRows = new StringBuilder();

		for (int i = 0; i < sourceColumnCount; i++) {
			colNames.append(sourceColName[i] + "\t");
		}
		colNames.append("\t\t");
		for (int i = 0; i < destinationColumnCount; i++) {
			colNames.append(targetColName[i] + "\t");
		}
		gr.writeToFile("" + colNames);
		System.out.println(colNames);

		if (sourceColumnCount == destinationColumnCount) {
			while (sourceSampleData.next() && targetSampleData.next()) {
				for (int i = 1; i <= sourceColumnCount; i++) {
					sourceRows.append(sourceSampleData.getString(i) + "\t");
					targetRows.append(targetSampleData.getString(i) + "\t");
				}

				if (sourceRows.toString().equalsIgnoreCase(targetRows.toString())) {
					count--;
				} else {
					gr.writeToFile(sourceRows + targetData.printTab(2) + targetRows);
					System.out.println(sourceRows + targetData.printTab(2) + targetRows);
					sourceRows.setLength(0);
					targetRows.setLength(0);
				}
			}
		} else {
			gr.writeToFile("Column count does not match for source table '" + sourceTable + "'" + " and target table '"
					+ targetTable + "'");
			System.out.println("Column count does not match for source table '" + sourceTable + "'"
					+ " and target table '" + targetTable + "'");
		}
		if (count == 0) {
			gr.writeToFile("All Samples matched Successfully");
			System.out.println("All Samples matched Successfully");
		}
	}

	public boolean checkSourceConnection(String table) {
		boolean result = false;
		ResultSet source = sourceData.getSampleData(table, 1);
		try {
			if (source.next()) {
				result = true;
			} else {
				result = false;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	public boolean checkTargetConnection(String table) {
		boolean result = false;
		ResultSet source = targetData.getSampleData(table, 1);
		try {
			if (source.next()) {
				result = true;
			} else {
				result = false;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	public void validateRowbyRow(String sourceTable, String destinationTable) {
	}

	public static void main(String[] args) throws IOException, SQLException {

		String sourceName, sourceUser, sourcePassword, sourceDatabase, sourceTable;
		String destinationName, destinationUser, destinationPassword, destinationDatabase, destinationTable;

		sourceName = "mysql";
		sourceUser = "root";
		sourcePassword = "";
		sourceDatabase = "library";
		sourceTable = "books";

		destinationName = "mysql";
		destinationUser = "root";
		destinationPassword = "";
		destinationDatabase = "library";
		destinationTable = "books";

		Implementation implement = new Implementation(sourceUser, sourcePassword, sourceDatabase, destinationUser,
				destinationPassword, destinationDatabase);
		GenerateReport gr = new GenerateReport();

		gr.writeToFile("\n\n");
		gr.writeToFile("Metadata Validation........................................");
		gr.writeToFile("Validating Column Counts for source table and destination table...");

		gr.writeToFile("\n");
		System.out.println();
		System.out.println("Validating Column Counts for source table and destination table...");
		implement.compareColumnCount(sourceTable, destinationTable);

		gr.writeToFile("\n");
		gr.writeToFile("Validating Column Datatypes for source table and destination table...");
		System.out.println();
		System.out.println("Validating Column Datatypes for source table and destination table...");
		implement.CompareDataType(sourceTable, destinationTable);

		gr.writeToFile("\n\n\n");
		gr.writeToFile("Data Validation........................................");
		gr.writeToFile("Validating Sample Data from source table and target tables...");
		int limit = 2;
		gr.writeToFile("Sample Size: " + limit);
		System.out.println();
		System.out.println("Validating Sample Data from source table and target table...");
		implement.validateSamples(sourceTable, destinationTable, limit);

		gr.writeToFile("\n\n\n");
		System.out.println("\n");
		gr.writeToFile("Validating Row by Row using MD5 from source table and target tables...");
		System.out.println("Validating Row by Row using MD5 from source table and target tables...");
		implement.validateRowbyRow(sourceTable, destinationTable);
	}
}
