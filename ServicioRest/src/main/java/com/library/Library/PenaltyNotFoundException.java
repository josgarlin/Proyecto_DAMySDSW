package com.library.Library;

class PenaltyNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

PenaltyNotFoundException(String dni) {
    super("Could not find the penalty of the user with dni " + dni);
  }
}