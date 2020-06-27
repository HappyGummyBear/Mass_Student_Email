package com.company.test;

/**
 * Student class to hold info read from excel file
 * 
 * @author Shook Kirk
 * 
 *version 1.0
 *
 *since 06/26/2020
 */

public class Student {
	private String fName;
	private String lName;
	private String ID;
	private String email;
	
	// Constructor
	Student(){}
	Student(String f, String l, String e){
		fName = f;
		lName = l;
		email = e;
	}
	Student(String f, String l, String i, String e){
		fName = f;
		lName = l;
		ID = i;
		email = e;
	}
	
	// Setters and getters
	String getFullName() {
		return fName+" "+lName;
	}
	String getfName() {
		return fName;
	}
	String getlName() {
		return lName;
	}
	String getID() {
		return ID;
	}
	String getEmail() {
		return email;
	}
	void setfName(String f) {
		fName = f;
	}
	void setlName(String l) {
		lName = l;
	}
	void setID(String i) {
		ID = i;
	}
	void setEmail(String e) {
		email = e;
	}
	
	// toString method to display student object info
	public String toString() {
		return ID+" - "+fName+" - "+lName+" - "+email+"\n";
	}
	
	
}
