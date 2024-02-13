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
public class Book implements Serializable{
	private static final long serialVersionUID = 5495279000560771404L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private long id;
	private String title;
	private String author;
	private String description;
	private String publishedDate;
	private String publisher;
	private String imageLinks; 
	private String buyLink;
	private  String isbn;
	private String location;
	private String	shelve;
	private int copies;
	
	public Book () {
	}
    public Book(String title, String author, String description, String publishedDate, String publisher,
     String imageLinks, String buyLink, String location, int copies, String isbn, String shelve) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
        this.imageLinks = imageLinks;
        this.buyLink = buyLink;
        this.location = location;
        this.copies = copies;
        this.setShelve(shelve);
        this.isbn=isbn;
    }
    
    public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getImageLinks() {
		return imageLinks;
	}
	public void setImageLinks(String imageLinks) {
		this.imageLinks = imageLinks;
	}
	public String getBuyLink() {
		return buyLink;
	}
	public void setBuyLink(String buyLink) {
		this.buyLink = buyLink;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getCopies() {
		return copies;
	}
	public void setCopies(int copies) {
		this.copies = copies;
	}
	public String getShelve() {
		return shelve;
	}
	public void setShelve(String shelve) {
		this.shelve = shelve;
	}


	



}
