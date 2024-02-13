package com.library.Library;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class PenaltyNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(PenaltyNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String penaltyNotFoundHandler(PenaltyNotFoundException ex) {
    return ex.getMessage();
  }
}