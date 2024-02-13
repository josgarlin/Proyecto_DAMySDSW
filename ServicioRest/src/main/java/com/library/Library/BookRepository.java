package com.library.Library;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.transaction.annotation.Transactional;

interface BookRepository extends JpaRepository<Book, String> {
	@Transactional
	List <Book> findByIsbn (String isbn);
	@Transactional
	Optional <Book> findByIsbnAndLocation (String isbn,String location);
	@Transactional
	List <Book> findByAuthor(String author);
	@Transactional
	List <Book> findByTitle (String title);
	@Transactional
	List <Book> findByAuthorAndTitle(String author, String title);	
}