package com.library.Library;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;


import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Transactional
@RestController
class LibraryController {

  private final BookRepository book_repository;
  private final UserLibRepository userlib_repository;
  private final LoanRepository loan_repository;
  private final PenaltyRepository penalty_repository;
  private final  MailService mailService;
  private final LibraryPassRepository librarypass_repository;
  DateTimeFormatter date_formater = DateTimeFormatter.ofPattern("MM/dd/yyyy");
  
  LibraryController(BookRepository book_repository,  UserLibRepository userlib_repository, LoanRepository loan_repository, 
		  PenaltyRepository penalty_repository, MailService mailService, LibraryPassRepository librarypass_repository) {
    this.book_repository = book_repository;
    this.userlib_repository = userlib_repository;
    this.loan_repository = loan_repository;
    this.penalty_repository = penalty_repository;
    this.mailService = new MailService();
    this.librarypass_repository = librarypass_repository;
  }
  
  //* BOOKS *//
  
  // Get all
  @GetMapping("/books")
   public List<Book> get_all_books() {
    return book_repository.findAll();
  }

  @RequestMapping(value = "/book", params={"author","title","isbn" }, method = RequestMethod.GET)
  List <Book> getBook ( @RequestParam(value = "author", defaultValue = "") String author,
		  @RequestParam(value = "title", defaultValue = "") String title,
		  @RequestParam(value = "isbn", defaultValue = "") String isbn) throws UnsupportedEncodingException {
	  title = URLDecoder.decode(title, "UTF-8");
	  author = URLDecoder.decode(author, "UTF-8");
	  List <Book> list_books = new ArrayList<Book>();
	  if(!isbn.isEmpty()) {
		  list_books = book_repository.findByIsbn(isbn);
	  }
	  else {
		  if(!title.isEmpty() && author.isEmpty()) {
			  list_books =  book_repository.findByTitle(title);
		  }
		  else {
			  if(title.isEmpty() && !author.isEmpty()) {
				  list_books =  book_repository.findByAuthor(author);
			  }
			  if(!title.isEmpty() && !author.isEmpty()) {
				  list_books =  book_repository.findByAuthorAndTitle(author, title );
			  }
		  }
	  }
	  
	 
	  return list_books;
  }

  //* USERS *//
  
  //Check password
  @RequestMapping(value = "/user/check", params= {"dni", "password"},method = RequestMethod.GET )
  boolean checkPassword ( @RequestParam(value = "dni", defaultValue = "") String dni, 
		  @RequestParam(value = "password", defaultValue = "") String password) {
	  boolean result = false;
	  Optional<UserLib> optional_user = userlib_repository.findByDni(dni);
	  if(optional_user.isPresent() && 0 == optional_user.get().getPassword().compareTo(password)) {
			  result = true;
	  }
	  return result;
  }
  
  //Check password
  @RequestMapping(value = "/return/check", params= {"university", "password"},method = RequestMethod.GET )
  boolean checkLibraryPassword ( @RequestParam(value = "university", defaultValue = "") String university, 
		  @RequestParam(value = "password", defaultValue = "") String password) throws UnsupportedEncodingException {
	  university = URLDecoder.decode(university, "UTF-8");
	  System.out.println(university);
	  boolean result = false;
	  Optional<LibraryPass> optional_libpass = librarypass_repository.findByUniversity(university);
	  if(optional_libpass.isPresent() && 0 == optional_libpass.get().getPassword().compareTo(password)) {
			  result = true;
	  }
	  return result;
  }
  

  //Get UserLib
  @RequestMapping(value = "/user", params="dni", method = RequestMethod.GET )
  UserLib getUserLib ( @RequestParam(value = "dni", defaultValue = "") String dni) {
	  UserLib result =null;
	  Optional<UserLib> optional_user = userlib_repository.findByDni(dni);
	  if(optional_user.isPresent()) {
			  result = optional_user.get();
			  result.setPassword("");
	  }
	  return result;
  }
  
  //Post user
 // @PostMapping("/user" )
  @RequestMapping(value = "/user/register", params="password",  method = RequestMethod.POST )
  boolean postUserLib(@RequestBody UserLib UserLib,
		  @RequestParam(value = "password", defaultValue = "") String password) {
	  boolean result = false;
	  Optional<UserLib> optional_user = userlib_repository.findById(UserLib.getDni());
	  if(optional_user.get().getPassword().compareTo(password) == 0) {
		  userlib_repository.save(UserLib);
		  mailService.sendUpdateUserNotification(UserLib);
		  result=true;
	  }
    return result;
  }
  
  
  //* LOAN *//
   
