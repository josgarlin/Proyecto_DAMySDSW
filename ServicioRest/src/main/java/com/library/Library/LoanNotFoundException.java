package com.library.Library;

class LoanNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

LoanNotFoundException(String dni, String isbn) {
    super("Could not find the loan of the user with dni " + dni + " and the book with isbn " + isbn);
  }
}