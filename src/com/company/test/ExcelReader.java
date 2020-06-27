package com.company.test;

/**
 * Class to read excel file and store info to an array
 * 
 * @author Shook Kirk
 * 
 *version 1.0
 *
 *since 06/26/2020
 */


import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	// Variable to find info needed from first row of excel file
    static int firstName_COL;
    static int lastName_COL;
    static int ID_COL;
    static int email_COL;
    
    // Variable to hold data to make Student object
    static String f;
    static String l;
    static String i;
    static String e;
    
    public StudentArray studentList;;
    
    // Variables to hold info to create Student Object
    
    public ExcelReader(String file) throws Exception {
    	
        String excelFilePath = file;
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        
        // Create array of student objects
        studentList = new StudentArray();
        
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        
        DataFormatter format = new DataFormatter();
        
        // Loop to find the column that hold the data needed
        for(Row row : firstSheet) {
     	   for(int cn=0; cn<row.getLastCellNum(); cn++) {
     		   
     	       Cell cell = row.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
     	       
     	       if(cell.toString().equals("FirstName")) { // Finds first name column number
     	    	   firstName_COL = cn;
     	       }
     	       if(cell.toString().equals("LastName")) { // Finds last name column number
    	    	   lastName_COL = cn;
     	       }
     	       if(cell.toString().equals("StudentID")) { // Finds ID column number
     	    	   ID_COL = cn;
     	       }
     	       if(cell.toString().equals("QT EMAIL")) { // Finds email column number
     	    	   email_COL = cn;
     	       }
     	   }
     	}
        
        // Loop to read through cells and create Student object
        for(Row row : firstSheet) {
        	   for(int cn=0; cn<row.getLastCellNum(); cn++) {
        	       Cell cell = row.getCell(cn, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        	       if(cn == firstName_COL) {
         	    	   f = format.formatCellValue(cell);
         	       }
        	       if(cn == lastName_COL) {
         	    	   l = format.formatCellValue(cell);
         	       }
        	       if(cn == ID_COL) {
         	    	   i = format.formatCellValue(cell);
         	       }
        	       if(cn == email_COL) {
         	    	   e = format.formatCellValue(cell);
         	       }
        	   }
        	   if(f != null && l != null && i != null && e != null) {
        		   Student temp = new Student(f,l,i,e);
            	   studentList.addStudent(temp);
        	   }
        	}
        
        workbook.close();
        inputStream.close();
    }
 
}
