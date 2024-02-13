package com.library.Library;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class UserLibNotFoundAdvice {
  @ResponseBody
  @ExceptionHandler(UserLibNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String userNotFoundHandler(UserLibNotFoundException ex) {
    return ex.getMessage();
  }
}