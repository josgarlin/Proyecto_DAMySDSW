package com.library.Library;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

interface LoanRepository extends JpaRepository<Loan, String> {
	@Transactional
	List <Loan> findByDni(String dni);
	@Transactional
	int deleteByDniAndIsbn(String dni, String isbn);
	@Transactional
	Optional <Loan> findByDniAndIsbn(String dni, String isbn);
	@Transactional
	Optional <Loan> findByDniAndIsbnAndLocation(String dni, String isbn, String location);
}