  @RequestMapping(value = "/loan", params="dni", method = RequestMethod.GET )
  List <Loan> getLoans ( @RequestParam(value = "dni", defaultValue = "") String dni) {
	  List<Loan> list_loans = loan_repository.findByDni(dni);
	  return list_loans; 
  }
  
  //Post Loan
  @RequestMapping(value = "/loan", params={"dni", "isbn", "location"}, method = RequestMethod.POST )
  boolean postLoan ( @RequestParam(value = "dni", defaultValue = "") String dni, 
		  @RequestParam(value = "location", defaultValue = "") String location, 
		  @RequestParam(value = "isbn", defaultValue = "") String isbn) throws UnsupportedEncodingException {
	  boolean result = false;
	  if(penalty_repository.findByDni(dni).isPresent() == false) {
		  location = URLDecoder.decode(location, "UTF-8");
		  Optional<Book> optional_book = book_repository.findByIsbnAndLocation(isbn, location);
		  Optional <Loan> optional_loan = loan_repository.findByDniAndIsbn(dni,isbn);
		  if(optional_loan.isEmpty() && optional_book.isEmpty() != true && optional_book.get().getCopies() >= 1) {
			  Book book = optional_book.get();
			  LocalDate start_date = LocalDate.now();
		      Loan newLoan = new Loan(dni, isbn, location, date_formater.format(start_date) ,date_formater.format(start_date.plusDays(20)));
			  loan_repository.save(newLoan);
		      mailService.sendLoanNotification(userlib_repository.findByDni(dni).get(), newLoan, book);
			  int copies = book.getCopies();
			  book.setCopies(copies-1);
			  book_repository.save(book);
			  result =true;
		  } 
	  }
    return result;
  }
  
  //Delete Loan
  @RequestMapping(value = "/loan", params={"dni", "isbn"}, method = RequestMethod.DELETE  )
  boolean deleteLoan( @RequestParam(value = "dni", defaultValue = "") String dni, 
		  @RequestParam(value = "isbn", defaultValue = "") String isbn) {
	  boolean result =false;
	  Optional <Loan> optional_loan = loan_repository.findByDniAndIsbn(dni, isbn);
	  if(optional_loan.isPresent()) {
		  
		  Optional<Book> optional_book = book_repository.findByIsbnAndLocation(isbn, optional_loan.get().getLocation());
		  loan_repository.deleteByDniAndIsbn(dni, isbn);
		  if(optional_book.isPresent()) {
			  Book book = optional_book.get();
			  int copies = book.getCopies();
			  book.setCopies(copies+1);
			  book_repository.save(book);
			  result = true;
			  mailService.sendReturnNotification(userlib_repository.findByDni(dni).get(), optional_loan.get(), book);
		 }
		  
	  }
	  
    return result;
  }
  
//* PENALTY *//
  
  @RequestMapping(value = "/penalty", params="dni", method = RequestMethod.GET )
  Penalty getPenalty ( @RequestParam(value = "dni", defaultValue = "") String dni) {
	  Optional<Penalty> optional_penalty = penalty_repository.findByDni(dni);
	  Penalty result = null;
	  if(optional_penalty.isPresent() == true) {
		  result = optional_penalty.get();
	  }
	  return result;
  }
  
  @RequestMapping(value = "/penalty", params="dni", method = RequestMethod.POST)
  boolean postPenalty ( @RequestParam(value = "dni", defaultValue = "") String dni) {
	  Optional<Penalty> optional_penalty = penalty_repository.findByDni(dni);
	  boolean result = false;
	  if(optional_penalty.isPresent() != true) {
		  result = true;
		  Optional<Book> optional_book = book_repository.findById("9788434444959");
		  Book book = optional_book.get();
		  Penalty newPenalty = new Penalty(dni, date_formater.format(LocalDate.now()), date_formater.format(LocalDate.now().plusDays(20)));
		  Optional <UserLib> optinal_userlib = userlib_repository.findByDni(dni);
		  mailService.sendPenaltyNotification(optinal_userlib.get(), newPenalty,book);
	  }
	  return result;
  }

}