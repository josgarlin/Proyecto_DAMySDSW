package com.library.Library;

class BookNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

BookNotFoundException(String isbn) {
    super("Could not find the book with isbn " + isbn);
  }
}