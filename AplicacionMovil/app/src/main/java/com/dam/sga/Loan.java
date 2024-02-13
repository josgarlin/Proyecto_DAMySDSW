package com.dam.sga;

import java.io.Serializable;

public class Loan implements Serializable{

	private static final long serialVersionUID = 1873794230125939260L;
	private String dni;
	private String isbn;
	private String location;
	private String start_date;
	private String end_date;
	

	public Loan(){
		
	}
	public Loan(String dni, String isbn, String location, String start_date, String end_date){
		this.dni = dni;
		this.isbn = isbn;
		this.location = location;
		this.start_date = start_date;
		this.end_date = end_date;
	}
	
	
	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}


	public String getIsbn() {
		return isbn;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	public String getStart_date() {
		return start_date;
	}


	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}


	public String getEnd_date() {
		return end_date;
	}


	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

}
