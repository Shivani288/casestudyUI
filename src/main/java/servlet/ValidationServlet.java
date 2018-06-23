package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String sourceDb;
	private String targetDb;
	private String sourceUser;
	private String targetUser;
	private String sourcePassword;
	private String targatPassword;
	private String sourceTable;
	private String targetTable;
	private String connection = "0";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String validationType = request.getParameter("validationType");
		if (validationType.equals("connnection")) {
			createConnection(request, response);
		} else if (validationType.equals("matchColCount")) {
			matchColumnCount(request, response);
		} else if (validationType.equals("matchColumnDataTypes")) {
			matchColumnDataType(request, response);
		} else if (validationType.equals("rowByRowValiation")) {
			rowByRowValiation(request, response);
		} else if (validationType.equals("sampleValidation")) {
			sampleValidation(request, response);
		} else if (validationType.equals("outputValidation")) {
			outputValidation(request, response);
		} else if (validationType.equals("assertValueValidation")) {
			assertValueValidation(request, response);
		} else if (validationType.equals("resultCountValidation")) {
			resultCountValidation(request, response);
		} else if (validationType.equals("generateReport")) {
			response.getWriter().write("Validation Report is Ready and will be downloaded.");
		} else {
			response.getWriter().write("Error can not be handled. contact developer");
		}
	}

	private void createConnection(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (connection.equals("0")) {
			String sourceType = request.getParameter("sourceType");
			String targetType = request.getParameter("targetType");
			sourceDb = request.getParameter("sourceDbName");
			targetDb = request.getParameter("targetDbName");
			sourceUser = request.getParameter("sourceUser");
			targetUser = request.getParameter("targetUser");
			sourceTable = request.getParameter("sourceTable");
			targetTable = request.getParameter("targetTable");

			String result = "Connection established with source and target datasources.";

			response.getWriter().write("<h3>" + result + "</h3>");
		}else {
			response.getWriter().write("Already Connected");
		}

	}

	private void matchColumnCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.getWriter().write("Matching Column Count in source and destination tables");
	}

	private void matchColumnDataType(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.getWriter().write("Matching Column Datatypes in source and destination tables");
	}

	private void rowByRowValiation(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.getWriter().write("Row by row validation in source and destination tables");
	}

	private void sampleValidation(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String limit = request.getParameter("sampleLimit");
		response.getWriter().write("Sample validation in source and destination tables with sample size " + limit);
	}

	private void outputValidation(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String sourceCustomQry = request.getParameter("sourceSql");
		String targetCustomQry = request.getParameter("targetSql");
		response.getWriter().write("Validating output from given queries</br>" + "<b>Source Query:</b> "
				+ sourceCustomQry + "</br><b>Target Query:</b> " + targetCustomQry);
	}

	private void assertValueValidation(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String sourceCustomQry = request.getParameter("sourceSql");
		String targetCustomQry = request.getParameter("targetSql");
		String assertValue = request.getParameter("assertValue");
		String table = request.getParameter("table");
		response.getWriter()
				.write("Validating assert value of table '" + table + "' for the given assert value '" + assertValue
						+ "'<br>" + "<b>Source Query: </b>" + sourceCustomQry + "</br><b>Target Query:</b> "
						+ targetCustomQry);
	}

	private void resultCountValidation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sourceCustomQry = request.getParameter("sourceSql");
		String targetCustomQry = request.getParameter("targetSql");
		response.getWriter().write("Validating result count for the tables");
	}
}
