package com.library.Library;

class UserLibNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

UserLibNotFoundException(String dni) {
    super("Could not find user with dni " + dni);
  }
}