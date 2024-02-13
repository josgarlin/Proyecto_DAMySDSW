package com.library.Library;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduleService {

	  private final UserLibRepository userlib_repository;
	  private final LoanRepository loan_repository;
	  private final PenaltyRepository penalty_repository;
	  private final BookRepository book_repository;
	  private final  MailService mailService;
	  DateTimeFormatter date_formater = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	  
	  public ScheduleService(UserLibRepository userlib_repository, LoanRepository loan_repository, 
			  PenaltyRepository penalty_repository,BookRepository book_repository, MailService mailService) {

	    this.userlib_repository = userlib_repository;
	    this.loan_repository = loan_repository;
	    this.penalty_repository = penalty_repository;
	    this.book_repository = book_repository;
	    this.mailService = new MailService();
	  }
	  
	  
	@Scheduled(cron = "@midnight")
	public void checkLoans() {
		 List<Loan> list_loans = loan_repository.findAll();
		 for (Loan loan: list_loans) {
			 LocalDate return_date = LocalDate.parse(loan.getEnd_date(), date_formater);
			  Optional <UserLib> optinal_userlib = userlib_repository.findByDni(loan.getDni());
			 if (return_date.isAfter(LocalDate.now().minusDays(2))){
				 if(return_date.isAfter(LocalDate.now())) {
					 Optional<Penalty> optional_penalty = penalty_repository.findById(loan.getDni());
					  if(optional_penalty.isPresent() == false) {
						  Penalty newPenalty = new Penalty(loan.getDni(), date_formater.format(LocalDate.now()), date_formater.format(LocalDate.now().plusDays(20)));	
						  penalty_repository.save(newPenalty);
						  Optional<Book> optional_book = book_repository.findById(loan.getIsbn());
						  if(optional_book.isPresent() == true) {
							  Book book = optional_book.get();
							  mailService.sendPenaltyNotification(optinal_userlib.get(), newPenalty,book);
						  }
						 
						  }
					  }
				 else {
					 Optional<Book> optional_book = book_repository.findById(loan.getIsbn());
					  if(optional_book.isPresent() == true) {
						  Book book = optional_book.get();
						  mailService.sendRememberNotification(optinal_userlib.get(), loan, book);
					  }
						  					  
				 }
	
			 }
		 }
				 
	}
	
	
	@Scheduled(cron = "@midnight")
	public void checkPenalties() {
		 List<Penalty> list_penalties = penalty_repository.findAll();
		 for (Penalty penalty: list_penalties) {
			 LocalDate end_date = LocalDate.parse(penalty.getEnd_date(), date_formater);
			 if (end_date.isBefore(LocalDate.now())){
				 penalty_repository.deleteByDni(penalty.getDni());
				 Optional <UserLib> optinal_userlib = userlib_repository.findByDni(penalty.getDni());
				 mailService.sendPenaltyDeleteNotification(optinal_userlib.get(), penalty);
			 }
		 }
				 
	}
	

}
