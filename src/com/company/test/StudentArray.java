package com.company.test;

/**
 * Class create array of student objects
 * 
 * @author Shook Kirk
 * 
 *version 1.0
 *
 *since 06/26/2020
 */

public class StudentArray {
	// Variables to keep track for student array
	public int count = 0;
	public int size = 50;
	public Student[] array;
	
	// Constructor
	StudentArray(int s) {
		size = s;
		array = new Student[size];
		for(int i = 0; i < size; i++) {
			array[i] = new Student(); // Initialize array
		}
	}
	StudentArray() {
		size = 50;
		array = new Student[size];
		for(int i = 0; i < size; i++) {
			array[i] = new Student(); // Initialize array
		}
	}
	
	// Setters and getters
	void setSize(int i) {
		size = i;
	}
	public int getCount() {
		return count;
	}
	int getSize() {
		return size;
	}
	
	// Method to add student to the array
	public void addStudent(Student s) {
		if(count == size-1) {
			growArray(); // If array full, grow method is called
		}
		array[count] = s;
		++count;
	}
	
	// Method to grow array if full
	public void growArray() {
		StudentArray temp = new StudentArray(size*2);
		for(int i = 0; i<=count; i++) {
			temp.array[i] = this.array[i];
		}
		size = temp.size;
		array = temp.array;
	}
	
	// Method to print student array to console
	public void print(){
		for(int i = 0;i<count;i++) {
			System.out.print(array[i].toString());
		}
	}
}
