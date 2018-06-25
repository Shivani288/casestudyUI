package com.impetus.mysqlValidator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateReport {
	public void writeToFile(String content) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			File file = new File("C:\\Users\\verma\\eclipse-workspace\\casestudy\\src\\main\\resources\\ValidationReport.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			// true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			bw.write(content);
			bw.newLine();
			//System.out.println("Report saved to file");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
