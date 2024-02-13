package com.library.Library;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import org.hibernate.annotations.DynamicUpdate;

//Clase que define un tipo Libro
@Entity
@DynamicUpdate
public class LibraryPass implements Serializable{
	private static final long serialVersionUID = 5495279000560771404L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private long id;
	private String university;
	private String password;
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}