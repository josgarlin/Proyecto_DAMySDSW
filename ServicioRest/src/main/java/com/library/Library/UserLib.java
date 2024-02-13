package com.library.Library;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class UserLib implements Serializable{
	private static final long serialVersionUID = -1471468529482088988L;
	private String name;
	private String surname1;
	private String surname2;
	@Id private String dni;
	private String rol;
	private String phone;
	private String mail;
	private String password;
	
	public UserLib() {
		
	}
	public UserLib(String name, String surname1, String surname2, String dni,
			String rol, String phone, String mail, String password) {
		this.name = name;
		this.surname1 = surname1;
		this.surname2 = surname2;
		this.dni = dni;
		this.rol = rol;
		this.phone = phone;
		this.mail = mail;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname1() {
		return surname1;
	}

	public void setSurname1(String surname1) {
		this.surname1 = surname1;
	}

	public String getSurname2() {
		return surname2;
	}

	public void setSurname2(String surname2) {
		this.surname2 = surname2;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
