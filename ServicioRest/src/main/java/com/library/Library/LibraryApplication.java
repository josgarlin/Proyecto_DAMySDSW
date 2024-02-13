package com.library.Library;


import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@Transactional 
public class LibraryApplication {
	
    public LibraryApplication() {
    }
	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

}
