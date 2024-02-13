package com.dam.sga;

import java.io.Serializable;

public class Penalty implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6745980706923338491L;
	private String dni = "";
	private String start_date = "";
	private String end_date = "";
	
	public Penalty() {
		
	}
	public Penalty(String dni, String start_date, String end_date) {
		this.dni = dni;
		this.start_date = start_date;
		this.end_date = end_date;
	}
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
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

}
